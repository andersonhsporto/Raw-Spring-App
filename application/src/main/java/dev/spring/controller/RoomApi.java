package dev.spring.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomApi<T> {

    List<T> list();

    ResponseEntity<T> get(Long id);

    ResponseEntity<T> create(T room);

    ResponseEntity<Void> update(Long id, T room);

    ResponseEntity<Void> delete(Long id);
}