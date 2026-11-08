package com.vehicle.dismantling.hazardous;

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
@RequestMapping("/api/hazardous-waste-records")
public class HazardousWasteRecordController {
    private final HazardousWasteRecordService service;

    public HazardousWasteRecordController(HazardousWasteRecordService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<HazardousWasteRecord>> list(@RequestParam(required = false) String keyword,
            @RequestParam(required = false) String wasteType) {
        return ApiResponse.success(service.list(keyword, wasteType));
    }

    @GetMapping("/{id}")
    public ApiResponse<HazardousWasteRecord> get(@PathVariable Long id) { return ApiResponse.success(service.get(id)); }

    @PostMapping
    public ApiResponse<HazardousWasteRecord> create(@RequestBody HazardousWasteRecord input) {
        return ApiResponse.success("新增成功", service.create(input));
    }

    @PutMapping("/{id}")
    public ApiResponse<HazardousWasteRecord> update(@PathVariable Long id, @RequestBody HazardousWasteRecord input) {
        return ApiResponse.success("保存成功", service.update(id, input));
    }
}
