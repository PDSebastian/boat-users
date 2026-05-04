package ro.mycode.boatsusers.users.integrationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.repository.Userrepository;
import ro.mycode.users.service.UserCommandService;
import ro.mycode.users.service.UserQueryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("tests")
@Transactional
public class UserQueryServcieIT {
    @Autowired
    private Userrepository userrepository;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private UserCommandService userCommandService;

    @BeforeEach
    public void setup() {
        userrepository.deleteAll();
    }
    @Test
    public void testGetAllUsersReturns200(){
        String firstName = "John";
        String lastName = "Doe";
        String email1="JD@gmail.com";
        String email2="aa@gmail.com";
        Integer age=19;

        UserRequest userRequest1=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email1)
                .age(age)
                .build();

        UserRequest userRequest2=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email2)
                .age(age)
                .build();

        userCommandService.addUser(userRequest1);
        userCommandService.addUser(userRequest2);
        List<UserResponse> users=userQueryService.getAllUsers();
        assertEquals(2,users.size());



    }
    @Test
    public void testGetUserByIdReturns200(){
        String firstName = "John";
        String lastName = "Doe";
        String email1="JD@gmail.com";
        Integer age=19;

        UserRequest userRequest1=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email1)
                .age(age)
                .build();

        UserResponse userResponse=userCommandService.addUser(userRequest1);
        UserResponse u=userQueryService.getUserById(userResponse.id());
        assertEquals(userResponse,u);

    }
    @Test
    public void testGetUserByEmailReturns200(){
        String firstName = "John";
        String lastName = "Doe";
        String email1="JD@gmail.com";
        Integer age=19;

        UserRequest userRequest1=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email1)
                .age(age)
                .build();

        UserResponse userResponse=userCommandService.addUser(userRequest1);
        UserResponse u=userQueryService.getUserByEmail(userResponse.email());
        assertEquals(userResponse,u);
    }
    @Test
    public void testGetUSerByFirstNameReturns200(){
        String firstName = "John";
        String lastName = "Doe";
        String email1="JD@gmail.com";
        Integer age=19;

        UserRequest userRequest1=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email1)
                .age(age)
                .build();

        UserResponse userResponse=userCommandService.addUser(userRequest1);
        UserResponse u=userQueryService.getUsersByFirstName(userResponse.firstName());
        assertEquals(userResponse,u);

    }




}
