package ra.md4.dao.category;

import ra.md4.dao.IGenericDao;
import ra.md4.models.Category;

import java.util.List;

public interface ICategoryDao extends IGenericDao<Category, Integer> {
    List<Category> searchByName(String name);
}
