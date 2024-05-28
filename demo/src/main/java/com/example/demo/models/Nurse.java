package com.example.demo.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Nurse")
public class Nurse extends User {

    public Nurse() {}

    public Nurse(Long id, String name, String username, String password, String email, String dob) {
        super(id, name, username, password, email, dob);
    }

    @Override
    public String toString() {
        return "Nurse{} " + super.toString();
    }
}
