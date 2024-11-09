package com.mortgageBank.mortgageRequestsStorage.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UpdateRequestDto {

    private Long id;
    private Map<String, Object> fields;

}
