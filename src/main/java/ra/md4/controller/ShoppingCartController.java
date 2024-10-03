package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.md4.dto.res.UserInfo;
import ra.md4.models.*;
import ra.md4.service.order.IOrderService;
import ra.md4.service.product.IProductService;
import ra.md4.service.shoppingcart.IShoppingCartService;
import ra.md4.service.user.IUserService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    private IShoppingCartService iShoppingCartService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IOrderService iOrderService;

    @PostMapping("/add")
    public String addToCart(@ModelAttribute("productId") Integer productId,
                            @ModelAttribute("quantity") Integer quantity,
                            HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userLogin");
        if (userInfo == null) {
            return "redirect:/login";
        }

        // Lấy thông tin người dùng
        User user = new User();
        user.setId(userInfo.getUserId());
        user.setUsername(userInfo.getUsername());
        user.setEmail(userInfo.getUsername());

        // Tìm sản phẩm
        Product product = iProductService.findById(productId);
        if (product == null) {
            // Xử lý nếu không tìm thấy sản phẩm
            return "redirect:/error"; // Hoặc trang khác để thông báo lỗi
        }

        // Kiểm tra giỏ hàng của người dùng
        ShoppingCart existingCart = iShoppingCartService.findByUserAndProduct(user.getId(), productId);

        if (existingCart != null) {
            // Nếu sản phẩm đã có trong giỏ hàng, cộng thêm số lượng
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            iShoppingCartService.update(existingCart); // Cập nhật giỏ hàng
        } else {
            // Nếu sản phẩm chưa có, thêm mới vào giỏ hàng
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            shoppingCart.setProduct(product);
            shoppingCart.setQuantity(quantity);
            iShoppingCartService.save(shoppingCart); // Lưu sản phẩm mới vào giỏ hàng
        }

        return "redirect:/cart"; // Quay lại trang giỏ hàng
    }



    @GetMapping
    public String cartPage(Model model){
        List<ShoppingCart> shoppingCarts = iShoppingCartService.getAll();
        double totalAmount = shoppingCarts.stream()
                        .mapToDouble(c -> c.getQuantity() * c.getProduct().getUnitPrice()).sum();
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("shoppingCarts", shoppingCarts);
        return "layout/shopping/shopping_cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("cartId") Integer cartId, @RequestParam("quantity") Integer quantity) {
        ShoppingCart shoppingCart = iShoppingCartService.findById(cartId);
        if (shoppingCart != null) {
            shoppingCart.setQuantity(quantity);
            iShoppingCartService.update(shoppingCart);
        }
        return "redirect:/cart";
    }

    @PostMapping("/delete")
    public String deleteCart(@RequestParam("cartId") Integer cartId) {
        iShoppingCartService.delete(cartId);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<ShoppingCart> shoppingCarts = iShoppingCartService.getAll();
        double totalAmount = iShoppingCartService.calculateTotalAmount(shoppingCarts);

        // Tạo đối tượng Address và thêm vào model để dùng trong form
        Address address = new Address();
        model.addAttribute("address", address);

        model.addAttribute("shoppingCarts", shoppingCarts);
        model.addAttribute("totalAmount", totalAmount);
        return "layout/shopping/shopping_cart";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("receiveName") String receiveName,
                           @RequestParam("address") String address,
                           @RequestParam("phone") String phone,
                           HttpSession session,
                           Model model) {
        // Lấy đơn hàng từ session
        Order order = (Order) session.getAttribute("order");

        // Kiểm tra xem order có null hay không
        if (order == null) {
            // Xử lý lỗi nếu order không có
            model.addAttribute("error", "Đơn hàng không tồn tại. Vui lòng thêm sản phẩm vào giỏ hàng.");
            return "layout/shopping/shopping_cart"; // Trở về trang giỏ hàng hoặc trang lỗi
        }

        // Lưu đơn hàng
        Order savedOrder = iOrderService.save(order);

        // Xóa giỏ hàng của người dùng
        iOrderService.clearCartByUser(session);

        // Thêm thông tin nhận hàng vào model
        model.addAttribute("receiveName", receiveName);
        model.addAttribute("address", address);
        model.addAttribute("phone", phone);
        model.addAttribute("orderId", savedOrder.getId()); // Thêm ID đơn hàng vào model

        // Hiển thị trang xác nhận
        return "layout/home";
    }



    @GetMapping("/checkout")
    public String orderHistory(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            model.addAttribute("error", "Người dùng chưa đăng nhập.");
            return "layout/shopping/shopping_cart";
        }

        User currentUser = iOrderService.getUserByEmail(principal.getName());
        if (currentUser == null) {
            model.addAttribute("error", "Không tìm thấy người dùng.");
            return "layout/shopping/shopping_cart";
        }

        // Lấy đơn hàng từ session
        Order order = (Order) session.getAttribute("order");
        if (order == null) {
            model.addAttribute("error", "Đơn hàng không tồn tại.");
            return "layout/shopping/shopping_cart";
        }

        // Lấy danh sách các sản phẩm (items) từ đối tượng Order
        List<OrderItem> items = order.getItems();
        if (items == null || items.isEmpty()) {
            model.addAttribute("error", "Đơn hàng không có sản phẩm nào.");
            return "layout/shopping/shopping_cart";
        }

        model.addAttribute("order", order);
        return "layout/shopping/checkout"; // Trang hiển thị thông tin đơn hàng
    }


}
