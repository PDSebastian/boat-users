package ro.mycode.boatsusers.boats.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.mycode.boats.controller.BoatController;
import ro.mycode.boats.dtos.BoatPatchRequest;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.service.BoatCommandService;
import ro.mycode.boats.service.BoatQueryService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BoatController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BoatControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BoatCommandService boatCommandService;

    @MockitoBean
    private BoatQueryService boatQueryService;

    @Test
    public void testAddBoatReturns201() throws Exception {
        BoatRequest boatRequest = BoatRequest.builder()
                .model("Speedboat X")
                .color("Red")
                .size(12)
                .userId(1L)
                .build();

        BoatResponse boatResponse = BoatResponse.builder()
                .id(100L)
                .model("Speedboat X")
                .color("Red")
                .size(12)
                .build();

        when(boatCommandService.addBoat(any(BoatRequest.class))).thenReturn(boatResponse);

        mockMvc.perform(post("/api/v2/boats/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boatRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.model").value("Speedboat X"));
    }

    @Test
    public void testUpdatePatchBoatReturns200() throws Exception {
        Long boatId = 10L;

        BoatPatchRequest boatPatchRequest = BoatPatchRequest.builder()
                .model("model")
                .color("Black")
                .build();

        BoatResponse boatResponse = BoatResponse.builder()
                .id(boatId)
                .model("model")
                .color("Black")
                .size(10)
                .build();

        when(boatCommandService.updatePatchBoat(eq(boatId), any(BoatPatchRequest.class))).thenReturn(boatResponse);

        mockMvc.perform(patch("/api/v2/boats/patch/{id}", boatId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boatPatchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(boatId))
                .andExpect(jsonPath("$.model").value("model"))
                .andExpect(jsonPath("$.color").value("Black"));
    }

    @Test
    public void testGetAllBoatsReturnsOk() throws Exception {
        BoatResponse boat1 = BoatResponse.builder()
                .id(1L)
                .model("Model1")
                .color("Blue")
                .size(10)
                .build();

        BoatResponse boat2 = BoatResponse.builder()
                .id(2L)
                .model("Model2")
                .color("White")
                .size(15)
                .build();

        List<BoatResponse> boatList = List.of(boat1, boat2);
        when(boatQueryService.getAllBoats()).thenReturn(boatList);

        mockMvc.perform(get("/api/v2/boats/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].model").value("Model1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].model").value("Model2"));
    }

    @Test
    public void testGetBoatByModelReturns200() throws Exception {
        String model = "acc";
        Long boatId = 10L;

        BoatResponse boatResponse = BoatResponse.builder()
                .id(boatId)
                .model(model)
                .color("bcsc")
                .size(10)
                .build();

        when(boatQueryService.getBoatByModel(model)).thenReturn(boatResponse);

        mockMvc.perform(get("/api/v2/boats/model/{model}", model)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(boatId))
                .andExpect(jsonPath("$.model").value(model));
    }

    @Test
    public void testGetBoatByIdReturns200() throws Exception {
        Long id = 10L;

        BoatResponse boatResponse = BoatResponse.builder()
                .id(id)
                .model("acc")
                .color("bcsc")
                .size(10)
                .build();

        when(boatQueryService.getBoatById(id)).thenReturn(boatResponse);

        mockMvc.perform(get("/api/v2/boats/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.model").value("acc"));
    }
}

