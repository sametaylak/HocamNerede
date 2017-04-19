package com.gelecegiyazanlar.hocamnerede.Model;

public class User {
    private String fullname;
    private String mail;
    private String university;
    private String role;

    public User() { }

    public User(String fullname, String mail, String university, String role) {
        this.fullname = fullname;
        this.mail = mail;
        this.university = university;
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
