package ro.mycode.boatsusers.users.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.users.dtos.UserPatchRequest;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.UserRepository;
import ro.mycode.users.service.UserCommandServiceImpl;
import ro.mycode.users.service.UserQueryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("tests")
@Transactional
public class UserCommandServiceIT {

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private UserCommandServiceImpl userCommandService;

    @BeforeEach
    public void setup() {
        userrepository.deleteAll();
    }

    @Test
    void addUser_persistsAndReturnsDto() {
        UserRequest req = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("JD@gmail.com")
                .age(19)
                .build();

        UserResponse created = userCommandService.addUser(req);

        assertNotNull(created);
        assertNotNull(created.id());
        assertEquals("John", created.firstName());
        assertEquals("JD@gmail.com", created.email());
        assertTrue(userrepository.findById(created.id()).isPresent());
    }

    @Test
    void deleteUser_removesFromDatabase() {
        User saved = userrepository.save(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("JD@gmail.com")
                .age(19)
                .build());

        userCommandService.deleteUser(saved.getId());

        assertFalse(userrepository.findById(saved.getId()).isPresent());
    }

//    @Test
//    void updateUser_updatesFieldsAndReturnsDto() {
//        User saved = userrepository.save(User.builder()
//                .firstName("Old FirstName")
//                .lastName("Old LastName")
//                .email("old@gmail.com")
//                .age(20)
//                .build());
//
//        UserRequest updateReq = UserRequest.builder()
//                .firstName("New FirstName")
//                .lastName("New LastName")
//                .email("new@gmail.com")
//                .age(25)
//                .build();
//
//        UserResponse updated = userCommandService.updateUser( updateReq);
//
//        assertNotNull(updated);
//        assertEquals("New FirstName", updated.firstName());
//        assertEquals("new@gmail.com", updated.email());
//        assertEquals(25, updated.age());
//    }

    @Test
    void updatePatchUser_partialUpdateReturnsDto() {
        User saved = userrepository.save(User.builder()
                .firstName("D")
                .lastName("I")
                .email("DI@test.com")
                .age(30)
                .build());

        UserPatchRequest patchReq = UserPatchRequest.builder()
                .email("aa@gmail.com")
                .build();

        UserResponse patched = userCommandService.updatePatchUser(saved.getId(), patchReq);

        assertNotNull(patched);
        assertEquals("aa@gmail.com", patched.email());
        assertEquals("D", patched.firstName());
    }



}
