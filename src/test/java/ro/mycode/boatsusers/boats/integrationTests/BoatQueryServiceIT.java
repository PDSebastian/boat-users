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
@ActiveProfiles("test")
@Transactional
public class BoatQueryServiceIT {

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private BoatQueryService boatQueryService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        boatRepository.deleteAll();
    }

    private User persistUser(String email) {
        return userRepository.save(User.builder()
                .firstName("Pop")
                .lastName("Rafael")
                .email(email)
                .age(25)
                .build());
    }

    private Boat persistBoat(User user, String model) {
        return boatRepository.save(Boat.builder()
                .model(model)
                .color("Red")
                .size(12)
                .user(user)
                .build());
    }

    @Test
    void getAllBoats() {
        User user = persistUser("a@gmail.com");
        persistBoat(user, "ModelA");
        persistBoat(user, "ModelB");

        List<BoatResponse> res = boatQueryService.getAllBoats();

        assertNotNull(res);
        assertEquals(2, res.size());
    }

    @Test
    void getBoatById() {
        User user = persistUser("b@gmail.com");
        Boat boat = persistBoat(user, "ModelX");

        BoatResponse res = boatQueryService.getBoatById(boat.getId());

        assertNotNull(res);
        assertEquals(boat.getId(), res.getId());
        assertEquals("ModelX", res.getModel());
    }

    @Test
    void getBoatByModel() {
        User user = persistUser("c@gmail.com");
        Boat boat = persistBoat(user, "ModelY");

        BoatResponse res = boatQueryService.getBoatByModel("ModelY");

        assertNotNull(res);
        assertEquals(boat.getId(), res.getId());
        assertEquals("ModelY", res.getModel());
    }
}