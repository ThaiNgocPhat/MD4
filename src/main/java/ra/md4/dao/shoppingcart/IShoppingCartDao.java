package ra.md4.dao.shoppingcart;

import ra.md4.dao.IGenericDao;
import ra.md4.models.Order;
import ra.md4.models.ShoppingCart;

public interface IShoppingCartDao {
    void addItemToCart(Integer userId, Integer productId, Integer quantity);
    void updateItemInCart(Integer userId, Integer productId, Integer quantity);
    void removeItemFromCart(Integer userId, Integer productId);
    ShoppingCart getCartByUserId(Integer userId);
    Order checkout(Integer id);

}
