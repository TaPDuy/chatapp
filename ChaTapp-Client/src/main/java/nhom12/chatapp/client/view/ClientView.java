package nhom12.chatapp.client.view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import nhom12.chatapp.client.listener.MessageListener;
import nhom12.chatapp.client.listener.WindowListener;
import nhom12.chatapp.model.Notification;
import nhom12.chatapp.model.User;

public class ClientView extends javax.swing.JPanel {
    
    private MessageListener listener;
    private WindowListener windowListener;
    private List<String> onlineUser;
    private User user;
    public List<User> listFriend;
    private List<User> usInSysList;
    
    private List<Notification> notifications;
    
    public ClientView(WindowListener windowListener, MessageListener messageListener) {

        initComponents();
	
	this.windowListener = windowListener;
	this.listener = messageListener;
	
        jTextArea1.setEditable(false);
        jTextArea2.setEditable(false);
        user = new User();
        listFriend = new ArrayList<>();
        usInSysList = new ArrayList<>();
        notifications = new ArrayList<>();
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
        txtf_chat = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboBoxGroup = new javax.swing.JComboBox<>();
        comboBoxUser = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
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
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNotification = new javax.swing.JTable();

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

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Home", jPanel1);

        jPanel2.setLayout(null);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(6, 107, 610, 270);
        jPanel2.add(txtf_chat);
        txtf_chat.setBounds(10, 420, 480, 29);

        jButton1.setText("Gửi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(510, 420, 100, 29);

        jLabel2.setText("Nhập tin nhắn");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 390, 112, 21);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("{Người nhận}");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(160, 80, 290, 20);

        jLabel6.setText("Group");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(520, 10, 48, 21);

        comboBoxGroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxGroupActionPerformed(evt);
            }
        });
        jPanel2.add(comboBoxGroup);
        comboBoxGroup.setBounds(470, 40, 140, 29);

        comboBoxUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxUserActionPerformed(evt);
            }
        });
        jPanel2.add(comboBoxUser);
        comboBoxUser.setBounds(20, 40, 140, 29);

        jLabel10.setText("Online Friend");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(40, 10, 90, 21);

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
        tblUserInSys.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserInSysMouseClicked(evt);
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
                        .addComponent(txtKeyNickName, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
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
        jScrollPane5.setBounds(12, 50, 610, 400);

        jTabbedPane1.addTab("My Friend", jPanel5);

        jLabel5.setText("Name");

        jButton2.setText("Create");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Name");

        jButton3.setText("Search");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Number of members"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel8.setText("Create Group");

        jLabel9.setText("Join Group");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jSeparator1)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jButton2))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jButton3))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Create Or Join Group Chat", jPanel6);

        tblNotification.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Content", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblNotification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNotificationMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblNotification);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Notification", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
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
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String messageContent = txtf_chat.getText();
        if (messageContent.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tin nhắn");
            return;
        }

//	String[] parner = ((String) comboBoxUser.getSelectedItem()).split(" ");
	listener.sendMessage(messageContent);
//	jTextArea1.setText(jTextArea1.getText() + "Bạn (tới Client " + parner[1] + "): " + messageContent + "\n");
//	jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());

        txtf_chat.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String key = txtKey.getText();
        List<User> f = new ArrayList<>();
        for (User friend : listFriend) {
            if (friend.getUsername().contains(key)) {
                f.add(friend);
            }
        }
        listFriend = f;
        if (key.equals("")) {
//            listFriend = user.getFriends();
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
            int choice = JOptionPane.showConfirmDialog(this, "Do you want delete friend " + friendDelete.getUsername()+ " ?", "Ask", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                    String formattedDate = myDateObj.format(myFormatObj);
                    listener.sendDeleteFriend(friendDelete.getUsername());
                } catch (IOException ex) {
                    Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_tblMyFriendMouseClicked

    private void btnSearchAddFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchAddFriendActionPerformed

        try {
            listener.sendFindFriend(txtKeyNickName.getText());
        } catch (IOException ex) {
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSearchAddFriendActionPerformed

    private void tblUserInSysMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserInSysMouseClicked
        int column = tblUserInSys.getColumnModel().
                getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tblUserInSys.getRowHeight(); // get row 
        // *Checking the row or column is valid or not

        if (row < tblUserInSys.getRowCount() && row >= 0
                && column < tblUserInSys.getColumnCount() && column >= 0) {
            User addFriend = usInSysList.get(row);
            int choice = JOptionPane.showConfirmDialog(this, "Do you want add friend " + addFriend.getUsername()+ " ?", "Ask", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                    String formattedDate = myDateObj.format(myFormatObj);
                    String[] fms = formattedDate.split(" ");
                    String time = "Ngay " + fms[0] + " luc " + fms[1];
                    listener.sendAddFriend(addFriend.getUsername());
                } catch (IOException ex) {
                    Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_tblUserInSysMouseClicked

    private void comboBoxUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxUserActionPerformed

	listener.setReceiverName((String) comboBoxUser.getSelectedItem());
    }//GEN-LAST:event_comboBoxUserActionPerformed

    private void comboBoxGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxGroupActionPerformed

	listener.setReceiverName("#" + (String) comboBoxGroup.getSelectedItem());
    }//GEN-LAST:event_comboBoxGroupActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
	try {
	    String groupName = jTextField2.getText();
	    if (!groupName.isEmpty())
		listener.createGroup(groupName);
	} catch (IOException ex) {
	    Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_jButton2ActionPerformed
    
    public void setChatBoxTitle(String txt) {
	jLabel3.setText(txt);
    }
    
    private void tblNotificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNotificationMouseClicked
        int column = tblNotification.getColumnModel().
                getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tblNotification.getRowHeight(); // get row 
        // *Checking the row or column is valid or not
        if (row < tblNotification.getRowCount() && row >= 0
                && column < tblNotification.getColumnCount() && column >= 0) {
            Notification notification = notifications.get(row);
            String[] content = notification.getContent().split(" ");
            if (notification.getActive().equalsIgnoreCase("add")) {
                String nameSend = content[0];
                int choice = JOptionPane.showConfirmDialog(this, "Do you want confirm add friend with " + nameSend + " ?", "Ask", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        LocalDateTime myDateObj = LocalDateTime.now();
                        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                        String formattedDate = myDateObj.format(myFormatObj);
                        listener.sendConfirmAddFriend(notification.getId());
                    } catch (IOException ex) {
                        Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else {
                int choice = JOptionPane.showConfirmDialog(this, "Do you want delete notification ?", "Ask", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        listener.sendDeleteNotification(notification.getId());
                    } catch (IOException ex) {
                        Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_tblNotificationMouseClicked

    public JTextArea getTextArea1() {
        return this.jTextArea1;
    }

    public JTextArea getTextArea2() {
        return this.jTextArea2;
    }
    
    public void printMessage(String msg) {
	jTextArea1.setText(jTextArea1.getText() + msg + "\n");
	jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }

    public void setUser(User user) {
        listFriend = new ArrayList<>();
        this.user = user;
//        listFriend = user.getFriends();
//        this.setTitle(user.getUsername());
    }

    public void updateCombobox(List<String> onlineList) {
        onlineUser = new ArrayList<>();
        this.onlineUser = onlineList;
        comboBoxUser.removeAllItems();
        for (String us : onlineUser) {
            String nickName = us.substring(7, us.length());
            for (User friend : listFriend) {
                if (nickName.equalsIgnoreCase(friend.getUsername())) {
                    comboBoxUser.addItem(nickName);
                }
            }
        }
        setTableFriend();
    }
    
    public void updateGroupCombobox(List<String> groupList) {
	comboBoxGroup.removeAllItems();
	groupList.forEach(group -> comboBoxGroup.addItem(group));
    }
    
    public void setTableFriend() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setRowCount(0);
        dtm.setColumnIdentifiers(new String[]{"Nick Name", "Full Name", "Date Is Friend"});

        if (listFriend != null) {
            for (User friend : listFriend) {
                dtm.addRow(new String[]{friend.getUsername(), friend.getFullname(), ""});
            }
        }
        tblMyFriend.setModel(dtm);

    }

    public void setTableUserSys(List<User> usInSys) {
        if (usInSys != null) {
            usInSysList = usInSys;
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.setRowCount(0);
            dtm.setColumnIdentifiers(new String[]{"Nick Name", "Full Name", "Status", "Is Friend"});

            for (User us : usInSysList) {
                if (!listFriend.isEmpty()) {
                    for (User friend : listFriend) {
                        if (!us.getUsername().equals(friend.getUsername()) && !us.getUsername().equalsIgnoreCase(user.getUsername())) {
                            dtm.addRow(new String[]{us.getUsername(), us.getFullname(), "", ""});
                        }
                    }
                } else {
                    if(!us.getUsername().equalsIgnoreCase(user.getUsername()))
                        dtm.addRow(new String[]{us.getUsername(), us.getFullname(), "", ""});
                }
            }
            tblUserInSys.setModel(dtm);
        }
    }

    public void setTableNotification(Notification notification) {
        notifications.add(notification);
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setRowCount(0);
        dtm.setColumnIdentifiers(new String[]{"Content", "Time"});
        dtm.addRow(new String[]{notification.getContent(), new SimpleDateFormat("dd-MM-yyyy").format(notification.getTimeDate())});
        tblNotification.setModel(dtm);
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
    private javax.swing.JComboBox<String> comboBoxGroup;
    private javax.swing.JComboBox<String> comboBoxUser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable tblMyFriend;
    private javax.swing.JTable tblNotification;
    private javax.swing.JTable tblUserInSys;
    private javax.swing.JTextField txtKey;
    private javax.swing.JTextField txtKeyNickName;
    private javax.swing.JTextField txtf_chat;
    // End of variables declaration//GEN-END:variables
}
