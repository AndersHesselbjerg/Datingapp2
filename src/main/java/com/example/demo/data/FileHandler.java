package com.example.demo.data;

//import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class FileHandler {
    private final Connection connection;

    FileHandler(Connector connector) {
        this.connection = connector.getConnection();
    }

    private final String IMAGE_PATH = "src/main/resources/static/img/uploaded-images";

    /*public FileHandler(MultipartFile multi) {
        String originalFilenamename = multi.getOriginalFilename();
       // File file = new File("C:\\tmp\\"+originalFilenamename);
        File file = new File(originalFilenamename);

        try {
            multi.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void uploadImageToDatabase(MultipartFile file) {
        try {
            byte[] fileAsBytes = file.getBytes();
            Blob fileAsBlob = new SerialBlob(fileAsBytes);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO mydb.img VALUES(?,?)");
            ps.setBlob(1,fileAsBlob);
            ps.setString(2,"Description");
            System.out.println(ps.execute());
        }
        catch(SQLException e){
            System.out.println("bad connection");
            e.printStackTrace();
            System.out.println(e.getSQLState());
        }
        catch(IOException e){
            System.out.println("File not found");
        }
    }

    public void uploadAsPath(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {

       /*     PreparedStatement ps = establishConnection().prepareStatement("insert into my_company.imgPath (description, path) VALUES(?,?)");
            ps.setString(1,fileName);
            ps.setString(2, "description");
            ps.execute();*/
            saveFile(IMAGE_PATH,fileName,file);
        }
        catch(/*SQLException | */IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    /*public List<File> getAllImages(){
        //ArrayList<File> files = new ArrayList<File>();
        Path p = Paths.get(IMAGE_PATH);
        File file = new File(IMAGE_PATH);
        Collection<File> files = FileUtils.listFiles(file,null,null);
        for (File f2: files) {
            System.out.println(f2.getName());
        }
        return (List) files;

    }*/
}
