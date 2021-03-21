package com.dovendev.track.jpa.converters;

import java.time.Duration;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, String> {

  @Override
  public String convertToDatabaseColumn(Duration attribute) {
    return attribute.toString();
  }

  @Override
  public Duration convertToEntityAttribute(String dbData) {
    return Duration.parse(dbData);
  }
}
