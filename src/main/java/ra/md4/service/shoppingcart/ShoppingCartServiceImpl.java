package ra.md4.service.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4.dao.shoppingcart.IShoppingCartDao;
import ra.md4.models.ShoppingCart;

import java.util.List;
@Service
public class ShoppingCartServiceImpl implements IShoppingCartService{

    @Autowired
    private IShoppingCartDao iShoppingCartDao;
    @Override
    public List<ShoppingCart> getAll() {
        return iShoppingCartDao.getAll();
    }

    @Override
    public ShoppingCart findById(Integer id) {
        return iShoppingCartDao.findById(id);
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        iShoppingCartDao.save(shoppingCart);
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        iShoppingCartDao.update(shoppingCart);
    }

    @Override
    public void delete(Integer id) {
        iShoppingCartDao.delete(id);
    }

    @Override
    public double calculateTotal(List<ShoppingCart> shoppingCarts) {
        return iShoppingCartDao.calculateTotal(shoppingCarts);
    }

    @Override
    public double calculateTotalAmount(List<ShoppingCart> shoppingCarts) {
        return iShoppingCartDao.calculateTotalAmount(shoppingCarts);
    }
}
