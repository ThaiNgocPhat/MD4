package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.md4.models.Product;
import ra.md4.service.product.IProductService;

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
        return "layout/home";
    }


    @GetMapping("/contact")
    public String contact(){
        return "layout/contact";
    }

    @GetMapping("/wishlist")
    public String wishlist(){
        return "layout/wishlist/wishlist";
    }


}
