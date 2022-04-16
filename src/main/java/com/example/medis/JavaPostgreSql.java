package com.example.medis;

import com.example.medis.Entities.Patient;
import com.example.medis.Entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class JavaPostgreSql {
    private static final String url = "jdbc:postgresql://postgresql.r1.websupport.sk:5432/medis";
    private static final String user = "medis";
    private static final String pswd = "Uu39FC4W#Z";

    public static void createUser(String fullname, String username, String password, String email,
                                  String phone, String position, String birthdate){


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
                System.out.println("Succesfully created user!");
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

        String query = "SELECT id, fullname, username, deleted, position FROM users WHERE email=? and password=?";

        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            System.out.println(preparedStatement);
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
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // update application users
    public static String updateUser(long id, String username, String fullname, String password, String email, String phone, String position, String birthdate){
        if (!isUserExist(id)){
            return "User with this id not exists!";
        }
        else {
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
                preparedStatement.setLong(7,id);
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
    }

    public static String deleteUser(long id){
        if (!isUserExist(id)){
            return "User with this id not exists!";
        }
        else {
            String query1 = "UPDATE USERS SET deleted=true, updated_at=now() WHERE id=?";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query1);

                preparedStatement.setString(1, String.valueOf(id));
                System.out.println(preparedStatement);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "User deleted!";
        }
    }

    public static String createPatient(String first_name, String sure_name, String insurance_co, String birthdate, String sex,
                                     String blood_group, String address, String phone, String email){

        String query = "INSERT INTO patients VALUES(default, ?, ?, cast(? as insurance_enum), ?, " +
                "cast(? as sex_enum), cast(? as blood_enum), ?, ?, now(), now(), false, ?);";

        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, sure_name);
            preparedStatement.setString(3, insurance_co);
            preparedStatement.setDate(4,  getDate(birthdate));
            preparedStatement.setString(5, sex);
            preparedStatement.setString(6, blood_group);
            preparedStatement.setString(7, address);
            preparedStatement.setString(8, phone);
            preparedStatement.setString(9, email);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            return "Succesfully created patient!";


        } catch (SQLException e) {
            e.printStackTrace();
            return "SQLException: " + e;
        } catch (ParseException e) {
            e.printStackTrace();
            return "ParseException: " + e;
        }

    }

    public static String updatePatient(long id, String first_name, String sure_name, String insurance_co, String birthdate, String sex,
                                       String blood_group, String address, String phone, String email){

        if (!isPatientExist(id)){
            return "Patient with this id not exists!";
        }
        else{
            String query1 = "UPDATE patients SET first_name=?, sure_name=?, insurance_co=cast(? as insurance_enum), birthdate=?, sex=cast(? as sex_enum), blood_group=cast(? as blood_enum), phone=?, updated_at=now()  WHERE id=?;";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query1);

                preparedStatement.setString(1, first_name);
                preparedStatement.setString(2, sure_name);
                preparedStatement.setString(3, insurance_co);
                preparedStatement.setDate(4,  getDate(birthdate));
                preparedStatement.setString(5, sex);
                preparedStatement.setString(6, blood_group);
                preparedStatement.setString(7, address);
                preparedStatement.setString(8, phone);

                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
                return "Succesfully updated patient with id=" + id + " !";


            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException: " + e;
            } catch (ParseException e) {
                e.printStackTrace();
                return "ParseException: " + e;
            }
        }
    }

    public static String deletePatient(long id){

        if (!isPatientExist(id)){
            return "Patient with this id not exists!";
        }
        else{
            String query1 = "UPDATE patients SET deleted=true, updated_at=now() WHERE id=?";


            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query1);

                preparedStatement.setString(1,String.valueOf(id));
                System.out.println(preparedStatement);

            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException: " + e;
            }
            return "Patient deleted!";
        }
    }

    public static String creteAppointment(String title, String description, String start_time, String end_time, long patient_id,
                                          long doctor_id, long created_by){

        String query = "INSERT INTO appointments VALUES(default, ?, ?, ?, ?, ?, ?, now(), now(),  false, ?);";


        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setDate(3, getDate(start_time));
            preparedStatement.setDate(4,  getDate(end_time));
            preparedStatement.setLong(5, patient_id);
            preparedStatement.setLong(6, doctor_id);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            return "Succesfully created appointment!";


        } catch (SQLException e) {
            e.printStackTrace();
            return "SQLException: " + e;
        } catch (ParseException e) {
            e.printStackTrace();
            return "ParseException: " + e;
        }
    }

    public static String updateAppointment(long id, String title, String description, String start_time,
                                           String end_time, long patient_id, long doctor_id){
        if(!isAppointmentExist(id)){
            return "Appointment with this id not exists!";
        }
        else {
            String query = "UPDATE appointments SET title=?, description=?, start_time=?, end_time=?, patient_id=?, doctor_id=?, updated_at=now()";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setDate(3, getDate(start_time));
                preparedStatement.setDate(4,  getDate(end_time));
                preparedStatement.setLong(5, patient_id);
                preparedStatement.setLong(6, doctor_id);

                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
                return "Succesfully updated appointment!";


            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException: " + e;
            } catch (ParseException e) {
                e.printStackTrace();
                return "ParseException: " + e;
            }
        }

    }

    public static String deleteAppointment(long id){

        if(!isAppointmentExist(id)){
            return "Appointment with this id not exists!";
        }
        else{
            String query = "UPDATE appointments SET deleted=true WHERE id=?";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setLong(1, id);

                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
                return "Succesfully deleted appointment!";


            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException: " + e;
            }
        }

    }

    private static Boolean isAppointmentExist(long id) {
        String query = "SELECT * FROM appointments WHERE id=?;";
        return getaBoolean(id, query);
    }

    private static Boolean isPatientExist(long id) {
        String query = "SELECT * FROM patients WHERE id=?;";
        return getaBoolean(id, query);
    }
    private static Boolean isUserExist(long id) {
        String query = "SELECT * FROM users WHERE id=?;";
        return getaBoolean(id, query);
    }

    private static Boolean isRecordExist(long id) {
        String query = "SELECT * FROM records WHERE id=?;";
        return getaBoolean(id, query);
    }
    private static Boolean isPrescriptionExist(long id) {
        String query = "SELECT * FROM prescriptions WHERE id=?;";
        return getaBoolean(id, query);
    }

    private static Boolean getaBoolean(long id, String query) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next() || resultSet.getBoolean("deleted")){
                return false;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String createRecord(String title, String description, String execute_date, String notes, long patient_id, long doctor_id){

        String query = "INSERT INTO appointments VALUES(default, ?, ?, ?, ?, ?, ?, now(), now(),  false);";

        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setDate(3, getDate(execute_date));
            preparedStatement.setString(4,  notes);
            preparedStatement.setLong(5, patient_id);
            preparedStatement.setLong(6, doctor_id);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            return "Succesfully created record!";


        } catch (SQLException e) {
            e.printStackTrace();
            return "SQLException: " + e;
        } catch (ParseException e) {
            e.printStackTrace();
            return "ParseException: " + e;
        }

    }

    public static String updateRecord(long id, String title, String description, String execute_date, String notes, long patient_id, long doctor_id){
        if(!isRecordExist(id)){
            return "Record with this id not exists!";
        }
        else{
            String query = "UPDATE appointments SET title=?, description=?, date_execute=?, notes=?, patient_id=?, doctor_id=?, updated_at=now()";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setDate(3, getDate(execute_date));
                preparedStatement.setString(4,  notes);
                preparedStatement.setLong(5, patient_id);
                preparedStatement.setLong(6, doctor_id);

                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
                return "Succesfully updated record!";


            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException: " + e;
            } catch (ParseException e) {
                e.printStackTrace();
                return "ParseException: " + e;
            }
        }
    }

    public static String deleteRecord(long id){

        if(!isRecordExist(id)){
            return "Record with this id not exists!";
        }
        else{
            String query = "UPDATE records SET deleted=true WHERE id=?";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setLong(1, id);

                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
                return "Succesfully deleted record!";


            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException: " + e;
            }
        }

    }

    public static String createPrescription(String title, String description, String drug, String expiration_date, long patient_id, long doctor_id, String notes){

        String query = "INSERT INTO prescriptions VALUES(default, ?, ?, ?, ?, ?, ?, ?, now(), now(),  false);";

        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, drug);
            preparedStatement.setDate(4,  getDate(expiration_date));
            preparedStatement.setLong(5, patient_id);
            preparedStatement.setLong(6, doctor_id);
            preparedStatement.setString(7, notes);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            return "Succesfully created record!";


        } catch (SQLException e) {
            e.printStackTrace();
            return "SQLException: " + e;
        } catch (ParseException e) {
            e.printStackTrace();
            return "ParseException: " + e;
        }

    }

    public static String updatePrescription(long id, String title, String description, String drug, String expiration_date, long patient_id, long doctor_id, String notes){
        if(!isPrescriptionExist(id)){
            return "Prescription with this id not exists!";
        }
        else{
            String query = "UPDATE prescription SET title=?, description=?, drug=?, expiration_date=?, notes=?, patient_id=?, doctor_id=?, updated_at=now()";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, drug);
                preparedStatement.setDate(4, getDate(expiration_date));
                preparedStatement.setString(5,  notes);
                preparedStatement.setLong(6, patient_id);
                preparedStatement.setLong(7, doctor_id);

                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
                return "Succesfully updated prescription!";


            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException: " + e;
            } catch (ParseException e) {
                e.printStackTrace();
                return "ParseException: " + e;
            }
        }
    }

    public static String deletePrescription(long id){

        if(!isPrescriptionExist(id)){
            return "Prescription with this id not exists!";
        }
        else{
            String query = "UPDATE prescription SET deleted=true WHERE id=?";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setLong(1, id);

                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
                return "Succesfully deleted prescription!";


            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException: " + e;
            }
        }

    }

    public static List<User> getAllUsers(){

        String query = "SELECT * from users;";
        List<User> result = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(createUserFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ObservableList<Patient> getAllPatients(){
        String query = "SELECT * from patients;";
        ObservableList<Patient> result = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(createPatientFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Patient createPatientFromResultSet(ResultSet resultSet) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setFirst_name(resultSet.getString("first_name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setPhone(resultSet.getString("phone"));
        //obj.setBirth_date(resultSet.getDate("birth_date"));
        patient.setCreated_at(resultSet.getObject("created_at", LocalDateTime.class));
        patient.setUpdated_at(resultSet.getObject("updated_at", LocalDateTime.class));
        patient.setDeleted(resultSet.getBoolean("deleted"));
        patient.setBirth_number(resultSet.getLong("identification_number"));

//        Button button = new Button();
//        button.setText("Open");
//        patient.setPatient_info(button);

        return patient;

    }

    private static User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User obj = new User();
        obj.setId(resultSet.getLong("id"));
        obj.setFullname(resultSet.getString("fullname"));
        obj.setUsername(resultSet.getString("username"));
        obj.setEmail(resultSet.getString("email"));
        obj.setPhone(resultSet.getString("phone"));
        obj.setPosition(resultSet.getString("position"));
        obj.setBirthdate(LocalDateTime.from(resultSet.getTimestamp("birthdate").toLocalDateTime()));
        obj.setCreated_at(LocalDateTime.from(resultSet.getTimestamp("created_at").toLocalDateTime()));
        obj.setUpdated_at(LocalDateTime.from(resultSet.getTimestamp("updated_at").toLocalDateTime()));
        obj.setDeleted(resultSet.getBoolean("deleted"));
        return obj;
    }

    public static List<Patient> filterPatients(String filterWord) {

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        String query = "";
        List<Patient> result = new ArrayList<>();

        if (pattern.matcher(filterWord).matches()){
            query = "SELECT * FROM patients WHERE identification_number=?";

        } else {
            query = "SELECT * FROM patients WHERE email=?";
        }
        try {

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, filterWord);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(createPatientFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static String filterRecords(Long patientId) {
        try {
            String query = "SELECT * FROM patients WHERE patient_id=?";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, patientId);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            return "Succesfully created appointment!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "SQLException: " + e;
        }

    }

    public static String filterAppointments(Long patientId) {
        try {
            String query = "SELECT * FROM patients WHERE patient_id=?";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, patientId);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            return "Succesfully created appointment!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "SQLException: " + e;
        }

    }

    public static ObservableList<User> getUser(Long patientId) {

        ObservableList<User> result = FXCollections.observableArrayList();;
        try {
            String query = "SELECT * FROM users WHERE id=?";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, patientId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(createUserFromResultSet(resultSet));
            }
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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

