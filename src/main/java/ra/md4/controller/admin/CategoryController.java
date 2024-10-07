package ra.md4.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.md4.models.Category;
import ra.md4.models.Product;
import ra.md4.service.admin.category.ICategoryService;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping
    public String getAll(Model model){
        List<Category> categories = iCategoryService.getAll();
        model.addAttribute("categories", categories);
        return "admin/category/category";
    }

    @GetMapping("/add")
    public String addCategory(Model model){
        model.addAttribute("category", new Category());
        return "admin/category/newCategory";
    }

    @PostMapping("/add")
    public String createCategory(Category category){
        iCategoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(Model model, @PathVariable Integer id){
        Category category = iCategoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/editCategory";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(Category category){
        iCategoryService.update(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id){
        iCategoryService.delete(id);
        return "redirect:/admin/category";
    }
    @GetMapping("/search")
    public String searchCategory(@RequestParam("name") String name, Model model) {
        List<Category> categories = iCategoryService.searchByName(name);
        model.addAttribute("categories", categories);
        return "admin/category/category";
    }
}
