package nhom12.chatapp.server.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    
    public Optional<T> findById(int id);
    public List<T> findAll();
    public boolean save(T t);
    public boolean update(T t);
    public boolean delete(T t);
}
