package com.krieger.warehouse.controllers;

import com.krieger.warehouse.Services.WarehousService;
import com.krieger.warehouse.dtos.bay.GetBayDto;
import com.krieger.warehouse.dtos.warehouse.GetWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.NewWarehousDto;
import com.krieger.warehouse.dtos.warehouse.UpdateWarehousDto;
import com.krieger.warehouse.models.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehauseController {


        private final WarehousService warehousService;

        @Autowired
        public WarehauseController(WarehousService warehousService) {
            this.warehousService = warehousService;
        }

        @GetMapping("/{id}")
        public ResponseEntity<ServiceResponse<GetWarehouseDto>> getById(@PathVariable Long id) {
            return ResponseEntity.ok(warehousService.getWarehause(id));
        }

       @GetMapping("/")
       public ResponseEntity<ServiceResponse<Page<GetWarehouseDto>>> getAllBay(@RequestParam(defaultValue = "1") int pageNo,
                                                                               @RequestParam(defaultValue = "5") int pageSize,
                                                                               @RequestParam(defaultValue = "id") String sortField,
                                                                               @RequestParam(defaultValue = "ASC") String sortOrder)
       {
            ServiceResponse<Page<GetWarehouseDto>> response = warehousService.getAll(pageNo,pageSize,sortField ,sortOrder );
            if (!response.isSuccess()) {
                return ResponseEntity.badRequest().body(response);
            }
            return ResponseEntity.ok(response);
       }

        @PostMapping()
        public ResponseEntity<ServiceResponse<GetWarehouseDto>> newWarehouse(@RequestBody NewWarehousDto newWarehouseDto)
        {
            ServiceResponse<GetWarehouseDto> response = warehousService.AddWarehause(newWarehouseDto);
            if (!response.isSuccess()) {
                return ResponseEntity.badRequest().body(response);
            }
            return ResponseEntity.ok(response);
        }

        @PutMapping()
        public ResponseEntity<ServiceResponse<GetWarehouseDto>> updateWarehouse (@RequestBody UpdateWarehousDto updateWarehouseDto)
        {
            ServiceResponse<GetWarehouseDto> response = warehousService.updateWarehause(updateWarehouseDto);
            if (!response.isSuccess()) {
                return ResponseEntity.badRequest().body(response);
            }
            return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity  deleteWarehouse(@PathVariable Long id) {

            warehousService.deleteWarehause(id);
            return  ResponseEntity.ok().build();
        }
}
