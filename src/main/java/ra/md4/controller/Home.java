package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.md4.models.Product;
import ra.md4.service.product.IProductService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class Home {
    @Autowired
    private IProductService iProductService;
    @GetMapping
    public String home(Model model) {
        int id = 1;
        List<Product> sellerProduct = iProductService.findByCategoryId(id);
        if (sellerProduct.size() > 5){
            sellerProduct = sellerProduct.stream().limit(5).collect(Collectors.toList());
        }
        model.addAttribute("sellerProduct", sellerProduct);

        int id1 = 3;
        List<Product> babyBoyProduct = iProductService.findByCategoryId(id1);
        if (babyBoyProduct.size() > 5){
            babyBoyProduct = babyBoyProduct.stream().limit(5).collect(Collectors.toList());
        }
        model.addAttribute("babyBoyProduct", babyBoyProduct);

        int id2 = 4;
        List<Product> babyGirlProduct = iProductService.findByCategoryId(id2);
        if (babyGirlProduct.size() > 5){
            babyGirlProduct = babyGirlProduct.stream().limit(5).collect(Collectors.toList());
        }
        model.addAttribute("babyGirlProduct", babyGirlProduct);

        int id3 = 5;
        List<Product> pregnantMotherProduct = iProductService.findByCategoryId(id3);
        if (pregnantMotherProduct.size() > 5){
            pregnantMotherProduct = pregnantMotherProduct.stream().limit(5).collect(Collectors.toList());
        }
        model.addAttribute("pregnantMotherProduct", pregnantMotherProduct);
        return "layout/home";
    }

//    @GetMapping("/logout")
//    public String handleLogout(HttpSession session) {
//        session.removeAttribute("userLogin");
//        return "redirect:/";
//    }

    @GetMapping("/contact")
    public String contact(){
        return "layout/contact";
    }

    @GetMapping("/wishlist")
    public String wishlist(){
        return "layout/wishlist/wishlist";
    }


}
