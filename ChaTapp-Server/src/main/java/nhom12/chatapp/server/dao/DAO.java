package nhom12.chatapp.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {
    
    public static Connection con;
    
    public DAO(){
	
	try {
	    if (con == null)  
	    {
		String url = "jdbc:mysql://localhost:3306/app_multi_chat";
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url, "root", "bandzu1231");
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
	} catch (ClassNotFoundException ex) {
	    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
}
