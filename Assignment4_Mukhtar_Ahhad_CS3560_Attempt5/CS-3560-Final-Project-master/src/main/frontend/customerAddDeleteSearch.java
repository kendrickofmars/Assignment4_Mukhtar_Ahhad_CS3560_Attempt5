package src.main.frontend;
import entity.Customer;
import entity.Address;
import javax.swing.*;

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


/**
 The MetricConverter class lets the user enter a
 distance in kilometers. Radio buttons can be selected to
 convert the kilometers to miles, feet, or inches.
 */

public class customerAddDeleteSearch extends JFrame
{
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    private JPanel CustomerTopPanel;
    private JPanel NamePhoneEmailPanel;
    private JPanel AddressDetailPanel;
    private JPanel ButtonPanel;
    private JButton searchButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField ZipCodeField;
    private JTextField Street;
    private JTextField StateField;
    private JTextField CityField;
    private JTextField NameField;
    private JTextField PhoneNumberField;
    private JTextField EmailField;
    private JLabel StateLabel;


    /**
     Constructor
     */



    /**
     The buildPanel method adds a label, text field, and
     and three buttons to a panel.
     */



    /**
     Private inner class that handles the event when
     the user clicks one of the radio buttons.
     */

    public customerAddDeleteSearch(){
        searchButton.addActionListener(new ButtonActionListener());
        addButton.addActionListener(new ButtonActionListener());
        updateButton.addActionListener(new ButtonActionListener());
        deleteButton.addActionListener(new ButtonActionListener());
    }
    //NameField,PhoneNumberField, EmailField, Street, CityField, StateField,ZipCodeField
    private class ButtonActionListener implements ActionListener
    {



        //constructor takes in values in this order Customer(String name, String email, String phone)
        //constructor for the address takes in String street, String city, String state, int zipcode
        //we set pgadmin to increment the id for address and customer value by itself so we don't have to worry about that
        public void actionPerformed(ActionEvent e) {
            //customername, phonenumber and  email sent to Customer table
            String customerName = NameField.getText();
            String phoneNumber = PhoneNumberField.getText();
            String email = EmailField.getText();

            //fill in these street, city, zip and state values and pass them in to address table
            String streetVal = Street.getText();
            String city = CityField.getText();
            String state = StateField.getText();

            int zipcode;
            try{
                zipcode = Integer.parseInt(ZipCodeField.getText());
            }catch(NumberFormatException n){
                throw new RuntimeException(n);
            }

            Customer customerInstance = new Customer(customerName, phoneNumber,email);
            Address addressInstance = new Address(streetVal, city, state, zipcode);

            if(e.getSource() == addButton){
                transaction.begin();
                entityManager.persist(customerInstance);
                entityManager.persist(addressInstance);
                transaction.commit();

            }else if(e.getSource() == updateButton){

            }else if(e.getSource() == searchButton){

            }else if(e.getSource() == deleteButton){

            }
        }
    }

    /**
     The main method creates an instance of the
     MetricConverter class, displaying its window.
     */

    public static void main(String[] args) {
        JFrame frame = new JFrame("customerAddDeleteSearch");
        frame.setContentPane(new customerAddDeleteSearch().CustomerTopPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}