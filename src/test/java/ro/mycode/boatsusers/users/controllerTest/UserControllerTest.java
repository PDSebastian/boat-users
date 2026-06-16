package ro.mycode.boatsusers.users.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserCommandServiceImpl userCommandService;

    @MockitoBean
    private UserQueryServiceImpl userQueryService;

    @Test
    public void testAddUserReturns201() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .firstName("John").lastName("Doe").email("JD@gmail.com").age(25).build();
        UserResponse userResponse = UserResponse.builder()
                .id(1L).firstName("John").lastName("Doe").email("JD@gmail.com").age(25).build();

        when(userCommandService.addUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/v2/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testDeleteUserReturns200() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/api/v2/users/delete/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testUpdatePatchReturns200() throws Exception {
//        Long userId = 1L;
//        UserPatchRequest userPatchRequest = UserPatchRequest.builder().firstName("John").build();
//        UserResponse userResponse = UserResponse.builder().id(userId).firstName("John").lastName("Doe").build();
//
//        when(userCommandService.updatePatchUser(eq(userId), any(UserPatchRequest.class))).thenReturn(userResponse);
//
//        mockMvc.perform(patch("/api/v2/users/patch/{id}", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userPatchRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("John"));
//    }

//    @Test
//    public void testUpdatePutUserReturns200() throws Exception {
//        Long userId = 1L;
//        UserRequest userRequest = UserRequest.builder()
//                .firstName("John Updated").lastName("Doe").email("JD@gmail.com").age(26).build();
//        UserResponse userResponse = UserResponse.builder()
//                .id(userId).firstName("John Updated").lastName("Doe").email("JD@gmail.com").age(26).build();
//
//        // FIX: Schimbat din updatePatchUser în updateUser (sau cum se numește metoda ta de PUT din service)
//        when(userCommandService.updateUser(eq(userId), any(UserRequest.class))).thenReturn(userResponse);
//
//        mockMvc.perform(put("/api/v2/users/{id}", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("John Updated"));
//    }

    @Test
    public void testFindByIdReturns200() throws Exception {
        Long userId = 1L;
        UserResponse userResponse = UserResponse.builder().id(userId).firstName("John").email("JD@gmail.com").build();

        when(userQueryService.getUserById(userId)).thenReturn(userResponse);

        mockMvc.perform(get("/api/v2/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testFindByFirstNameReturns200() throws Exception {
        String firstName = "John";
        UserResponse userResponse = UserResponse.builder().id(1L).firstName(firstName).build();

        when(userQueryService.getUsersByFirstName(firstName)).thenReturn(userResponse);

        mockMvc.perform(get("/api/v2/users/firstname/{firstname}", firstName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(firstName));
    }

    @Test
    public void testFindByEmailReturns200() throws Exception {
        String email = "JD@gmail.com";
        UserResponse userResponse = UserResponse.builder().id(1L).email(email).build();

        when(userQueryService.getUserByEmail(email)).thenReturn(userResponse);

        mockMvc.perform(get("/api/v2/users/email/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    public void testGetAllUsersReturns200() throws Exception {
        UserResponse user1 = UserResponse.builder().id(1L).firstName("John").build();
        UserResponse user2 = UserResponse.builder().id(2L).firstName("Mara").build();

        when(userQueryService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/api/v2/users/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }
}