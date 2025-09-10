/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mohal
 */
   package quickchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

 
public class RegistrationForm extends javax.swing.JFrame {
    // Color scheme
    private final Color DARK_BG = new Color(45, 45, 45);
    private final Color MEDIUM_GRAY = new Color(70, 70, 70);
    private final Color ACCENT_BLUE = new Color(0, 150, 200);
    
    public RegistrationForm() {
        initComponents();
        setupDarkTheme();
    }
    
    private void setupDarkTheme() {
        // Main window
        getContentPane().setBackground(DARK_BG);
        
        // Form components
        titleLabel.setForeground(Color.WHITE);
        
        usernameLabel.setForeground(Color.WHITE);
        usernameField.setBackground(MEDIUM_GRAY);
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        
        phoneLabel.setForeground(Color.WHITE);
        phoneField.setBackground(MEDIUM_GRAY);
        phoneField.setForeground(Color.WHITE);
        phoneField.setCaretColor(Color.WHITE);
        
        passwordLabel.setForeground(Color.WHITE);
        passwordField.setBackground(MEDIUM_GRAY);
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        
        registerBtn.setBackground(ACCENT_BLUE);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // NetBeans auto-generated component initialization
        titleLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        phoneLabel = new javax.swing.JLabel();
        phoneField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        registerBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QuickChat Registration");
        setResizable(false);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Register for QuickChat");

        usernameLabel.setText("Username:");

        phoneLabel.setText("Phone (+27...):");

        passwordLabel.setText("Password:");

        registerBtn.setText("Register");
        registerBtn.addActionListener(this::registerBtnActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameLabel)
                    .addComponent(phoneLabel)
                    .addComponent(passwordLabel))
                .addGap(18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordField)
                    .addComponent(phoneField)
                    .addComponent(usernameField))
                .addGap(30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addComponent(titleLabel)
                .addGap(30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneLabel)
                    .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private void registerBtnActionPerformed(ActionEvent evt) {
        String username = usernameField.getText();
        String phone = phoneField.getText();
        String password = new String(passwordField.getPassword());
        
        if (checkUser.registerUser(username, phone, password)) {
            JOptionPane.showMessageDialog(this,
                "Registration successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new RegistrationForm().setVisible(true);
        });
    }
    
    // Variables declaration - NetBeans will auto-generate these
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JTextField phoneField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerBtn;
} 

