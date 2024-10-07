package ra.md4.service.admin.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4.dao.order.IOrderDao;
import ra.md4.models.Order;
import ra.md4.models.User;

import javax.servlet.http.HttpSession;
import java.util.List;
@Service
public class OrderServiceImpl implements IOrderService{
    @Autowired
    private IOrderDao iOrderDao;
    @Override
    public List<Order> getOrdersByUser(User user) {
        return iOrderDao.getOrdersByUser(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return iOrderDao.getUserByEmail(email);
    }

    @Override
    public void clearCartByUser(HttpSession session) {
        iOrderDao.clearCartByUser(session);
    }

    @Override
    public Order save(Order order) {
        iOrderDao.save(order);
        return order;
    }
}
