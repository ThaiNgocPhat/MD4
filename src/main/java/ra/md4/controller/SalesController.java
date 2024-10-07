package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.md4.models.Category;
import ra.md4.models.Product;
import ra.md4.service.admin.category.ICategoryService;
import ra.md4.service.admin.product.IProductService;


import java.util.List;

@Controller
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private IProductService iProductService;

    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping
    public String sales( Model model) {
        int id = 1;
        List<Product> products = iProductService.findByCategoryId(id);
        model.addAttribute("products", products);
        return "layout/best-sales";
    }
}
