package com.example.model;


public class Doctor {
    private String code, name, specialty, email;
    private String availability;

    public Doctor(String code, String name, String specialty, String ava, String email) {
        this.code = code;
        this.name = name;
        this.specialty = specialty;
        this.availability = ava;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return code;
    }
    public void setAvai(String ava){
        this.availability = ava;
    }
    public String getAvai(){
        return availability;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    
}
