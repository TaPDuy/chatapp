package nhom12.chatapp.server.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import nhom12.chatapp.util.ConsoleLogger;

public abstract class BasicDAO<T> implements DAO<T> {
    
    public void close() {
	entityManager.close();
    }
    
    @Override
    public abstract Optional<T> findById(int id);

    @Override
    public abstract List<T> findAll();

    @Override
    public abstract boolean save(T t);

    @Override
    public abstract boolean update(T t);
    
    @Override
    public abstract boolean delete(T t);
    
    protected EntityManager entityManager;
    
    
    protected void executeTransaction(Consumer<EntityManager> action) {
        
	EntityTransaction tx = entityManager.getTransaction();
        
	try {
            tx.begin();
            action.accept(entityManager);
            tx.commit(); 
        }
        catch (RuntimeException e) {
            tx.rollback();
            ConsoleLogger.log(e.getMessage(), "DB", ConsoleLogger.ERROR);
	    throw e;
        }
    }
}
