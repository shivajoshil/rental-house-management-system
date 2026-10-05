package rentalhouse;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MaintenanceForm extends JFrame implements ActionListener {

    JLabel title, l1, l2, l3, l4, l5;

    JComboBox<String> propertyBox;
    JComboBox<String> statusBox;

    JTextField issue, cost, maintenanceDate;

    JButton save, clear, delete, back;

    JTable table;
    DefaultTableModel model;

    MaintenanceForm() {

        setTitle("Maintenance Management");
        setSize(950, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ================= TITLE =================

        title = new JLabel("MAINTENANCE MANAGEMENT");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(300, 20, 400, 40);
        add(title);

        // ================= PROPERTY =================

        l1 = new JLabel("Property:");
        l1.setBounds(40, 90, 120, 30);
        add(l1);

        propertyBox = new JComboBox<String>();
        propertyBox.setBounds(160, 90, 200, 30);
        add(propertyBox);

        // ================= ISSUE =================

        l2 = new JLabel("Issue:");
        l2.setBounds(40, 140, 120, 30);
        add(l2);

        issue = new JTextField();
        issue.setBounds(160, 140, 200, 30);
        add(issue);

        // ================= COST =================

        l3 = new JLabel("Cost:");
        l3.setBounds(40, 190, 120, 30);
        add(l3);

        cost = new JTextField();
        cost.setBounds(160, 190, 200, 30);
        add(cost);

        // ================= DATE =================

        l4 = new JLabel("Date:");
        l4.setBounds(40, 240, 120, 30);
        add(l4);

        maintenanceDate = new JTextField();
        maintenanceDate.setBounds(160, 240, 200, 30);
        add(maintenanceDate);

        // ================= STATUS =================

        l5 = new JLabel("Status:");
        l5.setBounds(40, 290, 120, 30);
        add(l5);

        statusBox = new JComboBox<String>(
                new String[] {
                        "PENDING",
                        "IN PROGRESS",
                        "COMPLETED"
                }
        );

        statusBox.setBounds(160, 290, 200, 30);
        add(statusBox);

        // ================= SAVE =================

        save = new JButton("SAVE");
        save.setBounds(40, 350, 100, 35);
        save.addActionListener(this);
        add(save);

        // ================= CLEAR =================

        clear = new JButton("CLEAR");
        clear.setBounds(150, 350, 100, 35);
        clear.addActionListener(this);
        add(clear);

        // ================= DELETE =================

        delete = new JButton("DELETE");
        delete.setBounds(260, 350, 100, 35);
        delete.addActionListener(this);
        add(delete);

        // ================= BACK =================

        back = new JButton("BACK");
        back.setBounds(370, 350, 100, 35);
        back.addActionListener(this);
        add(back);

        // ================= TABLE =================

        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Property ID");
        model.addColumn("Issue");
        model.addColumn("Cost");
        model.addColumn("Date");
        model.addColumn("Status");

        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(400, 90, 500, 220);
        add(scroll);

        // Load data
        loadProperties();
        loadMaintenance();

        setVisible(true);
    }

    // =========================================================
    // LOAD PROPERTIES
    // =========================================================

    void loadProperties() {

        propertyBox.removeAllItems();

        try {

        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

		    System.out.println(con);
		    
            String sql =
                    "SELECT property_id, house_number " +
                    "FROM properties";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                propertyBox.addItem(
                        rs.getInt("property_id")
                        + " - "
                        + rs.getString("house_number")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading properties: "
                    + ex.getMessage()
            );
        }
    }

    // =========================================================
    // SAVE MAINTENANCE
    // =========================================================

    void saveMaintenance() {

        if (propertyBox.getSelectedItem() == null
                || issue.getText().trim().isEmpty()
                || cost.getText().trim().isEmpty()
                || maintenanceDate.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields"
            );

            return;
        }

        try {

            // Get Property ID
            String property =
                    propertyBox.getSelectedItem().toString();

            int propertyId =
                    Integer.parseInt(
                            property.split(" - ")[0]
                    );

            String issueText =
                    issue.getText().trim();

            double maintenanceCost =
                    Double.parseDouble(
                            cost.getText().trim()
                    );

            Date date =
                    Date.valueOf(
                            maintenanceDate.getText().trim()
                    );

            String status =
                    statusBox.getSelectedItem().toString();

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

		    System.out.println(con);
		    
            String sql =
                    "INSERT INTO maintenance "
                    + "(property_id, issue, cost, maintenance_date, status) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, propertyId);
            ps.setString(2, issueText);
            ps.setDouble(3, maintenanceCost);
            ps.setDate(4, date);
            ps.setString(5, status);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Maintenance Saved Successfully!"
            );

            ps.close();
            con.close();

            clearFields();
            loadMaintenance();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cost must be a number"
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Date format must be YYYY-MM-DD"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage()
            );
        }
    }

    // =========================================================
    // LOAD MAINTENANCE
    // =========================================================

    void loadMaintenance() {

        model.setRowCount(0);

        try {

        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

		    System.out.println(con);
		    
            String sql =
                    "SELECT * FROM maintenance";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                model.addRow(
                        new Object[] {
                                rs.getInt("maintenance_id"),
                                rs.getInt("property_id"),
                                rs.getString("issue"),
                                rs.getDouble("cost"),
                                rs.getDate("maintenance_date"),
                                rs.getString("status")
                        }
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading maintenance: "
                    + ex.getMessage()
            );
        }
    }

    // =========================================================
    // DELETE MAINTENANCE
    // =========================================================

    void deleteMaintenance() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a maintenance record"
            );

            return;
        }

        int id =
                Integer.parseInt(
                        model.getValueAt(row, 0).toString()
                );

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to delete this record?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice == JOptionPane.YES_OPTION) {

            try {

            	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

                String sql =
                        "DELETE FROM maintenance "
                        + "WHERE maintenance_id=?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setInt(1, id);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Maintenance Deleted Successfully"
                );

                ps.close();
                con.close();

                loadMaintenance();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex.getMessage()
                );
            }
        }
    }

    // =========================================================
    // CLEAR FIELDS
    // =========================================================

    void clearFields() {

        issue.setText("");
        cost.setText("");
        maintenanceDate.setText("");

        statusBox.setSelectedIndex(0);

        if (propertyBox.getItemCount() > 0) {
            propertyBox.setSelectedIndex(0);
        }
    }

    // =========================================================
    // BUTTON ACTIONS
    // =========================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == save) {

            saveMaintenance();

        } else if (e.getSource() == clear) {

            clearFields();

        } else if (e.getSource() == delete) {

            deleteMaintenance();

        } else if (e.getSource() == back) {

            new Dashboard();
            dispose();
        }
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        new MaintenanceForm();
    }
}