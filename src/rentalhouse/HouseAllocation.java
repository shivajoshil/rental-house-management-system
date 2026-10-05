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

public class HouseAllocation extends JFrame implements ActionListener {

	    JLabel title, l1, l2, l3;

	    JComboBox<String> propertyBox;
	    JComboBox<String> tenantBox;
	    JTextField date;

	    JButton allocate, clear, delete, back;

	    JTable table;
	    DefaultTableModel model;

	    HouseAllocation() {

	        setTitle("House Allocation");
	        setSize(900, 600);
	        setLayout(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        // TITLE
	        title = new JLabel("HOUSE ALLOCATION");
	        title.setFont(new Font("Arial", Font.BOLD, 24));
	        title.setBounds(330, 20, 300, 40);
	        add(title);

	        // PROPERTY
	        l1 = new JLabel("Property:");
	        l1.setBounds(50, 100, 100, 30);
	        add(l1);

	        propertyBox = new JComboBox<>();
	        propertyBox.setBounds(150, 100, 200, 30);
	        add(propertyBox);

	        // TENANT
	        l2 = new JLabel("Tenant:");
	        l2.setBounds(50, 150, 100, 30);
	        add(l2);

	        tenantBox = new JComboBox<>();
	        tenantBox.setBounds(150, 150, 200, 30);
	        add(tenantBox);

	        // DATE
	        l3 = new JLabel("Date:");
	        l3.setBounds(50, 200, 100, 30);
	        add(l3);

	        date = new JTextField();
	        date.setBounds(150, 200, 200, 30);
	        add(date);

	        // ALLOCATE
	        allocate = new JButton("ALLOCATE");
	        allocate.setBounds(50, 270, 120, 35);
	        allocate.addActionListener(this);
	        add(allocate);

	        // CLEAR
	        clear = new JButton("CLEAR");
	        clear.setBounds(180, 270, 100, 35);
	        clear.addActionListener(this);
	        add(clear);

	        // DELETE
	        delete = new JButton("DELETE");
	        delete.setBounds(290, 270, 100, 35);
	        delete.addActionListener(this);
	        add(delete);

	        // BACK
	        back = new JButton("BACK");
	        back.setBounds(400, 270, 100, 35);
	        back.addActionListener(this);
	        add(back);

	        // TABLE
	        model = new DefaultTableModel();

	        model.addColumn("Allocation ID");
	        model.addColumn("Property ID");
	        model.addColumn("Tenant ID");
	        model.addColumn("Date");

	        table = new JTable(model);

	        JScrollPane scroll = new JScrollPane(table);
	        scroll.setBounds(400, 90, 450, 150);
	        add(scroll);

	        // LOAD DATA
	        loadProperties();
	        loadTenants();
	        loadAllocations();

	        setVisible(true);
	    }

	    // LOAD AVAILABLE PROPERTIES
	    void loadProperties() {

	        propertyBox.removeAllItems();

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);
    		    
	            String sql =
	                "SELECT property_id, house_number " +
	                "FROM properties WHERE status='AVAILABLE'";

	            PreparedStatement ps =
	                con.prepareStatement(sql);

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

	    // LOAD TENANTS
	    void loadTenants() {

	        tenantBox.removeAllItems();

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);
    		    
	            String sql =
	                "SELECT tenant_id, name FROM tenants";

	            PreparedStatement ps =
	                con.prepareStatement(sql);

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

	    // ALLOCATE HOUSE
	    void allocateHouse() {

	        if (propertyBox.getSelectedItem() == null ||
	            tenantBox.getSelectedItem() == null ||
	            date.getText().isEmpty()) {

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

	            // Get Tenant ID
	            String tenant =
	                tenantBox.getSelectedItem().toString();

	            int tenantId =
	                Integer.parseInt(
	                    tenant.split(" - ")[0]
	                );

	            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);
    		    
	            // Insert allocation
	            String sql =
	                "INSERT INTO house_allocation " +
	                "(property_id, tenant_id, allocation_date) " +
	                "VALUES (?, ?, ?)";

	            PreparedStatement ps =
	                con.prepareStatement(sql);

	            ps.setInt(1, propertyId);
	            ps.setInt(2, tenantId);
	            ps.setDate(
	                3,
	                Date.valueOf(date.getText())
	            );

	            ps.executeUpdate();

	            // Change property status
	            String updateSql =
	                "UPDATE properties " +
	                "SET status='RENTED' " +
	                "WHERE property_id=?";

	            PreparedStatement ps2 =
	                con.prepareStatement(updateSql);

	            ps2.setInt(1, propertyId);

	            ps2.executeUpdate();

	            JOptionPane.showMessageDialog(
	                this,
	                "House Allocated Successfully!"
	            );

	            ps.close();
	            ps2.close();
	            con.close();

	            clearFields();

	            loadProperties();
	            loadTenants();
	            loadAllocations();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Error: " + ex.getMessage()
	            );
	        }
	    }

	    // LOAD ALLOCATIONS
	    void loadAllocations() {

	        model.setRowCount(0);

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);
    		    
	            String sql =
	                "SELECT * FROM house_allocation";

	            PreparedStatement ps =
	                con.prepareStatement(sql);

	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {

	                model.addRow(new Object[] {

	                    rs.getInt("allocation_id"),

	                    rs.getInt("property_id"),

	                    rs.getInt("tenant_id"),

	                    rs.getDate("allocation_date")
	                });
	            }

	            rs.close();
	            ps.close();
	            con.close();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Error loading allocation: "
	                + ex.getMessage()
	            );
	        }
	    }

	    // DELETE ALLOCATION
	    void deleteAllocation() {

	        int row = table.getSelectedRow();

	        if (row == -1) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Please select an allocation"
	            );

	            return;
	        }

	        int allocationId =
	            Integer.parseInt(
	                model.getValueAt(row, 0).toString()
	            );

	        int propertyId =
	            Integer.parseInt(
	                model.getValueAt(row, 1).toString()
	            );

	        int choice = JOptionPane.showConfirmDialog(
	            this,
	            "Do you want to remove this allocation?",
	            "Confirm",
	            JOptionPane.YES_NO_OPTION
	        );

	        if (choice == JOptionPane.YES_OPTION) {

	            try {

	            	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

	    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

	    		    System.out.println(con);
	    		    
	                // Delete allocation
	                String sql =
	                    "DELETE FROM house_allocation " +
	                    "WHERE allocation_id=?";

	                PreparedStatement ps =
	                    con.prepareStatement(sql);

	                ps.setInt(1, allocationId);

	                ps.executeUpdate();

	                // Make property available again
	                String updateSql =
	                    "UPDATE properties " +
	                    "SET status='AVAILABLE' " +
	                    "WHERE property_id=?";

	                PreparedStatement ps2 =
	                    con.prepareStatement(updateSql);

	                ps2.setInt(1, propertyId);

	                ps2.executeUpdate();

	                JOptionPane.showMessageDialog(
	                    this,
	                    "Allocation Deleted Successfully"
	                );

	                ps.close();
	                ps2.close();
	                con.close();

	                loadProperties();
	                loadAllocations();

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

	        date.setText("");
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {

	        if (e.getSource() == allocate) {

	            allocateHouse();

	        } else if (e.getSource() == clear) {

	            clearFields();

	        } else if (e.getSource() == delete) {

	            deleteAllocation();

	        } else if (e.getSource() == back) {

	            new Dashboard();
	            dispose();
	        }
	    }

	    public static void main(String[] args) {

	        new HouseAllocation();
	    }
	
}
