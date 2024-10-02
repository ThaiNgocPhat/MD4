package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.md4.models.Product;
import ra.md4.service.product.IProductService;
import ra.md4.service.sales.ISalesService;

import java.util.List;

@Controller
@RequestMapping("/baby-newborn")
public class NewBornController {

    @Autowired
    private IProductService iProductService;

    @GetMapping
    public String babyBoy(Model model){
        int id = 2;
        List<Product> products = iProductService.findByCategoryId(id);
        model.addAttribute("products", products);
        return "layout/new-born/new-born";
    }

    @GetMapping("/set")
    public String babyBoyShirts(Model model){
        String name = "SKU-TSS";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/new-born/new-born-set";
    }

    @GetMapping("/bodysuit")
    public String babyBoyPants(Model model){
        String name = "SKU-BD";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/new-born/new-born-bodysuit";
    }
}
