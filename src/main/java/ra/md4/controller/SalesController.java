package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.md4.dao.sales.ISalesDao;
import ra.md4.models.Product;
import ra.md4.service.sales.ISalesService;

import java.util.List;

@Controller
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private ISalesService iSalesService;

    @GetMapping
    public String sales( Model model) {
        int id = 1;
        List<Product> products = iSalesService.findByCategoryId(id);
        model.addAttribute("products", products);
        return "layout/best-sales";
    }
}
