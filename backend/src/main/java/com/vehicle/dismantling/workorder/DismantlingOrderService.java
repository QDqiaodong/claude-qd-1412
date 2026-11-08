package com.vehicle.dismantling.workorder;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vehicle.dismantling.common.BizException;
import com.vehicle.dismantling.vehicle.ScrapVehicleService;

@Service
public class DismantlingOrderService {
    private final DismantlingOrderRepository repository;
    private final ScrapVehicleService vehicleService;

    public DismantlingOrderService(DismantlingOrderRepository repository, ScrapVehicleService vehicleService) {
        this.repository = repository;
        this.vehicleService = vehicleService;
    }

    public List<DismantlingOrder> list(String keyword) {
        String term = keyword == null ? "" : keyword.trim().toLowerCase();
        return repository.findAll().stream().map(this::enrich)
                .filter(item -> term.isEmpty() || contains(item.orderNo, term) || contains(item.teamName, term)
                        || contains(item.vehiclePlate, term))
                .toList();
    }

    public DismantlingOrder get(Long id) {
        return enrich(repository.findById(id).orElseThrow(() -> new BizException("拆解工单不存在")));
    }

    public DismantlingOrder create(DismantlingOrder input) {
        require(input);
        vehicleService.get(input.vehicleId);
        input.id = null;
        return enrich(repository.save(input));
    }

    public DismantlingOrder update(Long id, DismantlingOrder input) {
        DismantlingOrder target = repository.findById(id).orElseThrow(() -> new BizException("拆解工单不存在"));
        if (input.orderNo != null) target.orderNo = input.orderNo;
        if (input.vehicleId != null) target.vehicleId = input.vehicleId;
        if (input.startTime != null) target.startTime = input.startTime;
        target.completionTime = input.completionTime;
        if (input.teamName != null) target.teamName = input.teamName;
        require(target);
        vehicleService.get(target.vehicleId);
        return enrich(repository.save(target));
    }

    private DismantlingOrder enrich(DismantlingOrder item) {
        item.vehiclePlate = vehicleService.get(item.vehicleId).plateNumber;
        return item;
    }

    private void require(DismantlingOrder item) {
        if (blank(item.orderNo) || item.vehicleId == null || item.startTime == null || blank(item.teamName)) {
            throw new BizException("拆解工单字段不完整");
        }
    }

    private boolean contains(String value, String term) {
        return value != null && value.toLowerCase().contains(term);
    }

    private boolean blank(String value) {
        return value == null || value.isBlank();
    }
}
