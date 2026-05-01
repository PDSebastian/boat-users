package ro.mycode.users.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPatchRequest(
        @Size(min = 2, message = "Prenumele trebuie sa aiba minim 2 caractere")
        String firstName,
        @Size(min = 2, message = "Numele trebuie sa aiba minim 2 caractere")
        String lastName,

        @NotBlank(message = "Email-ul trebuie sa fie valid")
        String email,
        Integer age
) {
}
