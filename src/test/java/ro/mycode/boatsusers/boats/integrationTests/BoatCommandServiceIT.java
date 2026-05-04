package ro.mycode.boatsusers.boats.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.boats.dtos.BoatPatchRequest;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.repository.BoatRepository;
import ro.mycode.boats.service.BoatCommandService;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.repository.Userrepository;
import ro.mycode.users.service.UserCommandService;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("tests")
@Transactional
public class BoatCommandServiceIT {
    @Autowired
    BoatRepository  boatRepository;
    @Autowired
    BoatCommandService boatCommandService;
    @Autowired
    Userrepository  userrepository;
    @Autowired
    UserCommandService userCommandService;

    @BeforeEach
    public void setup(){
        boatRepository.deleteAll();
    }
    @Test
    public void testAddBoat(){
        String model="adwd";
        String color="bdwdw";
        Integer size=10;

        String firstName="aadad";
        String lastName="bfrr";
        String email="vvva";
        Integer age=19;

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse userResponse=userCommandService.addUser(userRequest);

        BoatRequest boatRequest=BoatRequest.builder()
                .model(model)
                .color(color)
                .size(size)
                .userId(userResponse.id())
                .build();


        BoatResponse boatResponse = boatCommandService.addBoat(boatRequest);
        assertEquals(model,boatResponse.getModel());

    }
    @Test
    public void testDeleteBoat(){
        String model="adwd";
        String color="bdwdw";
        Integer size=10;

        String firstName="aadad";
        String lastName="bfrr";
        String email="vvva";
        Integer age=19;

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse userResponse=userCommandService.addUser(userRequest);

        BoatRequest boatRequest=BoatRequest.builder()
                .model(model)
                .color(color)
                .size(size)
                .userId(userResponse.id())
                .build();

        BoatResponse boatResponse1 = boatCommandService.addBoat(boatRequest);

        BoatResponse boatResponse = boatCommandService.deleteBoat(boatResponse1.getId());
        assertEquals(model,boatResponse.getModel());

    }
    @Test
    public void testUpdateBoat(){
        String model="adwd";
        String color="bdwdw";
        Integer size=10;
        String newModel="vvv";
        String newColor="www";

        String firstName="aadad";
        String lastName="bfrr";
        String email="vvva";
        Integer age=19;

        UserRequest userRequest = UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse userResponse=userCommandService.addUser(userRequest);

        BoatRequest boatRequest=BoatRequest.builder()
                .model(newModel)
                .color(newColor)
                .size(size)
                .userId(userResponse.id())
                .build();
        BoatResponse boatResponse = boatCommandService.addBoat(boatRequest);

        BoatResponse boatResponse1 = boatCommandService.updateBoat(boatResponse.getId(),boatRequest);
        assertEquals(newModel,boatResponse.getModel());
        assertEquals(newColor,boatResponse.getColor());

    }
    @Test
    public void updatePatchBoat(){
        String model="adwd";
        String color="bdwdw";
        Integer size=10;
        String newModel="vvv";
        String newColor="www";

        String firstName="aadad";
        String lastName="bfrr";
        String email="vvva";
        Integer age=19;

        UserRequest userRequest=UserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .age(age)
                .build();

        UserResponse userResponse=userCommandService.addUser(userRequest);
        BoatRequest boatRequest = BoatRequest.builder()
                .model(model)
                .color(color)
                .size(size)
                .userId(userResponse.id())
                .build();

        BoatPatchRequest boatPatchRequest = BoatPatchRequest.builder()
                .model(newModel)
                .color(newColor)
                .build();

        BoatResponse addedBoat = boatCommandService.addBoat(boatRequest);
        BoatResponse boatResponse = boatCommandService.updatePatchBoat(addedBoat.getId(), boatPatchRequest);

        assertEquals(newModel, boatResponse.getModel());
        assertEquals(newColor, boatResponse.getColor());



    }







}
