package ra.md4.dao.order;

import ra.md4.models.Order;
import ra.md4.models.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IOrderDao {
    List<Order> getOrdersByUser(User user);
    User getUserByEmail(String email);
    void clearCartByUser(HttpSession session);
    void save(Order order);
}
