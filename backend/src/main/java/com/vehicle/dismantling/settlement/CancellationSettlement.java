package com.vehicle.dismantling.settlement;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "cancellation_settlement")
public class CancellationSettlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long vehicleId;
    public String cancellationStatus;
    public String certificateNo;
    public BigDecimal residualValue;
    public BigDecimal processingFee;
    public BigDecimal paidAmount;
    @Transient
    public String vehiclePlate;
}
