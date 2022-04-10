package com.seepine.mybatis.enums;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author seepine
 */
@Configuration
public class EnumWebMvcConfig implements WebMvcConfigurer {
  @Override
  public void addFormatters(@NonNull FormatterRegistry registry) {
    registry.addConverterFactory(new StringToIEnumConverterFactory());
  }
}
