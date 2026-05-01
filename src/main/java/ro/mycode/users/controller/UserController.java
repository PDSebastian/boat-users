package ro.mycode.users.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.users.service.UserCommandService;
import ro.mycode.users.dtos.UserPatchRequest;
import ro.mycode.users.dtos.UserRequest;
import ro.mycode.users.dtos.UserResponse;
import ro.mycode.users.service.UserQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/users")
@Slf4j
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    public UserController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;

    }
    @PostMapping("/add")
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest userRequest) {
        log.debug("http post /api/v2/users/add");
        return ResponseEntity.status(HttpStatus.OK).body(userCommandService.addUser(userRequest));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("http delete /api/v2/users/delete/{id}", id);
        userCommandService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PatchMapping("/patch/{id}")
    public ResponseEntity<UserResponse> patchUser(@PathVariable Long id, @Valid @RequestBody UserPatchRequest userPatchRequest) {
        UserResponse response = userCommandService.updatePatchUser(id, userPatchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse response = userCommandService.updateUser( userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> users = userQueryService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        UserResponse userResponse = userQueryService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<List<UserResponse>> getByFirstName(@PathVariable String firstName) {
        return ResponseEntity.status(HttpStatus.OK).body(userQueryService.getUsersByFirstName(firstName));
    }

    @GetMapping("email/{email}")
    public ResponseEntity<List<UserResponse>> getByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userQueryService.getUserByEmail(email));
    }






}
