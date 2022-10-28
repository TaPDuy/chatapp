package nhom12.chatapp.server.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nhom12.chatapp.model.Group;
import nhom12.chatapp.model.User;
import static nhom12.chatapp.server.dao.DAO.con;

public class GroupUserDAO extends DAO {

    public static final String INSERT = "INSERT INTO tbl_group_user (group_id, user_id) VALUES (?, ?)";
    
    public GroupUserDAO() {
	super();
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
}
