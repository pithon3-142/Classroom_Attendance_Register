package attendance_register;

import javax.swing.JOptionPane;


public class AdminLogin extends javax.swing.JFrame {

    public AdminLogin() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Admin Priviledges");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public String getAdminUsername() {
        String adminUsername = usernameTxtField3.getText();
        return adminUsername;
    }
    
    public String getAdminPass() {
        String adminPassword = passwordTxtField3.getText();
        return adminPassword;
    }
    
    boolean isAdminAuth = false;
    public boolean isAdminAuth() {
        
        if ((getAdminUsername().equals("admin")) && (getAdminPass().equals("admin"))) {
            isAdminAuth = true;
            return isAdminAuth;
        }
        return false;
    }
    
    int check = 0;
    public void bit(int a) {
        check = a;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        loginBtn3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        resetBtn3 = new javax.swing.JButton();
        usernameTxtField3 = new javax.swing.JTextField();
        passwordTxtField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        loginPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel10.setText("Admin Username");

        loginBtn3.setBackground(new java.awt.Color(51, 255, 51));
        loginBtn3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        loginBtn3.setText("Login");
        loginBtn3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(153, 255, 153), null, new java.awt.Color(153, 255, 153)));
        loginBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtn3ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 102, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Admin Priviledges");

        jLabel12.setText("Admin Password");

        resetBtn3.setBackground(new java.awt.Color(255, 102, 102));
        resetBtn3.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        resetBtn3.setText("Reset");
        resetBtn3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        resetBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtn3ActionPerformed(evt);
            }
        });

        usernameTxtField3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(102, 102, 102), null, new java.awt.Color(102, 102, 102)));
        usernameTxtField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTxtField3ActionPerformed(evt);
            }
        });

        passwordTxtField3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(102, 102, 102), null, new java.awt.Color(102, 102, 102)));
        passwordTxtField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTxtField3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loginPanel3Layout = new javax.swing.GroupLayout(loginPanel3);
        loginPanel3.setLayout(loginPanel3Layout);
        loginPanel3Layout.setHorizontalGroup(
            loginPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(loginPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanel3Layout.createSequentialGroup()
                        .addComponent(loginBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107)
                        .addComponent(resetBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanel3Layout.createSequentialGroup()
                        .addGroup(loginPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(loginPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(usernameTxtField3, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordTxtField3, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        loginPanel3Layout.setVerticalGroup(
            loginPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(loginPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameTxtField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(loginPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordTxtField3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(loginPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetBtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtn3ActionPerformed
        AdminLogin admin = new AdminLogin();
        String id;
        if (isAdminAuth() == true) {
            JOptionPane.showMessageDialog(this,"Authentication successful. \nWelcome Administrator");
            this.dispose();
            switch (check) {
                case 1 : new AddStudentFrame().setVisible(true);
                    break;
                case 2 : 
                    id = JOptionPane.showInputDialog(this, "Enter the Student's ID:");
                    // Check if input is not null and not empty
                    if (id != null && !id.trim().isEmpty()) {
                        // Perform action based on the input
                        Attendance_Register.getInstance().delStudent(id,"student_file.dat");
                        JOptionPane.showMessageDialog(this, "Student '"+id+"' deleted successfully.\nRefresh to update Attendance table");
                    }
                    else {
                        // Handle the case where no input was provided
                        JOptionPane.showMessageDialog(this, "No ID entered. Action not performed.");
                    } 
                    break;
                case 3 : new AddTeacherFrame().setVisible(true);
                        break;
                case 4 : 
                    id = JOptionPane.showInputDialog(this, "Enter the Student's ID:");
                    // Check if input is not null and not empty
                    if (id != null && !id.trim().isEmpty()) {
                        // Perform action based on the input
                        Attendance_Register.getInstance().delTeacher(id);
                        JOptionPane.showMessageDialog(this, "Teacher '"+id+"' deleted successfully.\nRecord Updated");
                    }
                    else {
                        // Handle the case where no input was provided
                        JOptionPane.showMessageDialog(this, "No ID entered. Action not performed.");
                    } 
                    break;
            
                default : JOptionPane.showMessageDialog(this, "Authentication failed. Please check your credentials.");
            }
        }
        check = 0;
    }//GEN-LAST:event_loginBtn3ActionPerformed

    private void resetBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtn3ActionPerformed
        usernameTxtField3.setText("");
        passwordTxtField3.setText("");
    }//GEN-LAST:event_resetBtn3ActionPerformed

    private void usernameTxtField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTxtField3ActionPerformed

    }//GEN-LAST:event_usernameTxtField3ActionPerformed

    private void passwordTxtField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTxtField3ActionPerformed

    }//GEN-LAST:event_passwordTxtField3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AdminLogin admin = new AdminLogin();
                while (admin.isVisible()) {
                    try {
                        Thread.sleep(500); // Polling delay to avoid busy-wait
                    } 
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        admin.isVisible();
                    }
                }
                admin.isAdminAuth();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JButton loginBtn3;
    private javax.swing.JPanel loginPanel3;
    private javax.swing.JTextField passwordTxtField3;
    private javax.swing.JButton resetBtn3;
    private javax.swing.JTextField usernameTxtField3;
    // End of variables declaration//GEN-END:variables
}
