package ki.agh.dyeti.controller;

import java.util.List;
import ki.agh.dyeti.dto.ProductPreferenceDTO;
import ki.agh.dyeti.service.ProductPreferenceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preference")
public class ProductPreferenceController {
    private final ProductPreferenceService productPreferenceService;

    public ProductPreferenceController(ProductPreferenceService productPreferenceService) {
        this.productPreferenceService = productPreferenceService;
    }

    @GetMapping()
    public List<ProductPreferenceDTO> getProductPreferences() {
        return productPreferenceService.getAllProductPreferences();
    }

    @GetMapping("/{id}")
    public List<ProductPreferenceDTO> getProductPreferencesByUserId(@PathVariable Long id) {
        return productPreferenceService.getProductPreferencesByUserId(id);
    }

    @PostMapping()
    public ProductPreferenceDTO createProductPreference(@RequestBody ProductPreferenceDTO productPreferenceDTO) {
        return productPreferenceService.createProductPreference(productPreferenceDTO);
    }

    @PutMapping()
    public ProductPreferenceDTO updateProductPreference(@RequestBody ProductPreferenceDTO productPreferenceDTO) {
        return productPreferenceService.updateProductPreference(productPreferenceDTO);
    }

    @DeleteMapping("/{productId}")
    public ProductPreferenceDTO deleteProductPreference(@PathVariable Long productId) {
        return productPreferenceService.deleteProductPreference(productId);
    }
}
