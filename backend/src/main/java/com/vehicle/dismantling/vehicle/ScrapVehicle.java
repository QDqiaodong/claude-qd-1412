package com.vehicle.dismantling.vehicle;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "scrap_vehicle")
public class ScrapVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String plateNumber;
    public String vin;
    public String brandModel;
    public LocalDate entryDate;
    public String sourceType;
    public String status;
}
