package com.example.medis;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.*;


public class JavaPostgreSql {

    public static void createUser(String fullname, String username, String password, String email,
                                  String phone, String position, String birthdate){

        String url = "jdbc:postgresql://postgresql.r1.websupport.sk:5432/medis";
        String user = "medis";
        String pswd = "Uu39FC4W#Z";


//        String query = "SELECT * from public.users;";
        //(id,fullname, username, password, email, phone, position, birthdate, created_at, updated_at, deleted(boolean))
        String query = "INSERT INTO users VALUES(default, ?, ?, ?, ?, ?, ?, ?, ?, ?, false);";



        {
            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                SimpleDateFormat ft =  new SimpleDateFormat("yyyy-MM-dd");
                Date t = ft.parse(birthdate);

                preparedStatement.setString(1, fullname);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, phone);
                preparedStatement.setObject(6, Position.valueOf(position));
                preparedStatement.setString(7,  t.toString());
                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
                System.out.println("Succesfull!");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e){
                System.out.println("Wrong fate format");
            }
        }

    }

//    public static void main(String[] args){
//
//        String url = "jdbc:postgresql://postgresql.r1.websupport.sk:5432/medis";
//        String user = "medis";
//        String pswd = "Uu39FC4W#Z";
//
//        List<User> result = new ArrayList<>();
//
//        String SQL_SELECT = "Select * from users";
//
//        try (Connection conn = DriverManager.getConnection(url, user, pswd);
//             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//
//                long id = resultSet.getLong("id");
//                String name = resultSet.getString("fullname");
//                Timestamp createdDate = resultSet.getTimestamp("created_at");
//
//                User obj = new User();
//                obj.setId(id);
//                obj.setFullname(name);
//                // Timestamp -> LocalDateTime
//                obj.setCreated_at(createdDate.toLocalDateTime());
//                result.add(obj);
//                System.out.println(obj.getFullname());
//
//            }
//            result.forEach(x -> System.out.println(x));
//
//        } catch (SQLException e) {
//            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Exception: " + e);
//        }
//    }
}

