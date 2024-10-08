package ra.md4.service.admin.admin;

import ra.md4.models.Product;
import ra.md4.models.User;

import java.util.List;

public interface IAdminService {
    List<User> searchName(String name);
    void updateUserStatus(Integer id);
    void changeUserRole(Integer id, Boolean role);
    List<Product> searchProduct(String name);
}
