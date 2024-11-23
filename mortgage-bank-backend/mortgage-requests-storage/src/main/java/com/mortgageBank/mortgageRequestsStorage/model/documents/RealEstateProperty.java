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
    private MortgageComposition mortgageComposition;

    public static RealEstateProperty of(RealEstatePropertyDto dto) {
        return RealEstateProperty
                .builder()
                .propertyType(dto.getPropertyType())
                .propertyStatus(dto.getPropertyStatus())
                .price(dto.getPrice())
                .value(dto.getValue())
                .existingLeinAmount(dto.getExistingLeinAmount())
                .securityNumber(dto.getSecurityNumber())
                .city(dto.getCity())
                .street(dto.getStreet())
                .houseNumber(dto.getHouseNumber())
                .floor(dto.getFloor())
                .appartment(dto.getAppartment())
                .block(dto.getBlock())
                .plot(dto.getPlot())
                .subPlot(dto.getSubPlot())
                .mortgageComposition(MortgageComposition
                        .of(dto.getMortgageComposition()))
                .build();
    }
}
