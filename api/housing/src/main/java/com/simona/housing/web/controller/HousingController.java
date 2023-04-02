package com.simona.housing.web.controller;

import com.simona.housing.model.Housing;
import com.simona.housing.web.repository.HousingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HousingController {
    @Autowired
    private HousingRepository housingRepository;
    @GetMapping("/housings")
    public Page<Housing> list(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "20") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return housingRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @GetMapping("/housings/{id}")
    public Housing findById(@PathVariable long id) {
        return housingRepository.findById(id);
    }
}
