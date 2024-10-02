package ra.md4.dao.shoppingcart;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ra.md4.models.ShoppingCart;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class ShoppingCartDaoImpl implements IShoppingCartDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ShoppingCart> getAll() {
        TypedQuery<ShoppingCart> query = entityManager.createQuery("SELECT sc FROM ShoppingCart sc", ShoppingCart.class);
        return query.getResultList();
    }

    @Override
    public ShoppingCart findById(Integer id) {
        return entityManager.find(ShoppingCart.class, id);
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        if (shoppingCart.getId() == null) {
            entityManager.persist(shoppingCart);
        } else {
            entityManager.merge(shoppingCart);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        entityManager.merge(shoppingCart);
    }

    @Override
    public void delete(Integer id) {
        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, id);
        if (shoppingCart != null) {
            entityManager.remove(shoppingCart);
        }
    }
}
