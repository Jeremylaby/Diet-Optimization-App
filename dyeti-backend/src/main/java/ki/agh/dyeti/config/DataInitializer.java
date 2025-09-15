package ki.agh.dyeti.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ki.agh.dyeti.model.*;
import ki.agh.dyeti.model.Role;
import ki.agh.dyeti.model.Unit;
import ki.agh.dyeti.model.User;
import ki.agh.dyeti.model.util.Gender;
import ki.agh.dyeti.repository.ProductRepository;
import ki.agh.dyeti.repository.UnitRepository;
import ki.agh.dyeti.repository.UserRepository;
import ki.agh.dyeti.service.CustomUserDetailsService;
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
            CustomUserDetailsService userService) {
        return args -> {
            Map<String, Unit> units = initializeUnits(unitRepository);
            List<Product> products = initializeProducts(productRepository, units);
            User testUser = initializeTestUser(userRepository);
            initializeTestUsers(userService);
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

    private void initializeTestUsers(CustomUserDetailsService userService) {
        List<User> sampleUsers = List.of(
                User.builder()
                        .username("john_doe")
                        .email("john@example.com")
                        .password(passwordEncoder.encode("Password123!"))
                        .age(28)
                        .gender(Gender.MALE)
                        .height(180)
                        .weight(75)
                        .energyReq(2500)
                        .proteinReq(150)
                        .carbsReq(300)
                        .fatReq(70)
                        .role(Role.USER)
                        .build(),
                User.builder()
                        .username("emma_watson")
                        .email("emma@example.com")
                        .password(passwordEncoder.encode("SecurePass456!"))
                        .age(32)
                        .gender(Gender.FEMALE)
                        .height(165)
                        .weight(58)
                        .energyReq(1800)
                        .proteinReq(110)
                        .carbsReq(220)
                        .fatReq(50)
                        .role(Role.USER)
                        .build(),
                User.builder()
                        .username("admin_anna")
                        .email("anna.admin@example.com")
                        .password(passwordEncoder.encode("AdminPass789!"))
                        .age(35)
                        .gender(Gender.FEMALE)
                        .height(170)
                        .weight(65)
                        .energyReq(2100)
                        .proteinReq(130)
                        .carbsReq(250)
                        .fatReq(60)
                        .role(Role.ADMIN)
                        .build(),
                User.builder()
                        .username("sysadmin_mike")
                        .email("mike.admin@example.com")
                        .password(passwordEncoder.encode("SysAdminPass!123"))
                        .age(42)
                        .gender(Gender.MALE)
                        .height(175)
                        .weight(80)
                        .energyReq(2800)
                        .proteinReq(160)
                        .carbsReq(350)
                        .fatReq(80)
                        .role(Role.ADMIN)
                        .build(),
                User.builder()
                        .username("owner_tom")
                        .email("tom.owner@example.com")
                        .password(passwordEncoder.encode("OwnerMasterPass!"))
                        .age(50)
                        .gender(Gender.MALE)
                        .height(185)
                        .weight(85)
                        .energyReq(3000)
                        .proteinReq(180)
                        .carbsReq(380)
                        .fatReq(90)
                        .role(Role.OWNER)
                        .build(),
                User.builder()
                        .username("sarah_c")
                        .email("sarah.c@example.com")
                        .password(passwordEncoder.encode("SarahPass123"))
                        .age(22)
                        .gender(Gender.FEMALE)
                        .height(160)
                        .weight(55)
                        .energyReq(1700)
                        .proteinReq(100)
                        .carbsReq(200)
                        .fatReq(45)
                        .role(Role.USER)
                        .build(),
                User.builder()
                        .username("robert_k")
                        .email("robert.k@example.com")
                        .password(passwordEncoder.encode("RobertFit456"))
                        .age(45)
                        .gender(Gender.MALE)
                        .height(190)
                        .weight(90)
                        .energyReq(3200)
                        .proteinReq(200)
                        .carbsReq(400)
                        .fatReq(100)
                        .role(Role.USER)
                        .build(),
                User.builder()
                        .username("lisa_m")
                        .email("lisa.m@example.com")
                        .password(passwordEncoder.encode("LisaYoga789"))
                        .age(29)
                        .gender(Gender.FEMALE)
                        .height(168)
                        .weight(60)
                        .energyReq(1900)
                        .proteinReq(120)
                        .carbsReq(230)
                        .fatReq(55)
                        .role(Role.USER)
                        .build());

        sampleUsers.stream().forEach(userService::saveUser);
    }
}
