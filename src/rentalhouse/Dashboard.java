package rentalhouse;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Dashboard extends JFrame implements ActionListener {

    JLabel title;

    JButton property;
    JButton tenant;
    JButton rent;
    JButton maintenance;
    JButton reports;
    JButton logout;
    JButton allocation;

    Dashboard() {

        // Frame
        setTitle("Rental House Management System");
        setSize(700, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Title
        title = new JLabel("RENTAL HOUSE MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(130, 40, 500, 40);
        add(title);

        // PROPERTY BUTTON
        property = new JButton("PROPERTY");
        property.setBounds(80, 130, 150, 50);
        property.addActionListener(this);
        add(property);

        // TENANT BUTTON
        tenant = new JButton("TENANT");
        tenant.setBounds(270, 130, 150, 50);
        tenant.addActionListener(this);
        add(tenant);

        // RENT BUTTON
        rent = new JButton("RENT");
        rent.setBounds(460, 130, 150, 50);
        rent.addActionListener(this);
        add(rent);

        // MAINTENANCE BUTTON
        maintenance = new JButton("MAINTENANCE");
        maintenance.setBounds(80, 210, 150, 50);
        maintenance.addActionListener(this);
        add(maintenance);

        // REPORTS BUTTON
        reports = new JButton("REPORTS");
        reports.setBounds(270, 210, 150, 50);
        reports.addActionListener(this);
        add(reports);

        // ALLOCATION BUTTON
        allocation = new JButton("ALLOCATION");
        allocation.setBounds(460, 210, 150, 50);
        allocation.addActionListener(this);
        add(allocation);

        // LOGOUT BUTTON
        logout = new JButton("LOGOUT");
        logout.setBounds(270, 310, 150, 50);
        logout.addActionListener(this);
        add(logout);

        // Show Frame
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // PROPERTY
        if (e.getSource() == property) {

            new Property();
            dispose();
        }

        // TENANT
        else if (e.getSource() == tenant) {

            new Tenant();
            dispose();
        }

        // RENT
        else if (e.getSource() == rent) {

            new RentForm();
            dispose();
        }

        // MAINTENANCE
        else if (e.getSource() == maintenance) {

            new MaintenanceForm();
            dispose();
        }

        // ALLOCATION
        else if (e.getSource() == allocation) {

            new HouseAllocation();
            dispose();
        }

        // REPORTS
        else if (e.getSource() == reports) {

            new Reports();
            dispose();
        }

        // LOGOUT
        else if (e.getSource() == logout) {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                new login();
                dispose();
            }
        }
    }

    public static void main(String[] args) {

        new Dashboard();
    }
}