package dev.spring.service;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T> {

    List<T> list();

    T create(T t);

    Optional<T> get(Long id);

    void update(T t, Long id);

    void delete(Long id);
}
