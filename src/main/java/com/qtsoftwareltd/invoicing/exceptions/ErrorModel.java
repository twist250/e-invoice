package com.qtsoftwareltd.invoicing.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorModel {
    private String fieldName;
    private Object rejectedValue;
    private String messageError;
}