package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Nurse nurse;

    private String patientName;

    private Date appointmentDate;

    private Time appointmentTime;

    private String reason;

    public Appointment() {
    }

    public Appointment(Long id, Doctor doctor, Nurse nurse, String patientName, Date appointmentDate, Time appointmentTime, String reason) {
        this.id = id;
        this.doctor = doctor;
        this.nurse = nurse;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Nurse getNurse() {
        return this.nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getAppointmentDate() {
        return this.appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getAppointmentTime() {
        return this.appointmentTime;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Appointment)) {
            return false;
        }
        Appointment appointment = (Appointment) o;
        return Objects.equals(id, appointment.id) && Objects.equals(doctor, appointment.doctor) && Objects.equals(nurse, appointment.nurse) && Objects.equals(patientName, appointment.patientName) && Objects.equals(appointmentDate, appointment.appointmentDate) && Objects.equals(appointmentTime, appointment.appointmentTime) && Objects.equals(reason, appointment.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctor, nurse, patientName, appointmentDate, appointmentTime, reason);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", doctor='" + getDoctor() + "'" +
            ", nurse='" + getNurse() + "'" +
            ", patientName='" + getPatientName() + "'" +
            ", appointmentDate='" + getAppointmentDate() + "'" +
            ", appointmentTime='" + getAppointmentTime() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }

}
