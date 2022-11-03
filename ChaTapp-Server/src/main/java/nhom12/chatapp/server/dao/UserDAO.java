package nhom12.chatapp.server.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import nhom12.chatapp.model.User;
import nhom12.hibernate.util.JPAUtil;

public class UserDAO extends BasicDAO<User> {

//    private final String CHECK_LOGIN = "SELECT * FROM tbluser WHERE viewname = ? AND password = ?";
//    private final String INSERT_USER = "INSERT INTO tbluser(sdt, password, viewname, fullname,gender, address, dob) VALUES (?,?,?,?,?,?,?);";
//    private final String CHECK_EXIST = "SELECT * FROM tbluser WHERE viewname = ? limit 1";
//    private final String GET_USER_BY_PHONE = "SELECT * FROM tbluser WHERE sdt = ?";
//    private final String GET_ALL_FRIEND = "Select distinct b.*  from tbluser a, tbluser b join tblfriend1 c on (c.user_id = ? and b.id = c.userf_id and c.status = 1) or (c.userf_id = ? and b.id = c.user_id and c.status = 1)";
//    private final String DELETE_FRIEND = "Delete from tblfriend1 where (user_id = ? and userf_id=?) or (user_id = ? and userf_id=?)";
//    private final String GET_ALL_USER = "Select * from tbluser where viewname like ?";
//    private final String ADD_FRIEND = "INSERT INTO tblfriend1 (user_id, userf_id, status) values(?, ?, ?)";

    private static final String GET_ALL = "SELECT u FROM User u";
    private static final String GET_BY_USERNAME = "SELECT u FROM User u WHERE u.username = :name";
    private static final String GET_BY_LOGIN = "SELECT u FROM User u WHERE u.username = :name AND u.password = :pass";
    
    public UserDAO() {
	entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
    public User findByLoginInfo(String username, String password) {
	
	TypedQuery<User> query = entityManager.createQuery(GET_BY_LOGIN, User.class);
	query.setParameter("name", username);
	query.setParameter("pass", password);
	return query.getSingleResult();
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

    public boolean insertFriend(User user, int userf_id) {
//        try {
//            PreparedStatement ps = con.prepareStatement(ADD_FRIEND);
//            ps.setInt(1, user.getId());
//            ps.setInt(2, userf_id);
//            ps.setString(3, "2");            
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            return false;
//        }
	// TODO: Work this out later
        return false;
    }

    public boolean deleteFriend(User user, int id_friend) {
	return false;
    }
}
