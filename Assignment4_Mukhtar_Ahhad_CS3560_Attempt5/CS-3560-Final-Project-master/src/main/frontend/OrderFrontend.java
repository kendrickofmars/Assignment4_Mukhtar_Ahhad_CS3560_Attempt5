package src.main.frontend;
import entity.Customer;
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
import java.util.ArrayList;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import src.main.jdbc.ConnectionFactory;

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




    public void ShowCustomerValues() {
        try {
            //          String query = "SELECT * from public.student WHERE id =?";
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM public.\"Customer\"");
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                ArrayList<String> customerArray = new ArrayList<>();

                customerArray.add(rs.getString("name"));

                for(String s: customerArray){
                    customerBox.addItem(s);
                }
            }
//            receiptItems.setText((String)receiptValues.getSelectedItem());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public OrderFrontend() {
//        SessionFactory factory = new Configuration().configure;
        String[] itemArray = {"Caesar Salad", "Greek Salad", "Cobb Salad"};
        Customer[] customerArray = {};
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

        ShowCustomerValues();
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
            String customerName = (String)customerBox.getSelectedItem();
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

            Customer customerInst = new Customer();
            Order orderInst = new Order(orderNumber,java_sql_date,itemSelected,price, customerInst);
            if(e.getSource() == searchButton){
                try {
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt = connection.prepareStatement("SELECT date, item, price, \"number\", customer_id\n" +
                            "\tFROM public.\"Order\" WHERE \"number\" = ?");
                    stmt.setInt(1, orderNumber);
                    ResultSet rs = stmt.executeQuery();


                    while(rs.next()) {
                        if (orderNumber == rs.getInt("number")) {
                            JOptionPane.showMessageDialog(null,"Found Order#" + rs.getInt("number") +
                                    " within db, \nSetting fields to values found");
                            numberField.setText(String.valueOf(rs.getInt("number")));
                            dateField.setText(String.valueOf(rs.getDate("date")));
                            PriceField.setText(rs.getString("price"));

                            PreparedStatement stmt2 = connection.prepareStatement("SELECT name, phone, email, id, address_id\n" +
                                    "\tFROM public.\"Customer\" WHERE name = ?");
                            stmt2.setString(1, customerName);
                            ResultSet rs2 = stmt2.executeQuery();

                            if(rs2.next()) {
                                customerBox.setSelectedItem(rs.getString("name"));
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


            }else if(e.getSource() == addButton){
                transaction.begin();
                entityManager.persist(orderInst);

                transaction.commit();


            }else if(e.getSource() == updateButton){
                try{
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt1 = connection.prepareStatement("UPDATE public.\"Order\"\n" +
                            "\tSET date=?, item=?, price=?, \"number\"=? "+
                            "\tWHERE \"number\" = ?");
                    stmt1.setDate(1,java_sql_date);
                    stmt1.setString(2,itemSelected);
                    stmt1.setDouble(3, price);
                    stmt1.setInt(4,orderNumber);

                    stmt1.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Order Successfully Updated");
                    numberField.setText("");
                    dateField.setText("");
                    customerBox.setSelectedItem(null);
                    itemBox.setSelectedItem(null);
                    PriceField.setText("");

                }catch(ClassNotFoundException ex2){
                    throw new RuntimeException(ex2);
                }catch(SQLException ex2){
                    throw new RuntimeException(ex2);
                }

            }else if(e.getSource() == deleteButton){
                try{
                    Connection connection = ConnectionFactory.getConnection();
                    PreparedStatement stmt1 = connection.prepareStatement("DELETE FROM public.\"Order\"\n" +
                            "\tWHERE \"number\" = ?");
                    stmt1.setInt(1,orderNumber);

                    stmt1.executeQuery();
                    JOptionPane.showMessageDialog(null, "Order Successfully deleted");
                    numberField.setText("");
                    dateField.setText("");
                    customerBox.setSelectedItem(null);
                    itemBox.setSelectedItem(null);
                    PriceField.setText("");

                }catch(ClassNotFoundException ex2){
                    throw new RuntimeException(ex2);
                }catch(SQLException ex2){
                    throw new RuntimeException(ex2);
                }
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
