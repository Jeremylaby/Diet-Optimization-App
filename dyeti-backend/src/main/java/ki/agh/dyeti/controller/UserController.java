package ki.agh.dyeti.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import ki.agh.dyeti.dto.UserDTO;
import ki.agh.dyeti.dto.UserPasswordDTO;
import ki.agh.dyeti.exception.InvalidPasswordException;
import ki.agh.dyeti.exception.UserNotFoundException;
import ki.agh.dyeti.model.User;
import ki.agh.dyeti.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final CustomUserDetailsService userService;

    public UserController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        return userService.getUserById(user.getId());
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/admins")
    public List<UserDTO> getAdminUsers() {
        return userService.getAllAdmins();
    }

    @PatchMapping("/profile")
    public UserDTO updateProfile(@AuthenticationPrincipal User user, @RequestBody UserDTO userDTO) {
        UserDTO updatedUserDTO = userService.updateUserProfile(user, userDTO);
        return updatedUserDTO;
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal User user, @RequestBody @Valid UserPasswordDTO userPasswordDTO) {

        try {
            userService.changePassword(user.getId(), userPasswordDTO);
            return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
        } catch (InvalidPasswordException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/selfdeactivate")
    public ResponseEntity<?> deactivateLoggedUser(@AuthenticationPrincipal User user) {
        try {
            userService.deactivateUser(user.getUsername());
            return ResponseEntity.ok(Map.of("message", "User successfully deactivated"));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("deactivate/{username}")
    public ResponseEntity<?> deactivateUserByUsername(@PathVariable String username) {
        try {
            userService.deactivateUser(username);
            return ResponseEntity.ok(Map.of("message", "User successfully deactivated"));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
        }
    }
}
