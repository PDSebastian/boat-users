package ro.mycode.boatsusers.boats.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.repository.BoatRepository;
import ro.mycode.boats.service.BoatCommandService;
import ro.mycode.boats.service.BoatQueryService;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.repository.UserRepository;
import ro.mycode.users.service.UserCommandService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("tests")
@Transactional
public class BoatQueryServiceIT {
    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private BoatCommandService boatCommandService;

    @Autowired
    private BoatQueryService boatQueryService;
    @Autowired
    private UserRepository userrepository;
    @Autowired
    private UserCommandService  userCommandService;

    @BeforeEach
    public void setup() {
        boatRepository.deleteAll();
    }
    @Test
    public void testGetAllBoats() {
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        String boatModel="Yamaha";
        String boatColor="Blue";
        Integer size=10;

        String model="Honda";
        String color="Red";
        Integer boatSize=100;

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse userResponse = userCommandService.addUser(userRequest);

        BoatRequest b1 = BoatRequest.builder()
                .model(boatModel)
                .color(boatColor)
                .size(size)
                .userId(userResponse.id())
                .build();

        BoatRequest b2 = BoatRequest.builder()
                .model(model)
                .color(color)
                .size(boatSize)
                .userId(userResponse.id())
                .build();

        boatCommandService.addBoat(b1);
        boatCommandService.addBoat(b2);

        List<BoatResponse> boatResponse = boatQueryService.getAllBoats();
        assertEquals(2, boatResponse.size());
    }
    @Test
    public void testGetBoatById() {
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        String boatModel="Yamaha";
        String boatColor="Blue";
        Integer size=10;

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse userResponse = userCommandService.addUser(userRequest);

        BoatRequest b1 = BoatRequest.builder()
                .model(boatModel)
                .color(boatColor)
                .size(size)
                .userId(userResponse.id())
                .build();

        BoatResponse boatResponse=boatCommandService.addBoat(b1);

        BoatResponse b=boatQueryService.getBoatById(boatResponse.getId());
        assertEquals(boatResponse,b);

    }
    @Test
    public void testGetBoatByModel() {
        String firstName = "John";
        String lastName = "Doe";
        String email="JD@gmail.com";
        Integer age=19;

        String boatModel="Yamaha";
        String boatColor="Blue";
        Integer size=10;

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse userResponse = userCommandService.addUser(userRequest);
        BoatRequest b1 = BoatRequest.builder()
                .model(boatModel)
                .color(boatColor)
                .size(size)
                .userId(userResponse.id())
                .build();
        BoatResponse boatResponse=boatCommandService.addBoat(b1);
        BoatResponse b=boatQueryService.getBoatByModel(b1.getModel());
        assertEquals(boatResponse,b);

    }


}
