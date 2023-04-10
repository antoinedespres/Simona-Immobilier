package com.simona.rental.web.repository;

import com.simona.rental.model.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    Page<Rental> findAll(Pageable pageable);

    Optional<Rental> findById(Long id);

    Rental save(Rental housing);

    Optional<Page<Rental>> findByHousingId(long housingId, Pageable pageable);

}
