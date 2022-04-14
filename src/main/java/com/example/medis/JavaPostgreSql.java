package com.example.medis;

import javax.xml.transform.Result;
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

        //(id,fullname, username, password, email, phone, position, birthdate, created_at, updated_at, deleted(boolean))
        String query = "INSERT INTO users VALUES(default, ?, ?, ?, ?, ?, cast(? as position_enum), ?, now(), now(), false);";

        {
            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                //adding values
                preparedStatement.setString(1, fullname);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, phone);
                preparedStatement.setString(6, position);
                preparedStatement.setDate(7,  getDate(birthdate));
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

    // date modification for insert into query
    private static java.sql.Date getDate(String birthdate) throws ParseException {
        Date t = new SimpleDateFormat("yyyy-MM-dd").parse(birthdate);
        java.sql.Date sqlDate = new java.sql.Date(t.getTime());
        return sqlDate;
    }

    // login checker
    public static boolean checkUser(String email, String password){
        String url = "jdbc:postgresql://postgresql.r1.websupport.sk:5432/medis";
        String user = "medis";
        String pswd = "Uu39FC4W#Z";

        String query = "SELECT id, fullname, username, deleted, position FROM users WHERE email=? and password=?";

        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

//            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                System.out.println("User not found in DB!");
                return false;
            }
            else {
                List<User> result = new ArrayList<>();
                while (resultSet.next()) {
                    boolean deleted = resultSet.getBoolean("deleted");
                    if (deleted){
                        System.out.println("User was deleted before");
                        return false;
                    }
                    long id = resultSet.getLong("id");
                    String fullname = resultSet.getString("fullname");
                    String username = resultSet.getString("username");
                    String position = resultSet.getString("position");


                    User obj = new User();
                    obj.setId(id);
                    obj.setFullname(fullname);
                    obj.setPosition(position);
                    obj.setUsername(username);
                    result.add(obj);
//                    System.out.println(obj.getFullname());
//                    System.out.println(obj.getUsername());
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // update application users
    public static String updateUser(int id, String username, String fullname, String password, String email, String phone, String position, String birthdate){
        String url = "jdbc:postgresql://postgresql.r1.websupport.sk:5432/medis";
        String user = "medis";
        String pswd = "Uu39FC4W#Z";

        // checking if user with this username exist

        String query = "SELECT * FROM users WHERE id=? and username=?;";


        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,username);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                return "User with this username not exists!";
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // update the user

        String query1 = "UPDATE users SET fullname=?, password=?, email=?, phone=?, birthdate=?, position=cast(? AS position_enum), updated_at=now()  WHERE id=? and username=?;";

        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query1);

            preparedStatement.setString(1,fullname);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,phone);
            preparedStatement.setDate(5,getDate(birthdate));
            preparedStatement.setString(6,position);
            preparedStatement.setInt(7,id);
            preparedStatement.setString(8,username);
            System.out.println(preparedStatement);
            preparedStatement.execute();
            return "Succesfully updated!";

        } catch (SQLException e) {
            e.printStackTrace();
            return "SQLException: " + e;
        } catch (ParseException e) {
            e.printStackTrace();
            return "ParseException: " + e;
        }
    }



    public static String deleteUser(int id){
        String url = "jdbc:postgresql://postgresql.r1.websupport.sk:5432/medis";
        String user = "medis";
        String pswd = "Uu39FC4W#Z";

        // check if the user exist

        String query = "SELECT * FROM users WHERE id=?;";

        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                return "User with this username not exists!";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query1 = "UPDATE USERS SET deleted=true, updated_at=now() WHERE id=?";


        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query1);

            preparedStatement.setString(1,String.valueOf(id));
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return "User deleted!";
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

