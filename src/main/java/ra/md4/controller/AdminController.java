//package ra.md4.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ra.md4.models.Product;
//import ra.md4.service.product.IProductService;
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



package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.md4.models.Product;
import ra.md4.models.User;
import ra.md4.service.admin.IAdminService;
import ra.md4.service.product.IProductService;
import ra.md4.service.user.IUserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IAdminService iAdminService;

    // Trang dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    //Phân trang sản phẩm
    @GetMapping("/products")
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
    @GetMapping("/products/edit")
    public String editProduct(@RequestParam("id") Integer id, Model model) {
        Product product = iProductService.findById(id);
        model.addAttribute("product", product);
        return "admin/product/editProduct";
    }

    //Chỉnh sửa sản phẩm
    @PostMapping("/products/update")
    public String updateProduct(@ModelAttribute Product product) {
        iProductService.save(product);
        return "redirect:/admin/products";
    }

    // Xóa sản phẩm
    @GetMapping("/products/delete")
    public String deleteProduct(@RequestParam("id") Integer id) {
        iProductService.delete(id);
        return "redirect:/admin/products";
    }

    //lấy form thêm mới sản phẩm
    @GetMapping("/products/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product/newProduct";
    }

    // Thêm mới sản phẩm
    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute Product product) {
        iProductService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/search/product")
    public String searchProduct(@RequestParam("name") String name, Model model) {
        List<Product> products = iAdminService.searchProduct(name);
        model.addAttribute("products", products);
        return "admin/product/product";
    }

    // Lấy danh sách người dùng
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = iUserService.getAll();
        model.addAttribute("users", users);
        return "admin/user/user";
    }

    //Tìm kiếm người dùng
    @GetMapping("/search")
    public String searchUser(@RequestParam("name") String name, Model model) {
        List<User> users = iAdminService.searchName(name);
        model.addAttribute("users", users);
        return "admin/user/user";
    }

    //Thay đổi trạng thái hoạt động
    @PostMapping("/users/changeStatus")
    public String changeUserStatus(@RequestParam("id") Integer id,
                                   @RequestParam(value = "status", required = false) Boolean status) {
        if (status == null) {
            User user = iUserService.findById(id);
            status = !user.isStatus();
        }
        iAdminService.updateUserStatus(id, status);
        return "redirect:/admin/users";
    }

    // Thay đổi quyền người dùng
    @PostMapping("/users/changeRole")
    public String changeUserRole(@RequestParam("id") Integer id,
                                 @RequestParam("role") Boolean role) {
        iAdminService.changeUserRole(id, role);
        return "redirect:/admin/users";
    }


    // Lấy danh sách đơn hàng
    @GetMapping("/orders")
    public String getOrders() {
        return "admin/order/order";
    }
}
