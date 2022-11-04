package nhom12.chatapp.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.User;

public class UserDAO extends DAO {

    private final String CHECK_LOGIN = "SELECT * FROM tbluser WHERE viewname = ? AND password = ?";
    private final String INSERT_USER = "INSERT INTO tbluser(sdt, password, viewname, fullname,gender, address, dob) VALUES (?,?,?,?,?,?,?);";
    private final String CHECK_EXIST = "SELECT * FROM tbluser WHERE viewname = ? limit 1";
    private final String GET_USER_BY_ID = "SELECT * FROM tbluser WHERE sdt = ?";
    private final String GET_ALL_FRIEND = "Select distinct b.*  from tbluser a, tbluser b join tblfriend1 c on (c.user_id = ? and b.id = c.userf_id and c.status = 1) or (c.userf_id = ? and b.id = c.user_id and c.status = 1)";
    private final String DELETE_FRIEND = "Delete from tblfriend1 where (user_id = ? and userf_id=?) or (user_id = ? and userf_id=?)";
    private final String GET_ALL_USER = "Select * from tbluser where viewname like ?";
    private final String ADD_FRIEND = "INSERT INTO tblfriend1 (user_id, userf_id, status) values(?, ?, ?)";
    private final String CONFIRM_ADD_FRIEND = "Update tblfriend1 set status = 1 where user_id=? and userf_id=?";

    public UserDAO() {
        super();
    }
    
    public List<User> getAllUser(String key){
        PreparedStatement ps;      
        List<User> listUser = new ArrayList<>();
        try {
            ps = con.prepareStatement(GET_ALL_USER);
            ps.setString(1, '%'+key+'%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setSdt(rs.getString("sdt"));
                user.setViewName(rs.getString("viewname"));
                user.setFullname(rs.getString("fullname"));
                user.setGender(rs.getString("gender"));
                user.setAddress(rs.getString("address"));
                user.setDob(rs.getDate("dob"));
                listUser.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return listUser;
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
                user.setSdt(rs.getString("sdt"));
                user.setPassword(password);
                user.setFullname(rs.getString("fullname"));
                user.setGender(rs.getString("gender"));
                user.setAddress(rs.getString("address"));
                user.setDob(rs.getDate("dob"));
                user.setStatus("online");
                PreparedStatement ps1 = con.prepareStatement(GET_ALL_FRIEND);
                ps1.setInt(1, user.getId());
                ps1.setInt(2, user.getId());
                ResultSet rs1 = ps1.executeQuery();
                List<User> friends = new ArrayList<>();
                while(rs1.next()){
                    User friend = new User();
                    friend.setId(rs1.getInt("id"));
                    friend.setViewName(rs1.getString("viewname"));
                    friend.setSdt(rs1.getString("sdt"));
                    friend.setFullname(rs1.getString("fullname"));
                    friend.setGender(rs1.getString("gender"));
                    friend.setAddress(rs1.getString("address"));
                    friend.setDob(rs1.getDate("dob"));
                    friends.add(friend);
                }
                user.setFriends(friends);
            }else
		return null;
	    
        } catch (SQLException ex) {
	    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
        return user;
    }

    public boolean checkExist(User user) {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(CHECK_EXIST);
            ps.setString(1, user.getViewName());
	    
	    System.out.println("[DB]: Executing sql statement '" + ps.toString() + "'");
            ResultSet rs = ps.executeQuery();
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
            p = con.prepareStatement(GET_USER_BY_ID);
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
    
    public boolean deleteFriend(User user, User friendDel){
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(DELETE_FRIEND);
            ps.setInt(1, user.getId());
            ps.setInt(2, friendDel.getId());
            ps.setInt(3, friendDel.getId());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean insertAddFriend(User user, int userf_id) {
        try {
            PreparedStatement ps = con.prepareStatement(ADD_FRIEND);
            ps.setInt(1, user.getId());
            ps.setInt(2, userf_id);
            ps.setString(3, "2");            
            ps.executeUpdate();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    public boolean confirmAddFriend(User userSend, User userReceive) {
        try {
            PreparedStatement ps = con.prepareStatement(CONFIRM_ADD_FRIEND);
            ps.setInt(1, userSend.getId());
            ps.setInt(2, userReceive.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
