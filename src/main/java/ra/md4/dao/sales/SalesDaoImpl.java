package ra.md4.dao.sales;

import org.springframework.stereotype.Repository;
import ra.md4.models.Product;
import ra.md4.utils.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class SalesDaoImpl implements ISalesDao{
    @Override
    public List<Product> getProductsByCategoryId(Integer categoryId) {
        Connection connection = DBConnection.getConnection();
        CallableStatement call = null;
        List<Product> products = new ArrayList<>();
        ResultSet rs = null;
        try{
            call = connection.prepareCall("SELECT * FROM product WHERE category_id = ?");
            call.setInt(1, categoryId);
            rs = call.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setUnitPrice(rs.getDouble("unit_price"));
                product.setDiscount(rs.getDouble("discount"));
                product.setDiscounted(rs.getDouble("discounted"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setImage(rs.getString("image"));
                products.add(product);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
        return products;
    }
}
