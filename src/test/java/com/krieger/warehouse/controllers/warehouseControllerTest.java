package com.krieger.warehouse.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krieger.warehouse.Services.WarehouseService;
import com.krieger.warehouse.dtos.warehouse.GetWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.NewWarehouseDto;
import com.krieger.warehouse.dtos.warehouse.UpdateWarehouseDto;
import com.krieger.warehouse.models.ServiceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class warehouseControllerTest {

    public MockMvc mockMvc;

    @MockBean
    private WarehouseService warehouseService;

    @Autowired
    private WarehauseController warehauseController;

    private ObjectMapper content = new ObjectMapper();

    private GetWarehouseDto warehouseDto =  new GetWarehouseDto(1L,"001","name","address");
    private UpdateWarehouseDto updateWarehouseDto =  new UpdateWarehouseDto(1l,"001","name2","newAddress");
    private NewWarehouseDto newWarehouseDto =  new NewWarehouseDto("001","name","address");
    @BeforeEach
    public void Setup()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(warehauseController).build();
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(warehauseController).isNotNull();
    }

    @Test
    void testGetById() throws Exception {
        // give

        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>(warehouseDto, true, "");
        when(warehouseService.getWarehause(anyLong())).thenReturn(response);

        //When Then
        mockMvc.perform(get("/api/v1/warehouse/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.code").value("001"))
                .andExpect(jsonPath("$.data.name").value("name"))
                .andExpect(jsonPath("$.data.address").value("address"))
                .andDo(print());

    }

    @Test
    void testGetByIdNotFounde() throws Exception {
        // give
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>(null, false, "No value present");
        when(warehouseService.getWarehause(anyLong())).thenReturn(response);

        //When Then
        mockMvc.perform(get("/api/v1/warehouse/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());

    }

    @Test
    void testGetAllBay() throws Exception {
        // Mock the service response
        List<GetWarehouseDto> getWarehouseDtoList = new ArrayList<>();
        getWarehouseDtoList.add( warehouseDto);
        Page<GetWarehouseDto> warehouseDtoPage = new PageImpl<>(getWarehouseDtoList);
        ServiceResponse<Page<GetWarehouseDto>> response = new ServiceResponse<>(warehouseDtoPage, true, "Success");
        when(warehouseService.getAll(anyInt(), anyInt(), anyString(), anyString())).thenReturn(response);

        //When Then
        mockMvc.perform(get("/api/v1/warehouse/")
                .param("pageNo", "1")
                .param("pageSize", "5")
                .param("sortField", "id")
                .param("sortOrder", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content.length()").value(1L))
                .andExpect(jsonPath("$.data.content[0].id").value(1L))
                .andExpect(jsonPath("$.data.content[0].code").value("001"))
                .andExpect(jsonPath("$.data.content[0].name").value("name"))
                .andExpect(jsonPath("$.data.content[0].address").value("address"))
                .andDo(print());
    }

    @Test
    void testPostBay() throws Exception{
        // give
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>(warehouseDto, true, "");
        when(warehouseService.AddWarehause(any())).thenReturn(response);

        //When Then
        mockMvc.perform(post("/api/v1/warehouse/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content.writeValueAsBytes(newWarehouseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.code").value("001"))
                .andExpect(jsonPath("$.data.name").value("name"))
                .andExpect(jsonPath("$.data.address").value("address"))
                .andDo(print());
    }

    @Test
    void testPostBayBadRequest() throws Exception{
        // give
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>(null, false, "");
        when(warehouseService.AddWarehause(any())).thenReturn(response);

        //When Then
        mockMvc.perform(post("/api/v1/warehouse/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content.writeValueAsBytes(newWarehouseDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(print());
    }

    @Test
    void testPutBay() throws Exception{
        // give
        GetWarehouseDto updatedWarehouseDto = new GetWarehouseDto(1l,"001","name2" , "newAddress");
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>(updatedWarehouseDto, true, "");
        when(warehouseService.updateWarehause(any())).thenReturn(response);

        //When Then
        mockMvc.perform(put("/api/v1/warehouse/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content.writeValueAsBytes(updateWarehouseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.code").value("001"))
                .andExpect(jsonPath("$.data.name").value("name2"))
                .andExpect(jsonPath("$.data.address").value("newAddress"))
                .andDo(print());
    }

    @Test
    void testPutBayBadRequest() throws Exception{
        // give
        ServiceResponse<GetWarehouseDto> response = new ServiceResponse<>(null, false, "");
        when(warehouseService.updateWarehause(any())).thenReturn(response);

        //When Then
        mockMvc.perform(put("/api/v1/warehouse/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content.writeValueAsBytes(updateWarehouseDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(print());
    }

    @Test
    void testDeleteBay() throws Exception{
        // give
        doNothing().when(warehouseService).deleteWarehause(anyLong());

        // When Then
        mockMvc.perform(delete("/api/v1/warehouse/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
