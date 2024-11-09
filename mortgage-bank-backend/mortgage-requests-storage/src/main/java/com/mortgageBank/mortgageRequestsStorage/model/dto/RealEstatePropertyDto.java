package com.mortgageBank.mortgageRequestsStorage.model.dto;

import com.mortgageBank.mortgageRequestsStorage.model.documents.RealEstateProperty;
import com.mortgageBank.mortgageRequestsStorage.model.enums.PropertyStatus;
import com.mortgageBank.mortgageRequestsStorage.model.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RealEstatePropertyDto {

    private String location;
    private PropertyType propertyType;
    private PropertyStatus propertyStatus;
    private int price;
    private int value;

    public static RealEstatePropertyDto of(RealEstateProperty realEstateProperty) {
        return RealEstatePropertyDto
                .builder()
                .location(realEstateProperty.getLocation())
                .propertyType(realEstateProperty.getPropertyType())
                .propertyStatus(realEstateProperty.getPropertyStatus())
                .price(realEstateProperty.getPrice())
                .value(realEstateProperty.getValue())
                .build();
    }
}
