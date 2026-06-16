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
@ActiveProfiles("tests")
@Transactional
public class BoatCommandServiceIT {

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private BoatCommandService boatCommandService;

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        boatRepository.deleteAll();
    }

    @Test
    void testAddBoat() {
        User user = userrepository.save(User.builder()
                .firstName("Pop")
                .lastName("Sebastian")
                .email("PDSeb@gmail.com")
                .age(19)
                .password(passwordEncoder.encode("password"))
                .permissions(PermissionTemplates.permissions)
                .build());

        BoatRequest req = BoatRequest.builder()
                .model("model")
                .color("Red")
                .size(10)
                .userId(user.getId())
                .build();

        BoatResponse created = boatCommandService.addBoat(req);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("model", created.getModel());
        assertEquals("Red", created.getColor());
        assertTrue(boatRepository.findById(created.getId()).isPresent());
    }

    @Test
    void testDeleteBoat() {
        User user = userrepository.save(User.builder()
                .firstName("Alex")
                .lastName("Stan")
                .email("alex.stan@test.com")
                .age(25)
                .password(passwordEncoder.encode("password"))
                .permissions(PermissionTemplates.permissions)
                .build());

        Boat saved = boatRepository.save(Boat.builder()
                .model("model")
                .color("Black")
                .size(15)
                .user(user)
                .build());

        BoatResponse deleted = boatCommandService.deleteBoat(saved.getId());

        assertNotNull(deleted);
        assertEquals("model", deleted.getModel());
        assertFalse(boatRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void testUpdateBoat() {
        User user1 = userrepository.save(User.builder()
                .firstName("Alex")
                .lastName("Stan")
                .email("alex.stan@test.com")
                .age(22)
                .password(passwordEncoder.encode("password"))
                .build());

        User user2 = userrepository.save(User.builder()
                .firstName("a")
                .lastName("b")
                .email("ab@test.com")
                .age(20)
                .password(passwordEncoder.encode("password"))
                .build());

        Boat saved = boatRepository.save(Boat.builder()
                .model("Old Model")
                .color("Blue")
                .size(12)
                .user(user1)
                .build());

        BoatRequest req = BoatRequest.builder()
                .model("New Model")
                .color("Green")
                .size(14)
                .userId(user2.getId())
                .build();

        BoatResponse updated = boatCommandService.updateBoat(saved.getId(), req);

        assertNotNull(updated);
        assertEquals("New Model", updated.getModel());
        assertEquals("Green", updated.getColor());
    }

    @Test
    void updatePatchBoat() {
        User user = userrepository.save(User.builder()
                .firstName("d")
                .lastName("i")
                .email("di@test.com")
                .age(30)
                .password(passwordEncoder.encode("password"))
                .build());

        Boat saved = boatRepository.save(Boat.builder()
                .model("model")
                .color("White")
                .size(8)
                .user(user)
                .build());

        BoatPatchRequest req = BoatPatchRequest.builder()
                .model("model")
                .color("Yellow")
                .build();

        BoatResponse patched = boatCommandService.updatePatchBoat(saved.getId(), req);

        assertNotNull(patched);
        assertEquals("model", patched.getModel());
        assertEquals("Yellow", patched.getColor());
    }
}
