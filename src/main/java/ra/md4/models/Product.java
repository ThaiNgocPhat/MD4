package ra.md4.models;

import lombok.*;

import javax.persistence.*;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sku", length = 100, nullable = false, unique = true)
    private String sku;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private Double unitPrice;

    @Column(name = "discount", precision = 5, scale = 2)
    private Double discount;

    @Column(name = "discounted", precision = 5, scale = 2)
    private Double discounted;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "image", length = 1000)
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "update_at", nullable = false, updatable = false)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + "₫";
    }

    public String getFormattedUnitPrice() {
        return formatCurrency(unitPrice);
    }

    public String getFormattedDiscountedPrice() {
        return formatCurrency(discounted);
    }
}
