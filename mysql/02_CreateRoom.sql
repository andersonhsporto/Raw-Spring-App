DELIMITER //

CREATE PROCEDURE CREATE_ROOM(
    IN p_name VARCHAR(255),
    IN p_location VARCHAR(255),
    IN p_capacity INT,
    OUT p_message VARCHAR(255)
)
BEGIN
    INSERT INTO rooms (name, location, capacity)
    VALUES (p_name, p_location, p_capacity);

    SET p_message = 'Room created successfully';
END //