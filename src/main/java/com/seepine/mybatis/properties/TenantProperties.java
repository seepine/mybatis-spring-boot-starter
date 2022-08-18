package com.seepine.mybatis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

/**
 * @author Seepine
 */
@Data
@ConfigurationProperties(prefix = "mybatis.tenant")
public class TenantProperties {
  /** 扫描多租户表实体类包路径 */
  private String[] scanPackages;

  /** 请求头,未实现TenantFilterService时租户id从请求头获取 */
  private String header = "Tenant-Id";
  /** 数据库字段 */
  private String dbField = "tenant_id";
  /** 实体类字段 */
  private String entityField = "tenantId";
  /** 默认租户id */
  private String defaultId = "0";

  /** 扫描多租户表实体类规则 */
  private String resourcePattern = "/**/*.class";
  /** 租户过滤器order值，一般要在权限order之后、其他Component之前 */
  private Integer tenantInterceptorOrder = Ordered.HIGHEST_PRECEDENCE + 10000;
  /**
   * 配置了扫描包路径则说明开启多租户
   *
   * @return 是否开启多租户
   */
  public boolean getEnabled() {
    return scanPackages != null && scanPackages.length > 0;
  }
}
