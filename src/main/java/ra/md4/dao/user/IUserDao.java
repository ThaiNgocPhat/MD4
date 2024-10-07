package ra.md4.dao.user;

import ra.md4.dao.IGenericDao;
import ra.md4.models.User;

import java.util.List;

public interface IUserDao {
    User login(String username);
    void register(User user);
    List<User> getUsers();
    User findById(Integer id);
    void changeStatus(Integer id);
}
