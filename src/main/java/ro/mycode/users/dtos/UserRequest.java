package ro.mycode.users.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "Prenume obligatoriu")
    @Size(min = 1, max =100)
    private String firstName;

    @NotBlank(message = "Numele de familie obligatoriu")
    @Size(min = 1, max = 100)
    private String lastName;

    @NotBlank(message = "Email obligatoriu")
    @Size(min = 1, max = 100)
    private String email;

    @NotNull(message = "Varsta este obligatorie")
    @Positive(message = "Varsta >18")
    private int age;





}
