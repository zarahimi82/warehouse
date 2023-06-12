package com.krieger.warehouse.dtos.warehouse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewWarehousDto {
    @NotNull(message = "Code is required")
    @Pattern(regexp = "\\d{3}", message = "Code must contain exactly 3 digits")
    private String code;

    private String name;

    @NotNull(message = "address is required")
    private String address;
}
