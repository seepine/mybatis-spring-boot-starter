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
  public void setTenantId(Object tenantId) {
    if (tenantId != null) {
      THREAD_LOCAL_TENANT.set(tenantId);
    }
  }

  /**
   * 获取TTL中的租户ID
   *
   * @return 租户id
   */
  public String getTenantId() {
    Object tenantId = THREAD_LOCAL_TENANT.get();
    return tenantId == null ? null : tenantId.toString();
  }

  /** 清理 */
  public void clear() {
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
    } catch (RuntimeException e) {
      throw e;
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
   */
  public void run(Long tenant, Run func) {
    run(tenant.toString(), func);
  }
  /**
   * 以某个租户的身份运行
   *
   * @param tenant 租户id
   * @param func func
   */
  public void run(Integer tenant, Run func) {
    run(tenant.toString(), func);
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
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
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
   * @param <R> R
   * @return R
   */
  public <T, R> R apply(T tenant, Apply<R> func) {
    return applyAs(tenant, (val) -> func.apply());
  }
  /**
   * 以某个租户的身份运行
   *
   * @param tenant 租户ID
   * @param func func
   * @param <T> T
   * @return T
   */
  public <T, R> R applyAs(T tenant, ApplyAs<T, R> func) {
    final String pre = getTenantId();
    try {
      log.debug("TenantBroker 切换租户{} -> {}", pre, tenant);
      setTenantId(tenant);
      return func.apply(tenant);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
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
  public interface Apply<R> {
    /**
     * 执行业务逻辑,返回一个值
     *
     * @return R
     * @throws Exception 异常
     */
    R apply() throws Exception;
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
