package dev.spring.mapper;


import dev.spring.model.Room;
import dev.spring.model.RoomStatus;
import dev.spring.model.RoomType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoomRowMapper implements RowMapper<Room> {

    @Override
    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setNumber(rs.getString("number"));
        room.setType(RoomType.valueOf(rs.getString("type")));
        room.setCapacity(rs.getInt("capacity"));
        room.setPricePerNight(rs.getBigDecimal("price_per_night"));
        room.setStatus(RoomStatus.valueOf(rs.getString("status")));
        return room;
    }
}