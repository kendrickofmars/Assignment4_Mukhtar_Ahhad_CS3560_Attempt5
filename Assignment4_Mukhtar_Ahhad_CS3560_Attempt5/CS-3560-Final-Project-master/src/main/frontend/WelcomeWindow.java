package src.main.frontend;

import entity.Loan;

import javax.swing.*; // Needed for Swing classes
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 This class demonstrates how panels may be added to
 the cells created by a GridLayout manager.
 */

public class WelcomeWindow extends JFrame
{
    JFrame frame = new JFrame("WelcomeWindow");
    private JButton generateRevenueReportButton;
    public JPanel revenuePanel;
    private JPanel choicesPanel;
    private JButton StudentFrontendButton;
    private JButton loansButton;
    private JButton itemsButton;

    /**
     Constructor
     */

    public WelcomeWindow()
    {

        StudentFrontendButton.addActionListener(new WelcomeButtons());
        StudentFrontendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Frame[] to_die  = WelcomeWindow.getFrames();
            to_die[0].dispose(); }
        });
        loansButton.addActionListener(new WelcomeButtons());
        itemsButton.addActionListener(new WelcomeButtons());
        generateRevenueReportButton.addActionListener(new WelcomeButtons());

    }
    private class WelcomeButtons implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == StudentFrontendButton){

                //create a new StudentFrontend object and set its components to visible
                StudentFrontend studentWindow = new StudentFrontend();
                studentWindow.frame.setContentPane(new StudentFrontend().nameAndButtons);
                studentWindow.frame.setLocationRelativeTo(null);
                studentWindow.frame.pack();
                studentWindow.frame.setVisible(true);

            }
            else if(e.getSource() == loansButton){
                LoanWindow loanInstance = new LoanWindow();
                loanInstance.frame.setContentPane(new LoanWindow().fullPanel);
                loanInstance.frame.setLocationRelativeTo(null);
                loanInstance.frame.pack();
                loanInstance.frame.setVisible(true);
            }
            else if(e.getSource() == itemsButton){
                ItemsFrontend itemInstance = new ItemsFrontend();
                itemInstance.setContentPane(itemInstance.topLevelPanel);
                itemInstance.frame.setLocationRelativeTo(null);
                itemInstance.pack();
                itemInstance.setVisible(true);
            }
            else if(e.getSource() == generateRevenueReportButton){
                Loan loanValue = new Loan();

                JOptionPane.showMessageDialog(null, "Revenue is: $1000000" );
            }




        }




    }

    /**
     The main method creates an instance of the
     GridPanelWindow class, displaying its window.
     */

    public static void main(String[] args) {
        JFrame frame = new JFrame("WelcomeWindow");
        frame.setSize(500,500);
        frame.setContentPane(new WelcomeWindow().revenuePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}