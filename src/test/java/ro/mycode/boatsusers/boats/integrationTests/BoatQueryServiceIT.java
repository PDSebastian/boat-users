package ro.mycode.boatsusers.boats.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.model.Boat;
import ro.mycode.boats.repository.BoatRepository;
import ro.mycode.boats.service.BoatQueryService;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("tests")
@Transactional
public class BoatQueryServiceIT {

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private BoatQueryService boatQueryService;

    @Autowired
    private UserRepository userrepository;

    @BeforeEach
    public void setup() {
        boatRepository.deleteAll();
    }

    @Test
    void testGetAllBoats() {
        User user = userrepository.save(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("JD@gmail.com")
                .age(19)
                .build());

        boatRepository.save(Boat.builder()
                .model("Yamaha")
                .color("Blue")
                .size(10)
                .user(user)
                .build());

        boatRepository.save(Boat.builder()
                .model("Honda")
                .color("Red")
                .size(100)
                .user(user)
                .build());

        List<BoatResponse> boats = boatQueryService.getAllBoats();

        assertNotNull(boats);
        assertEquals(2, boats.size());
    }

    @Test
    void testGetBoatById() {
        User user = userrepository.save(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("JD@gmail.com")
                .age(19)
                .build());

        Boat savedBoat = boatRepository.save(Boat.builder()
                .model("Yamaha")
                .color("Blue")
                .size(10)
                .user(user)
                .build());

        BoatResponse found = boatQueryService.getBoatById(savedBoat.getId());

        assertNotNull(found);
        assertEquals(savedBoat.getId(), found.getId());
        assertEquals("Yamaha", found.getModel());
        assertEquals("Blue", found.getColor());
    }

    @Test
    void testGetBoatByModel() {
        User user = userrepository.save(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("JD@gmail.com")
                .age(19)
                .build());

        Boat savedBoat = boatRepository.save(Boat.builder()
                .model("Yamaha")
                .color("Blue")
                .size(10)
                .user(user)
                .build());

        BoatResponse found = boatQueryService.getBoatByModel("Yamaha");

        assertNotNull(found);
        assertEquals(savedBoat.getId(), found.getId());
        assertEquals("Yamaha", found.getModel());
    }
}
