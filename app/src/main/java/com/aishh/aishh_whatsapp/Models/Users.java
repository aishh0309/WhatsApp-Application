package com.aishh.aishh_whatsapp.Models;

public class Users {
    String profilepic,username,email,password,userid,lastmessage,about;


    public Users(String profilepic, String userName, String email, String password, String userid, String lastmessage,String about) {
        this.profilepic = profilepic;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.lastmessage = lastmessage;
        this.about = about;

    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Users(){

    }
    //SignUp Constructor
    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }
}
