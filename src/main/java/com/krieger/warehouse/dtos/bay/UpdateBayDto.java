package com.krieger.warehouse.dtos.bay;

import com.krieger.warehouse.models.BayType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBayDto {
    private Long id;

    private int rowNumber;

    private int shelfNumber;

    private int levelNumber;

    @NotNull(message = "Bay Type is required")
    private BayType type;

    @Min(value = 1, message = "Value must be greater than or equal to 1")
    @Max(value = 9, message = "Value must be less than or equal to 9")
    private int holldingPoint;

    private int occupiedHoldingPoint;

    private HashSet<String> tags;

}
