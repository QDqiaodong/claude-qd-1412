package com.vehicle.dismantling.workorder;

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
@RequestMapping("/api/dismantling-orders")
public class DismantlingOrderController {
    private final DismantlingOrderService service;

    public DismantlingOrderController(DismantlingOrderService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<DismantlingOrder>> list(@RequestParam(required = false) String keyword) {
        return ApiResponse.success(service.list(keyword));
    }

    @GetMapping("/{id}")
    public ApiResponse<DismantlingOrder> get(@PathVariable Long id) { return ApiResponse.success(service.get(id)); }

    @PostMapping
    public ApiResponse<DismantlingOrder> create(@RequestBody DismantlingOrder input) {
        return ApiResponse.success("新增成功", service.create(input));
    }

    @PutMapping("/{id}")
    public ApiResponse<DismantlingOrder> update(@PathVariable Long id, @RequestBody DismantlingOrder input) {
        return ApiResponse.success("保存成功", service.update(id, input));
    }
}
