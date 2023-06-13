package com.krieger.warehouse.controllers;

import com.krieger.warehouse.Services.BayService;
import com.krieger.warehouse.dtos.bay.GetBayDto;
import com.krieger.warehouse.dtos.bay.NewBayDto;
import com.krieger.warehouse.dtos.bay.UpdateBayDto;
import com.krieger.warehouse.models.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Bay")
public class BayController {
    @Autowired
    private final BayService bayService;

    public BayController(BayService bayService) {
        this.bayService = bayService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse<GetBayDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bayService.getBay(id));
    }

    @GetMapping()
    public ResponseEntity<ServiceResponse<Page<GetBayDto>>> getAllBay(@RequestParam(defaultValue = "1") int pageNo,
                                                                      @RequestParam(defaultValue = "5") int pageSize,
                                                                      @RequestParam(defaultValue = "id") String sortField,
                                                                      @RequestParam(defaultValue = "ASC") String sortOrder)
    {
        ServiceResponse<Page<GetBayDto>> response = bayService.getAll(pageNo,pageSize,sortField ,sortOrder);
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping()
    public ResponseEntity<ServiceResponse<GetBayDto>> newBay(@RequestBody NewBayDto newBay )
    {
        ServiceResponse<GetBayDto> response = bayService.AddBay(newBay);
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<ServiceResponse<GetBayDto>> updateBay (@RequestBody UpdateBayDto updateBay)
    {
        ServiceResponse<GetBayDto> response = bayService.updateBay(updateBay);
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity  deleteBay(@PathVariable Long id) {

        try {
            bayService.deleteBay(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().build();
        }

    }



}
