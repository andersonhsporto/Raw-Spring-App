DELIMITER //

CREATE PROCEDURE UPDATE_ROOM(
    IN p_id BIGINT,
    IN p_name VARCHAR(255),
    IN p_location VARCHAR(255),
    IN p_capacity INT
)
BEGIN
UPDATE rooms
SET
    name = p_name,
    location = p_location,
    capacity = p_capacity
WHERE id = p_id;
END //

DELIMITER ;
