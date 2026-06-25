package ro.mycode.boatsusers.boats.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.boats.dtos.BoatPatchRequest;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.model.Boat;
import ro.mycode.boats.repository.BoatRepository;
import ro.mycode.boats.service.BoatCommandService;
import ro.mycode.users.model.PermissionTemplates;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BoatCommandServiceIT {

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private BoatCommandService boatCommandService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .password(passwordEncoder.encode("password"))
                .permissions(PermissionTemplates.permissions)
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
    void addBoat() {
        User user = persistUser("a@gmail.com");

        BoatRequest req = BoatRequest.builder()
                .model("Model")
                .color("Red")
                .size(12)
                .userId(user.getId())
                .build();

        BoatResponse res = boatCommandService.addBoat(req);

        assertNotNull(res);
        assertNotNull(res.getId());
        assertEquals("Model", res.getModel());
        assertTrue(boatRepository.findById(res.getId()).isPresent());
    }

    @Test
    void deleteBoat() {
        User user = persistUser("b@gmail.com");
        Boat boat = persistBoat(user, "ModelX");

        BoatResponse res = boatCommandService.deleteBoat(boat.getId());

        assertNotNull(res);
        assertEquals("ModelX", res.getModel());
        assertFalse(boatRepository.findById(boat.getId()).isPresent());
    }

    @Test
    void updateBoat() {
        User user1 = persistUser("c@gmail.com");
        User user2 = persistUser("d@gmail.com");
        Boat boat = persistBoat(user1, "ModelOld");

        BoatRequest req = BoatRequest.builder()
                .model("ModelNew")
                .color("Green")
                .size(14)
                .userId(user2.getId())
                .build();

        BoatResponse res = boatCommandService.updateBoat(boat.getId(), req);

        assertNotNull(res);
        assertEquals("ModelNew", res.getModel());
        assertEquals("Green", res.getColor());
    }

    @Test
    void patchBoat() {
        User user = persistUser("e@gmail.com");
        Boat boat = persistBoat(user, "ModelY");

        BoatPatchRequest req = BoatPatchRequest.builder()
                .model("ModelPatch")
                .color("Green")
                .build();

        BoatResponse res = boatCommandService.updatePatchBoat(boat.getId(), req);

        assertNotNull(res);
        assertEquals("ModelPatch", res.getModel());
        assertEquals("Green", res.getColor());
    }
}