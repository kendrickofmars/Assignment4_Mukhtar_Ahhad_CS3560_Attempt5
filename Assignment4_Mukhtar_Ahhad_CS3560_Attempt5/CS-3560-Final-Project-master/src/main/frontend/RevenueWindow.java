package src.main.frontend;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import src.main.jdbc.ConnectionFactory;
public class RevenueWindow extends JFrame{
    private JTextPane revenuePane;
    private JPanel revenueHolder;


    public RevenueWindow(){

        setTitle("Revenue");
        setTitle("JEditorPane Test");
        revenuePane.setContentType("text/html");
        revenuePane.setText("<h1>Java</h1><p>is a general-purpose computer programming language that is       concurrent, class-based, object-oriented, and specifically designed to have as few          implementation dependencies as possible.</p>");
        setSize(350, 275);
        setContentPane(revenuePane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RevenueWindow();
    }
}
