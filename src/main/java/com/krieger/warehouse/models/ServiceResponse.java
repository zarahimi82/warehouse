package com.krieger.warehouse.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> {

    private T data;
    private boolean success = true;
    private String message = "";
}
