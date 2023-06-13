package com.krieger.warehouse.Services;

import com.krieger.warehouse.dtos.warehouse.GetWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.NewWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.UpdateWarehouseDto;
import com.krieger.warehouse.models.ServiceResponse;
import com.krieger.warehouse.models.Warehouse;
import com.krieger.warehouse.repositories.WarehaousRepository;
import com.krieger.warehouse.validators.ObjectValidator;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehaousRepository warehaousRepository;
    private static final ModelMapper modelMapper = new ModelMapper();
    private final ObjectValidator validator;


    public WarehouseServiceImpl(WarehaousRepository warehaousRepository, ObjectValidator validator) {
        this.warehaousRepository = warehaousRepository;
        this.validator = validator;
    }

    @Override
    public ServiceResponse<GetWarehouseDto> getWarehause(Long id) {
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>();
        try {
            Warehouse warehouse = warehaousRepository.findById(id).get();
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
    public ServiceResponse<GetWarehouseDto> AddWarehause(NewWarehouseDto newWarehaus)
    {   validator.validate(newWarehaus);
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>();
        try {
            Warehouse warehouse = new Warehouse(newWarehaus.getCode() , newWarehaus.getName() , newWarehaus.getAddress());
            warehaousRepository.save(warehouse);
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
    public void deleteWarehause(Long id) {
               warehaousRepository.deleteById(id);
    }

    @Override
    public ServiceResponse<GetWarehouseDto> updateWarehause(UpdateWarehouseDto uppdateWarehouseDto) {
        validator.validate(uppdateWarehouseDto);
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>();
        try {
            Warehouse warehouse = warehaousRepository.findById(uppdateWarehouseDto.getId()).get();
            warehouse.setAddress(uppdateWarehouseDto.getAddress());
            warehouse.setCode(uppdateWarehouseDto.getCode());
            warehouse.setName(uppdateWarehouseDto.getName());
            warehaousRepository.save(warehouse);
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
            Page<Warehouse> warehouses = warehaousRepository.findAll(pageable);
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
