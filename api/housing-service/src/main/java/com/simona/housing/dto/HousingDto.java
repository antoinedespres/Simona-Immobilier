package com.simona.housing.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simona.housing.model.Housing;

import java.util.List;

public class HousingDto {
    private long id;
    private int surface;
    private int nbRooms;
    private String street;
    private String postalCode;
    private String city;
    private double price;
    private long landlordId;
    private long typeId;
    List<RentalDto> rentals;

    public HousingDto (Housing foundHousing, List<RentalDto> rentals) {
        this.setId(foundHousing.getId());
        this.setSurface(foundHousing.getSurface());
        this.setNbRooms(foundHousing.getNbRooms());
        this.setStreet(foundHousing.getStreet());
        this.setPostalCode(foundHousing.getPostalCode());
        this.setCity(foundHousing.getCity());
        this.setPrice(foundHousing.getPrice());
        this.setLandlordId(foundHousing.getLandlordId());
        this.setTypeId(foundHousing.getTypeId());
        this.setRentals(rentals);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getNbRooms() {
        return nbRooms;
    }

    public void setNbRooms(int nbRooms) {
        this.nbRooms = nbRooms;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(long landlordId) {
        this.landlordId = landlordId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public List<RentalDto> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalDto> rentals) {
        this.rentals = rentals;
    }

}
