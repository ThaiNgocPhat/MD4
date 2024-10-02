package ra.md4.service.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4.dao.sales.ISalesDao;
import ra.md4.models.Product;

import java.util.List;
@Service
public class SalesServiceImpl implements ISalesService{
    @Autowired
    private ISalesDao iSalesDao;
    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return iSalesDao.getProductsByCategoryId(categoryId);
    }
}
