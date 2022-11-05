package nhom12.chatapp.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import nhom12.chatapp.model.Group;
import nhom12.chatapp.model.User;
import nhom12.chatapp.util.ConsoleLogger;
import nhom12.hibernate.util.JPAUtil;


public class GroupDAO extends BasicDAO<Group> {

    private static final String GET_ALL = "SELECT g FROM Group g";
    private static final String GET_BY_NAME = "SELECT g FROM Group g WHERE g.name = :name";
    private static final String GET_BY_KEY = "SELECT g FROM Group g WHERE g.name LIKE :key";
    private static final String GET_BY_MEMBER = "SELECT u FROM Group g JOIN g.members u WHERE g.id = :group AND u.id = :user";
    
    public GroupDAO() {
	entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    public boolean checkExist(Group group) {
	
	TypedQuery<Group> query = entityManager.createQuery(GET_BY_NAME, Group.class);
	query.setParameter("name", group.getName());
	return query.getResultList().size() == 1;
    }
    
    public boolean isMember(Group group, User user) {
	
	TypedQuery<User> query = entityManager.createQuery(GET_BY_MEMBER, User.class);
	query.setParameter("group", group.getId());
	query.setParameter("user", user.getId());
	return query.getResultList().size() == 1;
    }
    
    public Optional<Group> findByName(String name) {
	
	try {
	    TypedQuery<Group> query = entityManager.createQuery(GET_BY_NAME, Group.class);
	    query.setParameter("name", name);
	    return Optional.of(query.getSingleResult());
	} catch (RuntimeException e) {
	    ConsoleLogger.log(e.getMessage(), "DB", ConsoleLogger.ERROR);
	}
	
	return Optional.empty();
    }
    
    public List<Group> findByKey(String key) {
	
	try {
	    TypedQuery<Group> query = entityManager.createQuery(GET_BY_KEY, Group.class);
	    query.setParameter("key", "%" + key + "%");
	    return query.getResultList();
	} catch (RuntimeException e) {
	    ConsoleLogger.log(e.getMessage(), "DB", ConsoleLogger.ERROR);
	}
	
	return new ArrayList<>();
    }
    
    @Override
    public Optional<Group> findById(int id) {
	return Optional.ofNullable(entityManager.find(Group.class, id));
    }
    
    @Override
    public List<Group> findAll() {
	
	TypedQuery<Group> query = entityManager.createQuery(GET_ALL, Group.class);
	return query.getResultList();
    }

    @Override
    public boolean save(Group group) {
	
	try {
	    executeTransaction(entityManager -> entityManager.persist(group));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean update(Group group) {
	
	try {
	    executeTransaction(entityManager -> entityManager.merge(group));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean delete(Group group) {
	
	try {
	    executeTransaction(entityManager -> entityManager.remove(group));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }

}
