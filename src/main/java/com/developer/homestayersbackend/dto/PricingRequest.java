package com.developer.homestayersbackend.dto;

import com.developer.homestayersbackend.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricingRequest {
    private BigDecimal price;
    private String currency;
    private List<Discount> discounts;
}
