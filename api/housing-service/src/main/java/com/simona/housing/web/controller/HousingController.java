package com.simona.housing.web.controller;

import com.simona.housing.dto.HousingDto;
import com.simona.housing.dto.RentalDto;
import com.simona.housing.dto.ApiResponse;
import com.simona.housing.model.Housing;
import com.simona.housing.web.repository.HousingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "housing")
@RestController
public class HousingController {

    private final HousingRepository housingRepository;

    private final WebClient webClient;

    @Value("${gateway.url}")
    private String gatewayUrl;

    HousingController(HousingRepository housingRepository, WebClient webClient) {
        this.housingRepository = housingRepository;
        this.webClient = webClient;
    }

    @Operation(summary = "Find all housings")
    @GetMapping("/housings")
    public Page<Housing> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        return housingRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @Operation(summary = "Find housing by id")
    @GetMapping("/housings/{id}")
    public ResponseEntity<ApiResponse<HousingDto>> findById(@PathVariable Long id) {
        if (id == null || id <= 0)
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Id is required"));

        Optional<Housing> foundHousing = housingRepository.findById(id);
        if (foundHousing.isEmpty())
            return ResponseEntity.notFound().build();

        HousingDto housingDto = new HousingDto();
        List<RentalDto> rentals = webClient.get()
                .uri(String.format("%s/rental/api/v1/rentals/rental?housingId=%s", gatewayUrl, id))
                .retrieve()
                .bodyToFlux(RentalDto.class)
                .collectList()
                .block();

        return ResponseEntity.ok(new ApiResponse<>(housingDto.toDto(foundHousing.get(), rentals), ""));
    }

    @Operation(summary = "Save a new housing")
    @PostMapping("/housings")
    public ResponseEntity<ApiResponse<HousingDto>> save(@RequestBody Housing housing) {
        if (housing == null)
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Body is required"));

        Housing result = housingRepository.save(housing);

        if (result == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(new ApiResponse<>(new HousingDto().toDto(result, null), "" ));
    }

    @Operation(summary = "Update an existing housing")
    @PutMapping("/housings/{id}")
    public ResponseEntity<ApiResponse<HousingDto>> update(
            @PathVariable Long id, @RequestBody Housing newHousing) {
        if (id == null || id <= 0)
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Id is required"));

        boolean existHousing = housingRepository.existsById(id);
        if (existHousing) {
            newHousing.setId(id);
            Housing updatedHousing = housingRepository.save(newHousing);
            return ResponseEntity.ok(new ApiResponse<>(new HousingDto().toDto(updatedHousing, null), "" ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a housing by id")
    @DeleteMapping("housings/{id}")
    public ResponseEntity<ApiResponse<HousingDto>> delete(@PathVariable Long id) {
        if (id == null || id <= 0)
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Id is required"));

        Optional<Housing> existingHousing = housingRepository.findById(id);
        if (existingHousing.isPresent()) {
            housingRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(new HousingDto().toDto(existingHousing.get(), null), ""));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
