package ro.mycode.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.mycode.users.model.User;

import java.util.List;
import java.util.Optional;

    public interface Userrepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByFirstName(String firstName);
    Optional<User> findByEmail(String email);

    List<User> findByAge(int age);
}
