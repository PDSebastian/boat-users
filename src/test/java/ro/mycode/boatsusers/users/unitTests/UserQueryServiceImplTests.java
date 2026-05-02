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
import ro.mycode.users.repository.Userrepository;
import ro.mycode.users.service.UserQueryServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserQueryServiceImplTests {
    @Mock
    Userrepository  userrepository;
    @Mock
    UserMapper usermapper;
    @InjectMocks
    UserQueryServiceImpl userQueryService;

    @Test
    public void getAllUsersTest(){
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        UserResponse user =UserResponse.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse user1=UserResponse.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        List<UserResponse> userList = List.of(user,user1);

        User user2=User.builder().firstName(firstName).lastName(lastName).email(email).age(age).build();
        User user3=User.builder().firstName(firstName).lastName(lastName).email(email).age(age).build();

        List<User> users = List.of(user2,user3);

        when(userrepository.findAll()).thenReturn(users);
        when(usermapper.toDto(user2)).thenReturn(user);
        when(usermapper.toDto(user3)).thenReturn(user1);

        List<UserResponse> res = userQueryService.getAllUsers();
        assertEquals(users.size(),res.size());
        assertEquals(user.firstName(),res.get(1).firstName());



    }
    @Test
    public void testGetUserByIdReturnsOk(){
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        User user=User.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse expectedRespnose=UserResponse.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        when(userrepository.findById(userId)).thenReturn(Optional.of(user));
        when(usermapper.toDto(user)).thenReturn(expectedRespnose);
        UserResponse res = userQueryService.getUserById(userId);
        assertEquals(expectedRespnose,res);



    }
    @Test
    public void testGetUserByFirstNameReturnsOk(){
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        User user=User.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse expectedRespnose=UserResponse.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        when(userrepository.findByFirstName(firstName)).thenReturn(Optional.of(user));
        when(usermapper.toDto(user)).thenReturn(expectedRespnose);
        UserResponse res = userQueryService.getUsersByFirstName(firstName);
        assertEquals(expectedRespnose,res);





    }
    @Test
    public void testGetUserByEmailReturnsOk(){
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        User user=User.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse expectedRespnose=UserResponse.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        when(userrepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(usermapper.toDto(user)).thenReturn(expectedRespnose);
        UserResponse res = userQueryService.getUserByEmail(email);
        assertEquals(expectedRespnose,res);

    }
    @Test
    public void testGetUserByEmailThrowsUserNotFoundException(){
        String email="JD@gmail.com";

        when(userrepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundexception.class,()->userQueryService.getUserByEmail(email));

    }
    @Test
    public void testGetUserByFirstNameThrowsUserNotFoundException(){
        String firstName = "John";
        when(userrepository.findByFirstName(firstName)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundexception.class,()->userQueryService.getUsersByFirstName(firstName));
    }

}
