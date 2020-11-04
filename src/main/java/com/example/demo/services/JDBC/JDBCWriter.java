package com.example.demo.services.JDBC;

import com.example.demo.models.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

@Component
@Scope("Singleton")
public class JDBCWriter {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public void setConnection(Connection connection) { this.connection = connection; }

    public void addUser(User user) {}

    public void editUser(User user) {}

    public void removeUser(int user_id) {}

    public void writeMessage(int chat_id, int sender_id) {}

    public void editMessage(int chat_id, int message_id) {}

    public void deleteMessage(int chat_id, int message_id) {}

    public void addToPair(int pair_id, int user_id) {}

    public void removeFromPair(int pair_id, int user_id) {}








    // udkast til writer
        public boolean setConnection() {
            final String url = "jdbc:mysql://localhost:3306/urlread?serverTimezone=UTC";
            boolean bres = false;
            try {
                connection = DriverManager.getConnection(url, "yrsa", "x");
                bres = true;
            } catch (SQLException ioerr) {
                System.out.println("Vi fik IKKE connection=" + ioerr.getMessage());
            }
            return bres;
        }

        public int deleteColumn(String aUrl, String aWord) {
            String delStr = "DELETE FROM urlreads where url like ? and line like ?";
            PreparedStatement preparedStatement;
            int res = -1;
            try {
                preparedStatement = connection.prepareStatement(delStr);
                preparedStatement.setString(1, "%" + aUrl + "%");
                preparedStatement.setString(2, "%" + aWord + "%");
                res = preparedStatement.executeUpdate();
                System.out.println("Line deleted=" + res);
            } catch (SQLException sqlerr) {
                System.out.println("Error in delete=" + sqlerr.getMessage());
            }
            return res;
        }

        public Vector<String> getColumn(String aUrl, String aWord) {
            String searchStr = "SELECT left(line, 50) as line FROM urlreads where url like ? and line like ? LIMIT 20";
            PreparedStatement preparedStatement;
            Vector<String> v1 = new Vector<>();
            try {
                preparedStatement = connection.prepareStatement(searchStr);
                preparedStatement.setString(1, "%" + aUrl + "%");
                preparedStatement.setString(2, "%" + aWord + "%");
                System.out.println(searchStr);
                ResultSet resset = preparedStatement.executeQuery();
                String str1;
                while (resset.next()) {
                    str1 = "" + resset.getObject("line");
                    v1.add(str1);
                }
            } catch (SQLException sqlerr) {
                System.out.println("Error in select=" + sqlerr.getMessage());
            }
            return v1;
        }


        public int writeColumn(String aUlr, ArrayList<String> aLst) {
            String insstr = "INSERT INTO urlreads(url, line, linelen, medtext) values (?, ?, ?, ?)";
            PreparedStatement preparedStatement;
            int res = 0;
            String lineln = "";
            for (String line : aLst) {
                try {
                    preparedStatement = connection.prepareStatement(insstr);
                    preparedStatement.setString(1, aUlr);
                    if (line.length() > 0) {
                        lineln = "" + line.length();
                        if ((line.length() < 15000) && (line.length() > 0)) {
                            preparedStatement.setString(2, line);
                            preparedStatement.setString(3, "" + lineln);
                            preparedStatement.setString(4, " ");
                        } else {
                            preparedStatement.setString(2, " ");
                            preparedStatement.setString(3, "" + lineln);
                            preparedStatement.setString(4, line);
                        }
                        int rowcount = preparedStatement.executeUpdate();
                        res = res + rowcount;
                        if (res % 100 == 0) {
                            System.out.println("Har saved rowcount=" + res + " url=" + aUlr);
                        }
                    } //line.length > 0
                } catch (SQLException sqlerr) {
                    String errmsg = sqlerr.getMessage();
                    System.out.println("Fejl i INSERT=" + errmsg + " len=" + lineln);
                }
            }
            return res;
        }
    }
