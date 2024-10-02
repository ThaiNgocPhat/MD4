package ra.md4.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4.dao.cart.ICartDao;
import ra.md4.models.Cart;
import ra.md4.models.CartItem;
import ra.md4.models.Product;
import ra.md4.models.User;
import ra.md4.service.product.IProductService;

import java.util.List;
@Service
public class CartServiceImpl implements ICartService{
    @Autowired
    private ICartDao iCartDao;

    @Autowired
    private IProductService iProductService;
    @Override
    public void addProductToCart(User user, Integer productId, int quantity) {


        Cart cart = iCartDao.getCartByUserId(Long.valueOf(user.getId()));

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
        boolean itemExists = false;
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                // Nếu có, cập nhật số lượng
                item.setQuantity(item.getQuantity() + quantity);
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            Product product = iProductService.getById(productId);
            CartItem newItem = new CartItem(product, quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }
        iCartDao.saveCart(cart);
    }


    @Override
    public void removeProductFromCart(Long userId, Long productId) {
        iCartDao.removeProductFromCart(userId, productId);
    }

    @Override
    public void updateProductQuantity(Long userId, Long productId, int newQuantity) {
        iCartDao.updateProductQuantity(userId, productId, newQuantity);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return iCartDao.getCartByUserId(userId);
    }

    @Override
    public List<Product> getAllProductsInCart(Long userId) {
        return iCartDao.getAllProductsInCart(userId);
    }

    @Override
    public void clearCart(Long userId) {
        iCartDao.clearCart(userId);
    }
}
