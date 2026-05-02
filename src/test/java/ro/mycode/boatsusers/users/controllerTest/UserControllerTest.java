package ro.mycode.boatsusers.users.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.mycode.users.controller.UserController;
import ro.mycode.users.dtos.UserPatchRequest;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.service.UserCommandServiceImpl;
import ro.mycode.users.service.UserQueryServiceImpl;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserCommandServiceImpl  userCommandService;
    @MockitoBean
    UserQueryServiceImpl userQueryService;


    @Test
    public void testaddUserReturns201() throws Exception {
       String firstName = "John";
       String lastName = "Doe";
       String email="JD@gmail.com";
       Integer age = 25;
        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName).lastName(lastName).email(email).age(age).build();
        UserResponse userResponse = UserResponse.builder()
                .firstName(firstName).lastName(lastName).email(email).age(age).build();

        when(userCommandService.addUser(any(UserRequest.class))).thenReturn(userResponse);
        mockMvc.perform(post("/api/v2/users/add")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.age").value(age));




    }
    @Test
    public void testDeleteUserReturns200() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/api/v2/users/delete/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdatePatchReturns200() throws Exception {
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age = 25;

        UserPatchRequest userPatchRequest   =UserPatchRequest.builder()
                .firstName(firstName).lastName(lastName).email(email).age(age).build();

        UserResponse userResponse=UserResponse.builder()
                .firstName(firstName).lastName(lastName).email(email).age(age).build();

        when(userCommandService.updatePatchUser(any(Long.class), any(UserPatchRequest.class))).thenReturn(userResponse);

        mockMvc.perform(patch("/api/v2/users/patch/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPatchRequest)))
                .andExpect(status().isOk());


    }
    @Test
    public void testUpdatePutUserReturns200() throws Exception {
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age = 25;

        UserPatchRequest userPatchRequest   =UserPatchRequest.builder()
                .firstName(firstName).lastName(lastName).email(email).age(age).build();

        UserResponse userResponse=UserResponse.builder()
                .firstName(firstName).lastName(lastName).email(email).age(age).build();

        when(userCommandService.updatePatchUser(any(Long.class), any(UserPatchRequest.class))).thenReturn(userResponse);

        mockMvc.perform(put("/api/v2/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPatchRequest)))
                .andExpect(status().isOk());


    }
    @Test
    public void testFindByIdReturns200() throws Exception {
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age = 25;
        UserResponse userResponse = UserResponse.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        when(userQueryService.getUserById(userId)).thenReturn(userResponse);


        mockMvc.perform(get("/api/v2/users/{id}", userId)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }
    @Test
    public void testFindByFirstNameReturns200() throws Exception {
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age = 25;

        UserResponse userResponse = UserResponse.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        when(userQueryService.getUsersByFirstName(firstName)).thenReturn(userResponse);
        mockMvc.perform(get("/api/v2/users/firstname/{firstname}", firstName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
    @Test
    public void testFindByEmailReturns200() throws Exception {
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age = 25;

        UserResponse userResponse = UserResponse.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        when(userQueryService.getUserByEmail(email)).thenReturn(userResponse);
        mockMvc.perform(get("/api/v2/users/email/{email}",email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    public void testGetAllUsersReturns200() throws Exception {
        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age = 25;

        UserResponse userResponse1 = UserResponse.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse userResponse2=UserResponse.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

       when(userQueryService.getAllUsers()).thenReturn(List.of(userResponse1,userResponse2));
       mockMvc.perform(get("/api/v2/users/all")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(List.of(userResponse1,userResponse2))))
               .andExpect(status().isOk());

    }

}
