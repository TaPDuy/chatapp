package nhom12.chatapp.server.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import nhom12.chatapp.model.Group;
import nhom12.chatapp.util.ConsoleLogger;
import nhom12.hibernate.util.JPAUtil;


public class GroupDAO implements DAO<Group> {

    public GroupDAO() {
	
    }

    public boolean checkExist(Group group) {
	
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin();
	    
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Group> cr = cb.createQuery(Group.class);
	    Root<Group> root = cr.from(Group.class);

	    cr.select(root).where(cb.and(cb.equal(root.get("id"), group.getId())
		)
	    );

	    Query query = entityManager.createQuery(cr);
	    boolean result = query.getResultList().size() == 1;
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();
	    return result;

	} catch (Exception ex) {
	    
	    ConsoleLogger.log("Failed trying to check if user exist: " + group.toString(), 
		"DAO", 
		ConsoleLogger.ERROR
	    );
	    entityManager.close();
	}
	
	return false;
    }
    
    @Override
    public Group findById(int id) {
	
	Group result;
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin();
	    
	    result = entityManager.getReference(Group.class, id);
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();

	} catch (Exception ex) {
	    ConsoleLogger.log("Failed trying to get group of id: " + id, "DAO", ConsoleLogger.ERROR);
	    entityManager.close();
	    return null;
	}
	
	return result;
	
    }
    
    @Override
    public List<Group> findAll() {
	
	List<Group> results;
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin(); {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Group> cr = cb.createQuery(Group.class);
		cr.select(cr.from(Group.class));

		Query query = entityManager.createQuery(cr);
		results = query.getResultList();
	    }
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();

	} catch (Exception ex) {
	    ConsoleLogger.log("Failed trying to get all groups", "DAO", ConsoleLogger.ERROR);
	    entityManager.close();
	    return null;
	}
	
	return results;
    }

    @Override
    public boolean save(Group group) {
	
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin();

	    entityManager.persist(group);

	    entityManager.getTransaction().commit();
	    entityManager.close();
	    
	} catch (Exception ex) {
	    ConsoleLogger.log("Failed to insert group: " + group.toString(), "DAO", ConsoleLogger.ERROR);
	    entityManager.close();
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean update(Group group) {
	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
	
	try {
	    
	    entityManager.getTransaction().begin(); {

		Group updateGroup = entityManager.find(Group.class, group.getId());
		updateGroup.setName(group.getName());
	    }
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();

	} catch (Exception ex) {
	    ConsoleLogger.log("Failed to update group: " + group.toString(), "DAO", ConsoleLogger.ERROR);
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

		Group updateGroup = entityManager.find(Group.class, id);
		entityManager.remove(updateGroup);
	    }
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();

	} catch (Exception ex) {
	    ConsoleLogger.log("Failed to delete group of id: " + id, "DAO", ConsoleLogger.ERROR);
	    entityManager.close();
	    return false;
	}
	
	return true;
    }

}
