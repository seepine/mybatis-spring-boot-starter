package com.seepine.mybatis.tenant.interceptor.config;

import com.seepine.mybatis.AutoConfiguration;
import com.seepine.mybatis.properties.TenantProperties;
import com.seepine.mybatis.tenant.interceptor.TenantFilterService;
import com.seepine.mybatis.tenant.interceptor.impl.MybatisTenantServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author seepine
 * @since 0.1.0
 */
@Slf4j
@Configuration
@AutoConfigureAfter(AutoConfiguration.class)
public class TenantServiceConfiguration {

  @Resource private TenantProperties tenantProperties;

  @Bean
  @ConditionalOnMissingBean(TenantFilterService.class)
  public TenantFilterService tenantFilterService() {
    return new MybatisTenantServiceImpl(tenantProperties);
  }
}
