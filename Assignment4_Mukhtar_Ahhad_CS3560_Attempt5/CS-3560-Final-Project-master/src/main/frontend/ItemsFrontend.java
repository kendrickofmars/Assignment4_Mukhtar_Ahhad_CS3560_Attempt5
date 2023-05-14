package src.main.frontend;
import src.main.jdbc.ConnectionFactory;
import jakarta.persistence.*;
import javax.swing.*; // Needed for Swing classes
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.Item;

public class ItemsFrontend extends JFrame{
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    JFrame frame = new JFrame("ItemsFrontend");
    private JButton addButton;
    private JButton searchButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton backButton;
    private JLabel ItemCode;
    private JTextField itemType;
    private JComboBox<Boolean> status;
    private JComboBox<String> nameOfItem;
    private JTextField description;
    private JTextField location;
    public JPanel topLevelPanel;
    private JTextField dailyPrice;
    private JTextField txtTitle;


    public ItemsFrontend(){



        addButton.addActionListener(new ItemsListener());
        searchButton.addActionListener(new ItemsListener());
        backButton.addActionListener(new ItemsListener());
        updateButton.addActionListener(new ItemsListener());
        deleteButton.addActionListener(new ItemsListener());


        ShowItemValues();
    }


    public void ShowItemValues(){
        try{
            //          String query = "SELECT * from public.student WHERE id =?";
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM public.item");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){

                boolean [] checkedOut = {true, false};
                for (boolean b : checkedOut) {
                    status.addItem(b);
                }
                String itemName = rs.getString("title");
                nameOfItem.addItem(itemName);
            }
//            receiptItems.setText((String)receiptValues.getSelectedItem());

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);

        }

    }

    private class ItemsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String code = itemType.getText();
            String title = txtTitle.getText();
            String descriptionText = description.getText() ;
            String locationVal = location.getText();
            // Redundant check, already set to true by default
            boolean checkedOutVal = (boolean)status.getSelectedItem();
            double daily_price = Double.parseDouble(dailyPrice.getText());
            Item itemInp = new Item(code, title, descriptionText, locationVal, daily_price);

        if(e.getSource() ==addButton) {
            try {
//                Connection connection = ConnectionFactory.getConnection();
//
//
//                PreparedStatement stmt = connection.prepareStatement("INSERT INTO public.item(\n" +
//                        "\tcode, title, description, status, location, daily_price)\n" +
//                        "\tVALUES (?, ?, ?, ?, ?, ?)");
//                //since we have 4 parameters in this SQL statement, we need to pass each in one-by-one
//                stmt.setString(1, broncoID);
//                stmt.setString(2,name);
//                stmt.setString(3,courseVal);
//                stmt.setString(4,broncoID);
//
//                stmt.executeUpdate();
                transaction.begin();
                itemInp.setStatus((boolean) status.getSelectedItem());
                entityManager.persist(itemInp);
                transaction.commit();


                JOptionPane.showMessageDialog(null, "Item code entered: " + code +
                        "\nName of item: " + title + "\nDescription: " + descriptionText + "\nChecked out? " + checkedOutVal + "\nLocated at: " + locationVal + "\nCost: $" + daily_price);


            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }else if (e.getSource() == updateButton) {
                try {
                    //making a connection to the db and seeing if a row is returned that has the values we want
                    Connection connection = ConnectionFactory.getConnection();
                    System.out.println("Updating Item " + code);
                    PreparedStatement stmt = connection.prepareStatement("UPDATE public.item\n" +
                            "\tSET code=?, title=?, description=?, status=?, location=?, daily_price=?\n" +
                            "\tWHERE code =?");
                    //since we have 4 parameters in this SQL statement, we need to pass each in one-by-one
                    stmt.setString(1, code);
                    stmt.setString(2,title);
                    stmt.setString(3,descriptionText);
                    stmt.setString(4,String.valueOf(checkedOutVal));
                    stmt.setString(5, code);

                    stmt.executeUpdate();
                    //autocommit is ON, so we don't need to use connection.commit();
                    JOptionPane.showMessageDialog(null, "Item code entered: " + code + "\nName of item: " + title + "\nDescription: " + descriptionText + "\nChecked out? " + checkedOutVal + "\nLocated at: " + locationVal + "\nCost: $" + daily_price);
                    System.out.println("Successfully updated item");


                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            } else if (e.getSource() == searchButton) {
                try {
                    //making a connection to the db and seeing if a row is returned that has the values we want
                    Connection connection = ConnectionFactory.getConnection();
                    System.out.println("Searching for item with code: " +itemInp.getCode());
                    //searching for student based off of their ID value
                    PreparedStatement stmt = connection.prepareStatement("SELECT code, title, description,status,location,daily_price\n" +
                            "\tFROM public.item WHERE code =?");

                    stmt.setString(1, code);

                    ResultSet rs = stmt.executeQuery();
                    //autocommit is ON, so we don't need to use connection.commit();
                    while(rs.next()) {
                        if (code.equals(rs.getString("code"))) {
                            JOptionPane.showMessageDialog(null, "Found " + rs.getString("code") + " within db!" +"\nSetting values to ones found in db");
                            System.out.println("Found " + rs.getString("code") + " within db!" + "\nSetting values to ones found in db");
                            //Name, course, id, use setText for each of these textfields
                            itemType.setText(rs.getString("code"));
                            description.setText(rs.getString("description"));
                            status.setSelectedItem(rs.getBoolean("status"));
                            nameOfItem.setSelectedItem(rs.getString("title"));
                            location.setText(rs.getString("location"));
                            dailyPrice.setText(String.valueOf(rs.getDouble("daily_price")));

                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to find student with ID:" + code + " within db!");
                        }
                    }
//                    private JTextField itemType;
//                    private JComboBox<Boolean> status;
//                    private JComboBox<String> nameOfItem;
//                    private JTextField description;
//                    private JTextField location;
//                    public JPanel topLevelPanel;
//                    private JTextField dailyPrice;

                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            } else if (e.getSource() == deleteButton) {
                try {
                    //making a connection to the db and seeing if a row is returned that has the values we want
                    Connection connection = ConnectionFactory.getConnection();
                    System.out.println("Deleting item " + code);
                    //deleting student based on whether or not id matches input
                    PreparedStatement stmt = connection.prepareStatement("DELETE FROM public.item\n" +
                            "\tWHERE code= ?");
//                    stmt.setInt(1, Integer.parseInt(broncoID));
                    stmt.setString(1, code);

                    stmt.executeUpdate();
                    //autocommit is ON, so we don't need to use connection.commit();
                    System.out.println("Successfully deleted student");


                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        else if (e.getSource() == backButton){
                WelcomeWindow welcomeInstance = new WelcomeWindow();
                welcomeInstance.setContentPane(welcomeInstance.revenuePanel);
                welcomeInstance.frame.setLocationRelativeTo(null);
                welcomeInstance.pack();
                welcomeInstance.setVisible(true);
                //topLevelPanel.setVisible(false);

            }



            }
        }
    public static void main(String[] args) {
        JFrame frame = new JFrame("ItemsFrontend");
        frame.setContentPane(new ItemsFrontend().topLevelPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
