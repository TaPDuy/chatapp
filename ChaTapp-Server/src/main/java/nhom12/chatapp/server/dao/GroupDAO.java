package nhom12.chatapp.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.Group;
import static nhom12.chatapp.server.dao.DAO.con;

public class GroupDAO extends DAO {

    private static final String CHECK_EXIST = "SELECT * FROM tblgroup WHERE name = ? limit 1";
    private static final String INSERT_GROUP = "INSERT INTO tblgroup(name) VALUES (?)";
    private static final String GET_BY_ID = "SELECT * FROM tblgroup WHERE id = ?";
    
    public GroupDAO() {
	super();
    }
    
    public boolean checkExist(Group group) {
	
        try {
            PreparedStatement ps = con.prepareStatement(CHECK_EXIST);
            ps.setString(1, group.getName());
            
	    System.out.println("[DB]: Executing sql statement '" + ps.toString() + "'");
	    ResultSet rs = ps.executeQuery();
            return rs.next();
	    
        } catch (SQLException ex) {
	    System.out.println("[DB]: Could not perform checkExist for " + group.toString());
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Group getGroupByID (int id) {
	
	try {
	    
	    PreparedStatement ps = con.prepareStatement(CHECK_EXIST);
	    ps.setInt(1, id);
	    
	    Group group = new Group();
	    ResultSet rs = ps.executeQuery();
	    if(rs.next()) {
		group.setId(rs.getInt("id"));
		group.setName(rs.getString("name"));
	    }
	    
	} catch (SQLException ex) {
	    Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	return null;
    }
    
    public boolean insertGroup(Group group) {
	
	try {
	    
	    PreparedStatement ps = con.prepareStatement(INSERT_GROUP);
	    ps.setString(1, group.getName());
	    
	    System.out.println("[DB]: Executing sql statement '" + ps.toString() + "'");
	    ps.executeUpdate();
	    
	} catch (SQLException ex) {
	    System.out.println("[DB]: Could not insert " + group.toString());
	    Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	}
	return true;
    }
}
