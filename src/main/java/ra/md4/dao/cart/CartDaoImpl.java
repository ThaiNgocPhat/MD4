package ra.md4.dao.cart;

import org.springframework.stereotype.Repository;
import ra.md4.models.Cart;
import ra.md4.models.CartItem;
import ra.md4.models.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CartDaoImpl implements ICartDao {

    @PersistenceContext
    private EntityManager entityManager;
    private Map<Long, Cart> userCarts = new HashMap<>();

    @Override
    public void addProductToCart(Long userId, Product product, int quantity) {
        Cart cart = userCarts.getOrDefault(userId, new Cart());
        List<CartItem> items = cart.getItems();

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                cart.setItems(items);
                userCarts.put(userId, cart);
                return;
            }
        }

        // Nếu sản phẩm chưa có trong giỏ, thêm mới
        items.add(new CartItem(product, quantity));
        cart.setItems(items);
        userCarts.put(userId, cart);
    }

    @Override
    public void removeProductFromCart(Long userId, Long productId) {
        Cart cart = userCarts.get(userId);
        if (cart != null) {
            cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
            userCarts.put(userId, cart);
        }
    }

    @Override
    public void updateProductQuantity(Long userId, Long productId, int newQuantity) {
        Cart cart = userCarts.get(userId);
        if (cart != null) {
            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    item.setQuantity(newQuantity);
                    userCarts.put(userId, cart);
                    return;
                }
            }
        }
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return userCarts.get(userId);
    }

    @Override
    public List<Product> getAllProductsInCart(Long userId) {
        Cart cart = userCarts.get(userId);
        if (cart != null) {
            return cart.getItems().stream().map(CartItem::getProduct).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void clearCart(Long userId) {
        userCarts.remove(userId);
    }

    @Override
    public void saveCart(Cart cart) {
        if (cart.getId() == null) {
            entityManager.persist(cart);
        } else {
            entityManager.merge(cart);
        }
    }

}
