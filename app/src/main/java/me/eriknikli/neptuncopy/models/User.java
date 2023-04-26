package me.eriknikli.neptuncopy.models;

import java.util.Date;

public class User {
    private String familyname;
    private String forename;
    private Date birthdate;
    private String address;
    private String email;
    private Boolean isTeacher;

    public User(String familyname, String forename, Date birthdate, String address, String email, Boolean isTeacher) {
        setFamilyname(familyname);
        setForename(forename);
        setBirthdate(birthdate);
        setAddress(address);
        setEmail(email);
        setTeacher(isTeacher);
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }
}
