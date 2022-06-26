package com.okcoupon.okcoupon.beans;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(Category category) {
        if (category == null) {
            return null;
        }
        return category.getCategoryType();
    }

    @Override
    public Category convertToEntityAttribute(String dbCategoryName) {
        if (dbCategoryName == null || dbCategoryName.isEmpty()) {
            return null;
        }
        return Stream.of(Category.values())
                .filter(category -> category.getCategoryType().equals(dbCategoryName))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
