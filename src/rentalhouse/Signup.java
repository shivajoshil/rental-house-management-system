package rentalhouse;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Signup extends JFrame implements ActionListener{

	JLabel title, l1, l2, l3;
    JTextField username;
	JTextField email;
    JPasswordField password;
    JButton signup, clear, login;

    Signup() {

        // Frame
        setTitle("Rental House Management - Sign Up");
        setSize(450, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Title
        title = new JLabel("CREATE NEW ACCOUNT");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(95, 30, 300, 30);
        add(title);

        // Username
        l1 = new JLabel("Username:");
        l1.setBounds(60, 90, 100, 30);
        add(l1);

        username = new JTextField();
        username.setBounds(170, 90, 200, 30);
        add(username);

        // Email
        l2 = new JLabel("Email:");
        l2.setBounds(60, 130, 100, 30);
        add(l2);

        email = new JTextField();
        email.setBounds(170, 130, 200, 30);
        add(email);

        // Password
        l3 = new JLabel("Password:");
        l3.setBounds(60, 170, 100, 30);
        add(l3);

        password = new JPasswordField();
        password.setBounds(170, 170, 200, 30);
        add(password);

        // Signup button
        signup = new JButton("SIGN UP");
        signup.setBounds(70, 230, 100, 35);
        signup.addActionListener(this);
        add(signup);

        // Clear button
        clear = new JButton("CLEAR");
        clear.setBounds(180, 230, 100, 35);
        clear.addActionListener(this);
        add(clear);

        // Login button
        login = new JButton("LOGIN");
        login.setBounds(290, 230, 100, 35);
        login.addActionListener(this);
        add(login);

        setVisible(true);
    }

    
	@Override
    public void actionPerformed(ActionEvent e) {

        // SIGN UP button
        if (e.getSource() == signup) {

            String user = username.getText();
            String mail = email.getText();
            String pass = new String(password.getPassword());

            // Check empty fields
            if (user.isEmpty() || mail.isEmpty() || pass.isEmpty()) {

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

                // SQL query
                String sql =
                    "INSERT INTO users(username, password, email) VALUES (?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, user);
                ps.setString(2, pass);
                ps.setString(3, mail);

                // Execute query
                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                    this,
                    "Account Created Successfully!"
                );

                // Open Login page
                new login();

                // Close Signup page
                dispose();

                ps.close();
                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,"Username already exists or database error!");
                   
                ex.printStackTrace();
            }
        }

        // CLEAR button
        if (e.getSource() == clear) {

            username.setText("");
            email.setText("");
            password.setText("");
        }

        // LOGIN button
        if (e.getSource() == login) {

            new login();

            dispose();
        }
    }

    public static void main(String[] args) {

        new Signup();
    }
}

