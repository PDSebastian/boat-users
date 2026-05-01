package ro.mycode.boats.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.mycode.boats.exceptions.BoatAlreadyExistsException;
import ro.mycode.boats.exceptions.BoatNotFoundException;
import ro.mycode.boats.model.Boat;
import ro.mycode.boats.repository.BoatRepository;
import ro.mycode.boats.dtos.BoatPatchRequest;
import ro.mycode.boats.dtos.BoatRequest;
import ro.mycode.boats.dtos.BoatResponse;
import ro.mycode.boats.mapper.BoatMapper;
import ro.mycode.users.exceptions.UserNotFoundexception;
import ro.mycode.users.model.User;
import ro.mycode.users.repository.Userrepository;

@Component
public class BoatCommandServiceImpl implements BoatCommandService {
    BoatRepository boatRepository;
    BoatMapper boatMapper;
    Userrepository userrepository;
    public BoatCommandServiceImpl(BoatRepository boatRepository,BoatMapper boatMapper,Userrepository userrepository) {
        this.boatRepository = boatRepository;
        this.boatMapper = boatMapper;
        this.userrepository = userrepository;
    }



    @Override
    @Transactional
    public BoatResponse addBoat( BoatRequest boatRequest) {
        User user = userrepository.findById(boatRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundexception());
        boatRepository.findByModel(boatRequest.getModel()).ifPresent(b->{throw new BoatAlreadyExistsException();});

        Boat boat = Boat.builder()
                .model(boatRequest.getModel())
                .color(boatRequest.getColor())
                .size(boatRequest.getSize())
                .user(user)
                .build();

        Boat savedBoat = boatRepository.save(boat);
        return boatMapper.toDto(savedBoat);
    }

    @Override
    @Transactional
    public BoatResponse updateBoat(Long boatId, BoatRequest boatRequest) {
        Boat boat = boatRepository.findById(boatId)
                .orElseThrow(() -> new BoatNotFoundException());
        boat.setModel(boatRequest.getModel());
        boat.setColor(boatRequest.getColor());
        boat.setSize(boatRequest.getSize());

        return boatMapper.toDto(boatRepository.save(boat));
    }

    @Override
    @Transactional
    public void deleteBoat(Long boatId) {
        if (!boatRepository.existsById(boatId)) {
            throw new BoatNotFoundException();
        }

        boatRepository.deleteById(boatId);
    }

    @Override
    @Transactional
    public BoatResponse updatePatchBoat(Long id, BoatPatchRequest request) {
        Boat boat = boatRepository.findById(id)
                .orElseThrow(() -> new BoatNotFoundException());

        if (request.model() != null ) {
            boat.setModel(request.model());
        }
        if (request.color() != null ) {
            boat.setColor(request.color());
        }
        if (request.size() != null) {
            boat.setSize(request.size());
        }

        return boatMapper.toDto(boatRepository.save(boat));
    }
}
