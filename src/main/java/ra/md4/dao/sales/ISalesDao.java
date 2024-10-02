package ra.md4.dao.sales;

import ra.md4.models.Product;

import java.util.List;

public interface ISalesDao {
    List<Product> getProductsByCategoryId(Integer categoryId);
}
