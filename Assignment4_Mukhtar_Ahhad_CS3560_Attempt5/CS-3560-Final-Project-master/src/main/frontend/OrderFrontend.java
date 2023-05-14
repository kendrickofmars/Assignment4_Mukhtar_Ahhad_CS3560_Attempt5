package src.main.frontend;
import entity.Order;


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

public class OrderFrontend extends JFrame{
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    private JPanel orderContentPane;
    private JTextField numberField;
    private JTextField dateField;
    private JLabel DateLabel;
    private JLabel NumberLabel;
    private JComboBox customerBox;
    private JPanel customerItemPrice;
    private JComboBox itemBox;
    private JLabel CustomerLabel;
    private JLabel ItemLabel;
    private JTextField PriceField;
    private JLabel PriceLabel;
    private JButton searchButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton addButton;
    private JPanel buttonPanel;
    private JPanel numberAndDatePanel;


    public OrderFrontend() {
//        SessionFactory factory = new Configuration().configure;
        String[] itemArray = {"Caesar Salad", "Greek Salad", "Cobb Salad"};
        searchButton.addActionListener(new ButtonAndComboListener());
        deleteButton.addActionListener(new ButtonAndComboListener());
        addButton.addActionListener(new ButtonAndComboListener());
        updateButton.addActionListener(new ButtonAndComboListener());
//        customerBox.addActionListener(new ButtonAndComboListener());
        deleteButton.addActionListener(new ButtonAndComboListener());
//populating itemBox combobox with hardcoded items from itemArray
        for (int i = 0; i<itemArray.length; i++){
            itemBox.addItem(itemArray[i]);
        }
    }


    public class ButtonAndComboListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int orderNumber = Integer.parseInt(numberField.getText());
            //going to parse into java.util.date and then java.sql.date
            //must put date parsing in try-catch block, but make declare date vars in scope of call
            String date = dateField.getText();
            String itemSelected = (String)itemBox.getSelectedItem();
            Double price = Double.parseDouble(PriceField.getText());
            int customerIDVal = Integer.parseInt((String)customerBox.getSelectedItem());
            //int number, java.sql.Date date, String item, double price, int customer_id
            java.util.Date java_util_date;
            java.sql.Date java_sql_date;

            try {
                java_util_date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                java_sql_date = new java.sql.Date(java_util_date.getTime());
            }catch(ParseException ex){
                JOptionPane.showMessageDialog(null,ex);
                throw new RuntimeException(ex);
            }
            Order newOrder = new Order(orderNumber,java_sql_date,itemSelected,price);
            if(e.getSource() == searchButton){



            }else if(e.getSource() == addButton){



            }else if(e.getSource() == updateButton){

            }else if(e.getSource() == deleteButton){

            }

        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("OrderFrontend");

        frame.setContentPane(new OrderFrontend().orderContentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
