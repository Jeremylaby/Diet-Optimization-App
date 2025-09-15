package ki.agh.dyeti.dto;

import ki.agh.dyeti.model.ProductPreference;

public record ProductPreferenceDTO(ProductDTO product, Double preference) {
    public static ProductPreferenceDTO fromEntity(ProductPreference productPreference) {
        return new ProductPreferenceDTO(
                ProductDTO.fromEntity(productPreference.getProduct()), productPreference.getPreference());
    }
}
