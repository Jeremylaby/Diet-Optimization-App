package ki.agh.dyeti.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ki.agh.dyeti.dto.ProductPreferenceDTO;
import ki.agh.dyeti.exception.AccessDeniedException;
import ki.agh.dyeti.exception.ResourceNotFoundException;
import ki.agh.dyeti.model.Product;
import ki.agh.dyeti.model.ProductPreference;
import ki.agh.dyeti.model.Role;
import ki.agh.dyeti.model.User;
import ki.agh.dyeti.model.util.ProductPreferenceId;
import ki.agh.dyeti.repository.ProductPreferenceRepository;
import ki.agh.dyeti.repository.ProductRepository;
import ki.agh.dyeti.security.CurrentUserProvider;
import ki.agh.dyeti.security.ResourceAccessValidator;
import org.springframework.stereotype.Service;

@Service
public class ProductPreferenceService {

    private static final int MIN_PREFERENCE_VALUE = 0;
    private static final int MAX_PREFERENCE_VALUE = 10;
    private final ProductPreferenceRepository productPreferenceRepository;
    private final ProductRepository productRepository;
    private final CurrentUserProvider currentUserProvider;
    private final ResourceAccessValidator resourceAccessValidator;

    public ProductPreferenceService(
            ProductPreferenceRepository productPreferenceRepository,
            ProductRepository productRepository,
            CurrentUserProvider currentUserProvider,
            ResourceAccessValidator resourceAccessValidator) {
        this.productPreferenceRepository = productPreferenceRepository;
        this.productRepository = productRepository;
        this.currentUserProvider = currentUserProvider;
        this.resourceAccessValidator = resourceAccessValidator;
    }

    public List<ProductPreferenceDTO> getAllProductPreferences() {
        User currentUser = currentUserProvider
                .getCurrentUser()
                .orElseThrow(() -> new IllegalStateException("Current user is not logged in"));

        if (currentUser.getRole() == Role.ADMIN) {
            return productPreferenceRepository.findAll().stream()
                    .map(ProductPreferenceDTO::fromEntity)
                    .collect(Collectors.toList());
        }

        return productPreferenceRepository.findByOwnerId(currentUser.getId()).stream()
                .map(ProductPreferenceDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductPreferenceDTO> getProductPreferencesByUserId(Long userId) {
        User currentUser = currentUserProvider
                .getCurrentUser()
                .orElseThrow(() -> new IllegalStateException("Current user is not logged in"));

        if (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId)) {
            return productPreferenceRepository.findByOwnerId(currentUser.getId()).stream()
                    .map(ProductPreferenceDTO::fromEntity)
                    .collect(Collectors.toList());
        }

        throw new AccessDeniedException("You do not have permission to access this resource");
    }

    @Transactional
    public ProductPreferenceDTO createProductPreference(ProductPreferenceDTO productPreferenceDTO) {
        User currentUser = currentUserProvider
                .getCurrentUser()
                .orElseThrow(() -> new IllegalStateException("Current user is not logged in"));

        if (productPreferenceDTO.preference() == null
                || productPreferenceDTO.preference() <= MIN_PREFERENCE_VALUE
                || productPreferenceDTO.preference() > MAX_PREFERENCE_VALUE) {
            throw new IllegalArgumentException(
                    "Preference must be between " + MIN_PREFERENCE_VALUE + " and " + MAX_PREFERENCE_VALUE);
        }

        Product product = productRepository
                .findById(productPreferenceDTO.product().id())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Product with id " + productPreferenceDTO.product().id() + " does not exist"));

        ProductPreferenceId id = new ProductPreferenceId(currentUser.getId(), product.getId());

        if (productPreferenceRepository.existsById(id)) {
            throw new IllegalStateException("Product preference with id " + id + " already exists");
        }

        ProductPreference productPreference = ProductPreference.builder()
                .id(id)
                .owner(currentUser)
                .product(product)
                .preference(productPreferenceDTO.preference())
                .build();

        ProductPreference savedProductPreference = productPreferenceRepository.save(productPreference);

        return ProductPreferenceDTO.fromEntity(savedProductPreference);
    }

    @Transactional
    public ProductPreferenceDTO updateProductPreference(ProductPreferenceDTO productPreferenceDTO) {
        User currentUser = currentUserProvider
                .getCurrentUser()
                .orElseThrow(() -> new IllegalStateException("Current user is not logged in"));

        ProductPreferenceId id = new ProductPreferenceId(
                currentUser.getId(), productPreferenceDTO.product().id());
        ProductPreference existingProductPreference = productPreferenceRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Product preference with id " + id + " does not exist"));

        existingProductPreference.setPreference(productPreferenceDTO.preference());

        ProductPreference updatedProductPreference = productPreferenceRepository.save(existingProductPreference);

        return ProductPreferenceDTO.fromEntity(updatedProductPreference);
    }

    @Transactional
    public ProductPreferenceDTO deleteProductPreference(Long productId) {
        User currentUser = currentUserProvider
                .getCurrentUser()
                .orElseThrow(() -> new IllegalStateException("Current user is not logged in"));

        ProductPreferenceId id = new ProductPreferenceId(currentUser.getId(), productId);
        ProductPreference exisitingProductPreference = productPreferenceRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product preference with id " + id + " does not exist"));

        resourceAccessValidator.validateOwnership(exisitingProductPreference);

        productPreferenceRepository.delete(exisitingProductPreference);

        return ProductPreferenceDTO.fromEntity(exisitingProductPreference);
    }

    public Map<Product, Double> getProductPreferencesMapByUserId(Long userId) {
        User currentUser = currentUserProvider
                .getCurrentUser()
                .orElseThrow(() -> new IllegalStateException("Current user is not logged in"));

        if (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId)) {
            return productPreferenceRepository.findByOwnerId(userId).stream()
                    .collect(Collectors.toMap(ProductPreference::getProduct, ProductPreference::getPreference));
        }

        throw new AccessDeniedException("You do not have permission to access this resource");
    }
}
