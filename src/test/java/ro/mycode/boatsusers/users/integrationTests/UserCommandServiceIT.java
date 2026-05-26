package ro.mycode.boatsusers.users.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ro.mycode.users.dtos.UserPatchRequest;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.repository.UserRepository;
import ro.mycode.users.service.UserCommandServiceImpl;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("tests")
public class UserCommandServiceIT {
    @Autowired
    UserRepository userrepository;
    @Autowired
    UserCommandServiceImpl userCommandService;

    @BeforeEach
    public void setup() {
        userrepository.deleteAll();
    }
    @Test
    public void addUserReturns200(){
       String firstName = "John";
       String lastName = "Doe";
       String email="JD@gmail.com";
       Integer age=19;

       UserRequest userRequest = UserRequest.builder()
               .firstName(firstName)
               .lastName(lastName)
               .email(email)
               .age(age)
               .build();

        UserResponse userResponse = userCommandService.addUser(userRequest);

       UserResponse expectedResponse=UserResponse.builder()
               .id(userResponse.id())
               .firstName(firstName)
               .lastName(lastName)
               .email(email)
               .boatsResponses(new ArrayList<>())
               .age(age)
               .build();

       assertEquals(expectedResponse,userResponse);



    }
    @Test
    public void deleteUserReturns200(){
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

     UserResponse userResponse= userCommandService.addUser(userRequest);
     UserResponse expectedResponse=UserResponse.builder()
             .id(userResponse.id())
             .firstName(firstName)
             .lastName(lastName)
             .email(email)
             .age(age)
             .boatsResponses(new ArrayList<>())
             .build();

     userCommandService.deleteUser(userResponse.id());

     assertEquals(expectedResponse,userResponse);

    }
    @Test
    public void updateUserReturns200(){
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        userCommandService.addUser(userRequest);

        UserResponse userResponse = userCommandService.updateUser(userRequest);
        UserResponse expectedResponse=UserResponse.builder()
                .id(userResponse.id())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .boatsResponses(new ArrayList<>())
                .build();

        assertEquals(expectedResponse,userResponse);





    }
    @Test
    public void updatePatchUserReturns200(){
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        String newEmail="aa@gmail.com";
        Integer age=19;

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();


      UserResponse userResponse1=  userCommandService.addUser(userRequest);
        UserPatchRequest userPatchRequest=UserPatchRequest.builder()
                .email(newEmail)
                .build();

        UserResponse userResponse = userCommandService.updatePatchUser(userResponse1.id(), userPatchRequest);
        assertEquals(newEmail, userResponse.email());


    }





}
