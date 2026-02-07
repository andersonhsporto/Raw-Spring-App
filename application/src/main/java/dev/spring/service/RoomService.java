package dev.spring.service;

import dev.spring.model.Room;
import dev.spring.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements ICrudService<Room> {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> list() {
        return roomRepository.findAll();
    }

    @Override
    public Room create(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> get(int id) {
        return roomRepository.findById(id);
    }

    @Override
    public void update(Room room, int id) {
        room.setId((long) id);
        roomRepository.save(room);
    }

    @Override
    public void delete(int id) {
        roomRepository.deleteById(id);
    }
}
