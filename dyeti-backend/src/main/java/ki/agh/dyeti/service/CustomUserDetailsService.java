package ki.agh.dyeti.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ki.agh.dyeti.dto.UserDTO;
import ki.agh.dyeti.dto.UserPasswordDTO;
import ki.agh.dyeti.dto.request.RegisterRequest;
import ki.agh.dyeti.exception.InvalidPasswordException;
import ki.agh.dyeti.exception.InvalidRoleChangeException;
import ki.agh.dyeti.exception.UserEmailNotFoundException;
import ki.agh.dyeti.exception.UserNotFoundException;
import ki.agh.dyeti.model.Role;
import ki.agh.dyeti.model.User;
import ki.agh.dyeti.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User getUserByUsername(String username) {
        return (User) loadUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException(email));
    }

    // do testowania
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public UserDTO updateUserProfile(User user, UserDTO userDTO) {
        if (userDTO.username() != null) {
            user.setUsername(userDTO.username());
        }
        if (userDTO.email() != null) {
            user.setEmail(userDTO.email());
        }
        if (userDTO.age() != null) {
            user.setAge(userDTO.age());
        }
        if (userDTO.gender() != null) {
            user.setGender(userDTO.gender());
        }
        if (userDTO.height() != null) {
            user.setHeight(userDTO.height());
        }
        if (userDTO.weight() != null) {
            user.setWeight(userDTO.weight());
        }
        if (userDTO.energyReq() != null) {
            user.setEnergyReq(userDTO.energyReq());
        }
        if (userDTO.proteinReq() != null) {
            user.setProteinReq(userDTO.proteinReq());
        }
        if (userDTO.carbsReq() != null) {
            user.setCarbsReq(userDTO.carbsReq());
        }
        if (userDTO.fatReq() != null) {
            user.setFatReq(userDTO.fatReq());
        }

        userRepository.save(user);
        return UserDTO.fromUser(user);
    }

    @Transactional
    public UserDTO register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameNotFoundException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("Email already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return UserDTO.fromUser(user);
    }

    public List<UserDTO> getAllByRole(Role role) {
        return userRepository.findAllByRole(role).stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllAdmins() {
        return getAllByRole(Role.ADMIN);
    }

    public List<UserDTO> getAllUsers() {
        return getAllByRole(Role.USER);
    }

    public List<UserDTO> getAll() {
        return Stream.concat(getAllUsers().stream(), getAllAdmins().stream()).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO grantAdminRole(Long userId) {
        return changeUserRole(userId, Role.USER, Role.ADMIN);
    }

    @Transactional
    public UserDTO revokeAdminRole(Long userId) {
        return changeUserRole(userId, Role.ADMIN, Role.USER);
    }

    private UserDTO changeUserRole(Long userId, Role expectedCurrentRole, Role newRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getRole() != expectedCurrentRole) {
            throw new InvalidRoleChangeException(userId, user.getRole().name(), expectedCurrentRole.name());
        }

        user.setRole(newRole);
        User saved = userRepository.save(user);
        return UserDTO.fromUser(saved);
    }

    public UserDTO changePassword(Long userId, UserPasswordDTO userPasswordDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (!passwordEncoder.matches(userPasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(userPasswordDTO.getNewPassword()));
        User updatedUser = userRepository.save(user);

        return UserDTO.fromUser(updatedUser);
    }

    public void deactivateUser(String username) {
        User user = getUserByUsername(username);
        userRepository.delete(user);
    }
}
