package com.simona.rental.web.controller;

import com.simona.rental.dto.ApiResponse;
import com.simona.rental.dto.RentalDto;
import com.simona.rental.model.Rental;
import com.simona.rental.web.repository.RentalRepository;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class RentalController {
    private final RentalRepository rentalRepository;

    RentalController(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Operation(summary = "Find all rentals")
    @GetMapping("/rentals")
    public ApiResponse<Page<RentalDto>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                               @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Rental> result = rentalRepository.findAll(pageable);

        // Convert Page<Rental> to Page<RentalDto>
        Page<RentalDto> rentalDtoPage = result.map(RentalDto::new);

        return new ApiResponse<>(rentalDtoPage, null);
    }

    @Operation(summary = "Find rentals by housing id")
    @GetMapping("/rentals/housing/{housingId}")
    public ResponseEntity<ApiResponse<List<RentalDto>>> findByHousingId(@PathVariable Long housingId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                                        @RequestParam(defaultValue = "id") String sortBy) {
        if (housingId == null || housingId <= 0)
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Id must be positive."));

        Optional<Page<Rental>> result = rentalRepository.findByHousingId(housingId, PageRequest.of(page, size, Sort.by(sortBy)));

        if (result.isEmpty())
            return ResponseEntity.notFound().build();

        // Convert Page<Rental> to List<Rental>
        List<Rental> rentalList = result.get().getContent();
        // Convert List<Rental> to List<RentalDto>
        List<RentalDto> rentals = rentalList.stream().map(RentalDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse<>(rentals, null));
    }

    @Operation(summary = "Find rental by id")
    @GetMapping("/rentals/{id}")
    public ResponseEntity<ApiResponse<RentalDto>> findById(@PathVariable Long id) {
        if (id == null || id <= 0)
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Id must be positive."));

        Optional<Rental> foundRental = rentalRepository.findById(id);
        if (foundRental.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new ApiResponse<>(new RentalDto(foundRental.get()), ""));
    }

    @Operation(summary = "Save a new rental")
    @PostMapping("/rentals")
    public ResponseEntity<ApiResponse<RentalDto>> save(@RequestBody Rental rental) {
        if (rental == null)
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Body is required"));

        Rental result = rentalRepository.save(rental);

        if (result == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(new ApiResponse<>(new RentalDto(result), null));
    }

    @Operation(summary = "Update an existing rental")
    @PutMapping("/rentals/{id}")
    public ResponseEntity<ApiResponse<RentalDto>> update(
            @PathVariable Long id, @RequestBody Rental newRental) {
        if (id == null || id <= 0)
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Id must be positive."));

        boolean existRental = rentalRepository.existsById(id);
        if (existRental) {
            newRental.setId(id);
            Rental updatedHousing = rentalRepository.save(newRental);
            return ResponseEntity.ok(new ApiResponse<>(new RentalDto(updatedHousing), null));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
