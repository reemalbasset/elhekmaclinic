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

    private Long user_id;

    public Appointment(Long id, Doctor doctor, Nurse nurse, String patientName, Date appointmentDate, Time appointmentTime, String reason, Long user_id, String username) {
        this.id = id;
        this.doctor = doctor;
        this.nurse = nurse;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.user_id = user_id;
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Appointment username(String username) {
        setUsername(username);
        return this;
    }
    private String username;


    public Appointment() {
    }

    public Appointment(Long id, Doctor doctor, Nurse nurse, String patientName, Date appointmentDate, Time appointmentTime, String reason, Long user_id) {
        this.id = id;
        this.doctor = doctor;
        this.nurse = nurse;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.user_id = user_id;
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

    public Long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Appointment id(Long id) {
        setId(id);
        return this;
    }

    public Appointment doctor(Doctor doctor) {
        setDoctor(doctor);
        return this;
    }

    public Appointment nurse(Nurse nurse) {
        setNurse(nurse);
        return this;
    }

    public Appointment patientName(String patientName) {
        setPatientName(patientName);
        return this;
    }

    public Appointment appointmentDate(Date appointmentDate) {
        setAppointmentDate(appointmentDate);
        return this;
    }

    public Appointment appointmentTime(Time appointmentTime) {
        setAppointmentTime(appointmentTime);
        return this;
    }

    public Appointment reason(String reason) {
        setReason(reason);
        return this;
    }

    public Appointment user_id(Long user_id) {
        setUser_id(user_id);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Appointment)) {
            return false;
        }
        Appointment appointment = (Appointment) o;
        return Objects.equals(id, appointment.id) && Objects.equals(doctor, appointment.doctor) && Objects.equals(nurse, appointment.nurse) && Objects.equals(patientName, appointment.patientName) && Objects.equals(appointmentDate, appointment.appointmentDate) && Objects.equals(appointmentTime, appointment.appointmentTime) && Objects.equals(reason, appointment.reason) && Objects.equals(user_id, appointment.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctor, nurse, patientName, appointmentDate, appointmentTime, reason, user_id);
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
            ", user_id='" + getUser_id() + "'" +
            "}";
    }
    
    
}
