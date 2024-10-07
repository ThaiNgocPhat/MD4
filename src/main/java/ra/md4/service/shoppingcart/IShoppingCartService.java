package ra.md4.service.shoppingcart;

import ra.md4.models.Order;
import ra.md4.models.ShoppingCart;
import ra.md4.service.IService;

import java.util.List;

public interface IShoppingCartService {
    void addItemToCart(Integer userId, Integer productId, Integer quantity);
    void updateItemInCart(Integer userId, Integer productId, Integer quantity);
    void removeItemFromCart(Integer userId, Integer productId);
    ShoppingCart getCartByUserId(Integer userId);
    Order checkout(Integer userId);
}
