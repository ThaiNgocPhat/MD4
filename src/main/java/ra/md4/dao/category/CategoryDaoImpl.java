package ra.md4.dao.category;
import org.springframework.stereotype.Repository;
import ra.md4.models.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CategoryDaoImpl implements ICategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Category> getAll() {
        String query = "SELECT c FROM Category c";
        TypedQuery<Category> typedQuery = entityManager.createQuery(query, Category.class);
        return typedQuery.getResultList();
    }

    @Override
    public Category findById(Integer id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public void save(Category category) {
        entityManager.persist(category);
    }

    @Override
    public void update(Category category) {
        entityManager.merge(category);
    }

    @Override
    public void delete(Integer id) {
        Category category = entityManager.find(Category.class, id);
        if (category != null) {
            entityManager.remove(category);
        }
    }

    @Override
    public List<Category> searchByName(String query) {
        String searchQuery = "SELECT c FROM Category c WHERE c.name LIKE :query OR c.description LIKE :query";
        TypedQuery<Category> typedQuery = entityManager.createQuery(searchQuery, Category.class);
        typedQuery.setParameter("query", "%" + query + "%");
        return typedQuery.getResultList();
    }
}
