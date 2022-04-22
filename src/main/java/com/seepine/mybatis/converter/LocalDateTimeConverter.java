package com.seepine.mybatis.converter;

import com.seepine.mybatis.JacksonConfig;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author seepins
 */
public class LocalDateTimeConverter {
  public static class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String s) {
      DateTimeFormatter formatter =
          DateTimeFormatter.ofPattern(JacksonConfig.NORM_DATETIME_PATTERN, Locale.CHINESE);
      return LocalDateTime.parse(s, formatter);
    }
  }

  public static class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
      DateTimeFormatter formatter =
          DateTimeFormatter.ofPattern(JacksonConfig.NORM_DATE_PATTERN, Locale.CHINESE);
      return LocalDate.parse(s, formatter);
    }
  }
}
