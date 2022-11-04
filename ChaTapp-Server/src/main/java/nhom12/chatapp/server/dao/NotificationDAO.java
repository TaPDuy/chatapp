package nhom12.chatapp.server.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import nhom12.chatapp.model.Notification;
import nhom12.hibernate.util.JPAUtil;

/**
 *
 * @author Smile
 */
public class NotificationDAO extends BasicDAO<Notification>{
    
    private static final String GET_ALL = "SELECT not FROM Notification not";
    private static final String GET_ALL_NOTIFICATION = "Select * from tblnotification where id_user = ?";
    
    public NotificationDAO(){
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
//    public List<Notification> getAllNotification(int id_user){
//        PreparedStatement ps;      
//        List<Notification> notifications = new ArrayList<>();
//        try {
//            ps = con.prepareStatement(GET_ALL_NOTIFICATION);
//            ps.setInt(1, id_user);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Notification notification = new Notification();
//                notification.setActive(rs.getString("active"));
//                notification.setContent(rs.getString("content"));
//                //notification.setUserSend(rs.getString(""));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            return notifications;
//    }
    
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
}
