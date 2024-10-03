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

    @Override
    public double calculateTotal(List<ShoppingCart> shoppingCarts) {
        return shoppingCarts.stream()
                .mapToDouble(cart -> cart.getQuantity() * cart.getProduct().getUnitPrice())
                .sum();
    }

    @Override
    public double calculateTotalAmount(List<ShoppingCart> shoppingCarts) {
        return shoppingCarts.stream()
                .mapToDouble(cart -> cart.getQuantity() * cart.getProduct().getUnitPrice())
                .sum();
    }

    @Override
    public ShoppingCart findByUserAndProduct(Integer userId, Integer productId) {
        TypedQuery<ShoppingCart> query = entityManager.createQuery("SELECT sc FROM ShoppingCart sc WHERE sc.user.id = :userId AND sc.product.id = :productId", ShoppingCart.class);
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);
        return query.getResultList().stream().findFirst().orElse(null);
    }

}
