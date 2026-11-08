package com.vehicle.dismantling.assembly;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "major_assembly")
public class MajorAssembly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String assemblyType;
    public String assemblyNo;
    public Long vehicleId;
    public String destination;
    @Transient
    public String vehiclePlate;
}
