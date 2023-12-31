package com.krieger.warehouse.controllers;

import com.krieger.warehouse.Services.WarehouseService;
import com.krieger.warehouse.dtos.warehouse.GetWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.*;
import com.krieger.warehouse.models.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehauseController {

    private final WarehouseService warehouseService;

    public WarehauseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<GetWarehouseDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouse(id));
    }

    @GetMapping("/")
    public ResponseEntity<ServiceResponse<Page<GetWarehouseDto>>> getAllBay(@RequestParam(defaultValue = "1") int pageNo,
                                                                            @RequestParam(defaultValue = "5") int pageSize,
                                                                            @RequestParam(defaultValue = "id") String sortField,
                                                                            @RequestParam(defaultValue = "ASC") String sortOrder) {
        ServiceResponse<Page<GetWarehouseDto>> response = warehouseService.getAll(pageNo, pageSize, sortField, sortOrder);
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ServiceResponse<GetWarehouseDto>> newWarehouse(@RequestBody NewWarehouseDto newWarehouseDto) {
        ServiceResponse<GetWarehouseDto> response = warehouseService.addWarehouse(newWarehouseDto);
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<ServiceResponse<GetWarehouseDto>> updateWarehouse(@RequestBody UpdateWarehouseDto updateWarehouseDto) {
        ServiceResponse<GetWarehouseDto> response = warehouseService.updateWarehouse(updateWarehouseDto);
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteWarehouse(@PathVariable Long id) {

        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok().build();
    }
}
