package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.md4.models.Product;
import ra.md4.service.admin.product.IProductService;

@Controller
@RequestMapping("/product")
public class DetailsController {
    @Autowired
    private IProductService iProductService;
    @RequestMapping("/details/{id}")
    public String productDetails(@PathVariable("id") Integer id, Model model) {
        Product product = iProductService.getById(id);
        model.addAttribute("product", product);
        return "layout/details";
    }
}
