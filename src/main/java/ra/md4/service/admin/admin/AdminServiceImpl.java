package ra.md4.service.admin.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.md4.dao.admin.IAdminDao;
import ra.md4.models.Product;
import ra.md4.models.User;

import java.util.List;


@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private IAdminDao iAdminDao;

    @Override
    public List<User> searchName(String name) {
        return iAdminDao.searchName(name);
    }

    @Transactional
    @Override
    public void updateUserStatus(Integer id) {
        iAdminDao.updateUserStatus(id);
    }

    @Transactional
    @Override
    public void changeUserRole(Integer id, Boolean role) {
        iAdminDao.changeUserRole(id, role);
    }

    @Override
    public List<Product> searchProduct(String name) {
        return iAdminDao.searchProduct(name);
    }

}
