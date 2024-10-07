package ra.md4.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.md4.models.User;
import ra.md4.service.admin.admin.IAdminService;
import ra.md4.service.user.IUserService;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IAdminService iAdminService;
    // Lấy danh sách người dùng
    @GetMapping
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
    @GetMapping("/changeStatus/{id}")
    public String changeUserStatus(@PathVariable("id") Integer id) {
        iUserService.changeStatus(id);
        return "redirect:/admin/users";
    }


//     Thay đổi quyền người dùng
    @PostMapping("/changeRole")
    public String changeUserRole(@RequestParam("id") Integer id,
                                 @RequestParam("role") Boolean role) {
        iAdminService.changeUserRole(id, role);
        return "redirect:/admin/users";
    }
}
