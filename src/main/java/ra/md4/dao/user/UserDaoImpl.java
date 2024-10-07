//package ra.md4.dao.user;
//import org.springframework.stereotype.Repository;
//import ra.md4.models.User;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//
//@Repository
//public class UserDaoImpl implements IUserDao{
//    @PersistenceContext
//    private EntityManager em;
//    @Override
//    public User findByUsername(String username) {
//        TypedQuery<User> query = em.createQuery("from User where username = :username and isDeleted = false", User.class);
//        query.setParameter("username", username);
//        return query.getSingleResult();
//    }
//
//    @Override
//    public void register(User user) {
//        em.persist(user);
//    }
//}
//

package ra.md4.dao.user;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.md4.models.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements IUserDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EntityManager entityManager;
    @Override
    public void register(User user) {
        Session session = sessionFactory.openSession();
        Transaction tran = session.beginTransaction();
        try {
            session.saveOrUpdate(user);
            tran.commit();
        }catch (Exception e){
            tran.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
//        entityManager.persist(user);
    }

    @Override
    public User login(String username) {
        Session session = sessionFactory.openSession();
        TypedQuery<User> query = session.createQuery("from User where username= :username and isDeleted = false", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    public List<User> getUsers() {
        String query = "select u from User u";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        return typedQuery.getResultList();
    }

    @Override
    public User findById(Integer id) {
        String query = "select u from User u where id= :id";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    @Override
    public void changeStatus(Integer id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setStatus(!user.isStatus());
            entityManager.merge(user);
        }
    }
}
