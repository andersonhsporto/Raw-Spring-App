package dev.spring.repository;

import dev.spring.model.Room;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoomRepository {
    List<Room> findAll();
    Room save(Room room);
    Optional<Room> findById(long id);
    void deleteById(long id);
}
