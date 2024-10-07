package ra.md4.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.md4.models.Product;
import ra.md4.service.admin.admin.IAdminService;
import ra.md4.service.admin.product.IProductService;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    @Autowired
    private IAdminService iAdminService;

    //Phân trang sản phẩm
    @GetMapping
    public String getProducts(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size, Model model) {
        List<Product> products = iProductService.getProducts(page, size);
        long totalProducts = iProductService.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / size);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        return "admin/product/product";
    }

    // lấy form chỉnh sửa sản phẩm
    @GetMapping("/edit")
    public String editProduct(@RequestParam("id") Integer id, Model model) {
        Product product = iProductService.findById(id);
        model.addAttribute("product", product);
        return "admin/product/editProduct";
    }

    //Chỉnh sửa sản phẩm
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product) {
        iProductService.save(product);
        return "redirect:/admin/products";
    }

    // Xóa sản phẩm
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") Integer id) {
        iProductService.delete(id);
        return "redirect:/admin/products";
    }

    //lấy form thêm mới sản phẩm
    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product/newProduct";
    }

    // Thêm mới sản phẩm
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product) {
        iProductService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam("name") String name, Model model) {
        List<Product> products = iAdminService.searchProduct(name);
        model.addAttribute("products", products);
        return "admin/product/product";
    }
}
