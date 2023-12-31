package com.krieger.warehouse.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class ObjectNotValidException extends RuntimeException {

    private final Set<String> exceptionMessages;
}
