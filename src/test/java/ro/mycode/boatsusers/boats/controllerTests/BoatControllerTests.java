package ro.mycode.boatsusers.boats.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.mycode.boats.controller.BoatController;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.model.Boat;
import ro.mycode.boats.repository.BoatRepository;
import ro.mycode.boats.service.BoatCommandService;
import ro.mycode.boats.service.BoatQueryService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers=BoatController.class)
public class BoatControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private BoatCommandService boatCommandService;
    @MockitoBean
    private BoatQueryService boatQueryService;


    @Test
    public void testAddBoatReturns201() throws Exception {
        String model="acc";
        String color="bcsc";
        Integer size=10;
        Long userId=10L;
        Long boatId=10L;

        BoatRequest bRequest=BoatRequest.builder()
                .model(model)
                .color(color)
                .size(size)
                .userId(userId)
                .build();

        BoatResponse boatResponse=BoatResponse.builder()
                .id(boatId)
                .model(model)
                .color(color)
                .size(size)
                .build();

        when(boatCommandService.addBoat(any(BoatRequest.class))).thenReturn(boatResponse);
        mockMvc.perform(post("/api/v2/boats/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(boatId))
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.size").value(size));

    }
    @Test
    public void testUpdatePatchBoatReturns200() throws Exception {
        String model="acc";
        String color="bcsc";
        Integer size=10;
        Long userId=10L;
        Long boatId=10L;

        BoatRequest boatRequest=BoatRequest.builder()
                .model(model)
                .color(color)
                .size(size)
                .userId(userId)
                .build();

        BoatResponse boatResponse=BoatResponse.builder()
                .id(boatId)
                .model(model)
                .color(color)
                .size(size)
                .build();

        when(boatCommandService.updatePatchBoat(eq(boatId),any())).thenReturn(boatResponse);
        mockMvc.perform(patch("/api/v2/boats/patch/{id}",boatId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boatRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(boatId))
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.size").value(size));


    }
    @Test
    public void testGetAllBoatsReturnsOk() throws Exception {
        String model="acc";
        String color="bcsc";
        Integer size=10;
        Long userId=10L;
        Long boatId=10L;

        BoatResponse boat=BoatResponse.builder()
                .model(model)
                .color(color)
                .size(size)
                .build();

        BoatResponse boat1=BoatResponse.builder()
                .model(model)
                .color(color)
                .size(size)
                .build();

        List<BoatResponse> boatList=List.of(boat,boat1);
        when(boatQueryService.getAllBoats()).thenReturn(boatList);
        mockMvc.perform(get("/api/v2/boats/all")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(List.of(boat,boat1)))).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(boat.getId()))
                .andExpect(jsonPath("$[0].model").value(model))
                .andExpect(jsonPath("$[0].color").value(color))
                .andExpect(jsonPath("$[0].size").value(size));


    }
    @Test
    public void testGetBoatByModelReturns200() throws Exception {
        String model = "acc";
        String color = "bcsc";
        Integer size = 10;
        Long boatId = 10L;

        BoatResponse boatResponse = BoatResponse.builder()
                .id(boatId)
                .model(model)
                .color(color)
                .size(size)
                .build();


        when(boatQueryService.getBoatByModel(model)).thenReturn(boatResponse);

        mockMvc.perform(get("/api/v2/boats/model/{model}",model)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(boatId))
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.size").value(size));
    }
    @Test
    public void testGetBoatByIdReturns200() throws Exception {
        String model = "acc";
        String color = "bcsc";
        Integer size = 10;
        Long id = 10L;

        BoatResponse boatResponse=BoatResponse.builder()
                .id(id)
                .model(model)
                .color(color)
                .size(size)
                .build();

        when(boatQueryService.getBoatById(id)).thenReturn(boatResponse);
        mockMvc.perform(get("/api/v2/boats/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.model").value(model))
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.size").value(size));
    }
    @Test
    public void testDeleteBoatReturns200() throws Exception {
        Long id=10L;
        mockMvc.perform(delete("/api/v2/boats/delete/{id}",id)).andExpect(status().isOk());


    }





}
