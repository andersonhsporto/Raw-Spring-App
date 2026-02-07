package dev.spring.repository;

import dev.spring.model.Room;
import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    List<Room> findAll();
    Room save(Room room);
    Optional<Room> findById(long id);
    void deleteById(long id);
}
