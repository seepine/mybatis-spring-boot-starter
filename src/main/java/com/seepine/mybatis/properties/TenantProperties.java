package com.seepine.mybatis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Seepine
 */
@Data
@ConfigurationProperties(prefix = "mybatis.tenant")
public class TenantProperties {
  /** 扫描多租户表实体类包路径 */
  private String scanPackage = "";

  /** 请求头 */
  private String header = "Tenant-Id";
  /** 数据库字段 */
  private String dbField = "tenant_id";
  /** 实体类字段 */
  private String entityField = "tenantId";
  /** 默认租户id */
  private Object defaultId = 0L;

  /** 扫描多租户表实体类规则 */
  private String resourcePattern = "/**/*.class";

  /**
   * 配置了扫描包路径则说明开启多租户
   *
   * @return 是否开启多租户
   */
  public boolean getEnabled() {
    return scanPackage != null && !"".equals(scanPackage);
  }
}
