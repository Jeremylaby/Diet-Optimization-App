package ki.agh.dyeti.model;

import jakarta.persistence.*;
import ki.agh.dyeti.model.util.Ownable;
import ki.agh.dyeti.model.util.ProductPreferenceId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_preference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPreference implements Ownable {

    @EmbeddedId
    private ProductPreferenceId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Double preference;
}
