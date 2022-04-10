package com.seepine.mybatis.tenant;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 租户工具类
 *
 * @author seepine
 */
@Slf4j
@UtilityClass
public class TenantUtil {
  private static final List<String> TENANT_TABLE = new ArrayList<>();
  private final ThreadLocal<Object> THREAD_LOCAL_TENANT = new ThreadLocal<>();
  private static final String EMPTY = "";

  /**
   * 添加多租户表
   *
   * @param tableName 表名，对应数据库，例如sys_user
   */
  void addIgnoreTable(String tableName) {
    if (tableName != null && !EMPTY.equals(tableName)) {
      TENANT_TABLE.add(tableName);
    }
  }

  /**
   * 获取多租户表名
   *
   * @return List<String>
   */
  List<String> getTables() {
    return TENANT_TABLE;
  }
  /**
   * TTL 设置租户ID<br>
   * <b>谨慎使用此方法,避免嵌套调用。尽量使用 {@code TenantBroker} </b>
   *
   * @param tenantId 租户id
   */
  void setTenantId(Object tenantId) {
    THREAD_LOCAL_TENANT.set(tenantId);
  }

  /**
   * 获取TTL中的租户ID
   *
   * @param <T> T
   * @return 租户id
   */
  @SuppressWarnings("unchecked")
  public <T> T getTenantId() {
    return (T) THREAD_LOCAL_TENANT.get();
  }

  /** 清理 */
  void clear() {
    THREAD_LOCAL_TENANT.remove();
  }
  /**
   * 以某个租户的身份运行
   *
   * @param tenant 租户id
   * @param func func
   */
  public void run(String tenant, Run func) {
    final String pre = getTenantId();
    try {
      log.debug("TenantBroker 切换租户{} -> {}", pre, tenant);
      setTenantId(tenant);
      func.run();
    } catch (Exception e) {
      throw new TenantBrokerException(e.getMessage(), e);
    } finally {
      log.debug("TenantBroker 还原租户{} <- {}", pre, tenant);
      setTenantId(pre);
    }
  }
  /**
   * 以某个租户的身份运行
   *
   * @param tenant 租户id
   * @param func func
   * @param <T> T
   */
  public <T> void runAs(T tenant, RunAs<T> func) {
    final String pre = getTenantId();
    try {
      log.debug("TenantBroker 切换租户{} -> {}", pre, tenant);
      setTenantId(tenant);
      func.run(tenant);
    } catch (Exception e) {
      throw new TenantBrokerException(e.getMessage(), e);
    } finally {
      log.debug("TenantBroker 还原租户{} <- {}", pre, tenant);
      setTenantId(pre);
    }
  }

  /**
   * 以某个租户的身份运行
   *
   * @param tenant 租户ID
   * @param func func
   * @param <T> T
   * @return T
   */
  public <T> T applyAs(Long tenant, ApplyAs<Long, T> func) {
    final Long pre = getTenantId();
    try {
      log.debug("TenantBroker 切换租户{} -> {}", pre, tenant);
      setTenantId(tenant);
      return func.apply(tenant);
    } catch (Exception e) {
      throw new TenantBrokerException(e.getMessage(), e);
    } finally {
      log.debug("TenantBroker 还原租户{} <- {}", pre, tenant);
      setTenantId(pre);
    }
  }

  public static class TenantBrokerException extends RuntimeException {
    public TenantBrokerException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  @FunctionalInterface
  public interface Run {
    /**
     * 执行业务逻辑
     *
     * @throws Exception 异常
     */
    void run() throws Exception;
  }

  @FunctionalInterface
  public interface RunAs<T> {
    /**
     * 执行业务逻辑
     *
     * @param tenantId 租户id
     * @throws Exception 异常
     */
    void run(T tenantId) throws Exception;
  }

  @FunctionalInterface
  public interface ApplyAs<T, R> {
    /**
     * 执行业务逻辑,返回一个值
     *
     * @param tenantId 租户id
     * @return R
     * @throws Exception 异常
     */
    R apply(T tenantId) throws Exception;
  }
}
