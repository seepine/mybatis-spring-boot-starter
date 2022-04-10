package com.seepine.mybatis.tenant;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.seepine.mybatis.properties.TenantProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

/**
 * @author seepine
 */
public class TenantHandler implements TenantLineHandler {
  TenantProperties tenantProperties;

  public TenantHandler(TenantProperties tenantProperties) {
    this.tenantProperties = tenantProperties;
  }

  @Override
  public Expression getTenantId() {
    String tenantId = TenantUtil.getTenantId();
    if (tenantId == null) {
      throw new IllegalArgumentException("租户id为空");
    }
    return new StringValue(tenantId);
  }

  @Override
  public boolean ignoreTable(String tableName) {
    return !TenantUtil.getTables().contains(tableName);
  }

  @Override
  public String getTenantIdColumn() {
    return tenantProperties.getDbField();
  }
}
