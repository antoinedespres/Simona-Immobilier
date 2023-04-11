package com.simona.rental.dto;

import com.simona.rental.model.Rental;

import java.time.LocalDate;

public class RentalDto {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private long tenantId;
    private long housingId;

    public RentalDto(Rental rental) {
        this.id = rental.getId();
        this.startDate = rental.getStartDate();
        this.endDate = rental.getEndDate();
        this.tenantId = rental.getTenantId();
        this.housingId = rental.getHousingId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public long getHousingId() {
        return housingId;
    }

    public void setHousingId(long housingId) {
        this.housingId = housingId;
    }
}
