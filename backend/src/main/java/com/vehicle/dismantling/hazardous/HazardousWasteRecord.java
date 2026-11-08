package com.vehicle.dismantling.hazardous;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "hazardous_waste_record")
public class HazardousWasteRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String wasteType;
    public String containerNo;
    public BigDecimal quantityKg;
    public LocalDateTime storageTime;
    public Long vehicleId;
    @Transient
    public String vehiclePlate;
}
