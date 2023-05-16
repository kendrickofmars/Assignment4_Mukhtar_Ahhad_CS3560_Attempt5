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
import java.util.Objects;

@Entity
@Table(name="\"Order\"")
public class Order {


    @Id
    @Column(name="number")
    private int number;

    @Column(name="date")
    private java.sql.Date date;
    @Column(name="item")
    private String item;
    @Column(name="price")
    private double price;

    //we use these decorators because customer_id is a foreign key
    //and a single customer can have multiple orders
//    @ManyToOne(cascade = CascadeType.PERSIST)

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name="customer_id")
    private Customer customerInstance;
    public Order(){

    }




    public Order(int number, java.sql.Date date, String item, double price, Customer customerInstance) {
        this.number = number;
        this.date = date;
        this.item = item;
        this.price = price;
        this.customerInstance  = customerInstance;

    }

    public Customer getCustomerInstance() {
        return customerInstance;
    }

    public void setCustomerInstance(Customer customerInstance) {
        this.customerInstance = customerInstance;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getNumber() == order.getNumber() && Double.compare(order.getPrice(), getPrice()) == 0 && Objects.equals(getDate(), order.getDate()) && Objects.equals(getItem(), order.getItem()) && Objects.equals(getCustomerInstance(), order.getCustomerInstance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getDate(), getItem(), getPrice(), getCustomerInstance());
    }
}