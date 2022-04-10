package com.seepine.mybatis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author seepine
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfig {
  public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
  public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
  public static final String NORM_TIME_PATTERN = "HH:mm:ss";

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customizer() {
    return builder -> {
      builder.locale(Locale.CHINA);
      builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
      builder.simpleDateFormat(NORM_DATETIME_PATTERN);
      // 日期序列号
      builder.modules(new JavaTimeModule());
      // Long序列号，解决前端精度丢失
      builder.serializerByType(Long.class, ToStringSerializer.instance);
      // MybatisPlus 序列化枚举值为数据库存储值
      builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    };
  }

  static class JavaTimeModule extends SimpleModule {
    public JavaTimeModule() {
      super(PackageVersion.VERSION);
      this.addSerializer(
          LocalDateTime.class,
          new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN)));
      this.addSerializer(
          LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(NORM_DATE_PATTERN)));
      this.addSerializer(
          LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(NORM_TIME_PATTERN)));
      this.addDeserializer(
          LocalDateTime.class,
          new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN)));
      this.addDeserializer(
          LocalDate.class,
          new LocalDateDeserializer(DateTimeFormatter.ofPattern(NORM_DATE_PATTERN)));
      this.addDeserializer(
          LocalTime.class,
          new LocalTimeDeserializer(DateTimeFormatter.ofPattern(NORM_TIME_PATTERN)));
    }
  }
}
