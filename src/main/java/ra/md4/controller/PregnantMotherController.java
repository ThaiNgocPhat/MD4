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
@RequestMapping("/pregnant-mother")
public class PregnantMotherController {

    @Autowired
    private ISalesService iSalesService;

    @Autowired
    private IProductService iProductService;

    @GetMapping
    public String babyBoy(Model model){
        int id = 5;
        List<Product> products = iSalesService.findByCategoryId(id);
        model.addAttribute("products", products);
        return "layout/pregnant-mother/pregnant-mother";
    }

    @GetMapping("/shirts")
    public String babyBoyShirts(Model model){
        String name = "SKU-MB-AO";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/pregnant-mother/pregnant-mother-shirts";
    }

    @GetMapping("/pants")
    public String babyBoyPants(Model model){
        String name = "SKU-MB-QN";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/pregnant-mother/pregnant-mother-pants";
    }
    @GetMapping("/dresses")
    public String babyBoyJackets(Model model){
        String name = "SKU-MB-DM";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/pregnant-mother/pregnant-mother-dresses";
    }
}
