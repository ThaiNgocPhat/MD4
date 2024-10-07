//package ra.md4.dao.product;
//
//import org.springframework.stereotype.Repository;
//import ra.md4.models.Category;
//import ra.md4.models.Product;
//import ra.md4.utils.DBConnection;
//
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//@Repository
//public class ProductDaoImpl implements IProductDao{
//    @Override
//    public List<Product> getAll() {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        ResultSet rs = null;
//        List<Product> products = new ArrayList<>();
//
//        try {
//            call = connection.prepareCall("SELECT p.*, c.name AS category_name FROM product p JOIN categories c ON p.category_id = c.id");
//            rs = call.executeQuery();
//
//            while (rs.next()) {
//                Product product = new Product();
//                product.setId(rs.getInt("id"));
//                product.setSku(rs.getString("sku"));
//                product.setName(rs.getString("name"));
//                product.setDescription(rs.getString("description"));
//                product.setUnitPrice(rs.getDouble("unit_price"));
//                product.setDiscount(rs.getDouble("discount"));
//                product.setDiscounted(rs.getDouble("discounted"));
//                product.setStockQuantity(rs.getInt("stock_quantity"));
//                product.setImage(rs.getString("image"));
//                Category category = new Category();
//                category.setName(rs.getString("category_name"));
//                product.setCategory(category);
//                products.add(product);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error while fetching products: " + e.getMessage());
//        } finally {
//                DBConnection.closeConnection(connection);
//        }
//        return products;
//    }
//
//
//    @Override
//    public Product findById(Integer id) {
//        Product product = null;
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        ResultSet rs = null;
//        try {
//            call = connection.prepareCall(
//                    "SELECT p.*, c.name AS category_name, c.id AS category_id " +
//                            "FROM product p " +
//                            "JOIN category c ON p.category_id = c.id " +
//                            "WHERE p.id = ?");
//            call.setInt(1, id);
//            rs = call.executeQuery();
//            if (rs.next()) {
//                product = new Product();
//                product.setId(rs.getInt("id"));
//                product.setSku(rs.getString("sku"));
//                product.setName(rs.getString("name"));
//                product.setDescription(rs.getString("description"));
//                product.setUnitPrice(rs.getDouble("unit_price"));
//                product.setDiscount(rs.getDouble("discount"));
//                product.setDiscounted(rs.getDouble("discounted"));
//                product.setStockQuantity(rs.getInt("stock_quantity"));
//                product.setImage(rs.getString("image"));
//
//                Category category = new Category();
//                category.setId(rs.getInt("category_id"));
//                category.setName(rs.getString("category_name"));
//                product.setCategory(category);
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Error while finding product by id: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//        return product;
//    }
//
//
//    @Override
//    public void save(Product product) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//
//        try {
//            // Câu lệnh SQL để thêm sản phẩm mới
//            call = connection.prepareCall(
//                    "INSERT INTO product (sku, name, description, unit_price, discount, discounted, stock_quantity, image, category_id) " +
//                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
//            );
//
//            call.setString(1, product.getSku());
//            call.setString(2, product.getName());
//            call.setString(3, product.getDescription());
//            call.setDouble(4, product.getUnitPrice());
//            call.setDouble(5, product.getDiscount());
//            call.setDouble(6, product.getDiscounted());
//            call.setInt(7, product.getStockQuantity());
//            call.setString(8, product.getImage());
//            call.setInt(9, product.getCategory().getId());
//            call.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Error while saving product: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//    }
//
//
//    @Override
//    public void update(Product product) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//
//        try {
//            // Câu lệnh SQL để cập nhật sản phẩm dựa trên ID
//            call = connection.prepareCall(
//                    "UPDATE product SET sku = ?, name = ?, description = ?, unit_price = ?, discount = ?, discounted = ?, stock_quantity = ?, image = ?, category_id = ? " +
//                            "WHERE id = ?"
//            );
//            call.setString(1, product.getSku());
//            call.setString(2, product.getName());
//            call.setString(3, product.getDescription());
//            call.setDouble(4, product.getUnitPrice());
//            call.setDouble(5, product.getDiscount());
//            call.setDouble(6, product.getDiscounted());
//            call.setInt(7, product.getStockQuantity());
//            call.setString(8, product.getImage());
//            call.setInt(9, product.getCategory().getId());
//            call.setInt(10, product.getId());
//            call.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Error while updating product: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//    }
//
//
//    @Override
//    public void delete(Integer id) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        try {
//            // Câu lệnh SQL để xóa sản phẩm dựa trên ID
//            call = connection.prepareCall("DELETE FROM product WHERE id = ?");
//            call.setInt(1, id);
//            call.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Error while deleting product: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//    }
//
//    @Override
//    public List<Product> searchByName(String query) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        ResultSet rs = null;
//        List<Product> products = new ArrayList<>();
//
//        try {
//            call = connection.prepareCall("SELECT p.*, c.name AS category_name FROM product p JOIN category c ON p.category_id = c.id WHERE p.name LIKE ? OR p.description LIKE ?");
//            String searchPattern = "%" + query + "%";
//            call.setString(1, searchPattern); // Tìm kiếm theo tên
//            call.setString(2, searchPattern); // Tìm kiếm theo mô tả
//            rs = call.executeQuery();
//
//            while (rs.next()) {
//                Product product = new Product();
//                product.setId(rs.getInt("id"));
//                product.setSku(rs.getString("sku"));
//                product.setName(rs.getString("name"));
//                product.setDescription(rs.getString("description"));
//                product.setUnitPrice(rs.getDouble("unit_price"));
//                product.setDiscount(rs.getDouble("discount"));
//                product.setDiscounted(rs.getDouble("discounted"));
//                product.setStockQuantity(rs.getInt("stock_quantity"));
//                product.setImage(rs.getString("image"));
//                Category category = new Category();
//                category.setId(rs.getInt("category_id")); // Giả định bạn có id của category
//                category.setName(rs.getString("category_name"));
//                product.setCategory(category);
//                products.add(product);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error while searching products: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//        return products;
//    }
//
//    @Override
//    public List<Product> getProductsBySkuPrefix(String skuPrefix) {
//        List<Product> products = new ArrayList<>();
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        ResultSet rs = null;
//
//        try {
//            call = connection.prepareCall("SELECT * FROM product WHERE sku LIKE ?");
//            call.setString(1, skuPrefix + "%");
//            rs = call.executeQuery();
//            while (rs.next()) {
//                Product product = new Product();
//                product.setId(rs.getInt("id"));
//                product.setSku(rs.getString("sku"));
//                product.setName(rs.getString("name"));
//                product.setDescription(rs.getString("description"));
//                product.setUnitPrice(rs.getDouble("unit_price"));
//                product.setDiscount(rs.getDouble("discount"));
//                product.setDiscounted(rs.getDouble("discounted"));
//                product.setStockQuantity(rs.getInt("stock_quantity"));
//                product.setImage(rs.getString("image"));
//                Category category = new Category();
//                category.setId(rs.getInt("category_id"));
//                product.setCategory(category);
//                products.add(product);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//
//        return products;
//    }
//
//    @Override
//    public List<Product> getFiveProducts(List<Product> allProduct) {
//        return allProduct.size() > 5 ? allProduct.subList(0, 5) : allProduct;
//    }
//
//    @Override
//    public List<Product> getProductsByCategoryId(Integer categoryId) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        List<Product> products = new ArrayList<>();
//        ResultSet rs = null;
//        try{
//            call = connection.prepareCall("SELECT * FROM product WHERE category_id = ?");
//            call.setInt(1, categoryId);
//            rs = call.executeQuery();
//            while (rs.next()) {
//                Product product = new Product();
//                product.setId(rs.getInt("id"));
//                product.setName(rs.getString("name"));
//                product.setUnitPrice(rs.getDouble("unit_price"));
//                product.setDiscount(rs.getDouble("discount"));
//                product.setDiscounted(rs.getDouble("discounted"));
//                product.setStockQuantity(rs.getInt("stock_quantity"));
//                product.setImage(rs.getString("image"));
//                products.add(product);
//            }
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }finally {
//            DBConnection.closeConnection(connection);
//        }
//        return products;
//    }
//
//    @Override
//    public Product getById(Integer id) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        ResultSet rs = null;
//        Product product = null;
//
//        try {
//            call = connection.prepareCall("SELECT * FROM product WHERE id = ?");
//            call.setInt(1, id);
//            rs = call.executeQuery();
//            if (rs.next()) {
//                product = new Product();
//                product.setId(rs.getInt("id"));
//                product.setSku(rs.getString("sku"));
//                product.setName(rs.getString("name"));
//                product.setDescription(rs.getString("description"));
//                product.setUnitPrice(rs.getDouble("unit_price"));
//                product.setDiscount(rs.getDouble("discount"));
//                product.setDiscounted(rs.getDouble("discounted"));
//                product.setStockQuantity(rs.getInt("stock_quantity"));
//                product.setImage(rs.getString("image"));
//                Category category = new Category();
//                category.setId(rs.getInt("category_id"));
//                product.setCategory(category);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//        return product;
//    }
//
//}


package ra.md4.dao.product;
import org.springframework.stereotype.Repository;
import ra.md4.models.Category;
import ra.md4.models.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ProductDaoImpl implements IProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> getAll() {
        String query = "SELECT p FROM Product p JOIN FETCH p.category";
        TypedQuery<Product> typedQuery = entityManager.createQuery(query, Product.class);
        return typedQuery.getResultList();
    }

    @Override
    public Product findById(Integer id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public void update(Product product) {
        entityManager.merge(product);
    }

    @Override
    public void delete(Integer id) {
        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

    @Override
    public List<Product> searchByName(String query) {
        String searchQuery = "SELECT p FROM Product p JOIN FETCH p.category WHERE p.name LIKE :query OR p.description LIKE :query";
        TypedQuery<Product> typedQuery = entityManager.createQuery(searchQuery, Product.class);
        typedQuery.setParameter("query", "%" + query + "%");
        return typedQuery.getResultList();
    }

    @Override
    public List<Product> getProductsBySkuPrefix(String skuPrefix) {
        String searchQuery = "SELECT p FROM Product p WHERE p.sku LIKE :skuPrefix";
        TypedQuery<Product> typedQuery = entityManager.createQuery(searchQuery, Product.class);
        typedQuery.setParameter("skuPrefix", skuPrefix + "%");
        return typedQuery.getResultList();
    }

    @Override
    public List<Product> getFiveProducts(List<Product> allProduct) {
        return allProduct.size() > 5 ? allProduct.subList(0, 5) : allProduct;
    }

    @Override
    public Product getById(Integer id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        String jpql = "SELECT p FROM Product p JOIN FETCH p.category c WHERE c.id = :categoryId AND c.status = true";
        return entityManager.createQuery(jpql, Product.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }
}
