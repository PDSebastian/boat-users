package ro.mycode.boats.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.boats.service.BoatCommandService;
import ro.mycode.boats.dtos.BoatPatchRequest;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.service.BoatQueryService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v2/boats")

public class BoatController {
    private BoatCommandService boatCommandService;
    private BoatQueryService boatQueryService;
    public BoatController(BoatCommandService boatCommandService, BoatQueryService boatQueryService) {
        this.boatCommandService = boatCommandService;
        this.boatQueryService = boatQueryService;
    }
    @PostMapping("/add")
    public ResponseEntity<BoatResponse> addBoat(@Valid @RequestBody BoatRequest boatRequest) {
        log.debug("http post /api/v2/boats/add");
        BoatResponse b = boatCommandService.addBoat(boatRequest);
        return ResponseEntity.status(HttpStatus.OK).body(b);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoat(@PathVariable Long id) {
        log.debug("http delete /api/v2/boats/delete");
        boatCommandService.deleteBoat(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BoatResponse> getBoatById(@PathVariable Long id) {
        log.debug("http get /api/v2/boats/{id}", id);
        BoatResponse b = boatQueryService.getBoatById(id);
        return ResponseEntity.status(HttpStatus.OK).body(b);
    }
    @PatchMapping("/patch/{id}")
    public ResponseEntity<BoatResponse> patchBoat(@PathVariable Long id, @Valid @RequestBody BoatPatchRequest boatPatchRequest) {
        log.debug("http patch /api/v2/boats/patch/{id}", id);
        BoatResponse b=boatCommandService.updatePatchBoat(id, boatPatchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(b);
    }
    @GetMapping("/all")
    public ResponseEntity<List<BoatResponse>> getAllBoats() {
        log.debug("http get /api/v2/boats");
        List<BoatResponse> b=boatQueryService.getAllBoats();
        return ResponseEntity.status(HttpStatus.OK).body(b);
    }
    @GetMapping("/model/{model}")
    public ResponseEntity<BoatResponse> getBoatByModel(String model) {
        log.debug("http get /api/v2/boats/model/{model}", model);
        BoatResponse b=boatQueryService.getBoatByModel(model);
        return ResponseEntity.status(HttpStatus.OK).body(b);
    }






}
