package ro.mycode.boats.dtos;

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
public class BoatRequest {
    @NotBlank(message = "Modelul este obligatoriu")
    @Size(min = 3, max = 50)
    private String model;

    @NotBlank(message = "culoarea este obligatorie")
    @Size(min = 3, max = 50)
    private String color;

    @NotNull(message = "Marimea este obligatorie")
    @Positive(message = ">5")
    private int size;

    private Long userId;



}
