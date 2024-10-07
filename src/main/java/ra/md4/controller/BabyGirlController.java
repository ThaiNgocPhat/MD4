package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.md4.models.Product;
import ra.md4.service.admin.product.IProductService;

import java.util.List;

@Controller
@RequestMapping("/baby-girl")
public class BabyGirlController {

    @Autowired
    private IProductService iProductService;

    @GetMapping
    public String babyBoy(Model model){
        int id = 4;
        List<Product> products = iProductService.findByCategoryId(id);
        model.addAttribute("products", products);
        return "layout/baby-girl/baby-girl";
    }

    @GetMapping("/shirts")
    public String babyBoyShirts(Model model){
        String name = "SKU-BG-AO";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/baby-girl/baby-girl-shirts";
    }

    @GetMapping("/pants")
    public String babyBoyPants(Model model){
        String name = "SKU-BG-QN";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/baby-girl/baby-girl-pants";
    }
    @GetMapping("/jackets")
    public String babyBoyJackets(Model model){
        String name = "SKU-BG-AK";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/baby-girl/baby-girl-jackets";
    }
}
