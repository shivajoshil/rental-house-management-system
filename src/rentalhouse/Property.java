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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Property extends JFrame implements ActionListener  {
	
	
	    JLabel title, l1, l2, l3, l4, l5;

	    JTextField houseNumber, address, rent;

	    JComboBox<String> propertyType;
	    JComboBox<String> status;

	    JButton add, update, delete, clear, back;

	    JTable table;
	    DefaultTableModel model;

	    Property() {

	        setTitle("Property Management");
	        setSize(900, 600);
	        setLayout(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        // Title
	        title = new JLabel("PROPERTY MANAGEMENT");
	        title.setFont(new Font("Arial", Font.BOLD, 24));
	        title.setBounds(300, 20, 350, 40);
	        add(title);

	        // House Number
	        l1 = new JLabel("House Number:");
	        l1.setBounds(40, 90, 120, 30);
	        add(l1);

	        houseNumber = new JTextField();
	        houseNumber.setBounds(160, 90, 180, 30);
	        add(houseNumber);

	        // Property Type
	        l2 = new JLabel("Property Type:");
	        l2.setBounds(40, 140, 120, 30);
	        add(l2);

	        String[] types = {
	            "House",
	            "Apartment",
	            "Flat",
	            "Villa"
	        };

	        propertyType = new JComboBox<>(types);
	        propertyType.setBounds(160, 140, 180, 30);
	        add(propertyType);

	        // Address
	        l3 = new JLabel("Address:");
	        l3.setBounds(40, 190, 120, 30);
	        add(l3);

	        address = new JTextField();
	        address.setBounds(160, 190, 180, 30);
	        add(address);

	        // Rent
	        l4 = new JLabel("Monthly Rent:");
	        l4.setBounds(40, 240, 120, 30);
	        add(l4);

	        rent = new JTextField();
	        rent.setBounds(160, 240, 180, 30);
	        add(rent);

	        // Status
	        l5 = new JLabel("Status:");
	        l5.setBounds(40, 290, 120, 30);
	        add(l5);

	        String[] statuses = {
	            "AVAILABLE",
	            "RENTED"
	        };

	        status = new JComboBox<>(statuses);
	        status.setBounds(160, 290, 180, 30);
	        add(status);

	        // Buttons
	        add = new JButton("ADD");
	        add.setBounds(40, 350, 100, 35);
	        add.addActionListener(this);
	        add(add);

	        update = new JButton("UPDATE");
	        update.setBounds(150, 350, 100, 35);
	        update.addActionListener(this);
	        add(update);

	        delete = new JButton("DELETE");
	        delete.setBounds(260, 350, 100, 35);
	        delete.addActionListener(this);
	        add(delete);

	        clear = new JButton("CLEAR");
	        clear.setBounds(370, 350, 100, 35);
	        clear.addActionListener(this);
	        add(clear);

	        back = new JButton("BACK");
	        back.setBounds(480, 350, 100, 35);
	        back.addActionListener(this);
	        add(back);

	        // Table
	        model = new DefaultTableModel();

	        model.addColumn("ID");
	        model.addColumn("House No");
	        model.addColumn("Type");
	        model.addColumn("Address");
	        model.addColumn("Rent");
	        model.addColumn("Status");

	        table = new JTable(model);

	        JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(390, 90, 470, 230);
	        add(scrollPane);

	        // Load data
	        loadProperties();

	        // Select table row
	        table.addMouseListener(new MouseAdapter() {

	            public void mouseClicked(MouseEvent e) {

	                int row = table.getSelectedRow();

	                houseNumber.setText(model.getValueAt(row, 1).toString());

	                propertyType.setSelectedItem(
	                    model.getValueAt(row, 2).toString()
	                );

	                address.setText(
	                    model.getValueAt(row, 3).toString()
	                );

	                rent.setText(
	                    model.getValueAt(row, 4).toString()
	                );

	                status.setSelectedItem(
	                    model.getValueAt(row, 5).toString()
	                );
	            }
	        });

	        setVisible(true);
	    }

	    // ADD PROPERTY
	    void addProperty() {

	        String house = houseNumber.getText();
	        String type = propertyType.getSelectedItem().toString();
	        String addr = address.getText();
	        String rentValue = rent.getText();
	        String stat = status.getSelectedItem().toString();

	        if (house.isEmpty() || addr.isEmpty() || rentValue.isEmpty()) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Please fill all fields"
	            );

	            return;
	        }

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);

	            String sql =
	                "INSERT INTO properties " +
	                "(house_number, property_type, address, rent, status) " +
	                "VALUES (?, ?, ?, ?, ?)";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setString(1, house);
	            ps.setString(2, type);
	            ps.setString(3, addr);
	            ps.setDouble(4, Double.parseDouble(rentValue));
	            ps.setString(5, stat);

	            ps.executeUpdate();

	            JOptionPane.showMessageDialog(
	                this,
	                "Property Added Successfully"
	            );

	            ps.close();
	            con.close();

	            clearFields();
	            loadProperties();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Error: " + ex.getMessage()
	            );
	        }
	    }

	    // UPDATE PROPERTY
	    void updateProperty() {

	        int row = table.getSelectedRow();

	        if (row == -1) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Please select a property"
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
	                "UPDATE properties SET house_number=?, " +
	                "property_type=?, address=?, rent=?, status=? " +
	                "WHERE property_id=?";

	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setString(1, houseNumber.getText());
	            ps.setString(2, propertyType.getSelectedItem().toString());
	            ps.setString(3, address.getText());
	            ps.setDouble(4, Double.parseDouble(rent.getText()));
	            ps.setString(5, status.getSelectedItem().toString());
	            ps.setInt(6, id);

	            ps.executeUpdate();

	            JOptionPane.showMessageDialog(
	                this,
	                "Property Updated Successfully"
	            );

	            ps.close();
	            con.close();

	            clearFields();
	            loadProperties();

	        } catch (Exception ex) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Error: " + ex.getMessage()
	            );
	        }
	    }

	    // DELETE PROPERTY
	    void deleteProperty() {

	        int row = table.getSelectedRow();

	        if (row == -1) {

	            JOptionPane.showMessageDialog(
	                this,
	                "Please select a property"
	            );

	            return;
	        }

	        int id = Integer.parseInt(
	            model.getValueAt(row, 0).toString()
	        );

	        int choice = JOptionPane.showConfirmDialog(
	            this,
	            "Do you want to delete this property?",
	            "Confirm Delete",
	            JOptionPane.YES_NO_OPTION
	        );

	        if (choice == JOptionPane.YES_OPTION) {

	            try {

	            	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

	    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

	    		    System.out.println(con);

	                String sql =
	                    "DELETE FROM properties WHERE property_id=?";

	                PreparedStatement ps =
	                    con.prepareStatement(sql);

	                ps.setInt(1, id);

	                ps.executeUpdate();

	                JOptionPane.showMessageDialog(
	                    this,
	                    "Property Deleted Successfully"
	                );

	                ps.close();
	                con.close();

	                clearFields();
	                loadProperties();

	            } catch (Exception ex) {

	                JOptionPane.showMessageDialog(
	                    this,
	                    "Error: " + ex.getMessage()
	                );
	            }
	        }
	    }

	    // DISPLAY PROPERTIES
	    void loadProperties() {

	        model.setRowCount(0);

	        try {

	        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

    		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rentalhouse","root","root");

    		    System.out.println(con);
    		    
	            String sql = "SELECT * FROM properties";

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
	                "Error loading properties: " +
	                ex.getMessage()
	            );
	        }
	    }

	    // CLEAR
	    void clearFields() {

	        houseNumber.setText("");
	        address.setText("");
	        rent.setText("");

	        propertyType.setSelectedIndex(0);
	        status.setSelectedIndex(0);

	        table.clearSelection();
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {

	        if (e.getSource() == add) {

	            addProperty();

	        } else if (e.getSource() == update) {

	            updateProperty();

	        } else if (e.getSource() == delete) {

	            deleteProperty();

	        } else if (e.getSource() == clear) {

	            clearFields();

	        } else if (e.getSource() == back) {

	            new Dashboard();
	            dispose();
	        }
	    }

	    public static void main(String[] args) {

	        new Property();
	    }
	

}
