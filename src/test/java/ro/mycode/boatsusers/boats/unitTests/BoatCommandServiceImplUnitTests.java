package ro.mycode.boatsusers.boats.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.boats.dtos.BoatPatchRequest;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.exceptions.BoatAlreadyExistsException;
import ro.mycode.boats.exceptions.BoatNotFoundException;
import ro.mycode.boats.mapper.BoatMapper;
import ro.mycode.boats.model.Boat;
import ro.mycode.boats.repository.BoatRepository;
import ro.mycode.boats.service.BoatCommandServiceImpl;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.Userrepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BoatCommandServiceImplUnitTests {
    @Mock
    BoatRepository boatRepository;

    @Mock
    Userrepository userrepository;

    @Mock
    BoatMapper boatMapper;

    @InjectMocks
    BoatCommandServiceImpl boatCommandService;

    @Test
    public void testCreatBoatRetunsOk() {
        Long boatId = 1L;
        Long userId = 2L;
        String model="a";
        String color="b";
        Integer size=10;

        User user=User.builder().id(userId).firstName("a").lastName("b").build();

        BoatRequest request = BoatRequest.builder()
                .model(model)
                .color(color)
                .userId(userId)
                .size(size)
                .build();

        Boat savedBoat = Boat.builder()
                .Id(boatId)
                .model(model)
                .color(color)
                .size(size)
                .user(user)
                .build();

        BoatResponse expectedResponse = BoatResponse.builder()
                .id(boatId)
                .model(model)
                .color(color)
                .size(size)
                .build();

        when(userrepository.findById(userId)).thenReturn(Optional.of(user));
        when(boatRepository.findByModel(request.getModel())).thenReturn(Optional.empty());
        when(boatMapper.toDto(any(Boat.class))).thenReturn(expectedResponse);
        when(boatRepository.save(any(Boat.class))).thenReturn(savedBoat);

        BoatResponse boatResponse = boatCommandService.addBoat(request);
        assertEquals(expectedResponse, boatResponse);


    }
    @Test
    public void testCreatBoatThrowsBoatAlreadyExistsException() {
        Long boatId = 1L;
        Long userId = 2L;
        String model="a";
        String color="b";
        Integer size=10;
        User user=User.builder().id(userId).firstName("a").lastName("b").build();

        Boat boat=Boat.builder()
                .model(model)
                .color(color)
                .size(size).user(user).build();

        BoatRequest boatRequest=BoatRequest.builder()
                .userId(userId)
                .model(model)
                .color(color)
                .size(size)
                .build();
        when(userrepository.findById(userId)).thenReturn(Optional.of(user));
        when(boatRepository.findByModel(model)).thenReturn(Optional.of(boat));

        assertThrows(BoatAlreadyExistsException.class, () -> boatCommandService.addBoat(boatRequest));


    }
    @Test
    public void testDeleteBoatRetunsOk() {
        Long boatId = 1L;
        String model="a";
        String color="b";
        Integer size=10;

        Boat boat=Boat.builder().Id(boatId).model(model).color(color).size(size).build();

        when(boatRepository.findById(boatId)).thenReturn(Optional.of(boat));
        boatCommandService.deleteBoat(boatId);



    }
    @Test
    public void testDeleteBoatThrowsBoatNotFoundException() {
        Long boatId = 1L;
        String model="a";
        String color="b";
        Integer size=10;

        Boat boat=Boat.builder().Id(boatId).model(model).color(color).size(size).build();

        when(boatRepository.findById(boatId)).thenReturn(Optional.empty());

        assertThrows(BoatNotFoundException.class,() -> boatCommandService.deleteBoat(boatId));

    }
    @Test
    public void testUpdateBoatReturnsOk(){
        Long boatId = 1L;
        String model="a";
        String color="b";
        Integer size=10;

     BoatRequest boatRequest=BoatRequest.builder()
             .model(model)
             .color(color)
             .size(size)
             .build();

     Boat boatFormDB=Boat.builder()
             .Id(boatId)
             .model(model)
             .color(color)
             .size(size)
             .build();

     BoatResponse boatResponse=BoatResponse.builder()
             .id(boatId)
             .model(model)
             .color(color)
             .size(size)
             .build();

     when(boatRepository.findById(boatId)).thenReturn(Optional.of(boatFormDB));
     when(boatMapper.toDto(boatFormDB)).thenReturn(boatResponse);
     when(boatRepository.save(boatFormDB)).thenReturn(boatFormDB);

     BoatResponse boatResponse1 = boatCommandService.updateBoat(boatId, boatRequest);
     assertEquals(boatResponse,boatResponse1);


    }
    @Test
    public void testUpdateBoatThrowsBoatNotFoundException() {
        Long boatId = 1L;
        String model="a";
        String color="b";
        Integer size=10;

        BoatRequest boatRequest=BoatRequest.builder()
                .model(model)
                .color(color)
                .size(size)
                .build();

        when(boatRepository.findById(boatId)).thenReturn(Optional.empty());
        assertThrows(BoatNotFoundException.class,() -> boatCommandService.updateBoat(boatId, boatRequest));


    }
    @Test
    public void testUpdatePatchBoatReturnsOk() {
        Long boatId = 1L;
        String newModel = "Model Nou";

        String model="a";
        String color="b";
        Integer size=10;

        BoatPatchRequest patchRequest = BoatPatchRequest.builder().model(newModel).build();

        Boat boatDB = Boat.builder()
                .Id(boatId)
                .model(model)
                .color(color)
                .size(10)
                .build();

        BoatResponse expectedResponse = BoatResponse.builder()
                .id(boatId)
                .model(newModel)
                .color(color)
                .size(size)
                .build();

        when(boatRepository.findById(boatId)).thenReturn(Optional.of(boatDB));
        when(boatRepository.save(any(Boat.class))).thenReturn(boatDB);
        when(boatMapper.toDto(any(Boat.class))).thenReturn(expectedResponse);

        BoatResponse result = boatCommandService.updatePatchBoat(boatId, patchRequest);
        assertEquals(expectedResponse, result);
        assertEquals(newModel, result.getModel());
    }
    @Test
    public void testUpdatePatchBoatThrowsBoatNotFoundException() {
        Long boatId = 1L;
        String model="a";
        String color="b";
        Integer size=10;

        BoatRequest boatRequest=BoatRequest.builder()
                .model(model)
                .color(color)
                .size(size)
                .build();

        when(boatRepository.findById(boatId)).thenReturn(Optional.empty());
        assertThrows(BoatNotFoundException.class,() -> boatCommandService.updateBoat(boatId, boatRequest));

    }




}
