package com.simona.housing.web.controller;

import com.simona.housing.client.RentalClient;
import com.simona.housing.dto.HousingDto;
import com.simona.housing.dto.RentalDto;
import com.simona.housing.dto.ApiResponse;
import com.simona.housing.model.Housing;
import com.simona.housing.web.repository.HousingRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
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
    private final RentalClient rentalClient;

    private final WebClient webClient;

    @Value("${gateway.url}")
    private String gatewayUrl;

    HousingController(HousingRepository housingRepository, RentalClient rentalClient, WebClient webClient) {
        this.housingRepository = housingRepository;
        this.rentalClient = rentalClient;
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

    // Ignore the authorization header
    public ResponseEntity<ApiResponse<HousingDto>> findById(@PathVariable Long id,
                                                           @Parameter(hidden = true) @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        if (id == null || id <= 0)
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Id is required"));

        Optional<Housing> foundHousing = housingRepository.findById(id);
        if (foundHousing.isEmpty())
            return ResponseEntity.notFound().build();

        ApiResponse<List<RentalDto>> rentalResponse = rentalClient.getRentalsByHousingId(authorization, id, 0, 20, "id");

        return ResponseEntity.ok(new ApiResponse<>(new HousingDto(foundHousing.get(), rentalResponse.getData()), ""));
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

        return ResponseEntity.created(location).body(new ApiResponse<>(new HousingDto(result, null), "" ));
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
            return ResponseEntity.ok(new ApiResponse<>(new HousingDto(updatedHousing, null), "" ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
