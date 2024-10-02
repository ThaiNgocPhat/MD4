package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.md4.dto.res.UserInfo;
import ra.md4.models.Product;
import ra.md4.models.ShoppingCart;
import ra.md4.models.User;
import ra.md4.service.product.IProductService;
import ra.md4.service.shoppingcart.IShoppingCartService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    private IShoppingCartService iShoppingCartService;

    @Autowired
    private IProductService iProductService;

    @PostMapping("/add")
    public String addToCart(@ModelAttribute("productId") Integer productId, @ModelAttribute("quantity") Integer quantity, HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("userLogin");
        if (userInfo == null){
            return "redirect:/login";
        }
        User user = new User();
        user.setId(userInfo.getUserId());
        user.setUsername(userInfo.getUsername());
        user.setEmail(userInfo.getUsername());
        Product product = iProductService.findById(productId);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setProduct(product);
        shoppingCart.setQuantity(quantity);
        iShoppingCartService.save(shoppingCart);
        return "redirect:/cart";
    }


    @GetMapping
    public String cartPage(Model model){
        List<ShoppingCart> shoppingCarts = iShoppingCartService.getAll();
        double totalAmount = shoppingCarts.stream()
                        .mapToDouble(c -> c.getQuantity() * c.getProduct().getUnitPrice()).sum();
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("shoppingCarts", shoppingCarts);
        return "layout/shopping_cart";
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
        model.addAttribute("shoppingCarts", shoppingCarts);
        model.addAttribute("totalAmount", totalAmount);
        return "layout/shopping_cart";
    }
}
