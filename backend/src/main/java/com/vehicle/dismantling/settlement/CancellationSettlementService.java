package com.vehicle.dismantling.settlement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vehicle.dismantling.common.BizException;
import com.vehicle.dismantling.vehicle.ScrapVehicleService;

@Service
public class CancellationSettlementService {
    private final CancellationSettlementRepository repository;
    private final ScrapVehicleService vehicleService;

    public CancellationSettlementService(CancellationSettlementRepository repository, ScrapVehicleService vehicleService) {
        this.repository = repository;
        this.vehicleService = vehicleService;
    }

    public List<CancellationSettlement> list(String keyword, String cancellationStatus) {
        String term = keyword == null ? "" : keyword.trim().toLowerCase();
        return repository.findAll().stream().map(this::enrich)
                .filter(item -> term.isEmpty() || contains(item.certificateNo, term) || contains(item.vehiclePlate, term))
                .filter(item -> cancellationStatus == null || cancellationStatus.isBlank()
                        || cancellationStatus.equals(item.cancellationStatus))
                .toList();
    }

    public CancellationSettlement get(Long id) {
        return enrich(repository.findById(id).orElseThrow(() -> new BizException("注销结算记录不存在")));
    }

    public CancellationSettlement create(CancellationSettlement input) {
        require(input);
        vehicleService.get(input.vehicleId);
        input.id = null;
        return enrich(repository.save(input));
    }

    public CancellationSettlement update(Long id, CancellationSettlement input) {
        CancellationSettlement target = repository.findById(id).orElseThrow(() -> new BizException("注销结算记录不存在"));
        if (input.vehicleId != null) target.vehicleId = input.vehicleId;
        if (input.cancellationStatus != null) target.cancellationStatus = input.cancellationStatus;
        if (input.certificateNo != null) target.certificateNo = input.certificateNo;
        if (input.residualValue != null) target.residualValue = input.residualValue;
        if (input.processingFee != null) target.processingFee = input.processingFee;
        if (input.paidAmount != null) target.paidAmount = input.paidAmount;
        require(target);
        vehicleService.get(target.vehicleId);
        return enrich(repository.save(target));
    }

    private CancellationSettlement enrich(CancellationSettlement item) {
        item.vehiclePlate = vehicleService.get(item.vehicleId).plateNumber;
        return item;
    }

    private void require(CancellationSettlement item) {
        if (item.vehicleId == null || blank(item.cancellationStatus) || blank(item.certificateNo)
                || item.residualValue == null || item.processingFee == null || item.paidAmount == null) {
            throw new BizException("注销结算字段不完整");
        }
    }

    private boolean contains(String value, String term) { return value != null && value.toLowerCase().contains(term); }
    private boolean blank(String value) { return value == null || value.isBlank(); }
}
