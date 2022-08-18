package com.seepine.mybatis.tenant.interceptor;

import com.seepine.mybatis.tenant.TenantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author seepine
 * @since 0.1.0
 */
@Slf4j
public class TenantHandlerInterceptor implements HandlerInterceptor {
  private final TenantFilterService tenantFilterService;

  public TenantHandlerInterceptor(TenantFilterService tenantFilterService) {
    this.tenantFilterService = tenantFilterService;
  }

  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest httpServletRequest,
      @NonNull HttpServletResponse httpServletResponse,
      @NonNull Object handler) {
    Object tenantId = tenantFilterService.fill(httpServletRequest, httpServletResponse);
    log.debug("获取header中的租户ID为:{}", tenantId);
    TenantUtil.setTenantId(tenantId);
    return true;
  }
  /**
   * clear ThreadLocal
   *
   * @param httpServletRequest httpServletRequest
   * @param httpServletResponse httpServletResponse
   * @param o o
   * @param e e
   */
  @Override
  public void afterCompletion(
      @NonNull HttpServletRequest httpServletRequest,
      @NonNull HttpServletResponse httpServletResponse,
      @NonNull Object o,
      Exception e) {
    TenantUtil.clear();
  }
}
