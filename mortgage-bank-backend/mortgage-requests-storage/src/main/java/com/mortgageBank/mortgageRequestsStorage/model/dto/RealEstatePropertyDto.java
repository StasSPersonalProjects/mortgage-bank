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

    private PropertyType propertyType;
    private PropertyStatus propertyStatus;
    private int price;
    private int value;
    private int existingLeinAmount;
    private long securityNumber;
    private String city;
    private String street;
    private int houseNumber;
    private int floor;
    private int appartment;
    private long block;
    private long plot;
    private long subPlot;
    private MortgageCompositionDto mortgageComposition;

    public static RealEstatePropertyDto of(RealEstateProperty realEstateProperty) {
        return RealEstatePropertyDto
                .builder()
                .propertyType(realEstateProperty.getPropertyType())
                .propertyStatus(realEstateProperty.getPropertyStatus())
                .price(realEstateProperty.getPrice())
                .value(realEstateProperty.getValue())
                .existingLeinAmount((realEstateProperty.getExistingLeinAmount()))
                .securityNumber(realEstateProperty.getSecurityNumber())
                .city(realEstateProperty.getCity())
                .street(realEstateProperty.getStreet())
                .houseNumber(realEstateProperty.getHouseNumber())
                .floor(realEstateProperty.getFloor())
                .appartment(realEstateProperty.getAppartment())
                .block(realEstateProperty.getBlock())
                .plot(realEstateProperty.getPlot())
                .subPlot(realEstateProperty.getSubPlot())
                .mortgageComposition(MortgageCompositionDto
                        .of(realEstateProperty.getMortgageComposition()))
                .build();
    }
}
