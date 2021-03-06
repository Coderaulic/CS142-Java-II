package GolfDataBase;

import java.awt.Container;
import java.awt.Toolkit;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Class:	<b>AboutForm</b>
*  File:	AboutForm.java
* <pre>
*  Description:	Description goes here.
*  @author:	<i>Ryu Muthui and Siki Zhang</i>
*  Environment:	PC, Windows 7, jdk1.7_17, NetBeans 7.3
*  Date:	5/15/2013
*  Hours:       1 hours
*  @version	1.01
*  @see         java.awt.Toolkit
*  @see         java.awt.Container
* </pre>
*  History Log:	Started 4/30/2013, Completed 5/15/2013.
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class AboutForm extends javax.swing.JFrame {

    /**
     * Creates new form AboutForm
     */
    public AboutForm() 
    {
        initComponents();
        
        //Sets the background color of the form.
        Container background = this.getContentPane();
        background.setBackground(new java.awt.Color(204,255,255));
        
        //Sets icon image on form.
        this.setIconImage(Toolkit.getDefaultToolkit()
                .getImage("src\\Resources\\GolfDB_Splash_Screen.png"));
        
        //Sets scan new button as default and request focus to it at start.
        this.getRootPane().setDefaultButton(closeJButton); 
        closeJButton.requestFocus();
        
        //Sets the form to display at the center.
        setLocationRelativeTo(null);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        aboutJLabel = new javax.swing.JLabel();
        closeJButton = new javax.swing.JButton();
        titleJLabel = new javax.swing.JLabel();
        titleImageJLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Golf Data Base | About the Program");
        setResizable(false);

        aboutJLabel.setText("<HTML> \nAbout Golf Database: <BR><BR>\nThis is a program to keep track of all the players on the golf team such as a College Golf Team.\nIts primary purpose is to keep track of each player's First/Last Name, their contact email, phone number and their ranking within the golf team. \nCurrently the databse is set up to hold up to the top 9 members of the golf team. <BR><BR>\n\nFeatures: <BR>\n-Add: New members can be added via a separate form and each field is validated. The rank is unique and is automatically assigned to the next available rank.<BR>\n-Edit/Update: Members can be edited and update the current members of the golf team. Rank is also automatically adjusted. This process can be cancel this process via the canel button from the file menu.<BR>\n-Delete: Members of the team can be removed via this feature and the ranks are also automatically adjusted.<BR>\n-Search: Members of the team can be searched via Last Name and First Name entry.<BR>\n-Print: The form can be printed with the selected member's information.<BR>\n-The program can also save the current members list when changes are made and or save the database as a new text file.<BR>\n-The program can also load up another database via the Open New menu option.<BR>\n<BR>\nAuthors: Ryu Muthui and Siqi Zhang <BR>\nVersion:1.01; Created 05/15/2013<BR>\nPlatform: PC, Windows 7, jdk:1.7.0_10, NetBeans IDE 7.3 <BR>\nCopyright: M&Z  Productions (2013)<BR>\n</HTML>\n");

        closeJButton.setMnemonic('c');
        closeJButton.setText("Close");
        closeJButton.setToolTipText("Click to close the About form.");
        closeJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeJButtonActionPerformed(evt);
            }
        });

        titleJLabel.setBackground(new java.awt.Color(153, 255, 255));
        titleJLabel.setFont(new java.awt.Font("Vijaya", 2, 48)); // NOI18N
        titleJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleJLabel.setText("Golf Database");

        titleImageJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/GolfDB_Title_Logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aboutJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(closeJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(titleImageJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(196, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(titleJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleImageJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aboutJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the about form window when selected.
     * @param evt closeJButtonActionPerformed
     */
    private void closeJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeJButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeJButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AboutForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AboutForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AboutForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AboutForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AboutForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aboutJLabel;
    private javax.swing.JButton closeJButton;
    private javax.swing.JLabel titleImageJLabel;
    private javax.swing.JLabel titleJLabel;
    // End of variables declaration//GEN-END:variables
}
