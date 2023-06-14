package com.krieger.warehouse.services;

import com.krieger.warehouse.Services.BayServiceImpl;
import com.krieger.warehouse.converters.TagListConverter;
import com.krieger.warehouse.converters.TagStringConverter;
import com.krieger.warehouse.dtos.bay.GetBayDto;
import com.krieger.warehouse.dtos.bay.NewBayDto;
import com.krieger.warehouse.dtos.bay.UpdateBayDto;
import com.krieger.warehouse.models.Bay;
import com.krieger.warehouse.models.BayType;
import com.krieger.warehouse.models.ServiceResponse;
import com.krieger.warehouse.models.Warehouse;
import com.krieger.warehouse.repositories.BayRepository;
import com.krieger.warehouse.validators.ObjectValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BayServiceTest {

    @Mock
    private BayRepository bayRepository;

    @Spy
    private ObjectValidator validator;

    @Spy
    private TagStringConverter tagStringConverter;

    @Spy
    private TagListConverter tagListConverter;

    @InjectMocks
    private BayServiceImpl bayService;


    @Test
    public void testGetBay() {
        // give
        Long bayId = 1L;
        Bay bay = new Bay(1l,1,1,1, BayType.CART,5,1,"tag1;Tag2",new Warehouse(1l,"001","Name","Address"));
        // when
        when(bayRepository.findById(any())).thenReturn(java.util.Optional.of(bay));

        // then
        ServiceResponse<GetBayDto> response = bayService.getBay(bayId);

        // Assert
        assertGetBayDto(response);


        // Verify that the repository method was called with the correct argument
        verify(bayRepository, times(1)).findById(bayId);
    }

    @Test
    public void testAddBay() {
        // Arrange
        NewBayDto newBayDto = new NewBayDto(1,1,1, BayType.CART,5,1,new HashSet<>(Arrays.asList("a", "b", "c")),1l);
        Bay warehouse = new Bay(1l,1,1,1, BayType.CART,5,1,"tag1;Tag2",new Warehouse(1l,"001","Name","Address"));;
        // Set up the mock behavior
        when(bayRepository.save(any(Bay.class))).thenReturn(warehouse);

        // Act
        ServiceResponse<GetBayDto> response = bayService.AddBay(newBayDto);

        // Assert
        assertGetBayDto(response);

    }

    @Test
    public void testDeleteBay() {
        // Arrange
        Long BayId = 1L;
        // Set up the mock behavior
        Mockito.doNothing().when(bayRepository).deleteById(anyLong());
        // Act
        bayService.deleteBay(BayId);

        // Verify that the repository method was called with the correct argument
        verify(bayRepository, times(1)).deleteById(BayId);
    }

    @Test
    public void testUpdateBay() {
        // Arrange
        UpdateBayDto updateBayDto = new UpdateBayDto(1l,1,1,1, BayType.CART,5,1, new HashSet<>(Arrays.asList("tag1", "Tag2")));
        Optional<Bay> warehouse =Optional.of( new Bay(1l,1,1,1, BayType.CART,5,1,"tag1;Tag2",new Warehouse(1l,"001","Name","Address")));
        // Set up the mock behavior
        when(bayRepository.save(any(Bay.class))).thenReturn(warehouse.get());
        when(bayRepository.findById(anyLong())).thenReturn(warehouse);

        // Act
        ServiceResponse<GetBayDto> response = bayService.updateBay(updateBayDto);

        // Assert
        assertGetBayDto(response);

    }
    private void assertGetBayDto(ServiceResponse<GetBayDto> response) {
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("", response.getMessage());
        GetBayDto getBayDto = response.getData();
        Assertions.assertEquals(1, getBayDto.getRowNumber());
        Assertions.assertEquals(1, getBayDto.getShelfNumber());
        Assertions.assertEquals(1, getBayDto.getLevelNumber());
        Assertions.assertEquals(BayType.CART, getBayDto.getType());
        Assertions.assertEquals(5, getBayDto.getHolldingPoint());
        Assertions.assertEquals(1, getBayDto.getOccupiedHoldingPoint());
        Assertions.assertEquals(new HashSet<>(Arrays.asList("tag1", "Tag2")), getBayDto.getTags());
        Assertions.assertEquals("001", getBayDto.getWarehouseCode());
        Assertions.assertEquals("Name", getBayDto.getWarehouseName());
        Assertions.assertEquals("Address", getBayDto.getWarehouseAddress());
    }


}
