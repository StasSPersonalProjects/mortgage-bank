package com.mortgageBank.mortgageRequestsStorage.model.documents;

import com.mortgageBank.mortgageRequestsStorage.model.dto.RealEstatePropertyDto;
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
public class RealEstateProperty {

    private String location;
    private PropertyType propertyType;
    private PropertyStatus propertyStatus;
    private int price;
    private int value;

    public static RealEstateProperty of(RealEstatePropertyDto dto) {
        return RealEstateProperty
                .builder()
                .location(dto.getLocation())
                .propertyType(dto.getPropertyType())
                .propertyStatus(dto.getPropertyStatus())
                .price(dto.getPrice())
                .value(dto.getValue())
                .build();
    }
}
