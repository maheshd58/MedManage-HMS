/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Code.DBConnect;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.*;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Ravindu Rathnagala
 */
public class DrugIssueView extends javax.swing.JFrame {

    Connection con = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    DefaultTableModel df =null;
    
    Double bHeight =0.0;
    
    public DrugIssueView() {
        initComponents();
        con =  (Connection) DBConnect.connect();
        
         try{
            String s = "SELECT DrugID,DrugName,Stock_availability,Unit_Price FROM drugs";
            pst = con.prepareStatement(s);
            rs = pst.executeQuery();
            
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch (SQLException e){
            
        }
    }
    public void sales(){
        String totalcost = lblTotal.getText();
        String Cash = txtCash.getText();
        String balance = lblBalance.getText();
        
        int lastid = 0;
        
        try{
            String query =  "INSERT into drugsales(Total,Cash,Balance) values (?,?,?)";
        pst = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, totalcost);
        pst.setString(2, Cash);
        pst.setString(3, balance);
        pst.executeUpdate();
        
        rs = pst.getGeneratedKeys();
        
        if(rs.next()){
            lastid= rs.getInt(1);
        }
        
        int rows =jTable2.getRowCount();
        
        String query1 = "INSERT into drug_product_sales(DrugId,DrugName,Price,Quantity,Total) values (?,?,?,?,?)";
        pst1 = con.prepareStatement(query1,Statement.RETURN_GENERATED_KEYS);
        
        String drugname="";
        String price;
        String qty;
        int total;
        
        for(int i=0;i<jTable2.getRowCount();i++){
            drugname = (String)jTable2.getValueAt(i, 0);
            price = (String)jTable2.getValueAt(i, 1);
            qty = (String)jTable2.getValueAt(i, 2);
            total = (int)jTable2.getValueAt(i, 3);
        
        
        pst1.setInt(1, lastid);
        pst1.setString(2, drugname);
        pst1.setString(3, price);
        pst1.setString(4,qty);
        pst1.setInt(5, total);
        pst1.executeUpdate();
        
        JOptionPane.showMessageDialog(this, "Sales Completed");
        
        }
        
        }
        catch(SQLException e){
                
                }
        
        
    }
    
        public PageFormat getPageFormat(PrinterJob pj){
            
            PageFormat pf = pj.defaultPage();
            Paper paper = pf.getPaper();
            
            double bodyHeight = bHeight;
            double headerHeight = 5.0;
            double footerHeight = 5.0;
            double width = cm_to_pp(8);
            double height = cm_to_pp(headerHeight+bodyHeight+footerHeight);
            
            paper.setSize(width, height);
            paper.setImageableArea(0, 10, width, height - cm_to_pp(1));
            
            pf.setOrientation(PageFormat.PORTRAIT);
            pf.setPaper(paper);
            
            return pf;
        }
        
        protected static double cm_to_pp(double cm){
            return toPPI( cm* 0.393600787);
        }
        protected static double toPPI(double inch){
            return inch * 72d;
        }
    
       public class BillPrintable implements Printable{
           
           public int print(Graphics graphics,PageFormat pageFormat, int pageIndex)
           throws PrinterException
           {
               int r= jTable2.getRowCount();
               ImageIcon icon = new ImageIcon("E:\\Lectures\\2.2 Lectures\\PPA\\MedManageHMS\\src\\Views\\logo.png");
               int result = NO_SUCH_PAGE;
               if(pageIndex==0){
                   Graphics2D g2d = (Graphics2D)graphics;
                   double width = pageFormat.getImageableWidth();
                   g2d.translate((int)pageFormat.getImageableX(), (int)pageFormat.getImageableY());
                   
                   try{
                      int y=20;
                      int yShift = 10;
                      int headerRecHeight =15;
                      
                      g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
                      g2d.drawImage(icon.getImage(), 50,20,90,30,rootPane);y+=yShift+30;
                      g2d.drawString("---------------------------------------", 12, y);y+=yShift;
                      g2d.drawString("             MedManage HMS             ", 12, y);y+=yShift;
                      g2d.drawString("       No 412/1, New Peradeniya        ", 12, y);y+=yShift;
                      g2d.drawString("                Gelioya                ", 12, y);y+=yShift;
                      g2d.drawString("      www.facebook.com/MedManage       ", 12, y);y+=yShift;
                      g2d.drawString("          +94 77 026 021 26            ", 12, y);y+=yShift;
                      g2d.drawString("---------------------------------------", 12, y);y+=headerRecHeight;
                      
                      g2d.drawString("  DrugName                    Price    ", 12, y);y+=yShift;
                      g2d.drawString("---------------------------------------", 12, y);y+=headerRecHeight;
                      
                      for(int i=0;i<jTable2.getRowCount();i++){
            
                      g2d.drawString("  "+(String)jTable2.getValueAt(i, 0)+"                  ", 10, y);y+=yShift;  
                      g2d.drawString("      "+(String)jTable2.getValueAt(i, 2)+" * "+(String)jTable2.getValueAt(i, 1), 10, y);g2d.drawString(jTable2.getValueAt(i, 3).toString(),160,y);y+=yShift;
                        
                      }
                      
                      g2d.drawString("---------------------------------------", 10, y);y+=yShift;
                      g2d.drawString("total Amount:              " + lblTotal.getText()+"    ", 10, y);y+=yShift;
                      g2d.drawString("---------------------------------------", 10, y);y+=yShift;
                      g2d.drawString("Cash:                      " + txtCash.getText()+"    ", 10, y);y+=yShift;
                      g2d.drawString("---------------------------------------", 10, y);y+=yShift;
                      g2d.drawString("Balance:                      " + lblBalance.getText()+"    ", 10, y);y+=yShift;
                      
                      g2d.drawString("***************************************", 10, y);y+=yShift;
                      g2d.drawString("              Thank You!               ", 10, y);y+=yShift;
                      g2d.drawString("***************************************", 10, y);y+=yShift;
                      g2d.drawString("  Contact: ravindulaksahan@gmail.com   ", 10, y);y+=yShift;
                
                   }
                   catch(Exception e){
                   e.printStackTrace();
                   }
                   
                   result = PAGE_EXISTS;
                   
                   
               }
               return result;
           }
       }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblDrugName = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblUnitPrice = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCash = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblBalance = new javax.swing.JLabel();
        btnAdd1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JSpinner();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 650));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel4.setText("Drug Id                   :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, 149, 35));

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });
        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIDKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIDKeyReleased(evt);
            }
        });
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 149, 35));

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel5.setText("Drug Name           :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, 35));

        lblDrugName.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jPanel1.add(lblDrugName, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 149, 35));

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel6.setText("Unit Price(Rs)      :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, -1, 35));

        lblUnitPrice.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jPanel1.add(lblUnitPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 149, 35));

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel7.setText("Quantity               :");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, 133, 35));

        btnAdd.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/add button-03.png"))); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, 90, 37));

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel8.setText("Total                      :");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 30, 132, 35));

        lblTotal.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jPanel1.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 30, 110, 35));

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel9.setText("Cash                       :");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 80, -1, 35));
        jPanel1.add(txtCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, 110, 35));

        jLabel10.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel10.setText("Balance                :");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 130, 132, 35));

        lblBalance.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jPanel1.add(lblBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, 110, 35));

        btnAdd1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAdd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/issue button-03.png"))); // NOI18N
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 230, 100, 37));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Drug Id", "Drug Name", "Availability", "Unit Price"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 480, 488, 136));

        txtSearch.setText("Search by Drug Name...");
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
        });
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 430, 488, -1));
        jPanel1.add(txtQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 180, 149, 35));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Drug name", "Unit Price", "Quantity", "Total"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, 488, 136));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/phrmacy side bar-04.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 650));

        jPanel2.setBackground(new java.awt.Color(244, 239, 239));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(229, 80, 80));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Issue Drugs");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 338, 61));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        
        int price,qty,total;
        
        price = Integer.parseInt(lblUnitPrice.getText());
        qty = Integer.parseInt(txtQuantity.getValue().toString());
        
        total = price*qty;
       

       df = (DefaultTableModel)jTable2.getModel();
       df.addRow(new Object[]
       {
           lblDrugName.getText(),
           lblUnitPrice.getText(),
           txtQuantity.getValue().toString(),
           total
       });
       int numrow = jTable2.getRowCount();
       int sum = 0;
       
       for(int a=0;a<numrow; a++)
       {
       int value = Integer.parseInt(jTable2.getValueAt(a, 3).toString());
       
       sum += value;
       }
 
       lblTotal.setText(Integer.toString(sum));
       txtID.setText("");
       lblDrugName.setText("");
       txtQuantity.setValue(0);
       lblUnitPrice.setText("");
       
       txtID.requestFocus();
       
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        int total = Integer.parseInt(lblTotal.getText());
        int cash = Integer.parseInt(txtCash.getText());
        int totalcost = cash - total;
        
        lblBalance.setText(String.valueOf(totalcost));
        
        sales();
        
        bHeight = Double.valueOf(jTable2.getRowCount());
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try{
            pj.print();
        }
        catch(PrinterException ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText("");
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
       String search = txtSearch.getText();
        try{
            String s = "SELECT DrugID,DrugName,Stock_availability,Unit_Price FROM drugs WHERE DrugName LIKE '%" + search + "%'";
            pst = con.prepareStatement(s);
            rs = pst.executeQuery();
            
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch (SQLException e){
            
        }
        finally{
        try{
            if(pst!=null){
                rs.close();
                pst.close();
            }
        }
        catch(SQLException e){
            
        }
    }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDKeyReleased

    private void txtIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
           
           
           
           try{
               String getId = txtID.getText();
               String q2 = "SELECT * from drugs WHERE DrugID=?";
               pst = con.prepareStatement(q2);
               pst.setString(1, getId);
               rs = pst.executeQuery();
               
               if(rs.next()== false){
                 JOptionPane.showMessageDialog(this, "Drug Id not found!","Message",JOptionPane.ERROR_MESSAGE);  
        
               }
               else{
                   String dname = rs.getString("DrugName");
                   lblDrugName.setText(dname.trim());
                   
                   String price = rs.getString("Unit_Price");
                   lblUnitPrice.setText(price.trim());
                   
                   txtQuantity.requestFocus();
                   
               }
              
               
               
           }catch(Exception e){
               
           }
       }
    }//GEN-LAST:event_txtIDKeyPressed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(DrugIssueView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DrugIssueView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DrugIssueView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DrugIssueView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DrugIssueView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblDrugName;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUnitPrice;
    private javax.swing.JTextField txtCash;
    private javax.swing.JTextField txtID;
    private javax.swing.JSpinner txtQuantity;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
