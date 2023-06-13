package com.krieger.warehouse.Services;

import com.krieger.warehouse.dtos.warehouse.GetWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.NewWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.UpdateWarehouseDto;
import com.krieger.warehouse.models.ServiceResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WarehouseService {



    public ServiceResponse<GetWarehouseDto> getWarehause(Long id);

    public ServiceResponse<GetWarehouseDto> AddWarehause(NewWarehouseDto newWarehaus);

    public void deleteWarehause(Long id);

    public ServiceResponse<GetWarehouseDto> updateWarehause(UpdateWarehouseDto uppdateWarehouse);

    public ServiceResponse<Page<GetWarehouseDto>> getAll(int pageNo, int pageSize, String sortField, String sortOrder);
}
