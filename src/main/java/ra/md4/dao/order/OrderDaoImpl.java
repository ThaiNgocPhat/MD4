package ra.md4.dao.order;

import org.springframework.stereotype.Repository;
import ra.md4.models.Order;
import ra.md4.models.ShoppingCart;
import ra.md4.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.util.List;
@Repository
public class OrderDaoImpl implements IOrderDao{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Order> getOrdersByUser(User user) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.user = :user", Order.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public User getUserByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
               .setParameter("email", email)
               .getSingleResult();
    }

    @Override
    public void clearCartByUser(HttpSession session) {
        User user = (User) session.getAttribute("userLogin");
        if (user != null) {
            List<ShoppingCart> shoppingCarts = entityManager.createQuery("SELECT sc FROM ShoppingCart sc WHERE sc.user = :user", ShoppingCart.class)
                    .setParameter("user", user)
                    .getResultList();
            for (ShoppingCart cart : shoppingCarts) {
                entityManager.remove(cart);
            }
        }
    }

    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }

}
