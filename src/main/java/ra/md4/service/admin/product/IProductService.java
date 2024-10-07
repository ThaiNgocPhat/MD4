package ra.md4.service.admin.product;

import ra.md4.models.Product;
import ra.md4.service.IService;

import java.util.List;

public interface IProductService extends IService<Product, Integer> {
    List<Product> getProducts(int page, int size);
    long getTotalProducts();
    List<Product> searchByName(String name);
    List<Product> getProductsBySkuPrefix(String skuPrefix);
    List<Product> getFiveProducts(List<Product> allProduct);
    Product getById(Integer id);
    List<Product> findByCategoryId(int categoryId);
}

