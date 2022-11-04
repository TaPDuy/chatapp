/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nhom12.chatapp.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.Notification;
import nhom12.chatapp.model.User;
import static nhom12.chatapp.server.dao.DAO.con;

/**
 *
 * @author Smile
 */
public class NotificationDAO extends DAO{
    
    private String GET_ALL_NOTIFICATION = "Select * from tblnotification where id_user = ?";
    public NotificationDAO(){
        super();
    }
    
    public List<Notification> getAllNotification(int id_user){
        PreparedStatement ps;      
        List<Notification> notifications = new ArrayList<>();
        try {
            ps = con.prepareStatement(GET_ALL_NOTIFICATION);
            ps.setInt(1, id_user);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setActive(rs.getString("active"));
                notification.setContent(rs.getString("content"));
                //notification.setUserSend(rs.getString(""));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return notifications;
    }
}
