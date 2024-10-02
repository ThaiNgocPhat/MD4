package ra.md4.service.user;

import ra.md4.dto.req.FormLogin;
import ra.md4.dto.req.FormRegister;
import ra.md4.dto.res.UserInfo;
import ra.md4.models.User;
import ra.md4.service.IService;

import java.util.List;

public interface IUserService extends IService<User, Integer> {
    UserInfo login(FormLogin request);
    void register(FormRegister request);
    User findByUsername(String username);
}
