package com.simona.housing.web.controller;

import com.simona.housing.dto.HousingDto;
import com.simona.housing.dto.RentalDto;
import com.simona.housing.model.Housing;
import com.simona.housing.web.repository.HousingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class HousingController {
    private final HousingRepository housingRepository;

    private final WebClient webClient;

    @Value("${gateway-url}")
    private String gatewayUrl;

    HousingController(HousingRepository housingRepository, WebClient webClient) {
        this.housingRepository = housingRepository;
        this.webClient = webClient;
    }

    @GetMapping("/housings")
    public Page<Housing> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        return housingRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @GetMapping("/housings/{id}")
    public HousingDto findById(@PathVariable long id) {
        Housing foundHousing = housingRepository.findById(id);

        HousingDto housingDto = new HousingDto();
        List<RentalDto> rentals = webClient.get()
                .uri(String.format("%s/rentals/rental?housingId=%s", gatewayUrl, id))
                .retrieve()
                .bodyToFlux(RentalDto.class)
                .collectList()
                .block();

        return housingDto.toDto(foundHousing, rentals);
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
