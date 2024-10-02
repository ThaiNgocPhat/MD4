package ra.md4.service.sales;

import ra.md4.models.Product;

import java.util.List;

public interface ISalesService {
    public List<Product> findByCategoryId(Integer categoryId);

}
