package ra.md4.service.admin.category;

import ra.md4.models.Category;
import ra.md4.service.IService;

import java.util.List;

public interface ICategoryService extends IService<Category, Integer> {
    List<Category> searchByName(String query);
}
