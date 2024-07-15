package com.example.model;


public class Doctor {
    private String code, name, specialty, email;
    private int Availability;


    public Doctor(String code, String name, String specialty, int ava, String email) {
        this.code = code;
        this.name = name;
        this.specialty = specialty;
        this.Availability = ava;
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
    public void setAvai(int ava){
        this.Availability = ava;
    }
    public int getAvai(){
        return Availability;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    
}
