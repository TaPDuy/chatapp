package nhom12.chatapp.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import nhom12.chatapp.model.Group;
import nhom12.chatapp.model.Message;
import nhom12.chatapp.model.User;
import nhom12.chatapp.util.ConsoleLogger;
import nhom12.hibernate.util.JPAUtil;

public class MessageDAO extends BasicDAO<Message> {

    private static final String GET_ALL = "SELECT msg FROM Message msg";
    private static final String GET_BY_GROUP = "SELECT msg FROM Message msg WHERE msg.group = :group ORDER BY msg.timeSent";
    private static final String GET_BY_USER = "SELECT msg FROM Message msg JOIN msg.recipients rec WHERE (rec = :rec AND msg.sender = :sen) OR (rec = :sen AND msg.sender = :rec) ORDER BY msg.timeSent";
    
    public MessageDAO() {
	entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
    public List<Message> findByUser(User sender, User recipient) {
	
	try {
	    
	    TypedQuery<Message> query = entityManager.createQuery(GET_BY_USER, Message.class);
	    query.setParameter("rec", recipient);
	    query.setParameter("sen", sender);
	    return query.getResultList();
		    
	} catch (RuntimeException e) {
	    ConsoleLogger.log(e.getMessage(), "DB", ConsoleLogger.ERROR);
	}
	
	return new ArrayList<>();
    }
    
    public List<Message> findByGroup(Group group) {
	
	try {
	    
	    TypedQuery<Message> query = entityManager.createQuery(GET_BY_GROUP, Message.class);
	    query.setParameter("group", group);
	    return query.getResultList();
		    
	} catch (RuntimeException e) {
	    ConsoleLogger.log(e.getMessage(), "DB", ConsoleLogger.ERROR);
	}
	
	return new ArrayList<>();
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
