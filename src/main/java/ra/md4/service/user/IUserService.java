package ra.md4.service.user;

import ra.md4.dto.req.FormLogin;
import ra.md4.dto.req.FormRegister;
import ra.md4.dto.res.UserInfo;
import ra.md4.models.User;

import java.util.List;

public interface IUserService {
    UserInfo login(FormLogin request);
    UserInfo register(FormRegister request);
    List<User> getAll();
    User findById(Integer id);
    void changeStatus(Integer id);
}
