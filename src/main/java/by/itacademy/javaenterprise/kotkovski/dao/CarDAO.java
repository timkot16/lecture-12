package by.itacademy.javaenterprise.kotkovski.dao;

import by.itacademy.javaenterprise.kotkovski.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CarDAO<K, E> {

    boolean delete(K id);

    E save(E car);

    void update(E car);

    List<E> findAll();

    Optional<E> findById(K id);
}
