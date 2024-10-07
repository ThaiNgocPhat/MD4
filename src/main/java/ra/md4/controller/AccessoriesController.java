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
@RequestMapping("/accessories")
public class AccessoriesController {


    @Autowired
    private IProductService iProductService;

    @GetMapping
    public String accessories(Model model){
        int id = 6;
        List<Product> products = iProductService.findByCategoryId(id);
        model.addAttribute("products", products);
        return "layout/accessories/accessories";
    }

    @GetMapping("/sandals")
    public String accessoriesSandals(Model model){
        String name = "SKU-GP-GS";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/accessories/accessories-sandals";
    }

    @GetMapping("/sports-shoes")
    public String accessoriesSportsShoes(Model model){
        String name = "SKU-GP-GT";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/accessories/accessories-sports";
    }
    @GetMapping("/socks")
    public String accessoriesSocks(Model model){
        String name = "SKU-GP-V";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/accessories/accessories-socks";
    }

    @GetMapping("/caps")
    public String accessoriesCaps(Model model){
        String name = "SKU-GP-N";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/accessories/accessories-caps";
    }

    @GetMapping("/backpacks")
    public String accessoriesBackpacks(Model model){
        String name = "SKU-GP-BL";
        List<Product> products = iProductService.getProductsBySkuPrefix(name);
        model.addAttribute("products", products);
        return "layout/accessories/accessories-backpacks";
    }
}
