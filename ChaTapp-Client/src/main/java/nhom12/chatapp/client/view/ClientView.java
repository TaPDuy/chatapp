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
    
    public ClientView(WindowListener windowListener, MessageListener messageListener) {

        initComponents();
	
	this.windowListener = windowListener;
	this.listener = messageListener;
	
        jTextArea1.setEditable(false);
        jTextArea2.setEditable(false);
        user = new User();
        listFriend = new ArrayList<>();
        usInSysList = new ArrayList<>();
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
        btn_unfriend = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_friends = new javax.swing.JTable();
        btn_profile = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtf_groupKey = new javax.swing.JTextField();
        btn_searchGroup = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_groupResult = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_groups = new javax.swing.JTable();
        btn_viewMembers = new javax.swing.JButton();
        btn_leave = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_notification = new javax.swing.JTable();

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

        comboBoxGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxGroupActionPerformed(evt);
            }
        });
        jPanel2.add(comboBoxGroup);
        comboBoxGroup.setBounds(470, 40, 140, 29);

        comboBoxUser.setToolTipText("");
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

        btn_unfriend.setText("Unfriend");
        btn_unfriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_unfriendActionPerformed(evt);
            }
        });
        jPanel5.add(btn_unfriend);
        btn_unfriend.setBounds(340, 10, 100, 29);

        tbl_friends.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
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
        jScrollPane5.setViewportView(tbl_friends);
        if (tbl_friends.getColumnModel().getColumnCount() > 0) {
            tbl_friends.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel5.add(jScrollPane5);
        jScrollPane5.setBounds(12, 50, 610, 400);

        btn_profile.setText("View profile");
        btn_profile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_profileActionPerformed(evt);
            }
        });
        jPanel5.add(btn_profile);
        btn_profile.setBounds(180, 10, 120, 29);

        jTabbedPane1.addTab("My Friend", jPanel5);

        jLabel5.setText("Name");

        jButton2.setText("Create");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Name");

        btn_searchGroup.setText("Search");
        btn_searchGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchGroupActionPerformed(evt);
            }
        });

        tbl_groupResult.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_groupResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_groupResultMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_groupResult);
        if (tbl_groupResult.getColumnModel().getColumnCount() > 0) {
            tbl_groupResult.getColumnModel().getColumn(0).setResizable(false);
            tbl_groupResult.getColumnModel().getColumn(1).setResizable(false);
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
                            .addComponent(txtf_groupKey, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jButton2))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(btn_searchGroup))))
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
                    .addComponent(txtf_groupKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_searchGroup))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Create Or Join Group Chat", jPanel6);

        jPanel8.setLayout(null);

        tbl_groups.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name"
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
        jScrollPane7.setViewportView(tbl_groups);

        jPanel8.add(jScrollPane7);
        jScrollPane7.setBounds(20, 51, 600, 395);

        btn_viewMembers.setText("View Members");
        btn_viewMembers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viewMembersActionPerformed(evt);
            }
        });
        jPanel8.add(btn_viewMembers);
        btn_viewMembers.setBounds(180, 10, 140, 29);

        btn_leave.setText("Leave");
        btn_leave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_leaveActionPerformed(evt);
            }
        });
        jPanel8.add(btn_leave);
        btn_leave.setBounds(350, 10, 94, 29);

        jTabbedPane1.addTab("My Groups", jPanel8);

        tbl_notification.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_notification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_notificationMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_notification);

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
		listener.createGroup(groupName.replaceAll("\\s+", "_"));
	} catch (IOException ex) {
	    Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_jButton2ActionPerformed
    
    public void setChatBoxTitle(String txt) {
	jLabel3.setText(txt);
    }
    
    private void tbl_notificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_notificationMouseClicked
        
	int column = tbl_notification.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tbl_notification.getRowHeight(); // get row 
	
        // *Checking the row or column is valid or not
        if (
	    row < tbl_notification.getRowCount() && 
	    row >= 0 && 
	    column < tbl_notification.getColumnCount() && 
	    column >= 0
	) {
	    try {
		listener.processNotification(row);
	    } catch (IOException ex) {
		Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
	    }
        }
    }//GEN-LAST:event_tbl_notificationMouseClicked

    private void btn_profileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_profileActionPerformed
        int row = tbl_friends.getSelectedRow();
        if (row != -1) {
	    try {
		listener.processViewProfile(row);
	    } catch (IOException ex) {
		Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }//GEN-LAST:event_btn_profileActionPerformed

    private void btn_unfriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_unfriendActionPerformed

	int row = tbl_friends.getSelectedRow();
        if (row != -1) {
	    try {
		listener.processUnfriend(row);
	    } catch (IOException ex) {
		Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }//GEN-LAST:event_btn_unfriendActionPerformed

    private void btn_searchGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchGroupActionPerformed
        try {
            listener.sendFindGroup(txtf_groupKey.getText());
        } catch (IOException ex) {
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_searchGroupActionPerformed

    private void tbl_groupResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_groupResultMouseClicked
        int row = tbl_groupResult.getSelectedRow();
        if (row != -1) {
	    try {
		listener.processJoinGroup((String) tbl_groupResult.getModel().getValueAt(row, 0));
	    } catch (IOException ex) {
		Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }//GEN-LAST:event_tbl_groupResultMouseClicked

    private void btn_leaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_leaveActionPerformed
        int row = tbl_groups.getSelectedRow();
        if (row != -1) {
	    try {
		listener.processLeaveGroup((String) tbl_groups.getModel().getValueAt(row, 0));
	    } catch (IOException ex) {
		Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }//GEN-LAST:event_btn_leaveActionPerformed

    private void btn_viewMembersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewMembersActionPerformed
        int row = tbl_groups.getSelectedRow();
        if (row != -1) {
	    try {
		listener.processViewMembers(row);
	    } catch (IOException ex) {
		Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }//GEN-LAST:event_btn_viewMembersActionPerformed

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
    
    public void printMessages(List<String> msg) {
	msg.forEach(m -> printMessage(m));
    }
    
    public void clearChatbox() {
	jTextArea1.setText("");
	jTextArea1.setCaretPosition(0);
    }

    public void setUser(User user) {
        listFriend = new ArrayList<>();
        this.user = user;
//        listFriend = user.getFriends();
//        this.setTitle(user.getUsername());
    }

    public void updateCombobox(List<String> onlineList) {
        comboBoxUser.removeAllItems();
	onlineList.forEach(group -> comboBoxUser.addItem(group));
    }
    
    public void updateGroupCombobox(List<String> groupList) {
	comboBoxGroup.removeAllItems();
	groupList.forEach(group -> comboBoxGroup.addItem(group));
    }
    
    public void clearFriendList() {
	DefaultTableModel dtm = (DefaultTableModel) tbl_friends.getModel();
	dtm.setRowCount(0);
    }
    
    public void addFriendRow(String username, String status) {
	DefaultTableModel dtm = (DefaultTableModel) tbl_friends.getModel();
	dtm.addRow(new String[] {username, status});
    }
    
    public void clearGroupResultList() {
	DefaultTableModel dtm = (DefaultTableModel) tbl_groupResult.getModel();
	dtm.setRowCount(0);
    }
    
    public void addGroupResultRow(String name, String memberCnt) {
	DefaultTableModel dtm = (DefaultTableModel) tbl_groupResult.getModel();
	dtm.addRow(new String[] {name, memberCnt});
    }
    
    public void clearGroupList() {
	DefaultTableModel dtm = (DefaultTableModel) tbl_groups.getModel();
	dtm.setRowCount(0);
    }
    
    public void addGroupRow(String name) {
	DefaultTableModel dtm = (DefaultTableModel) tbl_groups.getModel();
	dtm.addRow(new String[] {name});
    }
    
    public void clearNotificationList() {
	DefaultTableModel dtm = (DefaultTableModel) tbl_notification.getModel();
	dtm.setRowCount(0);
    }
    
    public void addNotificationRow(String content, String time) {
	DefaultTableModel dtm = (DefaultTableModel) tbl_notification.getModel();
	dtm.addRow(new String[] {content, time});
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

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }

    public void setWindowListener(WindowListener listener) {
        this.windowListener = listener;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearchAddFriend;
    private javax.swing.JButton btn_leave;
    private javax.swing.JButton btn_profile;
    private javax.swing.JButton btn_searchGroup;
    private javax.swing.JButton btn_unfriend;
    private javax.swing.JButton btn_viewMembers;
    private javax.swing.JComboBox<String> comboBoxGroup;
    private javax.swing.JComboBox<String> comboBoxUser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable tblUserInSys;
    private javax.swing.JTable tbl_friends;
    private javax.swing.JTable tbl_groupResult;
    private javax.swing.JTable tbl_groups;
    private javax.swing.JTable tbl_notification;
    private javax.swing.JTextField txtKeyNickName;
    private javax.swing.JTextField txtf_chat;
    private javax.swing.JTextField txtf_groupKey;
    // End of variables declaration//GEN-END:variables
}
