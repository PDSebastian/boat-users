package ro.mycode.boatsusers.users.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.mycode.users.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ControllerUnitTests {

    UserRepository userRepository;
    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }
    @Test
    void addUserReturnsCreatedUser() throws Exception {


        mockMvc.perform(post("/api/v2/users/add")
                .contentType(MediaType.APPLICATION_JSON).content(
                        """
                            
                             {
                              "firstName": "Pop",
                              "lastName": "Robert",
                               "email": "pop.Rober@gmail.com",
                               "age": 25
                             }
                                         
                            """
                        )).andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Pop"))
                .andExpect(jsonPath("$.lastName").value("Robert"));






    }
}
