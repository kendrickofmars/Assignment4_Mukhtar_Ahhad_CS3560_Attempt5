package src.main.frontend;
import entity.*;
import entity.Book;
import entity.Documentary;
import entity.Student;
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

public class LoanWindow {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    JFrame frame = new JFrame("LoanWindow");
    private JButton backButton;
    private JTextField loanNumber;
    private JTextField studentValue;
    private JTextField originationDate;
    private JTextField dueDate;
    private JPanel loanFieldsPanel;
    private JButton addButton;
    private JButton updateButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JButton viewCurrentLoansButton;
    private JPanel buttonPanel;
    public JPanel fullPanel;

    private JTextField studentIDs;
    private JLabel Student;
    private JRadioButton bookRadioButton;
    private JRadioButton documentaryRadioButton;
    private JComboBox itemCombo;
    private JComboBox studentCombo;


    public LoanWindow(){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        // Add Customers to Combobox
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> cr = cb.createQuery(Student.class);
        Root<Student> root = cr.from(Student.class);
        cr.select(root);
        TypedQuery<Student> query = entityManager.createQuery(cr);
        List<Student> students = query.getResultList();

        // removes all items currently in comboBox
        studentCombo.removeAllItems();

        for (int i = 0; i < students.toArray().length; i++)
        {
            studentCombo.addItem(students.get(i).getId());
        }


        CriteriaQuery<Book> cr2 = cb.createQuery(Book.class);
        Root<Book> root2 = cr2.from(Book.class);
        cr2.select(root2);
        TypedQuery<Book> query2 = entityManager.createQuery(cr2);
        List<Book> books = query2.getResultList();

        // Add Customers to Combobox
        CriteriaBuilder cb3 = entityManager.getCriteriaBuilder();
        CriteriaQuery<Documentary> cr3 = cb3.createQuery(Documentary.class);
        Root<Documentary> root3 = cr3.from(Documentary.class);
        cr3.select(root3);
        TypedQuery<Documentary> query3 = entityManager.createQuery(cr3);
        List<Documentary> docs = query3.getResultList();

        ButtonGroup group = new ButtonGroup();
        group.add(bookRadioButton);
        group.add(documentaryRadioButton);

        addButton.addActionListener(new LoanWindowButtons());
        updateButton.addActionListener(new LoanWindowButtons());
        searchButton.addActionListener(new LoanWindowButtons());
        deleteButton.addActionListener(new LoanWindowButtons());
        viewCurrentLoansButton.addActionListener(new LoanWindowButtons());
        backButton.addActionListener(new LoanWindowButtons());

//        FillStudentIDs();
        bookRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                EntityTransaction transaction = entityManager.getTransaction();



                // removes all items currently in comboBox
                itemCombo.removeAllItems();

                for (int i = 0; i < books.toArray().length; i++)
                {
                    itemCombo.addItem(books.get(i).getItemByItemCode());
                }


            }
        });
        documentaryRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                EntityTransaction transaction = entityManager.getTransaction();


                // removes all items currently in comboBox
                itemCombo.removeAllItems();

                for (int i = 0; i < docs.toArray().length; i++)
                {
                    itemCombo.addItem(docs.get(i).getItemByItemCode());
                }
            }
        });
    }

//    public void FillStudentIDs(){
//        try{
////          String query = "SELECT * from public.student WHERE id =?";
//            Connection connection = ConnectionFactory.getConnection();
//            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM public.student");
//            ResultSet rs = stmt.executeQuery();
//            while(rs.next()){
//                String studentIDVal = rs.getString("id");
//                studentIDs.addItem(studentIDVal);
//            }
//
//        }catch (Exception e){
//            JOptionPane.showMessageDialog(null,e);
//
//        }
//
//    }

    private class LoanWindowButtons implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
//            try {
//                int number = Integer.parseInt(loanNumber.getText());
//            }catch(NumberFormatException ex1){
//                throw new RuntimeException(ex1);
//
//            }
            int number = Integer.parseInt(loanNumber.getText());
            String bronco_id = studentCombo.getSelectedItem().toString();

            String itemCodeVal = ((Item) itemCombo.getSelectedItem()).getCode();

            //parsing string->java.util.Date->java.sql.Date
//            DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            java.sql.Date dateValue = (java.sql.Date) simpleDateFormat.parse("");
            java.util.Date java_due_date;
            java.sql.Date due_date;
            try {
                java_due_date = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate.getText());
                due_date = new java.sql.Date(java_due_date.getTime());
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            //declaring before initializing values in try catch so scope is wider
            java.sql.Date java_origination_date;
            java.util.Date java_util_origination_date;
            try {
                java_util_origination_date = new SimpleDateFormat("yyyy-dd-MM").parse(originationDate.getText());
                java_origination_date = new java.sql.Date(java_util_origination_date.getTime());

            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

            //pass in required parameters so entitymanager can do its operations properly
            //constructor for entity in Loan class is empty
            //Loan loanInstance = new Loan(number, origination_date, due_date, bronco_id, itemCodeVal);
            if (e.getSource() == addButton) {
                ReceiptWindow receiptWindowInstance = new ReceiptWindow();
                receiptWindowInstance.frame.setContentPane(receiptWindowInstance.containerPanel);
                receiptWindowInstance.frame.setLocationRelativeTo(null);
                receiptWindowInstance.frame.pack();
                receiptWindowInstance.frame.setVisible(true);
                try {
                    //making a connection to the db and seeing if a row is returned that has the values we want
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement("INSERT INTO public.\"loan\"" +
                            "(\"number\", due_date, student_id, item_code, date)" +
                            "\nVALUES (?, ?, ?, ?, ?)");
                    stmt.setInt(1, number);
                    stmt.setDate(2, due_date);
                    stmt.setString(3, bronco_id);
                    stmt.setString(4,itemCodeVal);
                    stmt.setDate(5,java_origination_date);
                    stmt.executeUpdate();
                } catch (ClassNotFoundException e2) {
                    throw new RuntimeException(e2);
                } catch (SQLException e3) {
                    JOptionPane.showMessageDialog(null,e3);
                    throw new RuntimeException(e3);

                }
                JOptionPane.showMessageDialog(null, "Loan number: " + number + "\nDate: " +
                        "\n");

            }
            if (e.getSource() == updateButton) {
                  try{
                      Connection connection = ConnectionFactory.getConnection();
                      PreparedStatement stmt = connection.prepareStatement("UPDATE public.\"Loan\"\n" +
                                        "SET \"number\"=?, due_date=?, bronco_id=?, item_code=?, date=?\n" +
                                        "WHERE number =?");

                      stmt.setInt(1, number);
                      stmt.setDate(2,due_date);
                      stmt.setString(3, bronco_id);
                      stmt.setString(4,itemCodeVal);
                      stmt.setDate(5,java_origination_date);
                      stmt.setInt(6, number);
                      stmt.executeUpdate();
                      JOptionPane.showMessageDialog(null, "Successfully updated values for Loans table");
                  } catch(ClassNotFoundException ex0){
                      throw new RuntimeException(ex0);

                  }catch(SQLException ex0){
                      throw new RuntimeException(ex0);
                  }

//                UPDATE public."Loan"
//                SET "number"=?, due_date=?, bronco_id=?, item_code=?, date=?
//                WHERE <condition>;
            }
            if (e.getSource() == searchButton) {
                try {
                    //making a connection to the db and seeing if a row is returned that has the values we want
                    Connection connection = ConnectionFactory.getConnection();
                    

                    System.out.println("Searching for student id #" +bronco_id + " with loan#" + number);
                    //searching for student based off of their ID value
                    PreparedStatement stmt = connection.prepareStatement("SELECT *\n" +
                            "\tFROM public.\"Loan\" WHERE bronco_id =? AND number =?");

                    stmt.setString(1, bronco_id);
                    stmt.setInt(2,number);

                    ResultSet rs = stmt.executeQuery();
                    //autocommit is ON, so we don't need to use connection.commit();
                    while(rs.next()) {
                        if (bronco_id.equals(rs.getString("bronco_id")) && number == (rs.getInt("number"))) {
                            JOptionPane.showMessageDialog(null, "Found Bronco ID: " + rs.getString("bronco_id") + "with loan #" + rs.getInt("number") +" within db!"+ "\nSetting values to ones found in db");
                            System.out.println("Found " + rs.getString("bronco_id") + " within db!" );
                            //Name, course, id, use setText for each of these textfields
                            studentIDs.setText(rs.getString("bronco_id"));
                            //itemCode.setText(rs.getString("item_code"));
                            originationDate.setText(rs.getString("date"));
                            dueDate.setText(rs.getString("due_date"));

                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to find loan with bronco ID:" + bronco_id + "and loan#" + number + " within db!\nPlease check to see if your ");
                        }
                    }


                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                //SELECT "number", due_date, bronco_id, item_code, date
                //	FROM public."Loan";
            }
            if (e.getSource() == deleteButton) {

                try {
                    //making a connection to the db and seeing if a row is returned that has the values we want
                    Connection connection = ConnectionFactory.getConnection();
                    System.out.println("Deleting Student");
                    //deleting student based on whether or not id matches input
                    PreparedStatement stmt = connection.prepareStatement("DELETE FROM public.\"Loan\"\n" +
                            "\tWHERE bronco_id= ? AND number=?");
//                    stmt.setInt(1, Integer.parseInt(broncoID));
                    stmt.setString(1, bronco_id);
                    stmt.setInt(2,number);

                    stmt.executeUpdate();
                    //autocommit is ON, so we don't need to use connection.commit();
                    JOptionPane.showMessageDialog(null,"Successfully deleted values from Loans table");


                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
//                DELETE FROM public."Loan"
//	            WHERE <condition>;
            }
            if (e.getSource() == viewCurrentLoansButton) {
                JOptionPane.showMessageDialog(null,"This feature is not available at the moment. Please try again later." + JOptionPane.ERROR_MESSAGE);
            }
            if (e.getSource() == backButton) {
                WelcomeWindow welcomeInstance = new WelcomeWindow();
                welcomeInstance.frame.setContentPane(welcomeInstance.revenuePanel);
//                welcomeInstance.frame.setDefaultCloseOperation(welcomeInstance.frame.EXIT_ON_CLOSE);
                welcomeInstance.frame.setLocationRelativeTo(null);
                welcomeInstance.frame.pack();
                welcomeInstance.frame.setVisible(true);
            }
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("LoanWindow");
        frame.setContentPane(new LoanWindow().fullPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
