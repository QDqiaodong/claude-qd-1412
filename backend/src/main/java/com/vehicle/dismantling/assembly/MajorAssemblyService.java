package com.vehicle.dismantling.assembly;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vehicle.dismantling.common.BizException;
import com.vehicle.dismantling.vehicle.ScrapVehicleService;

@Service
public class MajorAssemblyService {
    private final MajorAssemblyRepository repository;
    private final ScrapVehicleService vehicleService;

    public MajorAssemblyService(MajorAssemblyRepository repository, ScrapVehicleService vehicleService) {
        this.repository = repository;
        this.vehicleService = vehicleService;
    }

    public List<MajorAssembly> list(String keyword, String assemblyType) {
        String term = keyword == null ? "" : keyword.trim().toLowerCase();
        return repository.findAll().stream().map(this::enrich)
                .filter(item -> term.isEmpty() || contains(item.assemblyNo, term) || contains(item.destination, term)
                        || contains(item.vehiclePlate, term))
                .filter(item -> assemblyType == null || assemblyType.isBlank() || assemblyType.equals(item.assemblyType))
                .toList();
    }

    public MajorAssembly get(Long id) {
        return enrich(repository.findById(id).orElseThrow(() -> new BizException("总成记录不存在")));
    }

    public MajorAssembly create(MajorAssembly input) {
        require(input);
        vehicleService.get(input.vehicleId);
        input.id = null;
        return enrich(repository.save(input));
    }

    public MajorAssembly update(Long id, MajorAssembly input) {
        MajorAssembly target = repository.findById(id).orElseThrow(() -> new BizException("总成记录不存在"));
        if (input.assemblyType != null) target.assemblyType = input.assemblyType;
        if (input.assemblyNo != null) target.assemblyNo = input.assemblyNo;
        if (input.vehicleId != null) target.vehicleId = input.vehicleId;
        if (input.destination != null) target.destination = input.destination;
        require(target);
        vehicleService.get(target.vehicleId);
        return enrich(repository.save(target));
    }

    private MajorAssembly enrich(MajorAssembly item) {
        item.vehiclePlate = vehicleService.get(item.vehicleId).plateNumber;
        return item;
    }

    private void require(MajorAssembly item) {
        if (blank(item.assemblyType) || blank(item.assemblyNo) || item.vehicleId == null || blank(item.destination)) {
            throw new BizException("五大总成台账字段不完整");
        }
    }

    private boolean contains(String value, String term) { return value != null && value.toLowerCase().contains(term); }
    private boolean blank(String value) { return value == null || value.isBlank(); }
}
