package com.krieger.warehouse.Services;

import com.krieger.warehouse.converters.TagListConverter;
import com.krieger.warehouse.converters.TagStringConverter;
import com.krieger.warehouse.dtos.bay.GetBayDto;
import com.krieger.warehouse.dtos.bay.NewBayDto;
import com.krieger.warehouse.dtos.bay.UpdateBayDto;
import com.krieger.warehouse.models.Bay;
import com.krieger.warehouse.models.ServiceResponse;
import com.krieger.warehouse.repositories.BayRepository;
import com.krieger.warehouse.validators.ObjectValidator;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BayServiceImpl implements BayService {

    private final BayRepository bayRepository;
    private static final ModelMapper modelMapper = new ModelMapper();
    private final TagListConverter tagListConverter;
    private final TagStringConverter tagStringConverter;
    private final ObjectValidator validator;

    public BayServiceImpl(BayRepository bayRepository, TagListConverter tagListConverter, TagStringConverter tagStringConverter, ObjectValidator validator) {
        this.bayRepository = bayRepository;
        this.tagListConverter = tagListConverter;
        this.tagStringConverter = tagStringConverter;
        this.validator = validator;
    }

    @Override
    public ServiceResponse<Page<GetBayDto>> getAll(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        ServiceResponse<Page<GetBayDto>> response = new ServiceResponse<>();
        try {
            Page<Bay> bays = bayRepository.findAll(pageable);
            response.setData(bays.map(W -> modelMapper.map(W, GetBayDto.class)));
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<GetBayDto> getBay(Long id) {
        ServiceResponse<GetBayDto> response = new ServiceResponse<>();
        try {
            response.setData(getDtoMapper(bayRepository.findById(id).get()));
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<GetBayDto> addBay(NewBayDto newBay) {
        validator.validate(newBay);
        ServiceResponse<GetBayDto> response = new ServiceResponse<>();
        try {
            Bay newB = newDtoMapper(newBay);
            Bay savedBay = bayRepository.save(newB);
            response.setData(getDtoMapper(savedBay));
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    @Override
    public void deleteBay(Long id) {
        bayRepository.deleteById(id);
    }

    @Override
    public ServiceResponse<GetBayDto> updateBay(UpdateBayDto updateBay) {
        validator.validate(updateBay);
        ServiceResponse<GetBayDto> response = new ServiceResponse<>();
        try {
            Bay bay = bayRepository.findById(updateBay.getId()).get();
            bay.setHolldingPoint(updateBay.getHolldingPoint());
            bay.setLevelNumber(updateBay.getLevelNumber());
            bay.setRowNumber(updateBay.getRowNumber());
            bay.setShelfNumber(updateBay.getShelfNumber());
            bay.setType(updateBay.getType());
            bay.setOccupiedHoldingPoint(updateBay.getOccupiedHoldingPoint());
            bay.setTags(updateBay.getTags() != null ? updateBay.getTags().stream().collect(Collectors.joining(";")) : null);

            bayRepository.save(bay);
            response.setData(getDtoMapper(bay));
            response.setSuccess(true);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    private Bay newDtoMapper(NewBayDto newBay) {
        modelMapper.typeMap(NewBayDto.class, Bay.class).addMappings(mapper -> mapper.skip(Bay::setId));
        modelMapper.typeMap(NewBayDto.class, Bay.class).addMappings(mapper -> mapper.using(tagListConverter)
                .map(NewBayDto::getTags, Bay::setTags));
        return modelMapper.map(newBay, Bay.class);
    }

    private GetBayDto getDtoMapper(Bay bay) {
        modelMapper.typeMap(Bay.class, GetBayDto.class).addMappings(mapper -> mapper.using(tagStringConverter)
                .map(Bay::getTags, GetBayDto::setTags));
        return modelMapper.map(bay, GetBayDto.class);
    }
}
