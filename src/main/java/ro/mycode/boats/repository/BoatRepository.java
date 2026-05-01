package ro.mycode.boats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.mycode.boats.model.Boat;

import java.util.Optional;

public interface BoatRepository extends JpaRepository<Boat,Long> {
    Optional<Boat> findByModel(String model);


}
