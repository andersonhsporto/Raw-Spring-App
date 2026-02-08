package dev.spring.repository;

import dev.spring.model.Room;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("RoomStoredProceduresImpl")
public class RoomStoredProceduresImpl implements RoomRepository {

    private final SimpleJdbcCall createRoomCall;
    private final SimpleJdbcCall findAllRoomsCall;
    private final SimpleJdbcCall findRoomByIdCall;
    private final SimpleJdbcCall deleteRoomByIdCall;

    public RoomStoredProceduresImpl(
            @Qualifier("createRoomCall") SimpleJdbcCall createRoomCall,
            @Qualifier("findAllRoomsCall") SimpleJdbcCall findAllRoomsCall,
            @Qualifier("findRoomByIdCall") SimpleJdbcCall findRoomByIdCall,
            @Qualifier("deleteRoomByIdCall") SimpleJdbcCall deleteRoomByIdCall
    ) {
        this.createRoomCall = createRoomCall;
        this.findAllRoomsCall = findAllRoomsCall;
        this.findRoomByIdCall = findRoomByIdCall;
        this.deleteRoomByIdCall = deleteRoomByIdCall;
    }

    @Override
    public Room save(Room room) {
        createRoomCall.execute(Map.of(
                "number", room.getNumber(),
                "type", room.getType().name(),
                "capacity", room.getCapacity(),
                "price_per_night", room.getPricePerNight(),
                "status", room.getStatus().name()
        ));
        return room;
    }

    @Override
    public List<Room> findAll() {
        return (List<Room>) findAllRoomsCall.execute()
                .get("rooms");
    }

    @Override
    public Optional<Room> findById(long id) {
        List<Room> result =
                (List<Room>) findRoomByIdCall.execute(Map.of("p_id", id))
                        .get("room");

        return result.stream().findFirst();
    }

    @Override
    public void deleteById(long id) {
        deleteRoomByIdCall.execute(Map.of("p_id", id));
    }
}