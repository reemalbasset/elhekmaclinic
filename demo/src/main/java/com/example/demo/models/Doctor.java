package com.example.demo.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Doctor")
public class Doctor extends User {
    private String specialty;
    private String universityName;

    public Doctor() {}

    public Doctor(Long id, String name, String username, String password, String email, String dob, String specialty, String universityName) {
        super(id, name, username, password, email, dob);
        this.specialty = specialty;
        this.universityName = universityName;
    }

    // Getters and setters...

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "specialty='" + specialty + '\'' +
                ", universityName='" + universityName + '\'' +
                "} " + super.toString();
    }
}
