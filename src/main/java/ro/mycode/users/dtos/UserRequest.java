package ro.mycode.users.dtos;

import jakarta.validation.constraints.*;


public record UserRequest(
        @NotBlank(message = "Prenume obligatoriu")
        @Size(min = 1, max =100)
        String firstName,

        @NotBlank(message = "Numele de familie obligatoriu")
        @Size(min = 1, max = 100)
         String lastName,

        @NotBlank(message = "Email obligatoriu")
        @Size(min = 1, max = 100)
         String email,

        @NotNull(message = "Varsta este obligatorie")
        @Positive(message = "Varsta >18")
         int age,

        @NotBlank(message = "Parola este obligatorie")
        String password

) {






}
