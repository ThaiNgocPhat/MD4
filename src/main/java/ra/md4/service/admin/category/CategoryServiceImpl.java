package ra.md4.service.admin.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.md4.dao.category.ICategoryDao;
import ra.md4.models.Category;

import java.util.List;
@Service
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    private ICategoryDao iCategoryDao;

    @Override
    public List<Category> getAll() {
        return iCategoryDao.getAll();
    }

    @Override
    public Category findById(Integer id) {
        return iCategoryDao.findById(id);
    }

    @Transactional
    @Override
    public void save(Category category) {
        iCategoryDao.save(category);
    }

    @Transactional
    @Override
    public void update(Category category) {
        iCategoryDao.update(category);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        iCategoryDao.delete(id);
    }

    @Override
    public List<Category> searchByName(String query) {
        return iCategoryDao.searchByName(query);
    }
}
