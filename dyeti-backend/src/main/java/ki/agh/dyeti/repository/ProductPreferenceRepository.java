package ki.agh.dyeti.repository;

import java.util.List;
import ki.agh.dyeti.model.ProductPreference;
import ki.agh.dyeti.model.util.ProductPreferenceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPreferenceRepository extends JpaRepository<ProductPreference, ProductPreferenceId> {
    List<ProductPreference> findByOwnerId(Long ownerId);
}
