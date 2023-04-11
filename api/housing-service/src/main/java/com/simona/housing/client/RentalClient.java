package com.simona.housing.client;

import com.simona.housing.dto.ApiResponse;
import com.simona.housing.dto.RentalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "rental-service", url = "${gateway.url}/rental/api/v1")
public interface RentalClient {
    @GetMapping("/rentals/housing/{housingId}")
    ApiResponse<List<RentalDto>> getRentalsByHousingId(@RequestHeader("Authorization") String authorization, @PathVariable Long housingId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                                       @RequestParam(defaultValue = "id") String sortBy);
}
