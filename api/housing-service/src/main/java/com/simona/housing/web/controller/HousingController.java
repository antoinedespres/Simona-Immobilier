package com.simona.housing.web.controller;

import com.simona.housing.model.Housing;
import com.simona.housing.web.repository.HousingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class HousingController {
    private final HousingRepository housingRepository;

    HousingController(HousingRepository housingRepository) {
        this.housingRepository = housingRepository;
    }

    @GetMapping("/housings")
    public Page<Housing> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        return housingRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @GetMapping("/housings/{id}")
    public Housing findById(@PathVariable long id) {
        return housingRepository.findById(id);
    }

    @PostMapping("/housings")
    public ResponseEntity<Housing> save(@RequestBody Housing housing) {
        Housing result = housingRepository.save(housing);

        if (result == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @PutMapping("/housings/{id}")
    public ResponseEntity<Housing> update(
            @PathVariable Long id, @RequestBody Housing newHousing) {
        boolean existHousing = housingRepository.existsById(id);
        if (existHousing) {
            newHousing.setId(id);
            Housing updatedHousing = housingRepository.save(newHousing);
            return ResponseEntity.ok(updatedHousing);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("housings/{id}")
    public ResponseEntity<Housing> delete(@PathVariable Long id) {
        Optional<Housing> existingHousing = housingRepository.findById(id);
        if (existingHousing.isPresent()) {
            housingRepository.deleteById(id);
            return ResponseEntity.ok(existingHousing.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
