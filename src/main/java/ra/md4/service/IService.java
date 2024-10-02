package ra.md4.service;

import java.util.List;

public interface IService <T,E>{
    List<T> getAll();
    T findById(E id);
    void save(T t);
    void update(T t);
    void delete(E id);
}
