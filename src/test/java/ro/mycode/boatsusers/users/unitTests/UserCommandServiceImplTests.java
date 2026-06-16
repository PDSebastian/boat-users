package ro.mycode.boatsusers.users.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.users.dtos.UserPatchRequest;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.exceptions.InvalidAgeException;
import ro.mycode.users.exceptions.UserAlreadyExistsException;
import ro.mycode.users.exceptions.UserNotFoundexception;
import ro.mycode.users.mapper.UserMapper;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.UserRepository;
import ro.mycode.users.service.UserCommandServiceImpl;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserCommandServiceImplTests {

    @Mock
    private UserRepository userrepository;

    @Mock
    private UserMapper usermapper;

    @InjectMocks
    private UserCommandServiceImpl userCommandService;

    @Test
    void testCreateUserReturnsOk() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();
        User user = User.builder()
                .firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();
        UserResponse expectedResponse = UserResponse.builder()
                .id(1L).firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();

        when(userrepository.save(any(User.class))).thenReturn(user);
        when(usermapper.toDto(any(User.class))).thenReturn(expectedResponse);

        UserResponse actualResponse = userCommandService.addUser(userRequest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testUpdatePatchUserReturnsOk() {
        UserPatchRequest userPatchRequest = UserPatchRequest.builder().firstName("John").build();
        User userDB = User.builder().id(1L).firstName("Old").lastName("Doe").email("JD@gmail.com").age(19).build();
        UserResponse expectedResponse = UserResponse.builder()
                .id(1L).firstName("John").lastName("Doe").email("JD@gmail.com").age(19)
                .boatsResponses(new ArrayList<>()).build();

        when(userrepository.findById(1L)).thenReturn(Optional.of(userDB));
        when(userrepository.save(any(User.class))).thenReturn(userDB);
        when(usermapper.toDto(any(User.class))).thenReturn(expectedResponse);

        UserResponse actualResponse = userCommandService.updatePatchUser(1L, userPatchRequest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testDeleteUserReturnsOk() {
        when(userrepository.existsById(1L)).thenReturn(true);
        userCommandService.deleteUser(1L);
    }

    @Test
    void testUpdateUserByEmailReturnsOk() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();
        User userDB = User.builder().id(1L).firstName("Old").lastName("Doe").email("JD@gmail.com").age(19).build();
        UserResponse expectedResponse = UserResponse.builder()
                .id(1L).firstName("John").lastName("Doe").email("JD@gmail.com").age(19)
                .boatsResponses(new ArrayList<>()).build();

        when(userrepository.findByEmail("JD@gmail.com")).thenReturn(Optional.of(userDB));
        when(userrepository.save(any(User.class))).thenReturn(userDB);
        when(usermapper.toDto(userDB)).thenReturn(expectedResponse);

        UserResponse actualResponse = userCommandService.updateUser(userRequest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testAddUserThrowsInvalidAgeException() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("John").lastName("Doe").email("JD@gmail.com").age(11).build();

        assertThrows(InvalidAgeException.class, () -> userCommandService.addUser(userRequest));
    }

    @Test
    void testUpdateUserThrowsUserNotFoundException() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();

        when(userrepository.findByEmail("JD@gmail.com")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundexception.class, () -> userCommandService.updateUser(userRequest));
    }

    @Test
    void testDeleteUserThrowsUserNotFoundException() {
        when(userrepository.existsById(1L)).thenReturn(false);
        assertThrows(UserNotFoundexception.class, () -> userCommandService.deleteUser(1L));
    }

    @Test
    void testUpdatePatchUserThrowsExceptions() {
        UserPatchRequest userPatchRequest = UserPatchRequest.builder().firstName("John").build();

        when(userrepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundexception.class, () -> userCommandService.updatePatchUser(1L, userPatchRequest));
    }

    @Test
    void testAddUserThrowsUserAlreadyExistsException() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("John").lastName("Doe").email("JD@gmail.com").age(19).build();

        when(userrepository.findByEmail("JD@gmail.com")).thenReturn(Optional.of(new User()));
        assertThrows(UserAlreadyExistsException.class, () -> userCommandService.addUser(userRequest));
    }
}