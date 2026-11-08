package com.vehicle.dismantling.vehicle;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vehicle.dismantling.common.BizException;

@Service
public class ScrapVehicleService {
    private final ScrapVehicleRepository repository;

    public ScrapVehicleService(ScrapVehicleRepository repository) {
        this.repository = repository;
    }

    public List<ScrapVehicle> list(String keyword, String status) {
        String term = keyword == null ? "" : keyword.trim().toLowerCase();
        return repository.findAll().stream()
                .filter(item -> term.isEmpty() || contains(item.plateNumber, term) || contains(item.vin, term)
                        || contains(item.brandModel, term))
                .filter(item -> status == null || status.isBlank() || status.equals(item.status))
                .toList();
    }

    public ScrapVehicle get(Long id) {
        return repository.findById(id).orElseThrow(() -> new BizException("报废车辆不存在"));
    }

    public ScrapVehicle create(ScrapVehicle input) {
        require(input);
        input.id = null;
        return repository.save(input);
    }

    public ScrapVehicle update(Long id, ScrapVehicle input) {
        ScrapVehicle target = get(id);
        if (input.plateNumber != null) target.plateNumber = input.plateNumber;
        if (input.vin != null) target.vin = input.vin;
        if (input.brandModel != null) target.brandModel = input.brandModel;
        if (input.entryDate != null) target.entryDate = input.entryDate;
        if (input.sourceType != null) target.sourceType = input.sourceType;
        if (input.status != null) target.status = input.status;
        require(target);
        return repository.save(target);
    }

    private void require(ScrapVehicle item) {
        if (blank(item.plateNumber) || blank(item.vin) || blank(item.brandModel) || item.entryDate == null
                || blank(item.sourceType) || blank(item.status)) {
            throw new BizException("车辆台账字段不完整");
        }
    }

    private boolean contains(String value, String term) {
        return value != null && value.toLowerCase().contains(term);
    }

    private boolean blank(String value) {
        return value == null || value.isBlank();
    }
}
