package ro.mycode.boatsusers.boats.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.exceptions.BoatNotFoundException;
import ro.mycode.boats.model.Boat;
import ro.mycode.boats.repository.BoatRepository;
import ro.mycode.boats.service.BoatQueryServiceImpl;
import ro.mycode.boats.mapper.BoatMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BoatQueryServiceUnitTests {
    @Mock
    private BoatRepository boatRepository;
    @InjectMocks
    private BoatQueryServiceImpl boatQueryService;
    @Mock
    private BoatMapper boatMapper;

    @Test
    public void testGetAllBoatsReturnsOk() {
        Long id1 = 1L;
        Long id2 = 2L;
        String model1 = "A";
        String model2 = "B";
        String color = "Blue";
        Integer size = 10;

        Boat boat1 = Boat.builder().Id(id1).model(model1).color(color).size(size).build();
        Boat boat2 = Boat.builder().Id(id2).model(model2).color(color).size(size).build();
        List<Boat> boatList = List.of(boat1, boat2);

        BoatResponse res1 = BoatResponse.builder().id(id1).model(model1).color(color).size(size).build();
        BoatResponse res2 = BoatResponse.builder().id(id2).model(model2).color(color).size(size).build();
        List<BoatResponse> expectedResponses = List.of(res1, res2);

        when(boatRepository.findAll()).thenReturn(boatList);
        when(boatMapper.toDto(boat1)).thenReturn(res1);
        when(boatMapper.toDto(boat2)).thenReturn(res2);

        List<BoatResponse> actualResponses = boatQueryService.getAllBoats();

        assertEquals(expectedResponses.size(), actualResponses.size());
        assertEquals(expectedResponses.get(0).getModel(), actualResponses.get(0).getModel());
    }
    @Test
    public void testGetBoatByIdReturnsOk() {
        Long boatId = 1L;
        String model = "A";
        String color = "Blue";
        Integer size = 10;

        Boat boatDB = Boat.builder()
                .Id(boatId)
                .model(model)
                .color(color)
                .size(size)
                .build();

        BoatResponse expectedResponse = BoatResponse.builder()
                .id(boatId)
                .model(model)
                .color(color)
                .size(size)
                .build();


        when(boatRepository.findById(boatId)).thenReturn(Optional.of(boatDB));
        when(boatMapper.toDto(boatDB)).thenReturn(expectedResponse);
        BoatResponse actualResponse = boatQueryService.getBoatById(boatId);

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getModel(), actualResponse.getModel());
    }
    @Test
    public void getBoatByModelReturnsOk() {
        Long boatId = 1L;
        String model = "A";
        String color = "Blue";
        Integer size = 10;

        Boat boatDB = Boat.builder()
                .Id(boatId)
                .model(model)
                .color(color)
                .size(size)
                .build();

        BoatResponse expectedResponse = BoatResponse.builder()
                .id(boatId)
                .model(model)
                .color(color)
                .size(size)
                .build();

        when(boatRepository.findByModel(model)).thenReturn(Optional.of(boatDB));
        when(boatMapper.toDto(boatDB)).thenReturn(expectedResponse);
        BoatResponse boatResponse = boatQueryService.getBoatByModel(boatDB.getModel());

        assertEquals(expectedResponse.getModel(), boatResponse.getModel());

    }
    @Test
    public void testGetBoatByModelThrowsBoatNotFound(){
        String model = "A";
        String color = "Blue";
        Integer size = 10;

        BoatRequest boatRequest=BoatRequest.builder()
                .model(model)
                .color(color)
                .size(size)
                .build();

        when(boatRepository.findByModel(boatRequest.getModel())).thenReturn(Optional.empty());
        assertThrows(BoatNotFoundException.class, () -> boatQueryService.getBoatByModel(boatRequest.getModel()));

    }
    @Test
    public void testGetBoatByIdThrowsBoatNotFound(){
        Long boatId = 1L;
        String model = "A";
        String color = "Blue";
        Integer size = 10;

        BoatRequest boatRequest=BoatRequest.builder()
                .model(model)
                .color(color)
                .size(size)
                .build();

        when(boatRepository.findById(boatId)).thenReturn(Optional.empty());
        assertThrows(BoatNotFoundException.class, () -> boatQueryService.getBoatById(boatId));


    }
}
