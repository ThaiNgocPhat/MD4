package ra.md4.service.shoppingcart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4.dao.shoppingcart.IShoppingCartDao;
import ra.md4.models.Order;
import ra.md4.models.ShoppingCart;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IShoppingCartDao iShoppingCartDao;
    @Override
    public void addItemToCart(Integer userId, Integer productId, Integer quantity) {
        iShoppingCartDao.addItemToCart(userId, productId, quantity);
    }

    @Override
    public void updateItemInCart(Integer userId, Integer productId, Integer quantity) {
        iShoppingCartDao.updateItemInCart(userId, productId, quantity);
    }

    @Override
    public void removeItemFromCart(Integer userId, Integer productId) {
        iShoppingCartDao.removeItemFromCart(userId, productId);
    }

    @Override
    public ShoppingCart getCartByUserId(Integer userId) {
        return iShoppingCartDao.getCartByUserId(userId);
    }

    @Override
    public Order checkout(Integer userId) {
        return iShoppingCartDao.checkout(userId);
    }
}
