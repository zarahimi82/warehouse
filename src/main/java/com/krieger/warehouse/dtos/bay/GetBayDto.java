package com.krieger.warehouse.dtos.bay;

import com.krieger.warehouse.models.BayType;
import com.krieger.warehouse.models.Warehouse;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBayDto {
    private Long id;

    private int rowNumber;

    private int shelfNumber;

    private int levelNumber;

    private BayType type;

    private int holldingPoint;

    private int occupiedHoldingPoint;

    private HashSet<String> tags;

    private String warehouseCode;

    private String warehouseName;

    private String warehouseAddress;

}
