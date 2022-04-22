package com.seepine.mybatis.converter;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author seepine
 */
@Slf4j
public class IEnumConverter<T extends Serializable, E extends IEnum<T>> implements Converter<T, E> {
  private final Map<String, E> enumMap = new HashMap<>();

  public IEnumConverter(Class<E> enumType) {
    E[] enums = enumType.getEnumConstants();
    for (E e : enums) {
      enumMap.put(e.getValue().toString(), e);
    }
  }

  @Override
  public E convert(@NonNull T source) {
    E t = enumMap.get(source.toString());
    // 允许找不到对应的枚举
    if (t == null) {
      log.error("IEnumConverter:找不到对应的枚举:[{}]", source);
    }
    return t;
  }
}
