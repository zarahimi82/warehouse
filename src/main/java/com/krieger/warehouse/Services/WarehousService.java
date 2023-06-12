package com.krieger.warehouse.Services;

import com.krieger.warehouse.dtos.warehouse.GetWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.NewWarehousDto;
import com.krieger.warehouse.dtos.warehouse.UpdateWarehousDto;
import com.krieger.warehouse.models.ServiceResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WarehousService {



    public ServiceResponse<GetWarehouseDto> getWarehause(Long id);

    public ServiceResponse<GetWarehouseDto> AddWarehause(NewWarehousDto newWarehaus);

    public void deleteWarehause(Long id);

    public ServiceResponse<GetWarehouseDto> updateWarehause(UpdateWarehousDto uppdateWarehouse);

    public ServiceResponse<Page<GetWarehouseDto>> getAll(int pageNo, int pageSize, String sortField, String sortOrder);
}
