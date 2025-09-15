package ki.agh.dyeti.repository;

import java.util.List;
import java.util.Optional;
import ki.agh.dyeti.model.Role;
import ki.agh.dyeti.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAllByRole(Role role);
}
