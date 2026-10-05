package rentalhouse;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Reports extends JFrame implements ActionListener {

	    JLabel title;

	    JButton propertyReport;
	    JButton tenantReport;
	    JButton rentReport;
	    JButton maintenanceReport;
	    JButton back;

	    JTable table;
	    DefaultTableModel model;

	    Reports() {

	        setTitle("Rental House Reports");
	        setSize(1000, 600);
	        setLayout(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        // ================= TITLE =================

	        title = new JLabel("RENTAL HOUSE REPORTS");
	        title.setFont(new Font("Arial", Font.BOLD, 24));
	        title.setBounds(350, 20, 350, 40);
	        add(title);

	        // ================= PROPERTY REPORT =================

	        propertyReport = new JButton("PROPERTY");
	        propertyReport.setBounds(40, 90, 140, 35);
	        propertyReport.addActionListener(this);
	        add(propertyReport);

	        // ================= TENANT REPORT =================

	        tenantReport = new JButton("TENANT");
	        tenantReport.setBounds(190, 90, 140, 35);
	        tenantReport.addActionListener(this);
	        add(tenantReport);

	        // ================= RENT REPORT =================

	        rentReport = new JButton("RENT");
	        rentReport.setBounds(340, 90, 140, 35);
	        rentReport.addActionListener(this);
	        add(rentReport);

	        // ================= MAINTENANCE REPORT =================

	        maintenanceReport = new JButton("MAINTENANCE");
	        maintenanceReport.setBounds(490, 90, 140, 35);
	        maintenanceReport.addActionListener(this);
	        add(maintenanceReport);

	        // ================= BACK =================

	        back = new JButton("BACK");
	        back.setBounds(640, 90, 140, 35);
	        back.addActionListener(this);
	        add(back);

	        // ================= TABLE =================

	        model = new DefaultTableModel();

	        table = new JTable(model);

	        JScrollPane scroll = new JScrollPane(table);
	        scroll.setBounds(40, 150, 900, 350);
	        add(scroll);

	        setVisible(true);
	    }

	    // =========================================================
	    // PROPERTY REPORT
	    // =========================================================

	    void propertyReport() {

	        model.setRowCount(0);
	        model.setColumnCount(0);

	        model.addColumn("Property ID");
	        model.addColumn("House Number");
	        model.addColumn("Type");
	        model.addColumn("Address");
	        model.addColumn("Rent");
	        model.addColumn("Status");

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

	            String sql =
	                    "SELECT property_id, house_number, property_type, "
	                    + "address, rent, status FROM properties";

	            PreparedStatement ps =
	                    con.prepareStatement(sql);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {

	                model.addRow(new Object[] {
	                        rs.getInt("property_id"),
	                        rs.getString("house_number"),
	                        rs.getString("property_type"),
	                        rs.getString("address"),
	                        rs.getDouble("rent"),
	                        rs.getString("status")
	                });
	            }

	            rs.close();
	            ps.close();
	            con.close();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                    this,
	                    "Error: " + ex.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // TENANT REPORT
	    // =========================================================

	    void tenantReport() {

	        model.setRowCount(0);
	        model.setColumnCount(0);

	        model.addColumn("Tenant ID");
	        model.addColumn("Name");
	        model.addColumn("Phone");
	        model.addColumn("Email");
	        model.addColumn("Address");

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

	            String sql =
	                    "SELECT tenant_id, name, phone, email, address "
	                    + "FROM tenants";

	            PreparedStatement ps =
	                    con.prepareStatement(sql);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {

	                model.addRow(new Object[] {
	                        rs.getInt("tenant_id"),
	                        rs.getString("name"),
	                        rs.getString("phone"),
	                        rs.getString("email"),
	                        rs.getString("address")
	                });
	            }

	            rs.close();
	            ps.close();
	            con.close();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                    this,
	                    "Error: " + ex.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // RENT REPORT
	    // =========================================================

	    void rentReport() {

	        model.setRowCount(0);
	        model.setColumnCount(0);

	        model.addColumn("Rent ID");
	        model.addColumn("Tenant ID");
	        model.addColumn("Amount");
	        model.addColumn("Payment Date");
	        model.addColumn("Status");

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

	            String sql =
	                    "SELECT rent_id, tenant_id, amount, "
	                    + "payment_date, status FROM rent_payments";

	            PreparedStatement ps =
	                    con.prepareStatement(sql);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {

	                model.addRow(new Object[] {
	                        rs.getInt("rent_id"),
	                        rs.getInt("tenant_id"),
	                        rs.getDouble("amount"),
	                        rs.getDate("payment_date"),
	                        rs.getString("status")
	                });
	            }

	            rs.close();
	            ps.close();
	            con.close();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                    this,
	                    "Error: " + ex.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // MAINTENANCE REPORT
	    // =========================================================

	    void maintenanceReport() {

	        model.setRowCount(0);
	        model.setColumnCount(0);

	        model.addColumn("Maintenance ID");
	        model.addColumn("Property ID");
	        model.addColumn("Issue");
	        model.addColumn("Cost");
	        model.addColumn("Date");
	        model.addColumn("Status");

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

	            String sql =
	                    "SELECT maintenance_id, property_id, issue, "
	                    + "cost, maintenance_date, status "
	                    + "FROM maintenance";

	            PreparedStatement ps =
	                    con.prepareStatement(sql);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {

	                model.addRow(new Object[] {
	                        rs.getInt("maintenance_id"),
	                        rs.getInt("property_id"),
	                        rs.getString("issue"),
	                        rs.getDouble("cost"),
	                        rs.getDate("maintenance_date"),
	                        rs.getString("status")
	                });
	            }

	            rs.close();
	            ps.close();
	            con.close();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                    this,
	                    "Error: " + ex.getMessage()
	            );
	        }
	    }

	    // =========================================================
	    // BUTTON ACTIONS
	    // =========================================================

	    @Override
	    public void actionPerformed(ActionEvent e) {

	        if (e.getSource() == propertyReport) {

	            propertyReport();

	        } else if (e.getSource() == tenantReport) {

	            tenantReport();

	        } else if (e.getSource() == rentReport) {

	            rentReport();

	        } else if (e.getSource() == maintenanceReport) {

	            maintenanceReport();

	        } else if (e.getSource() == back) {

	            new Dashboard();
	            dispose();
	        }
	    }

	    // =========================================================
	    // MAIN
	    // =========================================================

	    public static void main(String[] args) {

	        new Reports();
	    }
	
}
