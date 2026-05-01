package ro.mycode.boatsusers.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.exceptions.BoatAlreadyExistsException;
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

        Boat boat=Boat.builder().model(model).color(color).size(size).build();
        BoatRequest.builder().model(model).color(color).size(size).build();
        when(boatRepository.findByModel(model)).thenReturn(Optional.of(boat));
        boatCommandService.deleteBoat(boatId);


    }
}
