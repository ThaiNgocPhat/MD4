package ra.md4.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4.dao.product.IProductDao;
import ra.md4.models.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@Service
public class ProductServiceImpl implements IProductService{
    @Autowired
    private IProductDao iProductDao;

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Product> getAll() {
        return iProductDao.getAll();
    }

    @Override
    public Product findById(Integer id) {
        return iProductDao.findById(id);
    }

    @Override
    public void save(Product product) {
        iProductDao.save(product);
    }

    @Override
    public void update(Product product) {
        iProductDao.update(product);
    }

    @Override
    public void delete(Integer id) {
        iProductDao.delete(id);
    }

    @Override
    public List<Product> getProducts(int page, int size) {
        String query = "SELECT p FROM Product p";
        TypedQuery<Product> typedQuery = entityManager.createQuery(query, Product.class);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }

    @Override
    public long getTotalProducts() {
        String countQuery = "SELECT COUNT(p) FROM Product p";
        TypedQuery<Long> typedQuery = entityManager.createQuery(countQuery, Long.class);
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Product> searchByName(String name) {
        return iProductDao.searchByName(name);
    }

    @Override
    public List<Product> getProductsBySkuPrefix(String skuPrefix) {
        return iProductDao.getProductsBySkuPrefix(skuPrefix);
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return iProductDao.getProductsByCategoryId(categoryId);
    }

    @Override
    public List<Product> getFiveProducts(List<Product> allProduct) {
        return iProductDao.getFiveProducts(allProduct);
    }

    @Override
    public Product getById(Integer id) {
        return iProductDao.getById(id);
    }
}
