package ro.mycode.boats.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import ro.mycode.users.model.User;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boats")
public class Boat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Modelul este obligatoriu")
    @Size(min = 3, max = 50)
    private String model;

    @NotBlank(message = "culoarea este obligatorie")
    @Size(min = 3, max = 50)
    private String color;

    @NotNull(message = "Marimea este obligatorie")
    @Positive(message = ">5")
    private int size;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Boat boat = (Boat) o;
        return Id == boat.Id && size == boat.size && Objects.equals(model, boat.model) && Objects.equals(color, boat.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, model, color, size);
    }

    @Override
    public String toString() {
        return "Boat{" +
                "id=" + Id +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", size=" + size +
                '}';
    }
}
