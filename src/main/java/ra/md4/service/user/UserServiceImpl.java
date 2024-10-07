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
    public UserInfo login(FormLogin request) throws AuthenticationException {
        try {
            User user = userDao.login(request.getUsername());
            // Kiểm tra mật khẩu
            if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
                // Nếu đăng nhập thành công, tạo đối tượng UserInfo
                return UserInfo.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .avatar(user.getAvatar())
                        .address(user.getAddress())
                        .fullName(user.getFullName())
                        .role(user.isRole())
                        .status(user.isStatus())
                        .build();
            }
            throw new AuthenticationException("Username or password incorrect");
        } catch (NoResultException e) {
            throw new AuthenticationException("Username or password incorrect");
        }
    }


    @Override
    public UserInfo register(FormRegister request) {
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(12));
        User user = User.builder()
                .id(request.getId())
                .username(request.getUsername())
                .address(request.getAddress())
                .phone(request.getPhone())
                .password(hashedPassword)
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(false)
                .avatar("example.jpg")
                .status(true)
                .createdAt(new Date())
                .updatedAt(new Date())
                .isDeleted(false)
                .build();
        userDao.register(user);
        return new UserInfo(user);
    }

    @Override
    public void changeStatus(Integer id) {
        userDao.changeStatus(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getUsers();
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }


}

