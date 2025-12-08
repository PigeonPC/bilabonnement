package com.example.bilabonnement.dataRegistration.repository;

/**
 * CarRepo (klasse-baseret JPA repository)
 * Simpelt repository for Car-entity med EntityManager (findById, findAll, mv.).
 */

import com.example.bilabonnement.dataRegistration.model.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CarRepo {

    @PersistenceContext
    private EntityManager entityManager;

// Optional er en form for beholder som kan indeholde data eller i dette
// tilfælde være tom (null).
    public Optional<Car> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Car.class, id));
    }

    public List<Car> findAll() {
        return entityManager.createQuery("SELECT c FROM Car c", Car.class).getResultList();
    }

    public Optional<Car> findByChassisNumber(String chassisNumber) {
        var list = entityManager.createQuery(
                        "SELECT c FROM Car c WHERE c.chassisNumber = :cn", Car.class)
                .setParameter("cn", chassisNumber)
                .setMaxResults(1)
                .getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Transactional
    public Car save(Car car) {
        if (car.getVehicleId() == null) {
            entityManager.persist(car);
            return car;
        } else {
            return entityManager.merge(car);
        }
    }

    @Transactional
    public void deleteById(Integer id) {
        Car ref = entityManager.find(Car.class, id);
        if (ref != null) entityManager.remove(ref);
    }
}
