package nhom12.chatapp.server.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import nhom12.chatapp.model.Message;
import nhom12.hibernate.util.JPAUtil;

public class MessageDAO extends BasicDAO<Message> {

    private static final String GET_ALL = "SELECT msg FROM Message msg";
    
    public MessageDAO() {
	entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
    @Override
    public Optional<Message> findById(int id) {
	return Optional.ofNullable(entityManager.find(Message.class, id));
    }
    
    @Override
    public List<Message> findAll() {
	
	TypedQuery<Message> query = entityManager.createQuery(GET_ALL, Message.class);
	return query.getResultList();
    }

    @Override
    public boolean save(Message msg) {
	
	try {
	    executeTransaction(entityManager -> entityManager.persist(msg));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean update(Message msg) {
	
	try {
	    executeTransaction(entityManager -> entityManager.merge(msg));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean delete(Message msg) {
	
	try {
	    executeTransaction(entityManager -> entityManager.remove(msg));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }
    
}
