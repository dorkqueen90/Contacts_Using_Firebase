package com.example.contactsusingfirebase;

public class Contact {
    public String name, email, phone, dept, icon;
    public int imgId, id;

    public  Contact(){
    }

    public Contact(String name, String email, String phone, String dept, int imgId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dept = dept;
        this.imgId = imgId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
