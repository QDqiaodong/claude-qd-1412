package com.vehicle.dismantling.workorder;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "dismantling_order")
public class DismantlingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String orderNo;
    public Long vehicleId;
    public LocalDateTime startTime;
    public LocalDateTime completionTime;
    public String teamName;
    @Transient
    public String vehiclePlate;
}
