package com.seepine.mybatis.tenant.interceptor;

import org.springframework.lang.NonNull;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * secret 验证接口
 *
 * @author seepine
 */
public interface TenantFilterService {
  /**
   * 填充租户id
   *
   * @param servletRequest request
   * @param servletResponse response
   * @return 租户id，一般为String或Long或Integer
   */
  @NonNull
  Object fill(@NonNull ServletRequest servletRequest, @NonNull ServletResponse servletResponse);
}
