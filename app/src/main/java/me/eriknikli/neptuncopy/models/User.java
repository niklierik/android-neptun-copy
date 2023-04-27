package me.eriknikli.neptuncopy.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.function.Consumer;

public class User {

    private static User user;

    private String familyname;
    private String forename;
    private Date birthdate;
    private String address;
    private String email;
    private Boolean isTeacher;


    private static void pullUserData(FirebaseAuth auth, FirebaseFirestore db, Consumer<User> runIfDone) {
        String uid = auth.getCurrentUser().getUid();
        db.collection("users").document(uid).get().addOnSuccessListener(l -> {
            if (l.exists()) {
                user = new User(l);
            } else {
                user = null;
            }
            if (runIfDone != null) {
                runIfDone.accept(user);
            }
        });
    }

    public static void getLatest(FirebaseAuth auth, FirebaseFirestore db, Consumer<User> runIfDone) {
        if (user == null) {
            pullUserData(auth, db, runIfDone);
            return;
        }
        if (runIfDone != null) {
            runIfDone.accept(user);
        }
    }

    public static void clearLatest() {
        user = null;
    }

    public User(DocumentSnapshot data) {
        setEmail(data.getString("email"));
        setFamilyname(data.getString("familyname"));
        setForename(data.getString("forename"));
        setTeacher(data.getBoolean("isTeacher"));
        setBirthdate(data.getDate("birthdate"));
        setAddress(data.getString("address"));
    }

    public User(String familyname, String forename, Date birthdate, String address, String email, Boolean isTeacher) {
        user = this;
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

    public void setTeacher(Boolean teacher) {
        isTeacher = teacher;
    }
}
