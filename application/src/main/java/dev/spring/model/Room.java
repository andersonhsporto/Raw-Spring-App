package dev.spring.model;

import java.math.BigDecimal;

public class Room {

    private Long id;

    private String number;

    private RoomType type;

    private int capacity;

    private BigDecimal pricePerNight;

    private RoomStatus status;

    public Room() {
    }

    public Room(String number,
                RoomType type,
                int capacity,
                BigDecimal pricePerNight,
                RoomStatus status) {
        this.number = number;
        this.type = type;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public void changeStatus(RoomStatus status) {
        this.status = status;
    }
}
