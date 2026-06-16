package ro.mycode.boatsusers.users.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.UserRepository;
import ro.mycode.users.service.UserQueryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("tests")
@Transactional
public class UserQueryServcieIT {

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private UserQueryService userQueryService;

    @BeforeEach
    public void setup() {
        userrepository.deleteAll();
    }

    @Test
    void testGetAllUsersReturns200() {
        userrepository.save(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("JD@gmail.com")
                .age(19)
                .build());

        userrepository.save(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("aa@gmail.com")
                .age(19)
                .build());

        List<UserResponse> users = userQueryService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserByIdReturns200() {
        User saved = userrepository.save(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("JD@gmail.com")
                .age(19)
                .build());

        UserResponse found = userQueryService.getUserById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.id());
        assertEquals("John", found.firstName());
        assertEquals("JD@gmail.com", found.email());
    }

    @Test
    void testGetUserByEmailReturns200() {
        User saved = userrepository.save(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("JD@gmail.com")
                .age(19)
                .build());

        UserResponse found = userQueryService.getUserByEmail("JD@gmail.com");

        assertNotNull(found);
        assertEquals(saved.getId(), found.id());
        assertEquals("JD@gmail.com", found.email());
    }

    @Test
    void testGetUSerByFirstNameReturns200() {
        User saved = userrepository.save(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("JD@gmail.com")
                .age(19)
                .build());

        UserResponse found = userQueryService.getUsersByFirstName("John");

        assertNotNull(found);
        assertEquals(saved.getId(), found.id());
        assertEquals("John", found.firstName());
    }
}
