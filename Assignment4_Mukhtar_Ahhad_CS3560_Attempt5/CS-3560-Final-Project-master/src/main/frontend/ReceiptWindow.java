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
public class ReceiptWindow extends JFrame{
    public JFrame frame = new JFrame("ReceiptWindow");
    public JPanel containerPanel;
    private JPanel textAndLabel;
    private JComboBox<String> receiptValues;
    private JTextPane receiptItems;

    public ReceiptWindow(){

        //receiptValues.addActionListener(new ReceiptListener());
        ShowReceiptValues();

    }


    public void ShowReceiptValues(){
        try{
    //          String query = "SELECT * from public.student WHERE id =?";
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM public.item");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String itemVal = rs.getString("code");
                receiptValues.addItem(itemVal);
            }
            receiptItems.setText((String)receiptValues.getSelectedItem());

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);

        }

    }


//    private class ReceiptListener implements ActionListener{
//        @Override
//        private actionPerformed(ActionEvent e){
//
//
//
//        }
//    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("ReceiptWindow");
        frame.setSize(500,500);
        frame.setContentPane(new ReceiptWindow().containerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
