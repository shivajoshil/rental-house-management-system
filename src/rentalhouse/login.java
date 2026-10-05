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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class login extends JFrame implements ActionListener {

    JLabel title, jl1, jl2;

    JTextField username;
    JPasswordField password;

    JButton login, clear, signup;

    String user;
    String pass;

    login() {

        // ================= FRAME =================

        setTitle("Rental House Management - Login");
        setSize(450, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ================= TITLE =================

        title = new JLabel("RENTAL HOUSE MANAGEMENT");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(65, 30, 330, 30);
        add(title);

        // ================= USERNAME =================

        jl1 = new JLabel("Username:");
        jl1.setBounds(60, 90, 100, 30);
        add(jl1);

        username = new JTextField();
        username.setBounds(170, 90, 200, 30);
        add(username);

        // ================= PASSWORD =================

        jl2 = new JLabel("Password:");
        jl2.setBounds(60, 130, 100, 30);
        add(jl2);

        password = new JPasswordField();
        password.setBounds(170, 130, 200, 30);
        add(password);

        // ================= LOGIN BUTTON =================

        login = new JButton("LOGIN");
        login.setBounds(60, 190, 100, 35);
        login.addActionListener(this);
        add(login);

        // ================= CLEAR BUTTON =================

        clear = new JButton("CLEAR");
        clear.setBounds(170, 190, 100, 35);
        clear.addActionListener(this);
        add(clear);

        // ================= SIGN UP BUTTON =================

        signup = new JButton("SIGN UP");
        signup.setBounds(280, 190, 100, 35);
        signup.addActionListener(this);
        add(signup);

        // ================= NEW USER =================

        JLabel newUser =
                new JLabel("New User? Create an account");

        newUser.setBounds(120, 250, 220, 30);
        add(newUser);

        // ================= SHOW FRAME =================

        setVisible(true);
    }

    // =========================================================
    // BUTTON ACTIONS
    // =========================================================

    @Override
    public void actionPerformed(ActionEvent ae) {

        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        if (ae.getSource() == login) {

            user = username.getText().trim();

            pass = new String(
                    password.getPassword()
            );

            // Check empty fields

            if (user.isEmpty() || pass.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter username and password"
                );

                return;
            }

            try {

                // Database connection

                Class.forName(
                        "com.mysql.cj.jdbc.Driver"
                );

                Connection con =
                        DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/rentalhouse",
                                "root",
                                "root"
                        );

                System.out.println(
                        "Database Connected"
                );

                // SQL query

                String sql =
                        "SELECT * FROM users "
                        + "WHERE username = ? "
                        + "AND password = ?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1, user);
                ps.setString(2, pass);

                ResultSet rs =
                        ps.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Login Successful!"
                    );

                    // Open Dashboard

                    new Dashboard();

                    // Close Login

                    dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid Username or Password",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

                rs.close();
                ps.close();
                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database connection error!\n"
                        + ex.getMessage()
                );

                ex.printStackTrace();
            }
        }

        // =====================================================
        // CLEAR BUTTON
        // =====================================================

        else if (ae.getSource() == clear) {

            username.setText("");
            password.setText("");
        }

        // =====================================================
        // SIGN UP BUTTON
        // =====================================================

        else if (ae.getSource() == signup) {

            new Signup();

            dispose();
        }
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        new login();
    }
}