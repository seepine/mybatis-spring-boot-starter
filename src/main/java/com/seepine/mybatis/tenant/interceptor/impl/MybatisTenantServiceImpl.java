package com.seepine.mybatis.tenant.interceptor.impl;

import com.seepine.mybatis.properties.TenantProperties;
import com.seepine.mybatis.tenant.interceptor.TenantFilterService;
import org.springframework.lang.NonNull;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 默认租户id填充实现类，从请求头获取租户id
 *
 * <p>生产环境建议从token对应的登录用户信息获取，避免被人恶意传递请求头拿到其他租户数据
 *
 * @author seepine
 * @since 0.1.0
 */
public class MybatisTenantServiceImpl implements TenantFilterService {
  private final TenantProperties tenantProperties;

  public MybatisTenantServiceImpl(TenantProperties tenantProperties) {
    this.tenantProperties = tenantProperties;
  }

  @NonNull
  @Override
  public Object fill(
      @NonNull ServletRequest servletRequest, @NonNull ServletResponse servletResponse) {
    try {
      HttpServletRequest request = (HttpServletRequest) servletRequest;
      String tenantId = request.getHeader(tenantProperties.getHeader());
      if (tenantId != null && !"".equals(tenantId)) {
        return tenantId;
      }
    } catch (Exception ignored) {
    }
    return tenantProperties.getDefaultId();
  }
}
