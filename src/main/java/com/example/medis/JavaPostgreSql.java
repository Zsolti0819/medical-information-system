package com.example.medis;

import com.example.medis.Entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;


public class JavaPostgreSql {
    private static final String url = "jdbc:postgresql://postgresql.r1.websupport.sk:5432/medis";
    private static final String user = "medis";
    private static final String pswd = "Uu39FC4W#Z";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String hashPass(String password) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashbytes);
    }

    private static String bytesToHex(byte[] hashbytes) {
        char[] hexChars = new char[hashbytes.length * 2];
        for (int j = 0; j < hashbytes.length; j++) {
            int v = hashbytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void createUser(String first_name, String last_name, String username, String password, String email,
                                  String phone, String position, String birthdate){


        String query = "INSERT INTO users VALUES(default, ?, ?, ?, ?, ?, ?, cast(? as position_enum), ?, now(), now(), false);";

        {
            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                //adding values
                preparedStatement.setString(1, first_name);
                preparedStatement.setString(2, last_name);
                preparedStatement.setString(3, username);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, phone);
                preparedStatement.setString(7, position);
                preparedStatement.setDate(8,  getDate(birthdate));
                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
                System.out.println("Succesfully created user!");
                GeneralLogger.log(Level.INFO, "REGISTER: User " + email + " created" );
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e){
                System.out.println("Wrong date format");
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

        String query = "SELECT id, first_name, last_name, username, deleted, position FROM users WHERE email=? and password=? and deleted=false;";

        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,email);
            preparedStatement.setString(2,hashPass(password));

            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                System.out.println("User not found in DB!");
                GeneralLogger.log(Level.WARNING, "LOGIN | USER | FAILED: Unsuccessful login | " + email + " | " + password );
                return false;
            }
            else {
                List<User> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(createUserFromResultSet(resultSet));
                }
                GeneralLogger.log(Level.INFO, "LOGIN | USER: User " + email + " logged in" );
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    // update application users
    public static String updateUser(long id, String username, String first_name, String last_name, String password, String email, String phone, String position, String birthdate){
        if (!isUserExist(id)){
            GeneralLogger.log(Level.WARNING, "USER | UPDATE | FAILED: User " + email + " not found" );
            return "User with this id not exists!";
        }
        else {
            String query1 = "UPDATE users SET first_name=?, last_name=?, password=?, email=?, phone=?, birthdate=?, position=cast(? AS position_enum), updated_at=now()  WHERE id=? and username=?;";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query1);

                preparedStatement.setString(1,first_name);
                preparedStatement.setString(2,last_name);
                preparedStatement.setString(3,password);
                preparedStatement.setString(4,email);
                preparedStatement.setString(5,phone);
                preparedStatement.setDate(6,getDate(birthdate));
                preparedStatement.setString(7,position);
                preparedStatement.setLong(7,id);
                preparedStatement.setString(9,username);
                System.out.println(preparedStatement);
                int res = preparedStatement.executeUpdate();
                System.out.println("Succesfully updated " + res +" row!");
                GeneralLogger.log(Level.INFO, "USER | UPDATE: User " + email + " updated" );
                return "Succesfully updated user!";

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
                int res = preparedStatement.executeUpdate();
                String email = getEmailByUserId(id);
                GeneralLogger.log(Level.INFO, "PATIENT | DELETE: User " + email + " deleted" );
                System.out.println("Succesfully updated " + res +" row!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "User deleted!";
        }
    }

    public static String createPatient(String first_name, String sure_name, String insurance_co, String birthdate, String sex,
                                     String blood_group, String address, String phone, String email, String birth_id){

        String query = "INSERT INTO patients VALUES(default, ?, ?, cast(? as insurance_enum), ?, " +
                "cast(? as sex_enum), cast(? as blood_enum), ?, ?, now(), now(), false, ?, ?);";

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
            preparedStatement.setString(10, birth_id);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            GeneralLogger.log(Level.INFO, "PATIENT | CREATE: Patient " + email + " created" );
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
                                       String blood_group, String address, String phone, String email, String identification_number){

        if (!isPatientExist(id)){
            GeneralLogger.log(Level.WARNING, "PATIENT | UPDATE | FAILED: Patient " + email + " not found" );
            return "Patient with this id not exists!";
        }
        else{
            String query1 = "UPDATE patients SET first_name=?, last_name=?, insurance_company=cast(? as insurance_enum), birthdate=?, " +
                    "sex=cast(? as sex_enum), blood_group=cast(? as blood_enum), phone=?, address=?, email=?, updated_at=now()  WHERE id=?;";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query1);

                preparedStatement.setString(1, first_name);
                preparedStatement.setString(2, sure_name);
                preparedStatement.setString(3, insurance_co);
                preparedStatement.setDate(4,  getDate(birthdate));
                preparedStatement.setString(5, sex);
                preparedStatement.setString(6, blood_group);
                preparedStatement.setString(7, phone);
                preparedStatement.setString(8, address);
                preparedStatement.setString(9, email);
                preparedStatement.setLong(10, id);

                System.out.println(preparedStatement);
                int res = preparedStatement.executeUpdate();
                System.out.println("Succesfully updated " + res +" row!");
                GeneralLogger.log(Level.INFO, "PATIENT | UPDATE: Patient " + email + " updated" );
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

                preparedStatement.setLong(1,id);
                System.out.println(preparedStatement);
                int res = preparedStatement.executeUpdate();
                System.out.println("Succesfully updated " + res +" row!");

                String email = getEmailByPatientId(id);
                GeneralLogger.log(Level.INFO, "PATIENT | DELETE: Patient " + email + " deleted" );

            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException: " + e;
            }
            return "Patient deleted!";
        }
    }

    public static String creteAppointment(String title, String description, String start_time, String end_time, long patient_id, long doctor_id, long created_by){

        String query = "INSERT INTO appointments VALUES(default, ?, ?, ?, ?, ?, ?, now(), now(),  false, ?);";


        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.parse(start_time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
            preparedStatement.setTimestamp(4,  Timestamp.valueOf(LocalDateTime.parse(end_time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
            preparedStatement.setLong(5, patient_id);
            preparedStatement.setLong(6, doctor_id);
            preparedStatement.setLong(7, created_by);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            GeneralLogger.log(Level.INFO, "APPOINTMENT | CREATE: Appointment " + title + " created" );
            return "Succesfully created appointment!";


        } catch (SQLException e) {
            e.printStackTrace();
            return "SQLException: " + e;
        }
    }

    public static String updateAppointment(long id, String title, String description, String start_time, String end_time, long patient_id, long doctor_id){
        if(!isAppointmentExist(id)){
            GeneralLogger.log(Level.WARNING, "APPOINTMENT | UPDATE | FAILED: Appointment " + title + " not found" );
            return "Appointment with this id not exists!";
        }
        else {
            String query = "UPDATE appointments SET title=?, description=?, start_time=?, end_time=?, patient_id=?, doctor_id=?, updated_at=now() WHERE id=?";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setDate(3, getDate(start_time));
                preparedStatement.setDate(4,  getDate(end_time));
                preparedStatement.setLong(5, patient_id);
                preparedStatement.setLong(6, doctor_id);
                preparedStatement.setLong(7,id);

                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
                GeneralLogger.log(Level.INFO, "APPOINTMENT | UPDATE: Appointment " + title + " updated" );
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
                int res = preparedStatement.executeUpdate();
                System.out.println("Succesfully updated " + res +" row!");
                String title = getTitleByAppointmentId(id);
                GeneralLogger.log(Level.INFO, "APPOINTMENT | DELETE: Appointment " + title + " deleted" );
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

        String query = "INSERT INTO records VALUES(default, ?, ?, ?, ?, ?, ?, now(), now(),  false);";

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
            GeneralLogger.log(Level.INFO, "RECORD | CREATE: Record " + title + " created" );
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
            GeneralLogger.log(Level.WARNING, "RECORD | UPDATE | FAILED: Record " + title + " not found" );
            return "Record with this id not exists!";
        }
        else{
            String query = "UPDATE records SET title=?, description=?, date_executed=?, notes=?, patient_id=?, doctor_id=?, updated_at=now() WHERE id=?";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setDate(3, getDate(execute_date));
                preparedStatement.setString(4,  notes);
                preparedStatement.setLong(5, patient_id);
                preparedStatement.setLong(6, doctor_id);
                preparedStatement.setLong(7, id);

                System.out.println(preparedStatement);
                int res = preparedStatement.executeUpdate();
                System.out.println("Succesfully updated " + res +" row!");
                GeneralLogger.log(Level.INFO, "RECORD | UPDATE: Record " + title + " updated" );
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
                int res = preparedStatement.executeUpdate();
                System.out.println("Succesfully updated " + res +" row!");
                String title = getTitleByRecordId(id);
                GeneralLogger.log(Level.INFO, "APPOINTMENT | DELETE: Appointment " + title + " deleted" );
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
            GeneralLogger.log(Level.INFO, "PRESCRIPTION | CREATE: Prescription " + title + " created" );
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
            GeneralLogger.log(Level.WARNING, "PRESCRIPTION | UPDATE | FAILED: Prescription " + title + " not found" );
            return "Prescription with this id not exists!";
        }
        else{
            String query = "UPDATE prescriptions SET title=?, description=?, drug=?, expiration_date=?, notes=?, patient_id=?, doctor_id=?, updated_at=now() WHERE id=?";

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
                preparedStatement.setLong(8, id);

                System.out.println(preparedStatement);
                int res = preparedStatement.executeUpdate();
                System.out.println("Succesfully updated " + res +" row!");
                GeneralLogger.log(Level.INFO, "PRESCRIPTION | UPDATE: Prescription " + title + " updated" );
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
            String query = "UPDATE prescriptions SET deleted=true WHERE id=?";

            try {
                Connection connection = DriverManager.getConnection(url, user, pswd);
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setLong(1, id);

                System.out.println(preparedStatement);
                int res = preparedStatement.executeUpdate();
                System.out.println("Succesfully updated " + res +" row!");
                String title = getTitleByPrescriptionId(id);
                GeneralLogger.log(Level.INFO, "PRESCRIPTION | DELETE: Prescription " + title + " deleted" );
                return "Succesfully deleted prescription!";


            } catch (SQLException e) {
                e.printStackTrace();
                return "SQLException: " + e;
            }
        }

    }

    public static ObservableList<Prescription> getAllNotDeletedPrescriptionsByEntityID(String entity , long id){
        String query = "SELECT * from prescriptions WHERE " + entity + "_id=? AND deleted=false;";
        ObservableList<Prescription> result = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(preparedStatement);

            while (resultSet.next()) {
                result.add(createPrescriptionFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ObservableList<Appointment> getAllNotDeletedAppointmentsByPatientId(long patient_id){
        String query = "SELECT * from appointments WHERE patient_id=? AND deleted=false;";
        ObservableList<Appointment> result = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,patient_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(createAppointmentFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ObservableList<Record> getAllNotDeletedRecordsByPatientId(long patient_id){
        String query = "SELECT * from records WHERE patient_id=? AND deleted=false;";
        ObservableList<Record> result = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,patient_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(createRecordFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Record createRecordFromResultSet(ResultSet resultSet) throws SQLException {
        Record record = new Record();
        record.setId(resultSet.getLong("id"));
        record.setTitle(resultSet.getString("title"));
        record.setDescription(resultSet.getString("description"));
        record.setDate_executed(resultSet.getDate("date_executed"));
        record.setNotes(resultSet.getString("notes"));
        record.setPatient_id(resultSet.getLong("patient_id"));
        record.setDoctor_id(resultSet.getLong("doctor_id"));
        record.setCreated_at(resultSet.getObject("created_at", LocalDateTime.class));
        record.setUpdated_at(resultSet.getObject("updated_at", LocalDateTime.class));
        record.setDeleted(resultSet.getBoolean("deleted"));
        return record;
    }

    private static Appointment createAppointmentFromResultSet(ResultSet resultSet) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(resultSet.getLong("id"));
        appointment.setTitle(resultSet.getString("title"));
        appointment.setDescription(resultSet.getString("description"));
        appointment.setStart_time(resultSet.getObject("start_time", LocalDateTime.class));
        appointment.setEnd_time(resultSet.getObject("end_time", LocalDateTime.class));
        appointment.setPatient_id(resultSet.getLong("patient_id"));
        appointment.setDoctor_id(resultSet.getLong("doctor_id"));
        appointment.setDoctor_name(JavaPostgreSql.getUser(resultSet.getLong("doctor_id")).getFull_name());
        appointment.setCreated_at(resultSet.getObject("created_at", LocalDateTime.class));
        appointment.setUpdated_at(resultSet.getObject("updated_at", LocalDateTime.class));
        appointment.setDeleted(resultSet.getBoolean("deleted"));
        appointment.setCreated_by(resultSet.getLong("created_by"));

        return appointment;
    }

    public static ObservableList<User> getAllUsers(){

        String query = "SELECT * from users;";
        ObservableList<User> result = FXCollections.observableArrayList();
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

    public static ObservableList<String> getUsersByPosition(String position){
        String query = "SELECT * from users WHERE position=cast(? as position_enum);";
        ObservableList<String> result = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,position);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User a = createUserFromResultSet(resultSet);
                result.add(a.getFirst_name() + " "+ a.getLast_name());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static User getUserByEmail(String email){
        User result = new User();
        try {
            String query = "SELECT * FROM users WHERE email=?;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = createUserFromResultSet(resultSet);
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getEmailByPatientId(long patient_id){
        String result = "";
        try {
            String query = "SELECT * FROM patients WHERE id=?;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, patient_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getString("email");
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getEmailByUserId(long user_id){
        String result = "";
        try {
            String query = "SELECT * FROM users WHERE id=?;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, user_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getString("email");
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getTitleByAppointmentId(long appointment_id){
        String result = "";
        try {
            String query = "SELECT * FROM appointments WHERE id=?;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, appointment_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getString("title");
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String getTitleByPrescriptionId(long prescription_id){
        String result = "";
        try {
            String query = "SELECT * FROM prescriptions WHERE id=?;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, prescription_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getString("title");
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getTitleByRecordId(long record_id){
        String result = "";
        try {
            String query = "SELECT * FROM records WHERE id=?;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, record_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getString("title");
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static User getUserByFirstAndLastName(String first_name, String last_name){
        User result = new User();
        try {
            String query = "SELECT * FROM users WHERE first_name=? and last_name=?;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = createUserFromResultSet(resultSet);
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ObservableList<Patient> getAllNotDeletedPatients(){
        String query = "SELECT * from patients WHERE deleted=false;";
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
        patient.setLast_name(resultSet.getString("last_name"));
        patient.setBlood_group(resultSet.getString("blood_group"));
        patient.setSex(resultSet.getString("sex"));
        patient.setAddress(resultSet.getString("address"));
        patient.setEmail(resultSet.getString("email"));
        patient.setInsurance_company(resultSet.getString("insurance_company"));
        patient.setPhone(resultSet.getString("phone"));
        patient.setBirth_date(resultSet.getDate("birthdate"));
        patient.setCreated_at(resultSet.getObject("created_at", LocalDateTime.class));
        patient.setUpdated_at(resultSet.getObject("updated_at", LocalDateTime.class));
        patient.setDeleted(resultSet.getBoolean("deleted"));
        patient.setBirth_ID(resultSet.getLong("identification_number"));

//        Button button = new Button();
//        button.setText("Open");
//        patient.setPatient_info(button);

        return patient;

    }

    private static User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User obj = new User();
        obj.setId(resultSet.getLong("id"));
        obj.setFirst_name(resultSet.getString("first_name"));
        obj.setLast_name(resultSet.getString("last_name"));
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

    public static Timestamp getUpcomingAppointmentDate(Long patient_id) {
        Timestamp result = null;
        try {
            String query = "SELECT * FROM appointments WHERE patient_id=? AND start_time>=? AND deleted=false ORDER BY start_time ASC LIMIT 1;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, patient_id);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                result = resultSet.getTimestamp("start_time");
                System.out.println(preparedStatement);
                System.out.println(result);
            }else{
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    Date parsedDate = dateFormat.parse("0000-00-00 00:00");
                    result = new java.sql.Timestamp(parsedDate.getTime());
                } catch(Exception e) {
                    System.out.println("Exception e: " + e);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ObservableList<Patient> filterPatients(String filterWord) {

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        String query = "";
        ObservableList<Patient> result = FXCollections.observableArrayList();



            if (pattern.matcher(filterWord).matches()){
                query = "SELECT * FROM patients WHERE identification_number=? AND deleted=false;";


                try {
                    Connection connection = DriverManager.getConnection(url, user, pswd);
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    preparedStatement.setString(1, filterWord);

                    while (resultSet.next()) {
                        result.add(createPatientFromResultSet(resultSet));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                query = "SELECT * FROM patients WHERE (LOWER(first_name) LIKE ? or LOWER(last_name) LIKE ?)AND deleted=false;";



                try {
                    Connection connection = DriverManager.getConnection(url, user, pswd);
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, "%" + filterWord + "%");
                    preparedStatement.setString(2, "%" + filterWord + "%");

                    ResultSet resultSet = preparedStatement.executeQuery();


                    while (resultSet.next()) {
                        result.add(createPatientFromResultSet(resultSet));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        return result;

    }

    public static String filterRecords(Long patientId) {
        try {
            String query = "SELECT * FROM patients WHERE patient_id=?;";

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
            String query = "SELECT * FROM patients WHERE patient_id=?;";

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

    public static Appointment getAppointment(Long appointment_id) {

        Appointment result = new Appointment();
        try {
            String query = "SELECT * FROM appointments WHERE id=?;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, appointment_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = createAppointmentFromResultSet(resultSet);
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Record getRecord(Long record_id) {

        Record result = new Record();
        try {
            String query = "SELECT * FROM records WHERE id=?;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, record_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = createRecordFromResultSet(resultSet);
            System.out.println(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static User getUser(Long user_id) {

        User result = new User();
        try {
            String query = "SELECT * FROM users WHERE id=?;";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, user_id);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = createUserFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Patient getPatient(Long patientId) {

        Patient result = new Patient();
        try {
            String query = "SELECT * FROM patients WHERE id=?";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, patientId);

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(preparedStatement);
            resultSet.next();
            result = createPatientFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Prescription getPrescriptionById(Long prescription_id) {

        Prescription result = new Prescription();
        try {
            String query = "SELECT * FROM prescriptions WHERE id=?";

            Connection connection = DriverManager.getConnection(url, user, pswd);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, prescription_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(preparedStatement);
            resultSet.next();
            result = createPrescriptionFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Prescription createPrescriptionFromResultSet(ResultSet resultSet) throws SQLException {
        Prescription obj = new Prescription();
        obj.setId(resultSet.getLong("id"));
        obj.setTitle(resultSet.getString("title"));
        obj.setDescription(resultSet.getString("description"));
        obj.setDrug(resultSet.getString("drug"));
        obj.setNotes(resultSet.getString("notes"));
        obj.setPatient_id(resultSet.getLong("patient_id"));
        obj.setDoctor_id(resultSet.getLong("doctor_id"));
        obj.setDoctor_name(JavaPostgreSql.getUser(resultSet.getLong("doctor_id")).getFull_name());
        obj.setExpiration_date(resultSet.getDate("expiration_date"));
        obj.setCreated_at(LocalDateTime.from(resultSet.getTimestamp("created_at").toLocalDateTime()));
        obj.setUpdated_at(LocalDateTime.from(resultSet.getTimestamp("updated_at").toLocalDateTime()));
        obj.setDeleted(resultSet.getBoolean("deleted"));
        return obj;
    }

}

