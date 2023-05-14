package src.main.frontend;
import javax.swing.*; // Needed for Swing classes
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import src.main.jdbc.ConnectionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entity.Student;
import jakarta.persistence.*;
//import org.postgresql.core.ConnectionFactory;

public class StudentFrontend extends JFrame {
    JFrame frame = new JFrame("StudentFrontend");

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    public JPanel nameAndButtons;
    private JTextField Name;
    private JPanel buttonBar;
    private JButton add;
    private JButton search;
    private JButton delete;
    private JButton update;
    private JTextField course;
    private JLabel Course;
    private JPanel coursePanel;
    private JTextField id;
    private JLabel idLabel;
    private JLabel student_name;
    private JButton backButton;


    public StudentFrontend(){

        setSize(600,500);
        add.addActionListener(new ButtonsAndTextField());
        update.addActionListener(new ButtonsAndTextField());
        delete.addActionListener(new ButtonsAndTextField());
        search.addActionListener(new ButtonsAndTextField());
        backButton.addActionListener(new ButtonsAndTextField());
    }



    private class ButtonsAndTextField implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = Name.getText();
            String courseVal = course.getText();
            String broncoID = id.getText();
            Student studentInp = new Student(broncoID, name, courseVal);

            if (e.getSource() == add) {

                transaction.begin();

                entityManager.persist(studentInp);
                transaction.commit();


                JOptionPane.showMessageDialog(null, "Name entered: " + name +
                        "\nAnd your course entered is: " + courseVal + "\nBronco ID: " + broncoID);

            } else if (e.getSource() == update) {
                try {
                    //making a connection to the db and seeing if a row is returned that has the values we want
                    Connection connection = ConnectionFactory.getConnection();
                    System.out.println("Updating student " +studentInp.getName());
                    PreparedStatement stmt = connection.prepareStatement("UPDATE public.student " +
                            "SET id=?, name=?, course=? WHERE id = ?");
                    //since we have 4 parameters in this SQL statement, we need to pass each in one-by-one
                    stmt.setString(1, broncoID);
                    stmt.setString(2,name);
                    stmt.setString(3,courseVal);
                    stmt.setString(4,broncoID);

                    stmt.executeUpdate();
                    //autocommit is ON, so we don't need to use connection.commit();
                    System.out.println("Successfully updated student");


                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            } else if (e.getSource() == search) {
                try {
                    //making a connection to the db and seeing if a row is returned that has the values we want
                    Connection connection = ConnectionFactory.getConnection();
                    System.out.println("Searching for " +studentInp.getName());
                    //searching for student based off of their ID value
                    PreparedStatement stmt = connection.prepareStatement("SELECT id, name, course\n" +
                            "\tFROM public.student WHERE id =?");

                    stmt.setString(1, broncoID);

                    ResultSet rs = stmt.executeQuery();
                    //autocommit is ON, so we don't need to use connection.commit();
                    while(rs.next()) {
                        if (broncoID.equals(rs.getString("id"))) {
                            JOptionPane.showMessageDialog(null, "Found " + rs.getString("name") + " within db!");
                            System.out.println("Found " + rs.getString("name") + " within db!" + "\nSetting values to ones found in db");
                            //Name, course, id, use setText for each of these textfields
                            Name.setText(rs.getString("name"));
                            course.setText(rs.getString("course"));
                            id.setText(rs.getString("id"));

                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to find student with ID:" + broncoID + " within db!");
                        }
                    }


                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            } else if (e.getSource() == delete) {
                try {
                    //making a connection to the db and seeing if a row is returned that has the values we want
                    Connection connection = ConnectionFactory.getConnection();
                    System.out.println("Deleting Student " +studentInp.getName());
//                    SessionFactory factory = new Configuration().configure("persistence.xml").addAnnotatedClass(Student.class).buildSessionFactory();
                    //deleting student based on whether or not id matches input
                    PreparedStatement stmt = connection.prepareStatement("DELETE FROM public.student\n" +
                            "\tWHERE id= ?");
//                    stmt.setInt(1, Integer.parseInt(broncoID));
                    stmt.setString(1, broncoID);

                    stmt.executeUpdate();
                    //autocommit is ON, so we don't need to use connection.commit();
                    System.out.println("Successfully deleted student");


                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            } else if (e.getSource() == backButton) {
                WelcomeWindow welcomeInstance = new WelcomeWindow();
                welcomeInstance.frame.setContentPane(welcomeInstance.revenuePanel);
                welcomeInstance.frame.setLocationRelativeTo(null);
                welcomeInstance.frame.pack();
                welcomeInstance.frame.setVisible(true);


            }
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("StudentFrontend");
        frame.setContentPane(new StudentFrontend().nameAndButtons);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


    }


}