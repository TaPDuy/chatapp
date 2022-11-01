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
			cb.equal(root.get("viewname"), username), 
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
}
