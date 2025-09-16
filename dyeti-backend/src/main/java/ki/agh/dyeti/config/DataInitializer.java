package ki.agh.dyeti.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import ki.agh.dyeti.model.*;
import ki.agh.dyeti.model.util.ProductPreferenceId;
import ki.agh.dyeti.repository.ProductPreferenceRepository;
import ki.agh.dyeti.repository.ProductRepository;
import ki.agh.dyeti.repository.UnitRepository;
import ki.agh.dyeti.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initializeData(
            UnitRepository unitRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            ProductPreferenceRepository preferenceRepository) {
        return args -> {
            Map<String, Unit> units = initializeUnits(unitRepository);
            List<Product> products = initializeProducts(productRepository, units);
            User testUser = initializeTestUser(userRepository);
            initializePreferences(preferenceRepository, testUser, products);
        };
    }

    private Map<String, Unit> initializeUnits(UnitRepository unitRepository) {
        List<Unit> defaultUnits =
                List.of(new Unit(null, "gram", "g"), new Unit(null, "milliliter", "ml"), new Unit(null, "piece", "pc"));

        Map<String, Unit> unitMap = new HashMap<>();
        for (Unit unit : defaultUnits) {
            Unit saved = unitRepository.save(unit);
            unitMap.put(saved.getName(), saved);
        }

        return unitMap;
    }

    private List<Product> initializeProducts(ProductRepository productRepository, Map<String, Unit> units) {
        List<Product> products = List.of(
                new Product(
                        null,
                        "Beef, ground, 90% lean meat / 10% fat, raw",
                        units.get("gram"),
                        1.0,
                        185.0,
                        18.2,
                        0.0,
                        12.8,
                        null),
                new Product(null, "Egg, whole, raw, frozen", units.get("piece"), 50.0, 150.0, 12.3, 0.91, 10.3, null),
                new Product(
                        null,
                        "Potatoes, gold, without skin, raw",
                        units.get("gram"),
                        1.0,
                        73.5,
                        1.81,
                        16.0,
                        0.264,
                        null),
                new Product(
                        null,
                        "Rice, white, long grain, unenriched, raw",
                        units.get("gram"),
                        1.0,
                        359.0,
                        7.04,
                        80.3,
                        1.03,
                        null),
                new Product(null, "Eggplant, raw", units.get("gram"), 1.0, 26.1, 0.852, 5.4, 0.12, null),
                new Product(null, "Spinach, mature", units.get("gram"), 1.0, 27.6, 2.91, 2.64, 0.604, null),
                new Product(null, "Tomato, roma", units.get("gram"), 1.0, 22.0, 0.696, 3.84, 0.425, null),
                new Product(
                        null,
                        "Bananas, ripe and slightly ripe, raw",
                        units.get("piece"),
                        115.0,
                        97.0,
                        0.74,
                        23.0,
                        0.29,
                        null));

        return productRepository.saveAll(products);
    }

    private User initializeTestUser(UserRepository userRepository) {
        User testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword(passwordEncoder.encode("password")); // Hash this in production
        testUser.setUsername("test");
        testUser.setRole(Role.USER);
        return userRepository.save(testUser);
    }

    private void initializePreferences(
            ProductPreferenceRepository preferenceRepository, User user, List<Product> products) {
        Random random = new Random();

        List<ProductPreference> preferences = products.stream()
                .map(product -> {
                    ProductPreferenceId id = new ProductPreferenceId(user.getId(), product.getId());

                    return ProductPreference.builder()
                            .id(id)
                            .owner(user)
                            .product(product)
                            .preference(random.nextDouble()
                                    * ProductPreference.HIGHEST_PREFERENCE) // preference between 0.0 and 10.0
                            .build();
                })
                .toList();

        preferenceRepository.saveAll(preferences);
    }
}
