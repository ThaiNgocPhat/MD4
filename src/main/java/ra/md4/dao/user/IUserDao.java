package ra.md4.dao.user;

import ra.md4.dao.IGenericDao;
import ra.md4.models.User;

public interface IUserDao extends IGenericDao<User, Integer> {
    User findByUsername(String username);
    void register(User user);
}
