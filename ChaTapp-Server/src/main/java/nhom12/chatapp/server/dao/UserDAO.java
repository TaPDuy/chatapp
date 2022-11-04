package nhom12.chatapp.server.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import nhom12.chatapp.model.User;
import nhom12.chatapp.util.ConsoleLogger;
import nhom12.hibernate.util.JPAUtil;

public class UserDAO extends BasicDAO<User> {

//    private final String CHECK_LOGIN = "SELECT * FROM tbluser WHERE viewname = ? AND password = ?";
//    private final String INSERT_USER = "INSERT INTO tbluser(sdt, password, viewname, fullname,gender, address, dob) VALUES (?,?,?,?,?,?,?);";
//    private final String CHECK_EXIST = "SELECT * FROM tbluser WHERE viewname = ? limit 1";
//    private final String GET_USER_BY_ID = "SELECT * FROM tbluser WHERE sdt = ?";
//    private final String GET_ALL_FRIEND = "Select distinct b.*  from tbluser a, tbluser b join tblfriend1 c on (c.user_id = ? and b.id = c.userf_id and c.status = 1) or (c.userf_id = ? and b.id = c.user_id and c.status = 1)";
//    private final String DELETE_FRIEND = "Delete from tblfriend1 where (user_id = ? and userf_id=?) or (user_id = ? and userf_id=?)";
//    private final String GET_ALL_USER = "Select * from tbluser where viewname like ?";
//    private final String ADD_FRIEND = "INSERT INTO tblfriend1 (user_id, userf_id, status) values(?, ?, ?)";
//    private final String CONFIRM_ADD_FRIEND = "Update tblfriend1 set status = 1 where user_id=? and userf_id=?";

    private static final String GET_ALL = "SELECT u FROM User u";
    private static final String GET_BY_USERNAME = "SELECT u FROM User u WHERE u.username = :name";
    private static final String GET_BY_LOGIN = "SELECT u FROM User u WHERE u.username = :name AND u.password = :pass";
    
    public UserDAO() {
	entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
    public User findByLoginInfo(String username, String password) {
	
	try {
	    
	    TypedQuery<User> query = entityManager.createQuery(GET_BY_LOGIN, User.class);
	    query.setParameter("name", username);
	    query.setParameter("pass", password);
	    return query.getSingleResult();
	    
	} catch (RuntimeException e) {
	    ConsoleLogger.log(e.getMessage(), "DB", ConsoleLogger.ERROR);
//=======
//            ResultSet rs = ps.executeQuery();
//	    user = new User();
//            if (rs.next()) {
//                user.setId(rs.getInt("id"));
//                user.setViewName(rs.getString("viewname"));
//                user.setSdt(rs.getString("sdt"));
//                user.setPassword(password);
//                user.setFullname(rs.getString("fullname"));
//                user.setGender(rs.getString("gender"));
//                user.setAddress(rs.getString("address"));
//                user.setDob(rs.getDate("dob"));
//                user.setStatus("online");
//                PreparedStatement ps1 = con.prepareStatement(GET_ALL_FRIEND);
//                ps1.setInt(1, user.getId());
//                ps1.setInt(2, user.getId());
//                ResultSet rs1 = ps1.executeQuery();
//                List<User> friends = new ArrayList<>();
//                while(rs1.next()){
//                    User friend = new User();
//                    friend.setId(rs1.getInt("id"));
//                    friend.setViewName(rs1.getString("viewname"));
//                    friend.setSdt(rs1.getString("sdt"));
//                    friend.setFullname(rs1.getString("fullname"));
//                    friend.setGender(rs1.getString("gender"));
//                    friend.setAddress(rs1.getString("address"));
//                    friend.setDob(rs1.getDate("dob"));
//                    friends.add(friend);
//                }
//                user.setFriends(friends);
//            }else
//		return null;
//	    
//        } catch (SQLException ex) {
//	    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
//>>>>>>> 8456aeb261e129b9beec9f8e6cb1fd8e02c72753
	}
	
	return null;
    }
    
    public boolean checkExist(User user) {
	
	TypedQuery<User> query = entityManager.createQuery(GET_BY_USERNAME, User.class);
	query.setParameter("name", user.getUsername());
	return query.getResultList().size() == 1;
    }
    
    @Override
    public Optional<User> findById(int id) {
	return Optional.ofNullable(entityManager.find(User.class, id));
    }
    
    @Override
    public List<User> findAll() {
	
	TypedQuery<User> query = entityManager.createQuery(GET_ALL, User.class);
	return query.getResultList();
    }

    @Override
    public boolean save(User user) {
	
	try {
	    executeTransaction(entityManager -> entityManager.persist(user));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean update(User user) {
	
	try {
	    executeTransaction(entityManager -> entityManager.merge(user));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean delete(User user) {
	
	try {
	    executeTransaction(entityManager -> entityManager.remove(user));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }

//    public User getUserByPhone(User user){
//        PreparedStatement p;
//        try {
//            p = con.prepareStatement(GET_USER_BY_ID);
//            p.setString(1, user.getSdt());
//            ResultSet rs = p.executeQuery();
//            while (rs.next()) {
//                user.setId(rs.getInt("id"));
//                user.setViewName(rs.getString("viewname"));
//                user.setFullname(rs.getString("fullname"));
//                user.setGender(rs.getString("gender"));
//                user.setAddress(rs.getString("address"));
//                user.setDob(rs.getDate("dob"));
//                user.setStatus("1");
//                return user;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//    
//    public boolean deleteFriend(User user, User friendDel){
//        PreparedStatement ps;
//        try {
//            ps = con.prepareStatement(DELETE_FRIEND);
//            ps.setInt(1, user.getId());
//            ps.setInt(2, friendDel.getId());
//            ps.setInt(3, friendDel.getId());
//            ps.setInt(4, user.getId());
//            ps.executeUpdate();
//            return true;
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
//
//    public boolean insertAddFriend(User user, int userf_id) {
//        try {
//            PreparedStatement ps = con.prepareStatement(ADD_FRIEND);
//            ps.setInt(1, user.getId());
//            ps.setInt(2, userf_id);
//            ps.setString(3, "2");            
//            ps.executeUpdate();

//    public boolean deleteFriend(User user, int id_friend) {
//	return false;
//    }
//    
//    public boolean confirmAddFriend(User userSend, User userReceive) {
//        try {
//            PreparedStatement ps = con.prepareStatement(CONFIRM_ADD_FRIEND);
//            ps.setInt(1, userSend.getId());
//            ps.setInt(2, userReceive.getId());
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            return false;
//        }
//        return true;
//    }
}
