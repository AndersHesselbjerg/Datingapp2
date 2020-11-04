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


        public int writeColumn(String aUrl, ArrayList<String> aLst) {
            String insstr = "INSERT INTO user (username, password, first_name, last_name, credit_info, phone_number, email, description, tags, time_of_registry) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // insert string
            PreparedStatement preparedStatement;
            int res = 0;
            String lineln = "";
            for (String line : aLst) {
                try {
                    preparedStatement = connection.prepareStatement(insstr);
                    preparedStatement.setString(1, aUrl);
                    if (line.length() > 0) {
                        lineln = "" + line.length();
                        if ((line.length() < 150) && (line.length() > 0)) {
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
                            System.out.println("Har saved rowcount=" + res + " url=" + aUrl);
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
