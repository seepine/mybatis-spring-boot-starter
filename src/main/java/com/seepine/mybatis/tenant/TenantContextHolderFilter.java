package com.seepine.mybatis.tenant;

import com.seepine.mybatis.properties.TenantProperties;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author seepine
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10000)
@AllArgsConstructor
public class TenantContextHolderFilter extends GenericFilterBean {
  private final TenantProperties tenantProperties;

  @Override
  @SneakyThrows
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String tenantId = request.getHeader(tenantProperties.getHeader());
    log.debug("获取header中的租户ID为:{}", tenantId);
    if (tenantId != null && !"".equals(tenantId)) {
      TenantUtil.setTenantId(tenantId);
    } else {
      TenantUtil.setTenantId(tenantProperties.getDefaultId());
    }
    filterChain.doFilter(request, response);
    TenantUtil.clear();
  }
}
