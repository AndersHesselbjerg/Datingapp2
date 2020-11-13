package com.example.demo.presentation;

import com.example.demo.data.FileHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Scope("Singleton")
public class FileUploadController {
    private final FileHandler fileHandler;

    public FileUploadController(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        fileHandler.saveFile(uploadDir, fileName, multipartFile);
    }

    public void uploadAsPath(MultipartFile file) {
        fileHandler.uploadAsPath(file);
    }
}
