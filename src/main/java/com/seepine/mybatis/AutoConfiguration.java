package com.seepine.mybatis;

import com.seepine.mybatis.properties.TenantProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author seepine
 */
@Configuration
@EnableConfigurationProperties({TenantProperties.class})
public class AutoConfiguration {}
