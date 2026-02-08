package dev.spring.service;

import dev.spring.model.Room;
import dev.spring.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("RoomService")
public class RoomService implements ICrudService<Room> {

    private final RoomRepository roomRepository;

    public RoomService(@Qualifier("RoomRepositoryJdbcImpl") RoomRepository roomRepository) {
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
    public Optional<Room> get(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public void update(Room room, Long id) {
        room.setId(id);
        roomRepository.save(room);
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }
}
