package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.PetType;
import com.codecool.hogwarts_potions.model.Room;
import com.codecool.hogwarts_potions.model.Student;
import com.codecool.hogwarts_potions.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void addRoom(Room room) {
        if(room != null) roomRepository.save(room);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void updateRoomById(Long id, Room updatedRoom) {
        Room room = getRoomById(id);
        if (room != null && updatedRoom != null) {
            room.setCapacity(updatedRoom.getCapacity());
            room.setResidents(updatedRoom.getResidents());
        }
    }

    public void deleteRoomById(Long id) {
        roomRepository.findById(id).ifPresent(room -> roomRepository.delete(room));
    }

    public List<Room> getRoomsForRatOwners() {
        return roomRepository.findAll()
                .stream()
                .filter(t -> t.getResidents()
                    .stream()
                        .noneMatch(z -> z.getPetType().equals(PetType.CAT) || z.getPetType().equals(PetType.OWL)))
                .collect(Collectors.toList());
    }

    public List<Room> availableRooms(Student student) {
        return roomRepository.findAll()
                .stream()
                .filter(room -> room.getResidents().size() == 0
                        || room.getResidents()
                        .stream()
                        .allMatch(resident -> resident.getHouseType().equals(student.getHouseType())))
                .collect(Collectors.toList());
    }
}
