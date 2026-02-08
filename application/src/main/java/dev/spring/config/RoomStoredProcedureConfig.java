package dev.spring.config;

import dev.spring.mapper.RoomRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

@Configuration
public class RoomStoredProcedureConfig {

    @Bean
    public SimpleJdbcCall createRoomCall(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CREATE_ROOM");
    }

    @Bean
    public SimpleJdbcCall findAllRoomsCall(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("FIND_ALL_ROOMS")
                .returningResultSet("rooms", new RoomRowMapper());
    }

    @Bean
    public SimpleJdbcCall findRoomByIdCall(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("FIND_ROOM_BY_ID")
                .returningResultSet("room", new RoomRowMapper());
    }

    @Bean
    public SimpleJdbcCall deleteRoomByIdCall(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("DELETE_ROOM_BY_ID");
    }
}
