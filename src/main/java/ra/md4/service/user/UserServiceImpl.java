package ra.md4.service.user;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4.dao.user.IUserDao;
import ra.md4.dto.req.FormLogin;
import ra.md4.dto.req.FormRegister;
import ra.md4.dto.res.UserInfo;
import ra.md4.exception.AuthenticationException;
import ra.md4.models.User;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUserDao userDao;

    @Override
    public UserInfo login(FormLogin request) {
        try {
            User user = userDao.findByUsername(request.getUsername());
            boolean isSuccess = BCrypt.checkpw(request.getPassword(), user.getPassword());
            if (isSuccess) {
                return new UserInfo(user);
            }
            throw new AuthenticationException("Username or password incorrect");
        } catch (NoResultException e) {
            throw new AuthenticationException("Username or password incorrect");
        }
    }


    @Override
    @Transactional
    public void register(FormRegister request) {
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(12));
        User user = User.builder()
                .username(request.getUsername())
                .address(request.getAddress())
                .phone(request.getPhone())
                .password(hashedPassword)
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(false) // mặc định user
                .avatar("example.jpg")
                .createdAt(new Date())
                .status(true)
                .isDeleted(false)
                .updatedAt(new Date())
                .build();
        userDao.register(user);
    }


    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public void save(User user) {
        userDao.register(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }
}

