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
import src.main.jdbc.ConnectionFactory;


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

            Address addressInstance = new Address(streetVal, city, state, zipcode);
            Customer customerInstance = new Customer(customerName, phoneNumber,email,addressInstance);


            if(e.getSource() == addButton){

                transaction.begin();
                entityManager.persist(addressInstance);
                entityManager.persist(customerInstance);


                transaction.commit();
                JOptionPane.showMessageDialog(null, "Customer " + customerName + "successfully included");

            }else if(e.getSource() == updateButton){
                try{
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt1 = connection.prepareStatement("UPDATE public.\"Customer\"\n" +
                            "\tSET name=?, phone=?, email=?" +
                            "\tWHERE name =?");
                    stmt1.setString(1, customerName);
                    stmt1.setString(2, phoneNumber);
                    stmt1.setString(3,email);
                    stmt1.setString(4,customerName);

                    PreparedStatement stmt2 = connection.prepareStatement("UPDATE public.address\n" +
                            "\tSET city=?, street=?, state=?, zip_code=?" +
                            "\tWHERE city=? AND street =?");
                    stmt2.setString(1, city);
                    stmt2.setString(2,streetVal);
                    stmt2.setString(3,state);
                    stmt2.setInt(4,zipcode);
                    stmt2.setString(5,city);
                    stmt2.setString(6,streetVal);

                    stmt1.executeUpdate();

                    stmt2.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Customer Data succesfully updated\nResetting Fields");
                    NameField.setText("");
                    PhoneNumberField.setText("");
                    EmailField.setText("");

                    Street.setText("");
                    CityField.setText("");
                    StateField.setText("");
                    ZipCodeField.setText("");

                }catch(ClassNotFoundException ex2){
                    throw new RuntimeException(ex2);
                }catch(SQLException ex2){
                    throw new RuntimeException(ex2);
                }

            }else if(e.getSource() == searchButton){
                try {
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement("SELECT name, phone, email, id, address_id\n" +
                            "\tFROM public.\"Customer\" WHERE name =?");
                    stmt.setString(1, customerName);
                    ResultSet rs = stmt.executeQuery();


                    while(rs.next()) {
                        if (customerName.equals(rs.getString("name"))) {
                            JOptionPane.showMessageDialog(null,"Found " + rs.getString("name") +
                                                            " within db, \nSetting fields to values found");
                            NameField.setText(rs.getString("name"));
                            PhoneNumberField.setText(rs.getString("phone"));
                            EmailField.setText(rs.getString("email"));

                            PreparedStatement stmt2 = connection.prepareStatement("SELECT city, street, state, zip_code, id\n" +
                                    "\tFROM public.address WHERE id =?");
                            stmt2.setInt(1, rs.getInt("address_id"));
                            ResultSet rs2 = stmt2.executeQuery();

                            if(rs2.next()) {
                                Street.setText(rs2.getString("street"));
                                CityField.setText(rs2.getString("city"));
                                StateField.setText(rs2.getString("state"));
                                ZipCodeField.setText(String.valueOf(rs2.getInt("zip_code")));
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "No Records Found");
                        }
                    }
                }catch(ClassNotFoundException ex){
                    throw new RuntimeException(ex);
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }else if(e.getSource() == deleteButton){
                try {
                    Connection connection = ConnectionFactory.getConnection();

                    PreparedStatement stmt = connection.prepareStatement("DELETE FROM public.\"Customer\"\n" +
                            "\tWHERE name =?");
                    stmt.setString(1, customerName);
                    ResultSet rs = stmt.executeQuery();


                    while(rs.next()) {
                        if (customerName.equals(rs.getString("name"))) {
                            JOptionPane.showMessageDialog(null,"Found " + rs.getString("name") +
                                    " within db, \nDeleting from db and resetting fields");
                            NameField.setText("");
                            PhoneNumberField.setText("");
                            EmailField.setText("");

                            PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM " +
                                    "public.address WHERE id =?");
                            stmt2.setInt(1, rs.getInt("address_id"));
                            ResultSet rs2 = stmt2.executeQuery();

                            if(rs2.next()) {
                                Street.setText("");
                                CityField.setText("");
                                StateField.setText("");
                                ZipCodeField.setText("");
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "No Records Found");
                        }
                    }
                }catch(ClassNotFoundException ex){
                    throw new RuntimeException(ex);
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(null,"Customer successfully deleted from db");


                NameField.setText("");
                PhoneNumberField.setText("");
                EmailField.setText("");
                Street.setText("");
                CityField.setText("");
                StateField.setText("");
                ZipCodeField.setText("");
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