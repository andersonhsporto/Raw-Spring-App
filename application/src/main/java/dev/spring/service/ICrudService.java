package dev.spring.service;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T> {

    List<T> list();

    T create(T t);

    Optional<T> get(int id);

    void update(T t, int id);

    void delete(int id);
}
