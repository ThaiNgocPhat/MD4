package ra.md4.dao.shoppingcart;

import ra.md4.dao.IGenericDao;
import ra.md4.models.ShoppingCart;

import java.util.List;

public interface IShoppingCartDao extends IGenericDao<ShoppingCart, Integer> {
    double calculateTotal(List<ShoppingCart> shoppingCarts);
    double calculateTotalAmount(List<ShoppingCart> shoppingCarts);
    ShoppingCart findByUserAndProduct(Integer userId, Integer productId);

}
