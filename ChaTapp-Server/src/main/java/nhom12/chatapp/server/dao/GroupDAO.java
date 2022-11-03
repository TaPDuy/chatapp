package nhom12.chatapp.server.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import nhom12.chatapp.model.Group;
import nhom12.hibernate.util.JPAUtil;


public class GroupDAO extends BasicDAO<Group> {

    private static final String GET_ALL = "SELECT g FROM Group g";
    private static final String GET_BY_NAME = "SELECT g FROM Group g WHERE g.name = :name";
    
    public GroupDAO() {
	entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    public boolean checkExist(Group group) {
	
	TypedQuery<Group> query = entityManager.createQuery(GET_BY_NAME, Group.class);
	query.setParameter("name", group.getName());
	return query.getResultList().size() == 1;
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
