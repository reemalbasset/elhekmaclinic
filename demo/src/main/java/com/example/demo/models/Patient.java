package com.example.demo.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Patient")
public class Patient extends User {
    private int age;
    private String address;

    public Patient() {}

    public Patient(Long id, String name, String username, String password, String email, String dob, int age, String address) {
        super(id, name, username, password, email, dob);
        this.age = age;
        this.address = address;
    }

    // Getters and setters...

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "age=" + age +
                ", address='" + address + '\'' +
                "} " + super.toString();
    }
}
