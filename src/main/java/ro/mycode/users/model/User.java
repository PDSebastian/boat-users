package ro.mycode.users.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.mycode.boats.model.Boat;
import ro.mycode.system.security.UserPermissions;

import java.util.*;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
    private String password;
    private Set<UserPermissions> permissions = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Boat> boats = new ArrayList<>() {
    };

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(UserPermissions::getPermission)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
