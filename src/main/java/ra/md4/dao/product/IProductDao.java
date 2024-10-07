package ra.md4.dao.product;

import ra.md4.dao.IGenericDao;
import ra.md4.models.Product;

import java.util.List;

public interface IProductDao extends IGenericDao<Product, Integer> {
    List<Product> searchByName(String name);
    List<Product> getProductsBySkuPrefix(String skuPrefix);
    List<Product> getFiveProducts(List<Product> allProduct);
    Product getById(Integer id);
    List<Product> findByCategoryId(int categoryId);
}
