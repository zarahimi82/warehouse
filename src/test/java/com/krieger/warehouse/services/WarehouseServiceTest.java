package com.krieger.warehouse.services;

import com.krieger.warehouse.Services.WarehouseServiceImpl;
import com.krieger.warehouse.dtos.warehouse.GetWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.NewWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.UpdateWarehouseDto;
import com.krieger.warehouse.models.ServiceResponse;
import com.krieger.warehouse.models.Warehouse;
import com.krieger.warehouse.repositories.WarehouseRepository;
import com.krieger.warehouse.validators.ObjectValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @Spy
    private ObjectValidator validator;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;


    @BeforeEach
    public void setup() {

    }

    @Test
    public void testGetWarehouse() {
        // give
        Long warehouseId = 1L;
        Warehouse warehouse = new Warehouse(1l, "001", "name", "address");
        // when
        when(warehouseRepository.findById(any())).thenReturn(java.util.Optional.of(warehouse));

        // then
        ServiceResponse<GetWarehouseDto> response = warehouseService.getWarehouse(warehouseId);

        // Assert
        assertGetWarehouseDto(response);

        // Verify that the repository method was called with the correct argument
        verify(warehouseRepository, times(1)).findById(warehouseId);
    }

    @Test
    public void testAddWarehouse() {
        // Arrange
        NewWarehouseDto newWarehouseDto = new NewWarehouseDto("001", "name", "address");
        Warehouse warehouse = new Warehouse(1l, "001", "name", "address");
        // Set up the mock behavior
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        // Act
        ServiceResponse<GetWarehouseDto> response = warehouseService.AddWarehouse(newWarehouseDto);

        // Assert
        assertGetWarehouseDto(response);

    }

    @Test
    public void testDeleteWarehouse() {
        // Arrange
        Long warehouseId = 1L;

        // Act
        warehouseService.deleteWarehouse(warehouseId);

        // Verify that the repository method was called with the correct argument
        verify(warehouseRepository, times(1)).deleteById(warehouseId);
    }

    @Test
    public void testUpdateWarehouse() {
        // Arrange
        UpdateWarehouseDto updateWarehouseDto = new UpdateWarehouseDto(1l, "001", "name2", "address2");
        Optional<Warehouse> warehouse =Optional.of( new Warehouse(1l, "001", "name", "address"));
        // Set up the mock behavior
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse.get());
        when(warehouseRepository.findById(anyLong())).thenReturn(warehouse);

        // Act
        ServiceResponse<GetWarehouseDto> response = warehouseService.updateWarehouse(updateWarehouseDto);

        // Assert
        assertGetWarehouseDto(response);

    }

    private void assertGetWarehouseDto(ServiceResponse<GetWarehouseDto> response) {
        GetWarehouseDto warehouseDto = response.getData();
        Assertions.assertEquals(response.isSuccess(), true);
        Assertions.assertEquals(response.getMessage(), "");
        Assertions.assertEquals("001", response.getData().getCode());
        Assertions.assertEquals("name2", response.getData().getName());
        Assertions.assertEquals("address2", response.getData().getAddress());
    }
}
