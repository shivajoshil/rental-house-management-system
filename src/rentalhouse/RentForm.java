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

public class RentForm extends JFrame implements ActionListener{

	

	    JLabel title, l1, l2, l3, l4;

	    JComboBox<String> tenantBox;
	    JTextField amount, paymentDate;

	    JComboBox<String> statusBox;

	    JButton pay, clear, delete, back;

	    JTable table;
	    DefaultTableModel model;

	    RentForm() {

	        setTitle("Rent Management");
	        setSize(900, 600);
	        setLayout(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        // TITLE
	        title = new JLabel("RENT MANAGEMENT");
	        title.setFont(new Font("Arial", Font.BOLD, 24));
	        title.setBounds(330, 20, 300, 40);
	        add(title);

	        // TENANT
	        l1 = new JLabel("Tenant:");
	        l1.setBounds(50, 100, 100, 30);
	        add(l1);

	        tenantBox = new JComboBox<>();
	        tenantBox.setBounds(150, 100, 200, 30);
	        add(tenantBox);

	        // AMOUNT
	        l2 = new JLabel("Rent Amount:");
	        l2.setBounds(50, 150, 100, 30);
	        add(l2);

	        amount = new JTextField();
	        amount.setBounds(150, 150, 200, 30);
	        add(amount);

	        // PAYMENT DATE
	        l3 = new JLabel("Payment Date:");
	        l3.setBounds(50, 200, 100, 30);
	        add(l3);

	        paymentDate = new JTextField();
	        paymentDate.setBounds(150, 200, 200, 30);
	        add(paymentDate);

	        // STATUS
	        l4 = new JLabel("Status:");
	        l4.setBounds(50, 250, 100, 30);
	        add(l4);

	        String[] statuses = {
	            "PAID",
	            "PENDING"
	        };

	        statusBox = new JComboBox<>(statuses);
	        statusBox.setBounds(150, 250, 200, 30);
	        add(statusBox);

	        // PAY BUTTON
	        pay = new JButton("SAVE");
	        pay.setBounds(50, 310, 100, 35);
	        pay.addActionListener(this);
	        add(pay);

	        // CLEAR BUTTON
	        clear = new JButton("CLEAR");
	        clear.setBounds(160, 310, 100, 35);
	        clear.addActionListener(this);
	        add(clear);

	        // DELETE BUTTON
	        delete = new JButton("DELETE");
	        delete.setBounds(270, 310, 100, 35);
	        delete.addActionListener(this);
	        add(delete);

	        // BACK BUTTON
	        back = new JButton("BACK");
	        back.setBounds(380, 310, 100, 35);
	        back.addActionListener(this);
	        add(back);

	        // TABLE
	        model = new DefaultTableModel();

	        model.addColumn("Rent ID");
	        model.addColumn("Tenant ID");
	        model.addColumn("Amount");
	        model.addColumn("Payment Date");
	        model.addColumn("Status");

	        table = new JTable(model);

	        JScrollPane scroll = new JScrollPane(table);
	        scroll.setBounds(400, 90, 450, 180);
	        add(scroll);

	        // LOAD TENANTS
	        loadTenants();

	        // LOAD RENT RECORDS
	        loadRent();

	        setVisible(true);
	    }

	    // LOAD TENANTS
	    void loadTenants() {

	        tenantBox.removeAllItems();

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

	            String sql = "SELECT tenant_id, name FROM tenants";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {

	                tenantBox.addItem(
	                    rs.getInt("tenant_id")
	                    + " - "
	                    + rs.getString("name")
	                );
	            }

	            rs.close();
	            ps.close();
	            con.close();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Error loading tenants: "
	                + ex.getMessage()
	            );
	        }
	    }

	    // SAVE RENT
	    void saveRent() {

	        if (tenantBox.getSelectedItem() == null ||
	            amount.getText().isEmpty() ||
	            paymentDate.getText().isEmpty()) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Please fill all fields"
	            );

	            return;
	        }

	        try {

	            // Get Tenant ID
	            String tenant =
	                tenantBox.getSelectedItem().toString();

	            int tenantId =
	                Integer.parseInt(
	                    tenant.split(" - ")[0]
	                );

	            double rentAmount =
	                Double.parseDouble(
	                    amount.getText()
	                );

	            Date date =
	                Date.valueOf(
	                    paymentDate.getText()
	                );

	            String status =
	                statusBox.getSelectedItem().toString();

	            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

	            String sql =
	                "INSERT INTO rent_payments " +
	                "(tenant_id, amount, payment_date, status) " +
	                "VALUES (?, ?, ?, ?)";

	            PreparedStatement ps =
	                con.prepareStatement(sql);

	            ps.setInt(1, tenantId);
	            ps.setDouble(2, rentAmount);
	            ps.setDate(3, date);
	            ps.setString(4, status);

	            ps.executeUpdate();

	            JOptionPane.showMessageDialog(
	                this,
	                "Rent Saved Successfully!"
	            );

	            ps.close();
	            con.close();

	            clearFields();
	            loadRent();

	        } catch (NumberFormatException ex) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Rent amount must be a number"
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

	    // LOAD RENT RECORDS
	    void loadRent() {

	        model.setRowCount(0);

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);


	            String sql =
	                "SELECT * FROM rent_payments";

	            PreparedStatement ps =
	                con.prepareStatement(sql);

	            ResultSet rs =
	                ps.executeQuery();

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
	                "Error loading rent: "
	                + ex.getMessage()
	            );
	        }
	    }

	    // DELETE RENT
	    void deleteRent() {

	        int row = table.getSelectedRow();

	        if (row == -1) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Please select a rent record"
	            );

	            return;
	        }

	        int rentId =
	            Integer.parseInt(
	                model.getValueAt(row, 0).toString()
	            );

	        int choice = JOptionPane.showConfirmDialog(
	            this,
	            "Do you want to delete this rent record?",
	            "Confirm Delete",
	            JOptionPane.YES_NO_OPTION
	        );

	        if (choice == JOptionPane.YES_OPTION) {

	            try {

	            	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

	    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

	    		    System.out.println(con);


	                String sql =
	                    "DELETE FROM rent_payments " +
	                    "WHERE rent_id=?";

	                PreparedStatement ps =
	                    con.prepareStatement(sql);

	                ps.setInt(1, rentId);

	                ps.executeUpdate();

	                JOptionPane.showMessageDialog(
	                    this,
	                    "Rent Record Deleted"
	                );

	                ps.close();
	                con.close();

	                loadRent();

	            } catch (Exception ex) {

	                JOptionPane.showMessageDialog(
	                    this,
	                    "Error: " + ex.getMessage()
	                );
	            }
	        }
	    }

	    // CLEAR
	    void clearFields() {

	        amount.setText("");
	        paymentDate.setText("");

	        statusBox.setSelectedIndex(0);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {

	        if (e.getSource() == pay) {

	            saveRent();

	        } else if (e.getSource() == clear) {

	            clearFields();

	        } else if (e.getSource() == delete) {

	            deleteRent();

	        } else if (e.getSource() == back) {

	            new Dashboard();
	            dispose();
	        }
	    }

	    public static void main(String[] args) {

	        new RentForm();
	    }
	
}
