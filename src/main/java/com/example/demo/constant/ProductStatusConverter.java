package com.example.demo.constant;

import com.example.demo.controller.ProductController;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProductStatusConverter implements AttributeConverter<ProductStatus, String> {

    @Override
    public String convertToDatabaseColumn(ProductStatus productStatus) {
        return (productStatus != null ? productStatus.getStatus() : "");
    }

    @Override
    public ProductStatus convertToEntityAttribute(String status) {
        return (status != null) ? ProductStatus.getEnum(status) : null;
    }
}
