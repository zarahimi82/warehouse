package com.krieger.warehouse.Services;

import com.krieger.warehouse.dtos.bay.GetBayDto;
import com.krieger.warehouse.dtos.bay.NewBayDto;
import com.krieger.warehouse.dtos.bay.UpdateBayDto;
import com.krieger.warehouse.models.ServiceResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BayService {
    public ServiceResponse<Page<GetBayDto>> getAll(int pageNo, int pageSize, String sortField, String sortOrder);

    public ServiceResponse<GetBayDto> getBay(Long id);

    public ServiceResponse<GetBayDto> addBay(NewBayDto newBay);

    public void deleteBay(Long id);

    public ServiceResponse<GetBayDto> updateBay(UpdateBayDto b);
}
