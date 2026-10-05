package rentalhouse;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Tenant extends JFrame implements ActionListener {

	    JLabel title, l1, l2, l3, l4;

	    JTextField name, phone, email, address;

	    JButton add, update, delete, clear, back;

	    JTable table;
	    DefaultTableModel model;

	    Tenant() {

	        setTitle("Tenant Management");
	        setSize(900, 600);
	        setLayout(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        // TITLE
	        title = new JLabel("TENANT MANAGEMENT");
	        title.setFont(new Font("Arial", Font.BOLD, 24));
	        title.setBounds(320, 20, 300, 40);
	        add(title);

	        // NAME
	        l1 = new JLabel("Tenant Name:");
	        l1.setBounds(40, 90, 120, 30);
	        add(l1);

	        name = new JTextField();
	        name.setBounds(160, 90, 180, 30);
	        add(name);

	        // PHONE
	        l2 = new JLabel("Phone:");
	        l2.setBounds(40, 140, 120, 30);
	        add(l2);

	        phone = new JTextField();
	        phone.setBounds(160, 140, 180, 30);
	        add(phone);

	        // EMAIL
	        l3 = new JLabel("Email:");
	        l3.setBounds(40, 190, 120, 30);
	        add(l3);

	        email = new JTextField();
	        email.setBounds(160, 190, 180, 30);
	        add(email);

	        // ADDRESS
	        l4 = new JLabel("Address:");
	        l4.setBounds(40, 240, 120, 30);
	        add(l4);

	        address = new JTextField();
	        address.setBounds(160, 240, 180, 30);
	        add(address);

	        // ADD BUTTON
	        add = new JButton("ADD");
	        add.setBounds(40, 310, 100, 35);
	        add.addActionListener(this);
	        add(add);

	        // UPDATE BUTTON
	        update = new JButton("UPDATE");
	        update.setBounds(150, 310, 100, 35);
	        update.addActionListener(this);
	        add(update);

	        // DELETE BUTTON
	        delete = new JButton("DELETE");
	        delete.setBounds(260, 310, 100, 35);
	        delete.addActionListener(this);
	        add(delete);

	        // CLEAR BUTTON
	        clear = new JButton("CLEAR");
	        clear.setBounds(370, 310, 100, 35);
	        clear.addActionListener(this);
	        add(clear);

	        // BACK BUTTON
	        back = new JButton("BACK");
	        back.setBounds(480, 310, 100, 35);
	        back.addActionListener(this);
	        add(back);

	        // TABLE
	        model = new DefaultTableModel();

	        model.addColumn("ID");
	        model.addColumn("Name");
	        model.addColumn("Phone");
	        model.addColumn("Email");
	        model.addColumn("Address");

	        table = new JTable(model);

	        JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(390, 90, 470, 190);
	        add(scrollPane);

	        // LOAD DATA
	        loadTenants();

	        // TABLE ROW CLICK
	        table.addMouseListener(new MouseAdapter() {

	            public void mouseClicked(MouseEvent e) {

	                int row = table.getSelectedRow();

	                name.setText(
	                    model.getValueAt(row, 1).toString()
	                );

	                phone.setText(
	                    model.getValueAt(row, 2).toString()
	                );

	                email.setText(
	                    model.getValueAt(row, 3).toString()
	                );

	                address.setText(
	                    model.getValueAt(row, 4).toString()
	                );
	            }
	        });

	        setVisible(true);
	    }

	    // ADD TENANT
	    void addTenant() {

	        String tenantName = name.getText();
	        String tenantPhone = phone.getText();
	        String tenantEmail = email.getText();
	        String tenantAddress = address.getText();

	        if (tenantName.isEmpty() ||
	            tenantPhone.isEmpty() ||
	            tenantAddress.isEmpty()) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Please fill all required fields"
	            );

	            return;
	        }

	        try {


	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

    		    String sql =
	                "INSERT INTO tenants " +
	                "(name, phone, email, address) " +
	                "VALUES (?, ?, ?, ?)";

	            PreparedStatement ps =
	                con.prepareStatement(sql);

	            ps.setString(1, tenantName);
	            ps.setString(2, tenantPhone);
	            ps.setString(3, tenantEmail);
	            ps.setString(4, tenantAddress);

	            ps.executeUpdate();

	            JOptionPane.showMessageDialog(
	                this,
	                "Tenant Added Successfully"
	            );

	            ps.close();
	            con.close();

	            clearFields();
	            loadTenants();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Error: " + ex.getMessage()
	            );
	        }
	    }

	    // UPDATE TENANT
	    void updateTenant() {

	        int row = table.getSelectedRow();

	        if (row == -1) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Please select a tenant"
	            );

	            return;
	        }

	        int id = Integer.parseInt(
	            model.getValueAt(row, 0).toString()
	        );

	        try {


	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

	            String sql =
	                "UPDATE tenants SET name=?, phone=?, " +
	                "email=?, address=? WHERE tenant_id=?";

	            PreparedStatement ps =
	                con.prepareStatement(sql);

	            ps.setString(1, name.getText());
	            ps.setString(2, phone.getText());
	            ps.setString(3, email.getText());
	            ps.setString(4, address.getText());
	            ps.setInt(5, id);

	            ps.executeUpdate();

	            JOptionPane.showMessageDialog(
	                this,
	                "Tenant Updated Successfully"
	            );

	            ps.close();
	            con.close();

	            clearFields();
	            loadTenants();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Error: " + ex.getMessage()
	            );
	        }
	    }

	    // DELETE TENANT
	    void deleteTenant() {

	        int row = table.getSelectedRow();

	        if (row == -1) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Please select a tenant"
	            );

	            return;
	        }

	        int id = Integer.parseInt(
	            model.getValueAt(row, 0).toString()
	        );

	        int choice = JOptionPane.showConfirmDialog(
	            this,
	            "Do you want to delete this tenant?",
	            "Confirm Delete",
	            JOptionPane.YES_NO_OPTION
	        );

	        if (choice == JOptionPane.YES_OPTION) {

	            try {


		        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

	    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

	    		    System.out.println(con);

	                String sql =
	                    "DELETE FROM tenants WHERE tenant_id=?";

	                PreparedStatement ps =
	                    con.prepareStatement(sql);

	                ps.setInt(1, id);

	                ps.executeUpdate();

	                JOptionPane.showMessageDialog(
	                    this,
	                    "Tenant Deleted Successfully"
	                );

	                ps.close();
	                con.close();

	                clearFields();
	                loadTenants();

	            } catch (Exception ex) {

	                JOptionPane.showMessageDialog(
	                    this,
	                    "Error: " + ex.getMessage()
	                );
	            }
	        }
	    }

	    // LOAD TENANTS
	    void loadTenants() {

	        model.setRowCount(0);

	        try {


	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

	            String sql = "SELECT * FROM tenants";

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
	                "Error loading tenants: " +
	                ex.getMessage()
	            );
	        }
	    }

	    // CLEAR FIELDS
	    void clearFields() {

	        name.setText("");
	        phone.setText("");
	        email.setText("");
	        address.setText("");

	        table.clearSelection();
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {

	        if (e.getSource() == add) {

	            addTenant();

	        } else if (e.getSource() == update) {

	            updateTenant();

	        } else if (e.getSource() == delete) {

	            deleteTenant();

	        } else if (e.getSource() == clear) {

	            clearFields();

	        } else if (e.getSource() == back) {

	            new Dashboard();
	            dispose();
	        }
	    }

	    public static void main(String[] args) {

	        new Tenant();
	    }
	
}
