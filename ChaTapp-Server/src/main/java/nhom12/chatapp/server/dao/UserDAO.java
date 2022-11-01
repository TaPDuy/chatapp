package nhom12.chatapp.server.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import nhom12.chatapp.model.User;
import nhom12.chatapp.util.ConsoleLogger;
import nhom12.hibernate.util.JPAUtil;

public class UserDAO implements DAO<User> {

//    private final String CHECK_LOGIN = "SELECT * FROM tbluser WHERE viewname = ? AND password = ?";
//    private final String INSERT_USER = "INSERT INTO tbluser(sdt, password, viewname, fullname,gender, address, dob) VALUES (?,?,?,?,?,?,?);";
//    private final String CHECK_EXIST = "SELECT * FROM tbluser WHERE viewname = ? limit 1";
//    private final String GET_USER_BY_PHONE = "SELECT * FROM tbluser WHERE sdt = ?";
//    private final String GET_ALL_FRIEND = "Select distinct b.*  from tbluser a, tbluser b join tblfriend1 c on (c.user_id = ? and b.id = c.userf_id and c.status = 1) or (c.userf_id = ? and b.id = c.user_id and c.status = 1)";
//    private final String DELETE_FRIEND = "Delete from tblfriend1 where (user_id = ? and userf_id=?) or (user_id = ? and userf_id=?)";
//    private final String GET_ALL_USER = "Select * from tbluser where viewname like ?";
//    private final String ADD_FRIEND = "INSERT INTO tblfriend1 (user_id, userf_id, status) values(?, ?, ?)";

    public UserDAO() {
    
    }
    
    public boolean checkExist(User user) {
	
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin();
	    
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<User> cr = cb.createQuery(User.class);
	    Root<User> root = cr.from(User.class);

	    cr.select(root).where(
		cb.and(
		    cb.equal(root.get("id"), user.getId())
		)
	    );

	    Query query = entityManager.createQuery(cr);
	    boolean result = query.getResultList().size() == 1;
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();
	    return result;

	} catch (Exception ex) {
	    
	    ConsoleLogger.log(
		"Failed trying to check if user exist: " + user.toString(), 
		"DAO", 
		ConsoleLogger.ERROR
	    );
	    entityManager.close();
	}
	
	return false;
    }
    
    public User findByLoginInfo(String username, String password) {
	
	User result;
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin(); {
	    
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);
		
		cr.select(root).where(
		    cb.and(
			cb.equal(root.get("username"), username), 
			cb.equal(root.get("password"), password)
		    )
		);

		Query query = entityManager.createQuery(cr);
		result = (User) query.getSingleResult();
	    }
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();

	} catch (Exception ex) {
	    
	    ConsoleLogger.log(
		"Failed trying to get user with login info: " + username + ":" + password, 
		"DAO", 
		ConsoleLogger.ERROR
	    );
	    entityManager.close();
	    return null;
	}
	
	return result;
    }
    
    @Override
    public User findById(int id) {
	
	User result;
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin();
	    
	    result = entityManager.getReference(User.class, id);
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();

	} catch (Exception ex) {
	    ConsoleLogger.log("Failed trying to get user of id: " + id, "DAO", ConsoleLogger.ERROR);
	    entityManager.close();
	    return null;
	}
	
	return result;
	
    }
    
    @Override
    public List<User> findAll() {
	
	List<User> results;
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin(); {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		cr.select(cr.from(User.class));

		Query query = entityManager.createQuery(cr);
		results = query.getResultList();
	    }
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();

	} catch (Exception ex) {
	    ConsoleLogger.log("Failed trying to get all users", "DAO", ConsoleLogger.ERROR);
	    entityManager.close();
	    return null;
	}
	
	return results;
    }

    @Override
    public boolean save(User user) {
	
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin();

	    entityManager.persist(user);

	    entityManager.getTransaction().commit();
	    entityManager.close();
	    
	} catch (Exception ex) {
	    ConsoleLogger.log("Failed to insert user: " + user.toString(), "DAO", ConsoleLogger.ERROR);
	    entityManager.close();
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean update(User user) {
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin(); {

		User updateGroup = entityManager.find(User.class, user.getId());
//		updateGroup.setName(group.getName());
	    }
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();

	} catch (Exception ex) {
	    ConsoleLogger.log("Failed to update user: " + user.toString(), "DAO", ConsoleLogger.ERROR);
	    entityManager.close();
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean delete(int id) {
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin(); {

		User updateGroup = entityManager.find(User.class, id);
		entityManager.remove(updateGroup);
	    }
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();

	} catch (Exception ex) {
	    ConsoleLogger.log("Failed to delete user of id: " + id, "DAO", ConsoleLogger.ERROR);
	    entityManager.close();
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
