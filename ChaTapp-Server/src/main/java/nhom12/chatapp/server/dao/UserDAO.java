package nhom12.chatapp.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import nhom12.chatapp.model.User;
 
public class UserDAO extends DAO{
     
    public UserDAO() {
        super();
    }
     
    public boolean checkLogin(User user) {
        boolean result = false;
        String sql = "SELECT * FROM user WHERE sdt = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getSdt());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUserName("username");
                user.setFullname(rs.getString("fullname"));
                user.setAddress(rs.getString("address"));
                user.setDob(rs.getDate("dob"));
                result = true;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean insertUser(User user) {
        String sql = "INSERT INTO user(sdt, password, userName, fullname, address, dob) VALUES (?,?,?,?,?,?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getSdt());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getFullname());
            ps.setString(5, user.getAddress());
            
            ps.setDate(6, new java.sql.Date(user.getDob().getTime()));

            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}