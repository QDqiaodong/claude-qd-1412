package com.vehicle.dismantling.hazardous;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vehicle.dismantling.common.BizException;
import com.vehicle.dismantling.vehicle.ScrapVehicleService;

@Service
public class HazardousWasteRecordService {
    private final HazardousWasteRecordRepository repository;
    private final ScrapVehicleService vehicleService;

    public HazardousWasteRecordService(HazardousWasteRecordRepository repository, ScrapVehicleService vehicleService) {
        this.repository = repository;
        this.vehicleService = vehicleService;
    }

    public List<HazardousWasteRecord> list(String keyword, String wasteType) {
        String term = keyword == null ? "" : keyword.trim().toLowerCase();
        return repository.findAll().stream().map(this::enrich)
                .filter(item -> term.isEmpty() || contains(item.containerNo, term) || contains(item.vehiclePlate, term))
                .filter(item -> wasteType == null || wasteType.isBlank() || wasteType.equals(item.wasteType))
                .toList();
    }

    public HazardousWasteRecord get(Long id) {
        return enrich(repository.findById(id).orElseThrow(() -> new BizException("危废暂存记录不存在")));
    }

    public HazardousWasteRecord create(HazardousWasteRecord input) {
        require(input);
        checkVehicle(input.vehicleId);
        input.id = null;
        return enrich(repository.save(input));
    }

    public HazardousWasteRecord update(Long id, HazardousWasteRecord input) {
        HazardousWasteRecord target = repository.findById(id).orElseThrow(() -> new BizException("危废暂存记录不存在"));
        if (input.wasteType != null) target.wasteType = input.wasteType;
        if (input.containerNo != null) target.containerNo = input.containerNo;
        if (input.quantityKg != null) target.quantityKg = input.quantityKg;
        if (input.storageTime != null) target.storageTime = input.storageTime;
        target.vehicleId = input.vehicleId;
        require(target);
        checkVehicle(target.vehicleId);
        return enrich(repository.save(target));
    }

    private HazardousWasteRecord enrich(HazardousWasteRecord item) {
        item.vehiclePlate = item.vehicleId == null ? null : vehicleService.get(item.vehicleId).plateNumber;
        return item;
    }

    private void checkVehicle(Long vehicleId) {
        if (vehicleId != null) vehicleService.get(vehicleId);
    }

    private void require(HazardousWasteRecord item) {
        if (blank(item.wasteType) || blank(item.containerNo) || item.quantityKg == null || item.storageTime == null) {
            throw new BizException("危废暂存记录字段不完整");
        }
    }

    private boolean contains(String value, String term) { return value != null && value.toLowerCase().contains(term); }
    private boolean blank(String value) { return value == null || value.isBlank(); }
}
