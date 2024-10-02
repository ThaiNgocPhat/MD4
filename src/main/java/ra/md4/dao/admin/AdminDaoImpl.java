//package ra.md4.dao.admin;
//
//import org.springframework.stereotype.Repository;
//import ra.md4.models.Category;
//import ra.md4.models.Product;
//import ra.md4.models.User;
//import ra.md4.utils.DBConnection;
//
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//public class AdminDaoImpl implements IAdminDao{
//    @Override
//    public List<User> searchName(String query) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        ResultSet rs = null;
//        List<User> users = new ArrayList<>();
//        try {
//            String sql = "SELECT * FROM users WHERE (username LIKE ? OR email LIKE ?) AND is_deleted = false";
//            call = connection.prepareCall(sql);
//            // Thêm dấu % để tìm kiếm tên tương đối
//            String searchQuery = "%" + query + "%";
//            call.setString(1, searchQuery);
//            call.setString(2, searchQuery);
//            rs = call.executeQuery();
//
//            while (rs.next()) {
//                User user = new User(
//                        rs.getInt("id"),
//                        rs.getString("username"),
//                        rs.getString("email"),
//                        rs.getString("fullName"),
//                        rs.getBoolean("status"),
//                        rs.getString("password"),
//                        rs.getString("avatar"),
//                        rs.getString("phone"),
//                        rs.getString("address"),
//                        rs.getDate("createdAt"),
//                        rs.getDate("updatedAt"),
//                        rs.getBoolean("is_deleted"),
//                        rs.getBoolean("role")
//                );
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//        return users;
//    }
//
//    @Override
//    public List<Product> searchProduct(String query) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        ResultSet rs = null;
//        List<Product> products = new ArrayList<>();
//        try {
//            String sql = "SELECT * FROM product WHERE (name LIKE ? OR description LIKE ?) AND stock_quantity > 0";
//            call = connection.prepareCall(sql);
//            String searchQuery = "%" + query + "%";
//            call.setString(1, searchQuery);
//            call.setString(2, searchQuery);
//            rs = call.executeQuery();
//
//            while (rs.next()) {
//                Category category = new Category();
//                category.setId(rs.getInt("category_id"));
//                category.setName(rs.getString("category_name"));
//                Product product = new Product(
//                        rs.getInt("id"),
//                        rs.getString("sku"),
//                        rs.getString("name"),
//                        rs.getString("description"),
//                        rs.getDouble("unit_price"),
//                        rs.getDouble("discount"),
//                        rs.getDouble("discounted"),
//                        rs.getInt("stock_quantity"),
//                        rs.getString("image"),
//                        category,
//                        rs.getDate("createdAt"),
//                        rs.getDate("update_at")
//                );
//                products.add(product);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//        return products;
//    }
//
//    @Override
//    public void updateUserStatus(Integer id, Boolean status) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        try {
//            call = connection.prepareCall("UPDATE users SET status =? WHERE id =?");
//            call.setBoolean(1, status);
//            call.setInt(2, id);
//            call.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Error while updating user status: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//    }
//
//    @Override
//    public void changeUserRole(Integer id, Boolean role) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        try {
//            call = connection.prepareCall("UPDATE users SET role =? WHERE id =?");
//            call.setBoolean(1, role);
//            call.setInt(2, id);
//            call.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Error while changing user role: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//    }
//
//}






package ra.md4.dao.admin;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ra.md4.models.Category;
import ra.md4.models.Product;
import ra.md4.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class AdminDaoImpl implements IAdminDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> searchName(String query) {
        String searchQuery = "%" + query + "%";
        TypedQuery<User> q = entityManager.createQuery(
                "SELECT u FROM User u WHERE (u.username LIKE :query OR u.email LIKE :query) AND u.isDeleted = false",
                User.class
        );
        q.setParameter("query", searchQuery);
        return q.getResultList();
    }

    @Override
    public List<Product> searchProduct(String query) {
        String searchQuery = "%" + query + "%";
        TypedQuery<Product> q = entityManager.createQuery(
                "SELECT p FROM Product p WHERE (p.name LIKE :query OR p.description LIKE :query) AND p.stockQuantity > 0",
                Product.class
        );
        q.setParameter("query", searchQuery);
        return q.getResultList();
    }

    @Override
    public void updateUserStatus(Integer id, Boolean status) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setStatus(status);
            entityManager.merge(user);
        }
    }

    @Override
    public void changeUserRole(Integer id, Boolean role) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setRole(role);
            entityManager.merge(user);
        }
    }
}
