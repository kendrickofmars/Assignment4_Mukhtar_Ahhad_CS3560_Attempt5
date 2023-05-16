package entity;

import java.util.Objects;
import jakarta.persistence.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.Root;

import javax.print.Doc;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
@Entity
@Table(name="\"Customer\"")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;


    @OneToOne(targetEntity = Address.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "address_id")
    private Address addressInst;
    public Customer(){

    }


    //foreign key, one-to-one relation w/ customer and address

    public Customer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Customer(String name, String phone, String email, Address addressInst) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.addressInst = addressInst;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return id == customer.id && addressInst == customer.addressInst && Objects.equals(getName(), customer.getName()) && Objects.equals(getPhone(), customer.getPhone()) && Objects.equals(getEmail(), customer.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName(), getPhone(), getEmail(), addressInst);
    }
}

