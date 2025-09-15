package ki.agh.dyeti.model;

import jakarta.persistence.*;
import java.util.List;
import ki.agh.dyeti.model.util.Ownable;
import ki.agh.dyeti.model.util.PlanProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan implements Ownable {

    private static final int MAX_DESCRIPTION_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = MAX_DESCRIPTION_LENGTH)
    private String description;

    private Double caloriesTarget;
    private Double proteinsTarget;
    private Double carbsTarget;
    private Double fatsTarget;

    private Double calories;
    private Double proteins;
    private Double carbs;
    private Double fats;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanProduct> products;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
