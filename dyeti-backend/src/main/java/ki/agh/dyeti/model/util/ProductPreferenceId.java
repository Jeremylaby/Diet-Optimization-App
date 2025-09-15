package ki.agh.dyeti.model.util;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPreferenceId implements Serializable {
    private Long userId;
    private Long productId;
}
