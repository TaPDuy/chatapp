package nhom12.chatapp.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.User;

public class UserDAO extends DAO {

    private final String CHECK_LOGIN = "SELECT * FROM tbluser WHERE viewname = ? AND password = ?";
    private final String INSERT_USER = "INSERT INTO tbluser(sdt, password, viewname, fullname,gender, address, dob) VALUES (?,?,?,?,?,?,?);";
    private final String CHECK_EXIST = "SELECT * FROM tbluser WHERE sdt = ? limit 1";
    private final String GET_USER_BY_PHONE = "SELECT * FROM tbluser WHERE sdt = ?";
    
    public UserDAO() {
        super();
    }

    public User checkLogin(String viewname, String password) {
        
	User user = null;
        try {
	    
            PreparedStatement ps = con.prepareStatement(CHECK_LOGIN);
            ps.setString(1, viewname);
            ps.setString(2, password);
	    
            ResultSet rs = ps.executeQuery();
	    user = new User();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setViewName(rs.getString("viewname"));
                user.setFullname(rs.getString("fullname"));
                user.setGender(rs.getString("gender"));
                user.setAddress(rs.getString("address"));
                user.setDob(rs.getDate("dob"));
                user.setStatus("1");
            }else
		return null;
	    
        } catch (SQLException ex) {
	    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
        return user;
    }

    public boolean checkExist(User user) {
        PreparedStatement p;
        try {
            p = con.prepareStatement(CHECK_EXIST);
            p.setString(1, user.getSdt());
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean insertUser(User user) {

        try {
            PreparedStatement ps = con.prepareStatement(INSERT_USER);
            ps.setString(1, user.getSdt());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getViewName());
            ps.setString(4, user.getFullname());
            ps.setString(5, user.getGender());
            ps.setString(6, user.getAddress());
            ps.setDate(7, new java.sql.Date(user.getDob().getTime()));
            
            ps.executeUpdate();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    public User getUserByPhone(User user){
        PreparedStatement p;
        try {
            p = con.prepareStatement(GET_USER_BY_PHONE);
            p.setString(1, user.getSdt());
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setViewName(rs.getString("viewname"));
                user.setFullname(rs.getString("fullname"));
                user.setGender(rs.getString("gender"));
                user.setAddress(rs.getString("address"));
                user.setDob(rs.getDate("dob"));
                user.setStatus("1");
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
