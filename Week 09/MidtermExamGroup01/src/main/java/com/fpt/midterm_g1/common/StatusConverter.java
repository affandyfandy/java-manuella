package com.fpt.midterm_g1.common;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status status) {
        return status == null ? null : status.name();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Status.valueOf(dbData);
    }
}
