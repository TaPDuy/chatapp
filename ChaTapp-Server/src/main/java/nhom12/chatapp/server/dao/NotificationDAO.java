package nhom12.chatapp.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import nhom12.chatapp.model.Notification;
import nhom12.chatapp.model.User;
import nhom12.chatapp.util.ConsoleLogger;
import nhom12.hibernate.util.JPAUtil;

public class NotificationDAO extends BasicDAO<Notification>{
    
    private static final String GET_BY_RECIPIENT = "SELECT n FROM Notification n WHERE n.recipient = :user";
    private static final String GET_ALL = "SELECT not FROM Notification not";
     
    public NotificationDAO(){
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
    public List<Notification> findByRecipient(User user) {
	
	try {
	    
	    TypedQuery<Notification> query = entityManager.createQuery(GET_BY_RECIPIENT, Notification.class);
	    query.setParameter("user", user);
	    return query.getResultList();
	    
	} catch (RuntimeException e) {
	    ConsoleLogger.log(e.getMessage(), "DB", ConsoleLogger.ERROR);
	}
	
	return new ArrayList<>();
    }
    
    @Override
    public Optional<Notification> findById(int id) {
	return Optional.ofNullable(entityManager.find(Notification.class, id));
    }
    
    @Override
    public List<Notification> findAll() {
	
	TypedQuery<Notification> query = entityManager.createQuery(GET_ALL, Notification.class);
	return query.getResultList();
    }

    @Override
    public boolean save(Notification not) {
	
	try {
	    executeTransaction(entityManager -> entityManager.persist(not));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }
    
    public boolean save(List<Notification> nots) {
	
	try {
	    executeTransaction(entityManager -> nots.forEach(not -> entityManager.persist(not)));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean update(Notification not) {
	
	try {
	    executeTransaction(entityManager -> entityManager.merge(not));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }

    @Override
    public boolean delete(Notification not) {
	
	try {
	    executeTransaction(entityManager -> entityManager.remove(not));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }
    
    public boolean refresh(Notification not) {
	
	try {
	    executeTransaction(entityManager -> entityManager.refresh(not));
	} catch (RuntimeException e) {
	    return false;
	}
	
	return true;
    }
}
