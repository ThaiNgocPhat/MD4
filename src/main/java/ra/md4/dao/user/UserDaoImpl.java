package ra.md4.dao.user;

import org.springframework.stereotype.Repository;
import ra.md4.models.User;
import ra.md4.utils.DBConnection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements IUserDao{
    @PersistenceContext
    private EntityManager em;
    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = em.createQuery("from User where username = :username and isDeleted = false", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    public void register(User user) {
        em.persist(user);
    }

    @Override
    public List<User> getAll() {
        Connection connection = DBConnection.getConnection();
        CallableStatement call = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try{
            call = connection.prepareCall("select * from users where is_deleted = false");
            rs = call.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("fullName"),
                        rs.getBoolean("status"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("avatar"),
                        rs.getString("address"),
                        rs.getDate("createdAt"),
                        rs.getDate("updatedAt"),
                        rs.getBoolean("is_deleted"),
                        rs.getBoolean("role")
                );
                users.add(user);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
        return users;
    }

    @Override
    public User findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        CallableStatement call = null;
        ResultSet rs = null;
        User user = null;
        try {
            call = connection.prepareCall("SELECT * FROM users WHERE id = ? AND is_deleted = false");
            call.setInt(1, id);
            rs = call.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("fullName"));
                user.setStatus(rs.getBoolean("status"));
                user.setPassword(rs.getString("password"));
                user.setAvatar(rs.getString("avatar"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setCreatedAt(rs.getDate("createdAt"));
                user.setUpdatedAt(rs.getDate("updatedAt"));
                user.setDeleted(rs.getBoolean("is_deleted"));
                user.setRole(rs.getBoolean("role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(connection);
        }
        return user;
    }

    @Override
    public void save(User user) {
    }


    @Override
    public void update(User user) {
        Connection connection = DBConnection.getConnection();
        CallableStatement call = null;
        try {
            call = connection.prepareCall("UPDATE users SET username = ?, email = ?, fullName = ?, status = ?, password = ?, avatar = ?, phone = ?, address = ?, updatedAt = ?, role = ? WHERE id = ?");
            call.setString(1, user.getUsername());
            call.setString(2, user.getEmail());
            call.setString(3, user.getFullName());
            call.setBoolean(4, user.isStatus());
            call.setString(5, user.getPassword());
            call.setString(6, user.getAvatar());
            call.setString(7, user.getPhone());
            call.setString(8, user.getAddress());
            call.setDate(9, new java.sql.Date(user.getUpdatedAt().getTime()));
            call.setBoolean(10, user.isRole());
            call.setInt(11, user.getId());
            call.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(connection);
        }
    }


    @Override
    public void delete(Integer id) {
        Connection connection = DBConnection.getConnection();
        CallableStatement call = null;
        try {
            call = connection.prepareCall("UPDATE users SET is_deleted = true WHERE id = ?");
            call.setInt(1, id);
            call.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(connection); // Đóng kết nối
        }
    }
}
