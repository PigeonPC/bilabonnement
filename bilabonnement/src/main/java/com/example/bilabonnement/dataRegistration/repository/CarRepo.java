package com.example.bilabonnement.dataRegistration.repository;


import com.example.bilabonnement.dataRegistration.CarStatus;
import com.example.bilabonnement.dataRegistration.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends JpaRepository<Car, Integer> {

    // Find bil efter vehicle_id (standard findById eksisterer også)
    Car findByVehicleId(Integer vehicleId);

    // Find alle biler af et bestemt mærke
    List<Car> findByBrand(String brand);

    // Find alle biler efter status (READY_FOR_RENT, SOLD osv.)
    List<Car> findAllByCarStatus(CarStatus carStatus);

    // Find biler der har kørt mere end X km
    List<Car> findByMileageGreaterThan(Integer mileage);

    // Ekstra metode – hvis du i fremtiden vil finde biler efter leasingCode
    List<Car> findByLeasingCode(String leasingCode);
}

