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
import com.krieger.warehouse.Services.BayService;
import com.krieger.warehouse.dtos.bay.*;
import com.krieger.warehouse.models.BayType;
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
import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class BayControllerTest
{

public MockMvc mockMvc;

@MockBean
private BayService bayService;

@Autowired
private BayController bayController;

private ObjectMapper content = new ObjectMapper();

private GetBayDto bayDto =  new GetBayDto(1L,1,1,1, BayType.CART,5,2,new HashSet<>(),"001","Wer1","Add");
@BeforeEach
public void Setup()
        {
        mockMvc = MockMvcBuilders.standaloneSetup(bayController).build();
        }

    @Test
    public void contextLoads() throws Exception {
        assertThat(bayController).isNotNull();
        }

    @Test
    void testGetById() throws Exception {
        // give

        ServiceResponse<GetBayDto> response = new ServiceResponse<>(bayDto, true, "");
        when(bayService.getBay(anyLong())).thenReturn(response);

        //When Then
        mockMvc.perform(get("/api/v1/Bay/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.rowNumber").value(1))
        .andExpect(jsonPath("$.data.shelfNumber").value(1))
        .andExpect(jsonPath("$.data.levelNumber").value(1))
        .andExpect(jsonPath("$.data.type").value("CART"))
        .andExpect(jsonPath("$.data.holldingPoint").value(5))
        .andExpect(jsonPath("$.data.occupiedHoldingPoint").value(2))
        .andExpect(jsonPath("$.data.warehouseCode").value("001"))
        .andExpect(jsonPath("$.data.warehouseName").value("Wer1"))
        .andExpect(jsonPath("$.data.warehouseAddress").value("Add"))

        .andDo(print());

        }

    @Test
    void testGetByIdNotFounde() throws Exception {
        // give
        ServiceResponse<GetBayDto> response = new ServiceResponse<>(null, false, "No value present");
        when(bayService.getBay(anyLong())).thenReturn(response);

        //When Then
        mockMvc.perform(get("/api/v1/Bay/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.data").isEmpty())
        .andDo(print());

        }

    @Test
    void testGetAllBay() throws Exception {
        // Mock the service response
        List<GetBayDto> getBayDtoList = new ArrayList<>();
        getBayDtoList.add( bayDto);
        Page<GetBayDto> bayDtoPage = new PageImpl<>(getBayDtoList);
        ServiceResponse<Page<GetBayDto>> response = new ServiceResponse<>(bayDtoPage, true, "Success");
        when(bayService.getAll(anyInt(), anyInt(), anyString(), anyString())).thenReturn(response);

        //When Then
        mockMvc.perform(get("/api/v1/Bay/")
        .param("pageNo", "1")
        .param("pageSize", "5")
        .param("sortField", "id")
        .param("sortOrder", "ASC"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.content.length()").value(1L))
        .andExpect(jsonPath("$.data.content[0].rowNumber").value(1))
        .andExpect(jsonPath("$.data.content[0].shelfNumber").value(1))
        .andExpect(jsonPath("$.data.content[0].levelNumber").value(1))
        .andExpect(jsonPath("$.data.content[0].type").value("CART"))
        .andExpect(jsonPath("$.data.content[0].holldingPoint").value(5))
        .andExpect(jsonPath("$.data.content[0].occupiedHoldingPoint").value(2))
        .andExpect(jsonPath("$.data.content[0].warehouseCode").value("001"))
        .andExpect(jsonPath("$.data.content[0].warehouseName").value("Wer1"))
        .andExpect(jsonPath("$.data.content[0].warehouseAddress").value("Add"))
        .andDo(print());
        }

    @Test
    void testPostBay() throws Exception{
            // give
        NewBayDto newBayDto =  new NewBayDto(1,1,1, BayType.CART,5,2,new HashSet<>(),1l);
        ServiceResponse<GetBayDto> response = new ServiceResponse<>(bayDto, true, "");
        when(bayService.AddBay(any())).thenReturn(response);

        //When Then
        mockMvc.perform(post("/api/v1/Bay/")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(content.writeValueAsBytes(newBayDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.rowNumber").value(1))
                .andExpect(jsonPath("$.data.shelfNumber").value(1))
                .andExpect(jsonPath("$.data.levelNumber").value(1))
                .andExpect(jsonPath("$.data.type").value("CART"))
                .andExpect(jsonPath("$.data.holldingPoint").value(5))
                .andExpect(jsonPath("$.data.occupiedHoldingPoint").value(2))
                .andExpect(jsonPath("$.data.warehouseCode").value("001"))
                .andExpect(jsonPath("$.data.warehouseName").value("Wer1"))
                .andExpect(jsonPath("$.data.warehouseAddress").value("Add"))
        .andDo(print());
        }

    @Test
    void testPostBayBadRequest() throws Exception{
        // give
        NewBayDto newBayDto =  new NewBayDto(1,1,1, BayType.CART,5,2,new HashSet<>(),1l);
        ServiceResponse<GetBayDto> response = new ServiceResponse<>(null, false, "");
        when(bayService.AddBay(any())).thenReturn(response);

        //When Then
        mockMvc.perform(post("/api/v1/Bay/")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(content.writeValueAsBytes(newBayDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andDo(print());
        }

    @Test
    void testPutBay() throws Exception{
            // give
            UpdateBayDto updatearehouseDto =  new UpdateBayDto(1l,1,1,1, BayType.CART,5,2,new HashSet<>());
            ServiceResponse<GetBayDto> response = new ServiceResponse<>(bayDto, true, "");
        when(bayService.updateBay(any())).thenReturn(response);

        //When Then
         mockMvc.perform(put("/api/v1/Bay/")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(content.writeValueAsBytes(updatearehouseDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.rowNumber").value(1))
                .andExpect(jsonPath("$.data.shelfNumber").value(1))
                .andExpect(jsonPath("$.data.levelNumber").value(1))
                .andExpect(jsonPath("$.data.type").value("CART"))
                .andExpect(jsonPath("$.data.holldingPoint").value(5))
                .andExpect(jsonPath("$.data.occupiedHoldingPoint").value(2))
                .andExpect(jsonPath("$.data.warehouseCode").value("001"))
                .andExpect(jsonPath("$.data.warehouseName").value("Wer1"))
                .andExpect(jsonPath("$.data.warehouseAddress").value("Add"))
        .andDo(print());
}

    @Test
    void testPutBayBadRequest() throws Exception{
        // give
        UpdateBayDto updateBayDto =   new UpdateBayDto(1l,1,1,1, BayType.CART,5,2,new HashSet<>());
        ServiceResponse<GetBayDto> response = new ServiceResponse<>(null, false, "");
        when(bayService.updateBay(any())).thenReturn(response);

        //When Then
        mockMvc.perform(put("/api/v1/Bay/")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(content.writeValueAsBytes(updateBayDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.success").value(false))
        .andDo(print());
        }

    @Test
    void testDeleteBay() throws Exception{
            // give
            doNothing().when(bayService).deleteBay(anyLong());

            // When Then
            mockMvc.perform(delete("/api/v1/Bay/1"))
            .andExpect(status().isOk())
            .andDo(print());
            }

}


