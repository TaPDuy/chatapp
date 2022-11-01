package nhom12.chatapp.server.dao;

import java.util.List;

public interface DAO<T> {
    
    public T findById(int id);
    public List<T> findAll();
    public boolean save(T t);
    public boolean update(T t);
    public boolean delete(int id);
}
