package com.vehicle.dismantling.settlement;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.dismantling.common.ApiResponse;

@RestController
@RequestMapping("/api/cancellation-settlements")
public class CancellationSettlementController {
    private final CancellationSettlementService service;

    public CancellationSettlementController(CancellationSettlementService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<CancellationSettlement>> list(@RequestParam(required = false) String keyword,
            @RequestParam(required = false) String cancellationStatus) {
        return ApiResponse.success(service.list(keyword, cancellationStatus));
    }

    @GetMapping("/{id}")
    public ApiResponse<CancellationSettlement> get(@PathVariable Long id) { return ApiResponse.success(service.get(id)); }

    @PostMapping
    public ApiResponse<CancellationSettlement> create(@RequestBody CancellationSettlement input) {
        return ApiResponse.success("新增成功", service.create(input));
    }

    @PutMapping("/{id}")
    public ApiResponse<CancellationSettlement> update(@PathVariable Long id, @RequestBody CancellationSettlement input) {
        return ApiResponse.success("保存成功", service.update(id, input));
    }
}
