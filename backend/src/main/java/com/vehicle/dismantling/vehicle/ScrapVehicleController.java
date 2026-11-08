package com.vehicle.dismantling.vehicle;

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
@RequestMapping("/api/vehicles")
public class ScrapVehicleController {
    private final ScrapVehicleService service;

    public ScrapVehicleController(ScrapVehicleService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<ScrapVehicle>> list(@RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status) {
        return ApiResponse.success(service.list(keyword, status));
    }

    @GetMapping("/{id}")
    public ApiResponse<ScrapVehicle> get(@PathVariable Long id) {
        return ApiResponse.success(service.get(id));
    }

    @PostMapping
    public ApiResponse<ScrapVehicle> create(@RequestBody ScrapVehicle input) {
        return ApiResponse.success("新增成功", service.create(input));
    }

    @PutMapping("/{id}")
    public ApiResponse<ScrapVehicle> update(@PathVariable Long id, @RequestBody ScrapVehicle input) {
        return ApiResponse.success("保存成功", service.update(id, input));
    }
}
