package com.developer.homestayersbackend.entity;

import com.developer.homestayersbackend.util.ListingType;
import com.developer.homestayersbackend.util.PropertyTypeEnum;
import com.developer.homestayersbackend.util.SimilarProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PropertyType {
    @Enumerated(EnumType.STRING)
    private SimilarProperties similarProperties;
    @Enumerated(EnumType.STRING)
    private PropertyTypeEnum propertyType;
    @Enumerated(EnumType.STRING)
    private ListingType listingType;
}
