package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        model.addAttribute("shoppingCarts", shoppingCarts);
        return "layout/shopping_cart";
    }
}
