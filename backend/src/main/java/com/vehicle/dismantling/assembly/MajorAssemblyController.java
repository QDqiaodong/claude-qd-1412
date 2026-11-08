package com.vehicle.dismantling.assembly;

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
@RequestMapping("/api/assemblies")
public class MajorAssemblyController {
    private final MajorAssemblyService service;

    public MajorAssemblyController(MajorAssemblyService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<MajorAssembly>> list(@RequestParam(required = false) String keyword,
            @RequestParam(required = false) String assemblyType) {
        return ApiResponse.success(service.list(keyword, assemblyType));
    }

    @GetMapping("/{id}")
    public ApiResponse<MajorAssembly> get(@PathVariable Long id) { return ApiResponse.success(service.get(id)); }

    @PostMapping
    public ApiResponse<MajorAssembly> create(@RequestBody MajorAssembly input) {
        return ApiResponse.success("新增成功", service.create(input));
    }

    @PutMapping("/{id}")
    public ApiResponse<MajorAssembly> update(@PathVariable Long id, @RequestBody MajorAssembly input) {
        return ApiResponse.success("保存成功", service.update(id, input));
    }
}
