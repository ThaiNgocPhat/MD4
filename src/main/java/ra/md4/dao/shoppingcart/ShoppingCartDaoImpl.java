package ra.md4.dao.shoppingcart;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ra.md4.models.Order;
import ra.md4.models.ShoppingCart;
import ra.md4.models.CartItem;
import ra.md4.models.Product;
import ra.md4.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Date;

@Repository
@Transactional
public class ShoppingCartDaoImpl implements IShoppingCartDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addItemToCart(Integer userId, Integer productId, Integer quantity) {
        ShoppingCart cart = getCartByUserId(userId);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setUser(entityManager.find(User.class, userId));
            entityManager.persist(cart);
        }

        // Tạo CartItem mới
        CartItem cartItem = new CartItem();
        cartItem.setProduct(entityManager.find(Product.class, productId));
        cartItem.setQuantity(quantity);
        cartItem.setShoppingCart(cart);

        // Thêm sản phẩm vào giỏ hàng
        cart.getItems().add(cartItem);
        entityManager.merge(cart);
    }

    @Override
    public void updateItemInCart(Integer userId, Integer productId, Integer quantity) {
        ShoppingCart cart = getCartByUserId(userId);
        if (cart != null) {
            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
            entityManager.merge(cart);
        }
    }

    @Override
    public void removeItemFromCart(Integer userId, Integer productId) {
        ShoppingCart cart = getCartByUserId(userId);
        if (cart != null) {
            cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
            entityManager.merge(cart);
        }
    }

    @Override
    public ShoppingCart getCartByUserId(Integer userId) {
        TypedQuery<ShoppingCart> query = entityManager.createQuery(
                "SELECT c FROM ShoppingCart c WHERE c.user.id = :id", ShoppingCart.class);
        query.setParameter("id", userId);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Trả về null nếu không tìm thấy giỏ hàng
        }
    }

    @Override
    public Order checkout(Integer id) {
        ShoppingCart cart = getCartByUserId(id);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Giỏ hàng trống!");
        }

        // Tạo đơn hàng mới
        Order order = new Order();
        order.setUser(entityManager.find(User.class, id));
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(Order.OrderStatus.WAITING);
        order.setCreatedAt(new Date());

        // Sao chép các CartItem vào Order
        for (CartItem item : cart.getItems()) {
            CartItem orderItem = new CartItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(order); // Thiết lập mối quan hệ với Order
            order.getItems().add(orderItem); // Thêm vào danh sách các sản phẩm trong đơn hàng
        }

        // Lưu đơn hàng
        entityManager.persist(order);

        // Xóa giỏ hàng sau khi thanh toán (tùy chọn)
        entityManager.remove(cart);
        return order;
    }

}
