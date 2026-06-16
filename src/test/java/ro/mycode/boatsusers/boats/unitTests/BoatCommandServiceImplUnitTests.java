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
import ro.mycode.users.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoatCommandServiceImplUnitTests {

    @Mock
    private BoatRepository boatRepository;

    @Mock
    private UserRepository userrepository;

    @Mock
    private BoatMapper boatMapper;

    @InjectMocks
    private BoatCommandServiceImpl boatCommandService;

    @Test
    void testCreateBoatReturnsOk() {
        User user = User.builder().id(2L).firstName("Pop").lastName("Sebastian").build();

        BoatRequest request = BoatRequest.builder()
                .model("Model X")
                .color("Red")
                .userId(2L)
                .size(10)
                .build();

        Boat savedBoat = Boat.builder()
                .Id(1L)
                .model("Model X")
                .color("Red")
                .size(10)
                .user(user)
                .build();

        BoatResponse expectedResponse = BoatResponse.builder()
                .id(1L)
                .model("Model X")
                .color("Red")
                .size(10)
                .build();

        when(userrepository.findById(2L)).thenReturn(Optional.of(user));
        when(boatRepository.findByModel("Model X")).thenReturn(Optional.empty());
        when(boatRepository.save(any(Boat.class))).thenReturn(savedBoat);
        when(boatMapper.toDto(any(Boat.class))).thenReturn(expectedResponse);

        BoatResponse actualResponse = boatCommandService.addBoat(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testCreateBoatThrowsBoatAlreadyExistsException() {
        User user = User.builder().id(2L).build();
        Boat existingBoat = Boat.builder().model("Model X").build();

        BoatRequest boatRequest = BoatRequest.builder()
                .userId(2L)
                .model("Model X")
                .build();

        when(userrepository.findById(2L)).thenReturn(Optional.of(user));
        when(boatRepository.findByModel("Model X")).thenReturn(Optional.of(existingBoat));

        assertThrows(BoatAlreadyExistsException.class, () -> boatCommandService.addBoat(boatRequest));
    }

    @Test
    void testDeleteBoatReturnsOk() {
        Boat boat = Boat.builder().Id(1L).model("To Delete").color("Black").build();
        BoatResponse expectedResponse = BoatResponse.builder().id(1L).model("To Delete").build();

        when(boatRepository.findById(1L)).thenReturn(Optional.of(boat));
        when(boatMapper.toDto(boat)).thenReturn(expectedResponse);

        BoatResponse actualResponse = boatCommandService.deleteBoat(1L);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testDeleteBoatThrowsBoatNotFoundException() {
        when(boatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoatNotFoundException.class, () -> boatCommandService.deleteBoat(1L));
    }

    @Test
    void testUpdateBoatReturnsOk() {
        BoatRequest boatRequest = BoatRequest.builder().model("New Model").color("Green").size(12).build();
        Boat boatFromDB = Boat.builder().Id(1L).model("Old Model").color("Blue").size(10).build();
        BoatResponse expectedResponse = BoatResponse.builder().id(1L).model("New Model").color("Green").size(12).build();

        when(boatRepository.findById(1L)).thenReturn(Optional.of(boatFromDB));
        when(boatRepository.save(boatFromDB)).thenReturn(boatFromDB);
        when(boatMapper.toDto(boatFromDB)).thenReturn(expectedResponse);

        BoatResponse actualResponse = boatCommandService.updateBoat(1L, boatRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testUpdateBoatThrowsBoatNotFoundException() {
        BoatRequest boatRequest = BoatRequest.builder().model("Model").build();

        when(boatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoatNotFoundException.class, () -> boatCommandService.updateBoat(1L, boatRequest));
    }

    @Test
    void testUpdatePatchBoatReturnsOk() {
        BoatPatchRequest patchRequest = BoatPatchRequest.builder().model("model").build();
        Boat boatDB = Boat.builder().Id(1L).model("Old Model").color("White").size(10).build();
        BoatResponse expectedResponse = BoatResponse.builder().id(1L).model("model").color("White").size(10).build();

        when(boatRepository.findById(1L)).thenReturn(Optional.of(boatDB));
        when(boatRepository.save(any(Boat.class))).thenReturn(boatDB);
        when(boatMapper.toDto(any(Boat.class))).thenReturn(expectedResponse);

        BoatResponse result = boatCommandService.updatePatchBoat(1L, patchRequest);

        assertEquals(expectedResponse, result);
        assertEquals("model", result.getModel());
    }

    @Test
    void testUpdatePatchBoatThrowsBoatNotFoundException() {
        BoatPatchRequest patchRequest = BoatPatchRequest.builder().model("Patched Model").build();

        when(boatRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoatNotFoundException.class, () -> boatCommandService.updatePatchBoat(1L, patchRequest));
    }
}
