package ro.mycode.boatsusers.boats.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.mycode.boats.controller.BoatController;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.service.BoatCommandService;
import ro.mycode.boats.service.BoatQueryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BoatController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BoatControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BoatCommandService boatCommandService;

    @MockitoBean
    private BoatQueryService boatQueryService;

    @Test
    public void testAddBoatReturnsCreated() throws Exception {
        BoatRequest boatRequest = BoatRequest.builder()
                .model("Model X")
                .color("Red")
                .size(10)
                .userId(1L)
                .build();

        BoatResponse boatResponse = BoatResponse.builder()
                .id(100L)
                .model("Model X")
                .color("Red")
                .size(10)
                .build();

        when(boatCommandService.addBoat(any(BoatRequest.class))).thenReturn(boatResponse);

        mockMvc.perform(post("/api/v2/boats/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boatRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.model").value("Model X"))
                .andExpect(jsonPath("$.color").value("Red"));
    }
}