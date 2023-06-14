package com.krieger.warehouse.Services;

import com.krieger.warehouse.dtos.warehouse.GetWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.NewWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.UpdateWarehouseDto;
import com.krieger.warehouse.models.ServiceResponse;
import com.krieger.warehouse.models.Warehouse;
import com.krieger.warehouse.repositories.WarehouseRepository;
import com.krieger.warehouse.validators.ObjectValidator;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private static final ModelMapper modelMapper = new ModelMapper();
    private final ObjectValidator validator;


    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, ObjectValidator validator) {
        this.warehouseRepository = warehouseRepository;
        this.validator = validator;
    }

    @Override
    public ServiceResponse<GetWarehouseDto> getWarehouse(Long id) {
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>();
        try {
            Warehouse warehouse = warehouseRepository.findById(id).get();
            response.setData(modelMapper.map(warehouse  , GetWarehouseDto.class));
            response.setSuccess(true);
        }
        catch (Exception e)
        {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<GetWarehouseDto> AddWarehouse(NewWarehouseDto newWarehouse)
    {   validator.validate(newWarehouse);
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>();
        try {
            Warehouse warehouse = new Warehouse(newWarehouse.getCode() , newWarehouse.getName() , newWarehouse.getAddress());
            warehouseRepository.save(warehouse);
            response.setData(modelMapper.map(warehouse  , GetWarehouseDto.class));
            response.setSuccess(true);
        }
        catch (Exception e)
        {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public void deleteWarehouse(Long id) {
               warehouseRepository.deleteById(id);
    }

    @Override
    public ServiceResponse<GetWarehouseDto> updateWarehouse(UpdateWarehouseDto uppdateWarehouseDto) {
        validator.validate(uppdateWarehouseDto);
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>();
        try {
            Warehouse warehouse = warehouseRepository.findById(uppdateWarehouseDto.getId()).get();
            warehouse.setAddress(uppdateWarehouseDto.getAddress());
            warehouse.setCode(uppdateWarehouseDto.getCode());
            warehouse.setName(uppdateWarehouseDto.getName());
            warehouseRepository.save(warehouse);
            response.setData(modelMapper.map(warehouse,GetWarehouseDto.class));
            response.setSuccess(true);
        }
        catch (Exception e)
        {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public ServiceResponse<Page<GetWarehouseDto>> getAll(int pageNo, int pageSize, String sortField, String sortDirection ) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo-1 , pageSize,sort);
        ServiceResponse<Page<GetWarehouseDto>> response = new ServiceResponse<>();
        try {
            Page<Warehouse> warehouses = warehouseRepository.findAll(pageable);
            response.setData(warehouses.map(W  -> modelMapper.map(W, GetWarehouseDto.class)));
            response.setSuccess(true);
        }
        catch (Exception e)
        {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
