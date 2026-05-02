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
import ro.mycode.users.repository.Userrepository;
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
    private Userrepository  userrepository;

    @Mock
    UserMapper usermapper;

    @InjectMocks
    private UserCommandServiceImpl userCommandService;

    @Test
    public void testCreateUserReturnsOk(){
        Long id=1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        User user=User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserRequest userRequest=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

       UserResponse userResponse=UserResponse.builder()
               .id(id)
               .firstName(firstName)
               .lastName(lastName)
               .email(email)
               .age(age)
               .build();

       when(usermapper.toDto(any(User.class))).thenReturn(userResponse);
       when(userrepository.save(any(User.class))).thenReturn(user);
       UserResponse userResponse1=userCommandService.addUser(userRequest);
       assertEquals(userResponse,userResponse1);




    }
    @Test
    public void testUpdatePatchUserReturnsOk(){
        Long id=1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        UserPatchRequest userPatchRequest=UserPatchRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        User userDB=User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse expectedResponse=UserResponse.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .boatsResponses(new ArrayList<>())
                .build();

        when(userrepository.findById(id)).thenReturn(Optional.of(userDB));
        when(usermapper.toDto(any(User.class))).thenReturn(expectedResponse);
        when(userrepository.save(any(User.class))).thenReturn(userDB);

        UserResponse userResponse=userCommandService.updatePatchUser(id,userPatchRequest);
        assertEquals(expectedResponse,userResponse);

    }
    @Test
    public void testDeleteUserReturnsOk(){
        Long id=1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        User userDB=User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        when(userrepository.existsById(id)).thenReturn(true);
        userCommandService.deleteUser(userDB.getId());

    }
    @Test
    public void testUpdateUserByEmailReturnsOk(){
        Long id=1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        User userDB=User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserRequest userRequest=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse expectedResponse = UserResponse.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .boatsResponses(new ArrayList<>())
                .build();


        when(userrepository.findByEmail(email)).thenReturn(Optional.of(userDB));
        when(userrepository.save(any(User.class))).thenReturn(userDB);
        when(usermapper.toDto(userDB)).thenReturn(expectedResponse);

       UserResponse userResponse= userCommandService.updateUser(userRequest);
       assertEquals(expectedResponse,userResponse);
    }
    @Test
    public void testAddUserThrowsInvalidAgeException(){
        Long id=1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=11;

        UserRequest userRequest=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();


        assertThrows(InvalidAgeException.class, ()->userCommandService.addUser(userRequest));
    }
    @Test
    public void testUpdateUserThrowsUserNotFoundException(){
        Long id=1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        UserRequest userRequest=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        when(userrepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundexception.class,()->userCommandService.updateUser(userRequest));

    }
    @Test
    public void testDeleteUserThrowsUserNotFoundException(){
        Long id=1L;
        assertThrows(UserNotFoundexception.class,()->userCommandService.deleteUser(id));
    }
    @Test
    public void testUpdatePatchUserThorwsExceptions(){
        Long id=1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        UserRequest userRequest=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        when(userrepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundexception.class,()->userCommandService.updateUser(userRequest));
    }
    @Test
    public void testAddUserThrowsUserAlreadyExistsException(){
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        UserRequest userRequest=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        when(userrepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userrepository.save(any(User.class))).thenThrow(UserAlreadyExistsException.class);
        assertThrows(UserAlreadyExistsException.class,()->userCommandService.addUser(userRequest));

    }



}
