package com.krieger.warehouse.dtos.warehouse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetWarehouseDto {
    private Long id;

    private String code;

    private String name;

    private String address;
}
