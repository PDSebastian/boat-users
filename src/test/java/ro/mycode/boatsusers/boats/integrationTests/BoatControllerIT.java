package ro.mycode.boatsusers.boats.integrationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.boats.repository.BoatRepository;
import ro.mycode.boats.model.Boat;
import ro.mycode.system.jwt.JWTTokenProvider;
import ro.mycode.system.security.UserPermissions;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.UserRepository;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class BoatControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    protected JWTTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User persistTestUser(String email, Set<UserPermissions> permissions) {
        User user = User.builder()
                .firstName("Pop")
                .lastName("Rafael")
                .email(email)
                .age(25)
                .password(passwordEncoder.encode("password"))
                .permissions(permissions)
                .build();
        return userRepository.save(user);
    }

    private Boat persistTestBoat(User user, String model) {
        Boat boat = Boat.builder()
                .model(model)
                .color("Red")
                .size(12)
                .user(user)
                .build();
        return boatRepository.save(boat);
    }

    @Test
    void addBoat() throws Exception {
        User user = persistTestUser("a@gmail.com", Set.of(UserPermissions.BOAT_ADD));
        String token = jwtTokenProvider.generateToken(user);

        mockMvc.perform(post("/api/v2/boats/add")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(String.format("""
                           {
                               "model": "Model",
                               "color": "Red",
                               "size": 12,
                               "userId": %d
                               
                           }
                           """, user.getId())))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteBoat() throws Exception {
        User user = persistTestUser("b@gmail.com", Set.of(UserPermissions.BOAT_DELETE));
        Boat boat = persistTestBoat(user, "ModelX");
        String token = jwtTokenProvider.generateToken(user);

        mockMvc.perform(delete("/api/v2/boats/delete/" + boat.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void patchBoat() throws Exception {
        User user = persistTestUser("c@gmail.com", Set.of(UserPermissions.BOAT_EDIT));
        Boat boat = persistTestBoat(user, "ModelY");
        String token = jwtTokenProvider.generateToken(user);

        mockMvc.perform(patch("/api/v2/boats/patch/" + boat.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                           {
                               "model": "Model Modificat",
                               "color": "Green"
                           }
                           """))
                .andExpect(status().isOk());
    }

    @Test
    void getBoatById() throws Exception {
        User user = persistTestUser("d@gmail.com", Set.of());
        Boat boat = persistTestBoat(user, "ModelZ");
        String token = jwtTokenProvider.generateToken(user);

        mockMvc.perform(get("/api/v2/boats/" + boat.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBoats() throws Exception {
        User user = persistTestUser("e@gmail.com", Set.of());
        persistTestBoat(user, "ModelA");
        String token = jwtTokenProvider.generateToken(user);

        mockMvc.perform(get("/api/v2/boats/all")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void getBoatByModel() throws Exception {
        User user = persistTestUser("f@gmail.com", Set.of());
        persistTestBoat(user, "SpecificModel");
        String token = jwtTokenProvider.generateToken(user);

        mockMvc.perform(get("/api/v2/boats/model/SpecificModel")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void addBoatForbidden() throws Exception {
        User user = persistTestUser("g@gmail.com", Set.of(UserPermissions.BOAT_DELETE));
        String token = jwtTokenProvider.generateToken(user);

        mockMvc.perform(post("/api/v2/boats/add")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                           {
                               "model": "Model",
                               "color": "Red",
                               "size": 12
                           }
                           """))
                .andExpect(status().isForbidden());
    }
}