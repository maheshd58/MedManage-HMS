package Views;


import Code.DBConnect;
import java.sql.*;
import java.sql.SQLException;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Gapstars
 */
public class PatientFeedbackView extends javax.swing.JFrame {

    Connection con = null;
    PreparedStatement pst = null;
    
    public PatientFeedbackView() {
        initComponents();
        con =  (Connection) DBConnect.connect();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        txt_name = new javax.swing.JTextField();
        txt_appointmentNo = new javax.swing.JTextField();
        txt_feedback = new javax.swing.JTextField();
        Btn_Insert = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));

        jPanel1.setBackground(new java.awt.Color(70, 73, 75));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 650));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(txt_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, 268, 36));
        jPanel1.add(txt_appointmentNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 150, 268, 36));
        jPanel1.add(txt_feedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 240, 268, 103));

        Btn_Insert.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Btn_Insert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/submit button-03.png"))); // NOI18N
        Btn_Insert.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Btn_Insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_InsertActionPerformed(evt);
            }
        });
        jPanel1.add(Btn_Insert, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 430, 110, 40));

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel6.setText("Name                                             :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 231, 35));

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel7.setText("Appointment Id                          :");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 231, 36));

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel8.setText("Feedback                                     :");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 231, 36));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/receptionist side bar-04.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 650));

        jPanel2.setBackground(new java.awt.Color(244, 239, 239));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Showcard Gothic", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(229, 80, 80));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Patient Feedback");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 493, 57));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//public connection getConnetion()
//{
//    connecton con = null;
//      try {
//          con = DriveManager.getConnection();
//          JOptionPane.showMessageDialog(null,connector);
//          return con;           
//          } catch (SQLException ex) {
//              Logger.getLogger(Patient_view.class.getName()).log(Level.SEVERE,null,ex);
//              JOptionPane.showMessageDialog(null,"Not Connected");
//              return null;
// }
//}
//Check Input Feilds
  
    public boolean checkInputs() {
        if (txt_name.getText() == null
                || txt_appointmentNo.getText() == null
                || txt_feedback.getText() == null) {
            return false;
        } else {
            }
            return true;
        }
    
    private void Btn_InsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_InsertActionPerformed
        System.out.println(txt_appointmentNo.getText());

        if (checkInputs()) {
            try {
               pst = con.prepareStatement("INSERT INTO patient_feedback(Name,AppointmentID,Feedback)" + "values(?,?,?)");
                
                pst.setString(1,txt_name.getText());
                pst.setString(2,txt_appointmentNo.getText());
                pst.setString(3,txt_feedback.getText());
                
                pst.executeUpdate();
 
            } catch (SQLException ex) {

                //Logger.getLogger(Patiet_view.class.getName()).log(Level.SEVERE, null, ex)
               System.out.println("Error");          
            }
        }else{
                   System.out.println("One or More Feild are empty");     
               // JOptionPane.showMessageDialog(null."One or More Feild are empty");
            }

        
        System.out.println("Name => "+txt_name.getText());  
        System.out.println("Name => "+txt_appointmentNo.getText());  
        System.out.println("Name => "+txt_feedback.getText());
    }//GEN-LAST:event_Btn_InsertActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        loginHome a1 = new loginHome();
        a1.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

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
            java.util.logging.Logger.getLogger(PatientFeedbackView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PatientFeedbackView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PatientFeedbackView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PatientFeedbackView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PatientFeedbackView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Insert;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txt_appointmentNo;
    private javax.swing.JTextField txt_feedback;
    private javax.swing.JTextField txt_name;
    // End of variables declaration//GEN-END:variables






    }

