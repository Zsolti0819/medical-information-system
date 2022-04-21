package com.example.medis.Entities;

import com.example.medis.JavaPostgreSql;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Patient {
    private final JavaPostgreSql javaPostgreSql = new JavaPostgreSql();
    private long id;
    private String first_name, last_name;
    private String insurance_company;
    private String phone;
    private Date birth_date;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean deleted;
    private long birth_id;
    private String sex;
    private String blood_group;
    private String address;
    private String email;
    private String next_visit;

    public String getInsurance_company() {
        return insurance_company;
    }

    public void setInsurance_company(String insurance_company) {
        this.insurance_company = insurance_company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public void setBirth_id(long birth_id) {
        this.birth_id = birth_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getBirth_id() {
        return birth_id;
    }

    public void setBirth_ID(long birth_id) {
        this.birth_id = birth_id;
    }

    public String getNext_visit() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(javaPostgreSql.getUpcomingAppointmentDate(getId()));
    }


    public static boolean hasValidID(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;
        int day = (int) (date % 100);
        int month = (int) (date / 100 % 100);

        if (month >= 51 && month <= 62)
            month -= 50;

        int[] mdays = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        return identificationNumberLong % 11 == 0 && month > 0 && (month <= 12 || month >= 51) &&
                month <= 62 && day > 0 && day <= mdays[month];
    }

    public static String getGender(String identificationNumber) {

        if (!(identificationNumber.equals(""))) {
            long identificationNumberLong = Long.parseLong(identificationNumber);
            long date = identificationNumberLong / 10000;
            int month = (int) (date / 100 % 100);
            if (month >= 51 && month <= 62)
                return "female";
            else
                return "male";
        }
        return null;
    }

    public static int getYear(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;
        int year = (int) (date / 10000 % 100);

        if (year >= 20)
            year += 1900;

        else
            year += 2000;

        System.out.println(year);
        return year;
    }

    public static int getMonth(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;
        int month = (int) (date / 100 % 100);

        if (month >= 51 && month <= 62)
            month -= 50;

        return month;
    }

    public static int getDay(String identificationNumber) {
        long identificationNumberLong = Long.parseLong(identificationNumber);
        long date = identificationNumberLong / 10000;

        return (int) (date % 100);
    }
}
