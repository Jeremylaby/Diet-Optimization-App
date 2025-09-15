package ki.agh.dyeti.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPasswordDTO {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 100;

    @NotBlank(message = "Current password cannot be blank")
    private String currentPassword;

    @NotBlank(message = "New password cannot be blank")
    @Size(
            min = MIN_PASSWORD_LENGTH,
            max = MAX_PASSWORD_LENGTH,
            message = "Password must be between 8 and 100 characters")
    private String newPassword;
}
