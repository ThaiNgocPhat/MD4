//package ra.md4.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ra.md4.models.Product;
//import ra.md4.service.admin.product.IProductService;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//    @Autowired
//    private IProductService iProductService;
//
//    @GetMapping("/dashboard")
//    public String dashboard() {
//        return "admin/dashboard";
//    }
//    @GetMapping("/products")
//    public String getProducts(Model model) {
//        List<Product> products = iProductService.getAll();
//        model.addAttribute("products", products);
//        return "admin/product";
//    }
//
//    @GetMapping("/products")
//    public String getProducts(@RequestParam(defaultValue = "0") int page,
//                              @RequestParam(defaultValue = "5") int size, Model model) {
//        List<Product> products = iProductService.getProducts(page, size);
//        long totalProducts = iProductService.getTotalProducts();
//
//        int totalPages = (int) Math.ceil((double) totalProducts / size);
//
//        model.addAttribute("products", products);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", totalPages);
//
//        return "admin/dashboard";
//    }
//
//
//    @GetMapping("/users")
//    public String getUsers() {
//        return "admin/user :: userList"; // Trả về đoạn nội dung userList
//    }
//
//    @GetMapping("/orders")
//    public String getOrders() {
//        return "admin/order :: orderList"; // Trả về đoạn nội dung orderList
//    }
//}



package ra.md4.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

}
