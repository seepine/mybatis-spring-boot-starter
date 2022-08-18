package com.seepine.mybatis.tenant.interceptor.config;

import com.seepine.mybatis.properties.TenantProperties;
import com.seepine.mybatis.tenant.interceptor.TenantFilterService;
import com.seepine.mybatis.tenant.interceptor.TenantHandlerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author seepine
 * @since 0.1.0
 */
@Configuration
@AutoConfigureAfter(TenantServiceConfiguration.class)
public class TenantInterceptorConfiguration implements WebMvcConfigurer {
  @Resource private TenantProperties tenantProperties;
  @Resource private TenantFilterService tenantFilterService;

  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    if (tenantProperties.getEnabled()) {
      registry
          .addInterceptor(new TenantHandlerInterceptor(tenantFilterService))
          .addPathPatterns("/**")
          .excludePathPatterns("/error")
          .order(tenantProperties.getTenantInterceptorOrder());
    }
  }
}
