package com.krieger.warehouse.Services;

import com.krieger.warehouse.dtos.warehouse.GetWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.NewWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.UpdateWarehouseDto;
import com.krieger.warehouse.models.ServiceResponse;
import org.springframework.data.domain.Page;

public interface WarehouseService {

    public ServiceResponse<GetWarehouseDto> getWarehouse(Long id);

    public ServiceResponse<GetWarehouseDto> addWarehouse(NewWarehouseDto newWarehouse);

    public void deleteWarehouse(Long id);

    public ServiceResponse<GetWarehouseDto> updateWarehouse(UpdateWarehouseDto uppdateWarehouse);

    public ServiceResponse<Page<GetWarehouseDto>> getAll(int pageNo, int pageSize, String sortField, String sortOrder);
}
