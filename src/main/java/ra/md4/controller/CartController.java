package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.md4.models.Cart;
import ra.md4.models.User;
import ra.md4.service.cart.ICartService;
import ra.md4.service.user.IUserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService iCartService;

    @Autowired
    private IUserService userService;


    @PostMapping("/add")
    public String showCart(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Cart cart = iCartService.getCartByUserId(Long.valueOf(user.getId()));
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("totalFormattedPrice", cart.getTotalPrice());
        return "layout/cart";
    }

}
