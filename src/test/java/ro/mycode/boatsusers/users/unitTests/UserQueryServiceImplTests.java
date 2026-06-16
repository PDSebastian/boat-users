package ro.mycode.boatsusers.users.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.exceptions.UserNotFoundexception;
import ro.mycode.users.mapper.UserMapper;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.UserRepository;
import ro.mycode.users.service.UserQueryServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserQueryServiceImplTests {

    @Mock
    private UserRepository userrepository;

    @Mock
    private UserMapper usermapper;

    @InjectMocks
    private UserQueryServiceImpl userQueryService;

    @Test
    void getAllUsersTest() {
        User user1 = User.builder().firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();
        User user2 = User.builder().firstName("PD").lastName("S").email("PDS@gmail.com").age(22).build();

        UserResponse res1 = UserResponse.builder().firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();
        UserResponse res2 = UserResponse.builder().firstName("PD").lastName("S").email("PDS@gmail.com").age(22).build();

        when(userrepository.findAll()).thenReturn(List.of(user1, user2));
        when(usermapper.toDto(user1)).thenReturn(res1);
        when(usermapper.toDto(user2)).thenReturn(res2);

        List<UserResponse> res = userQueryService.getAllUsers();

        assertEquals(2, res.size());
        assertEquals("John", res.get(0).firstName());
        assertEquals("PD", res.get(1).firstName());
    }

    @Test
    void testGetUserByIdReturnsOk() {
        User user = User.builder().id(1L).firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();
        UserResponse expectedResponse = UserResponse.builder().id(1L).firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();

        when(userrepository.findById(1L)).thenReturn(Optional.of(user));
        when(usermapper.toDto(user)).thenReturn(expectedResponse);

        UserResponse res = userQueryService.getUserById(1L);
        assertEquals(expectedResponse, res);
    }

    @Test
    void testGetUserByFirstNameReturnsOk() {
        User user = User.builder().id(1L).firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();
        UserResponse expectedResponse = UserResponse.builder().id(1L).firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();

        when(userrepository.findByFirstName("John")).thenReturn(Optional.of(user));
        when(usermapper.toDto(user)).thenReturn(expectedResponse);

        UserResponse res = userQueryService.getUsersByFirstName("John");
        assertEquals(expectedResponse, res);
    }

    @Test
    void testGetUserByEmailReturnsOk() {
        User user = User.builder().id(1L).firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();
        UserResponse expectedResponse = UserResponse.builder().id(1L).firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();

        when(userrepository.findByEmail("JD@gmail.com")).thenReturn(Optional.of(user));
        when(usermapper.toDto(user)).thenReturn(expectedResponse);

        UserResponse res = userQueryService.getUserByEmail("JD@gmail.com");
        assertEquals(expectedResponse, res);
    }

    @Test
    void testGetUserByEmailThrowsUserNotFoundException() {
        when(userrepository.findByEmail("JD@gmail.com")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundexception.class, () -> userQueryService.getUserByEmail("JD@gmail.com"));
    }

    @Test
    void testGetUserByFirstNameThrowsUserNotFoundException() {
        when(userrepository.findByFirstName("John")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundexception.class, () -> userQueryService.getUsersByFirstName("John"));
    }
}