package com.simona.rental.web.controller;

import com.simona.rental.model.Rental;
import com.simona.rental.web.repository.RentalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class RentalController {
    private final RentalRepository rentalRepository;

    RentalController(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @GetMapping("/rentals")
    public Page<Rental> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            return rentalRepository.findAll(pageable);
    }

    @GetMapping("/rentals/rental")
    public List<Rental> findByHousingId(@RequestParam long housingId) {
        return rentalRepository.findByHousingId(housingId);
    }

    @GetMapping("/rentals/{id}")
    public Rental findById(@PathVariable long id) {
        return rentalRepository.findById(id);
    }

    @PostMapping("/rentals")
    public ResponseEntity<Rental> save(@RequestBody Rental rental) {
        Rental result = rentalRepository.save(rental);

        if (result == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @PutMapping("/rentals/{id}")
    public ResponseEntity<Rental> update(
            @PathVariable Long id, @RequestBody Rental newRental) {
        boolean existRental = rentalRepository.existsById(id);
        if (existRental) {
            newRental.setId(id);
            Rental updatedRental = rentalRepository.save(newRental);
            return ResponseEntity.ok(updatedRental);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("rentals/{id}")
    public ResponseEntity<Rental> delete(@PathVariable Long id) {
        Optional<Rental> existingRental = rentalRepository.findById(id);
        if (existingRental.isPresent()) {
            rentalRepository.deleteById(id);
            return ResponseEntity.ok(existingRental.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
