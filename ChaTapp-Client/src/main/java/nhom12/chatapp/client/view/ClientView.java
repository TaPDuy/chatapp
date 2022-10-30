package nhom12.chatapp.client.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import nhom12.chatapp.client.listener.MessageListener;
import nhom12.chatapp.client.listener.WindowListener;
import nhom12.chatapp.model.User;

public class ClientView extends javax.swing.JFrame {

    private MessageListener listener;
    private WindowListener windowListener;
    private List<String> onlineUser;
    private User user;
    private List<User> listFriend;

    public ClientView() {
        initComponents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //this.setVisible(true);
        jTextArea1.setEditable(false);
        jTextArea2.setEditable(false);
        user = new User();
        listFriend = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnSearchAddFriend = new javax.swing.JButton();
        txtKeyNickName = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblUserInSys = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        txtKey = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblMyFriend = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Home", jPanel1);

        jPanel2.setLayout(null);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(6, 37, 297, 340);
        jPanel2.add(jTextField1);
        jTextField1.setBounds(0, 420, 280, 29);

        jButton1.setText("Gửi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(370, 420, 180, 29);

        jLabel2.setText("Nhập tin nhắn");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 380, 112, 21);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("{Người nhận}");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(6, 10, 290, 20);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel2.add(jScrollPane3);
        jScrollPane3.setBounds(340, 10, 230, 370);

        jTabbedPane1.addTab("Nhắn tin", jPanel2);

        jLabel4.setText("Nick Name");

        btnSearchAddFriend.setText("Search");
        btnSearchAddFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchAddFriendActionPerformed(evt);
            }
        });

        tblUserInSys.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nick Name", "Full Name", "Status", "Is Friend"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblUserInSys);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtKeyNickName, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearchAddFriend)
                        .addGap(26, 26, 26))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnSearchAddFriend)
                    .addComponent(txtKeyNickName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add Friend", jPanel4);

        jPanel5.setLayout(null);
        jPanel5.add(txtKey);
        txtKey.setBounds(110, 10, 249, 29);

        jLabel1.setText("Nick name");
        jPanel5.add(jLabel1);
        jLabel1.setBounds(20, 10, 70, 21);

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel5.add(btnSearch);
        btnSearch.setBounds(420, 10, 90, 29);

        tblMyFriend.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nick Name", "Full Name", "Date Is Friend"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMyFriend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMyFriendMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblMyFriend);
        if (tblMyFriend.getColumnModel().getColumnCount() > 0) {
            tblMyFriend.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel5.add(jScrollPane5);
        jScrollPane5.setBounds(12, 50, 560, 400);

        jTabbedPane1.addTab("My Friend", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String parner = jLabel3.getText();
        if (parner.equalsIgnoreCase("{Người nhận}")) {
            parner = onlineUser.get(0).substring(7, onlineUser.get(0).length());
        }

        String messageContent = jTextField1.getText();
        if (messageContent.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa nhập tin nhắn");
            return;
        }

        try {
            listener.send(messageContent, parner);
            jTextArea1.setText(jTextArea1.getText() + "Bạn (tới Client " + parner + "): " + messageContent + "\n");
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
            //}
        } catch (IOException ex) {
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }

        jTextField1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int column = jTable1.getColumnModel().
                getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / jTable1.getRowHeight(); // get row 
        // *Checking the row or column is valid or not
        if (row < jTable1.getRowCount() && row >= 0
                && column < jTable1.getColumnCount() && column >= 0) {
            jLabel3.setText("Đang nhắn với " + onlineUser.get(row).substring(7, onlineUser.get(row).length()));
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String key = txtKey.getText();
        List<User> f = new ArrayList<>();
        for (User friend : listFriend) {
            if (friend.getViewName().contains(key)) {
                f.add(friend);
            }
        }
        listFriend = f;
        if (key.equals("")) {
            listFriend = user.getFriends();
        }
        setTableFriend();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblMyFriendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMyFriendMouseClicked
        int column = tblMyFriend.getColumnModel().
                getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tblMyFriend.getRowHeight(); // get row 
        // *Checking the row or column is valid or not
        if (row < tblMyFriend.getRowCount() && row >= 0
                && column < tblMyFriend.getColumnCount() && column >= 0) {
            User friendDelete = listFriend.get(row);
            int choice = JOptionPane.showConfirmDialog(this, "Do you want delete friend " + friendDelete.getViewName() + " ?", "Ask", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    listener.sendDeleteFriend(friendDelete.getId() + "");
                    updateCombobox(onlineUser);
                } catch (IOException ex) {
                    Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_tblMyFriendMouseClicked

    private void btnSearchAddFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchAddFriendActionPerformed
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setRowCount(0);
        dtm.setColumnIdentifiers(new String[]{"Nick Name", "Full Name", "Status", "Is Friend"});
        for (String nickName : onlineUser) {
            for (User us : listFriend) {
                if (!us.getViewName().equalsIgnoreCase(nickName)) {
                    dtm.addRow(new String[]{us.getViewName(), us.getFullname(), "online", "no friend"});
                }
            }

        }
        tblUserInSys.setModel(dtm);
    }//GEN-LAST:event_btnSearchAddFriendActionPerformed

    public JTextArea getTextArea1() {
        return this.jTextArea1;
    }

    public JTextArea getTextArea2() {
        return this.jTextArea2;
    }

    public void setUser(User user) {
        this.user = user;
        listFriend = user.getFriends();
        this.setTitle(user.getViewName());
        setTableFriend();
    }

    public void updateCombobox(List<String> onlineList) {
        onlineUser = new ArrayList<>();
        this.onlineUser = onlineList;
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new String[]{"Online Friend"});
        dtm.setRowCount(0);
        for (String user : onlineList) {
            String nickName = user.substring(7, user.length());
            System.out.println(nickName);
            for (User friend : listFriend) {
                if (nickName.equalsIgnoreCase(friend.getViewName())) {
                    dtm.addRow(new String[]{nickName});
                }
            }
        }
        jTable1.setModel(dtm);
        setTableFriend();
    }

    public void setTableFriend() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setRowCount(0);
        dtm.setColumnIdentifiers(new String[]{"Nick Name", "Full Name", "Date Is Friend"});
        for (User friend : listFriend) {
            dtm.addRow(new String[]{friend.getViewName(), friend.getFullname(), ""});
        }
        tblMyFriend.setModel(dtm);
    }

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }

    public void setWindowListener(WindowListener listener) {
        this.windowListener = listener;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchAddFriend;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblMyFriend;
    private javax.swing.JTable tblUserInSys;
    private javax.swing.JTextField txtKey;
    private javax.swing.JTextField txtKeyNickName;
    // End of variables declaration//GEN-END:variables
}
