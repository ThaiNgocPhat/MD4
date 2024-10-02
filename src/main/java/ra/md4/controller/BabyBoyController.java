package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.md4.models.Product;
import ra.md4.service.product.IProductService;

import java.util.List;

@Controller
@RequestMapping("/baby-boy")
public class BabyBoyController {

    @Autowired
    private IProductService iProductService;

    @GetMapping
    public String babyBoy(Model model){
        int id = 3;
        List<Product> products = iProductService.findByCategoryId(id);
        model.addAttribute("products", products);
        return "layout/baby-boy/baby-boy";
    }

    @GetMapping("/shirts")
    public String babyBoyShirts(Model model){
        String name = "SKU-BT-AO";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/baby-boy/baby-boy-shirts";
    }

    @GetMapping("/pants")
    public String babyBoyPants(Model model){
        String name = "SKU-BT-QN";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/baby-boy/baby-boy-pants";
    }
    @GetMapping("/jackets")
    public String babyBoyJackets(Model model){
        String name = "SKU-BT-AK";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/baby-boy/baby-boy-jackets";
    }
}
