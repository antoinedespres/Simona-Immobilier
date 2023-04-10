package com.simona.housing.web.repository;

import com.simona.housing.model.Housing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HousingRepository extends JpaRepository<Housing, Long> {
    Page<Housing> findAll(Pageable pageable);

    Optional<Housing> findById(Long id);

    Housing save(Housing housing);

}
