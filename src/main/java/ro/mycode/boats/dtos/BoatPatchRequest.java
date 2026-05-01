package ro.mycode.boats.dtos;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BoatPatchRequest(
        @Size(min = 3, max = 50, message = "Modelul trebuie sa fie intre 3 si 50 de caractere")
        String model,

        @Size(min = 3, max = 50, message = "Culoarea trebuie sa fie intre 3 si 50 de caractere")
        String color,

        @Positive(message = "Marimea trebuie sa fie un numar pozitiv")
        Integer size

) {}