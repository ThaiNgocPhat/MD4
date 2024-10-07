package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.md4.models.Order;
import ra.md4.models.ShoppingCart;
import ra.md4.models.User;
import ra.md4.service.shoppingcart.IShoppingCartService;
import ra.md4.service.user.IUserService;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Autowired
    private IUserService iUserService;


    @PostMapping("/add")
    public String addItemToCart(@RequestParam Integer userId,
                                @RequestParam Integer productId,
                                @RequestParam Integer quantity) {
        shoppingCartService.addItemToCart(userId, productId, quantity);
        return "redirect:layout/shopping/view";
    }

    @PostMapping("/update")
    public String updateItemInCart(@RequestParam Integer userId,
                                   @RequestParam Integer productId,
                                   @RequestParam Integer quantity) {
        shoppingCartService.updateItemInCart(userId, productId, quantity);
        return "redirect:layout/shopping/view"; // Redirect đến trang giỏ hàng
    }

    @PostMapping("/remove")
    public String removeItemFromCart(@RequestParam Integer userId,
                                     @RequestParam Integer productId) {
        shoppingCartService.removeItemFromCart(userId, productId);
        return "redirect:/layout/shopping/view";
    }

    @GetMapping
    public String viewCart(@RequestParam Integer userId, Model model) {
        ShoppingCart cart = shoppingCartService.getCartByUserId(userId);
        model.addAttribute("cart", cart);
        User user = iUserService.findById(userId);
        model.addAttribute("user", user);
        return "layout/shopping/view";
    }


    @PostMapping("/checkout")
    public String checkout(@RequestParam Integer userId) {
        Order order = shoppingCartService.checkout(userId);
        return "redirect:/order/success"; // Redirect đến trang thành công
    }
}
