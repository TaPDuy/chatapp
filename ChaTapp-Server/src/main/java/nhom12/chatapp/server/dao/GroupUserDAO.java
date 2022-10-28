package nhom12.chatapp.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.Group;
import nhom12.chatapp.model.User;
import static nhom12.chatapp.server.dao.DAO.con;

public class GroupUserDAO extends DAO {

    public static final String INSERT = "INSERT INTO tbl_group_user (group_id, user_id) VALUES (?, ?)";
    public static final String SELECT_BY_USER = "SELECT * FROM tbl_group_user WHERE user_id = ?";
    
    private final GroupDAO groupDAO;
    
    public GroupUserDAO() {
	super();
	
	this.groupDAO = new GroupDAO();
    }
    
    public GroupUserDAO(GroupDAO groupDAO) {
	super();
	
	this.groupDAO = groupDAO;
    }
    
    public boolean insertGroupUser(Group group, User user) {
	
	try {
	    
	    PreparedStatement ps = con.prepareStatement(INSERT);
	    ps.setInt(1, group.getId());
	    ps.setInt(2, user.getId());
	    
	    System.out.println("[DB]: Executing sql statement '" + ps.toString() + "'");
	    ps.executeUpdate();
	    
	} catch (SQLException ex) {
	    System.out.println("[DB]: Could not insert group-user: " + group.getId() + "-" + user.getId());
	    Logger.getLogger(GroupUserDAO.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	}
	return true;
    }
    
    public List<Group> getGroupsByUser(User user) {
	
	List<Group> groups = new ArrayList<>();
	
	try {
	    
	    PreparedStatement ps = con.prepareStatement(SELECT_BY_USER);
	    ps.setInt(1, user.getId());
	    
	    System.out.println("[DB]: Executing sql statement '" + ps.toString() + "'");
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
		int id = rs.getInt("group_id");
		groups.add(groupDAO.getGroupByID(id));
	    }
	    
	} catch (SQLException ex) {
	    System.out.println("[DB]: Could not get groups by id of user: " + user.toString());
	    Logger.getLogger(GroupUserDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	return groups;
    }
}
