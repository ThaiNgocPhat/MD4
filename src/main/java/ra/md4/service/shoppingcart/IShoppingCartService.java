package ra.md4.service.shoppingcart;

import ra.md4.models.ShoppingCart;
import ra.md4.service.IService;

import java.util.List;

public interface IShoppingCartService extends IService<ShoppingCart, Integer> {
    double calculateTotal(List<ShoppingCart> shoppingCarts);
    double calculateTotalAmount(List<ShoppingCart> shoppingCarts);
    ShoppingCart findByUserAndProduct(Integer userId, Integer productId);

}
