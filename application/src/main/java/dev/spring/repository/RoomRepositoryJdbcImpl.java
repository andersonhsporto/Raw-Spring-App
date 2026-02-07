package dev.spring.repository;

import dev.spring.model.Room;
import dev.spring.model.RoomStatus;
import dev.spring.model.RoomType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepositoryJdbcImpl implements RoomRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoomRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Room> ROW_MAPPER = (rs, rowNum) -> {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setNumber(rs.getString("number"));
        room.setType(RoomType.valueOf(rs.getString("type")));
        room.setCapacity(rs.getInt("capacity"));
        room.setPricePerNight(rs.getBigDecimal("price_per_night"));
        room.setStatus(RoomStatus.valueOf(rs.getString("status")));
        return room;
    };

    private static final String SELECT_BASE =
            "SELECT id, number, type, capacity, price_per_night, status FROM rooms";

    private static final String INSERT_SQL =
            "INSERT INTO rooms (number, type, capacity, price_per_night, status) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
            "UPDATE rooms SET number = ?, type = ?, capacity = ?, price_per_night = ?, status = ? WHERE id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM rooms WHERE id = ?";

    @Override
    public List<Room> findAll() {
        return jdbcTemplate.query(SELECT_BASE, ROW_MAPPER);
    }

    @Override
    public Optional<Room> findById(long id) {
        return jdbcTemplate
                .query(SELECT_BASE + " WHERE id = ?", ROW_MAPPER, id)
                .stream()
                .findFirst();
    }

    @Override
    public Room save(Room room) {
        if (room.getId() == null) {
            insert(room);
        } else {
            update(room);
        }
        return room;
    }

    private void insert(Room room) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, room.getNumber());
            ps.setString(2, room.getType().name());
            ps.setInt(3, room.getCapacity());
            ps.setBigDecimal(4, room.getPricePerNight());
            ps.setString(5, room.getStatus().name());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            room.setId(keyHolder.getKey().longValue());
        }
    }

    private void update(Room room) {
        jdbcTemplate.update(
                UPDATE_SQL,
                room.getNumber(),
                room.getType().name(),
                room.getCapacity(),
                room.getPricePerNight(),
                room.getStatus().name(),
                room.getId()
        );
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }
}
