package ra.md4.dao;

import java.util.List;

public interface IGenericDao <T,E>{
    List<T> getAll();
    T findById(E id);
    void save(T t);
    void update(T t);
    void delete(E id);
}
