package com.seepine.mybatis.converter;

import com.baomidou.mybatisplus.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author seepine
 */
public class StringToIEnumConverterFactory implements ConverterFactory<String, IEnum<String>> {
  private static final Map<Class<?>, Converter> CONVERTERS = new HashMap<>();

  @Override
  @NonNull
  @SuppressWarnings("unchecked")
  public <E extends IEnum<String>> Converter<String, E> getConverter(@NonNull Class<E> targetType) {
    Converter<String, E> converter = CONVERTERS.get(targetType);
    if (converter == null) {
      converter = new IEnumConverter<>(targetType);
      CONVERTERS.put(targetType, converter);
    }
    return converter;
  }
}
