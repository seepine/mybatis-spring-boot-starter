package com.seepine.mybatis.tenant;

import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.seepine.mybatis.properties.TenantProperties;

/**
 * @author seepine
 */
public class TenantInnerInterceptor extends TenantLineInnerInterceptor {
  public TenantInnerInterceptor(TenantProperties tenantProperties) {
    super(new TenantHandler(tenantProperties));
  }
}
