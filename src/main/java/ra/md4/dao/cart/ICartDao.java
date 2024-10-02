package ra.md4.dao.cart;

import ra.md4.models.Cart;
import ra.md4.models.Product;

import java.util.List;

public interface ICartDao {
    // Thêm sản phẩm vào giỏ hàng
    void addProductToCart(Long userId, Product product, int quantity);

    // Xóa sản phẩm khỏi giỏ hàng
    void removeProductFromCart(Long userId, Long productId);

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    void updateProductQuantity(Long userId, Long productId, int newQuantity);

    // Lấy giỏ hàng của người dùng
    Cart getCartByUserId(Long userId);

    // Lấy danh sách tất cả sản phẩm trong giỏ hàng
    List<Product> getAllProductsInCart(Long userId);

    // Xóa toàn bộ giỏ hàng
    void clearCart(Long userId);


    void saveCart(Cart cart);
}
