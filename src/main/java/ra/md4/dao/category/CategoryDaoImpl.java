//package ra.md4.dao.category;
//
//import org.springframework.stereotype.Repository;
//import ra.md4.models.Category;
//import ra.md4.utils.DBConnection;
//
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//@Repository
//public class CategoryDaoImpl implements ICategoryDao{
//    @Override
//    public List<Category> getAll() {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        ResultSet rs = null;
//        List<Category> categories = new ArrayList<>();
//
//        try {
//            call = connection.prepareCall("SELECT * FROM categories");
//            rs = call.executeQuery();
//
//            while (rs.next()) {
//                Category category = new Category();
//                category.setId(rs.getInt("id"));
//                category.setName(rs.getString("name"));
//                category.setDescription(rs.getString("description"));
//                category.setStatus(rs.getBoolean("status"));
//                categories.add(category);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error while fetching categories: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//        return categories;
//    }
//
//
//    @Override
//    public Category findById(Integer id) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        ResultSet rs = null;
//
//        try {
//            call = connection.prepareCall("SELECT * FROM categories WHERE id = ?");
//            call.setInt(1, id);
//            rs = call.executeQuery();
//
//            if (rs.next()) {
//                return new Category(
//                        rs.getInt("id"),
//                        rs.getString("name"),
//                        rs.getString("description"),
//                        rs.getBoolean("status")
//                );
//            }
//        } catch (SQLException e) {
//            System.err.println("Error while finding category: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//        return null;
//    }
//
//
//    @Override
//    public void save(Category category) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//
//        try {
//            call = connection.prepareCall(
//                    "INSERT INTO categories (name, description, status) VALUES (?, ?, ?)"
//            );
//
//            call.setString(1, category.getName());
//            call.setString(2, category.getDescription());
//            call.setBoolean(3, category.isStatus());
//
//            call.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Error while saving category: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//    }
//
//
//    @Override
//    public void update(Category category) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//
//        try {
//            call = connection.prepareCall(
//                    "UPDATE categories SET name = ?, description = ?, status = ? WHERE id = ?"
//            );
//
//            call.setString(1, category.getName());
//            call.setString(2, category.getDescription());
//            call.setBoolean(3, category.isStatus());
//            call.setInt(4, category.getId());
//
//            call.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Error while updating category: " + e.getMessage());
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
//
//        try {
//            call = connection.prepareCall("DELETE FROM categories WHERE id = ?");
//            call.setInt(1, id);
//
//            call.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Error while deleting category: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//    }
//
//    @Override
//    public List<Category> searchByName(String query) {
//        Connection connection = DBConnection.getConnection();
//        CallableStatement call = null;
//        ResultSet rs = null;
//        List<Category> categories = new ArrayList<>();
//        try {
//            call = connection.prepareCall("SELECT * FROM categories WHERE name LIKE ? OR description LIKE ?");
//            String searchPattern = "%" + query + "%";
//            call.setString(1, searchPattern);
//            call.setString(2, searchPattern);
//            rs = call.executeQuery();
//
//            while (rs.next()) {
//                Category category = new Category();
//                category.setId(rs.getInt("id"));
//                category.setName(rs.getString("name"));
//                category.setDescription(rs.getString("description"));
//                category.setStatus(rs.getBoolean("status"));
//                categories.add(category);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error while searching categories: " + e.getMessage());
//        } finally {
//            DBConnection.closeConnection(connection);
//        }
//        return categories;
//    }
//}

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
