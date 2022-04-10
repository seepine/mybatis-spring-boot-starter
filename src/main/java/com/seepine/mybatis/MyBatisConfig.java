package com.seepine.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.seepine.mybatis.properties.TenantProperties;
import com.seepine.mybatis.tenant.TenantInnerInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author seepine
 */
@Configuration
@AllArgsConstructor
public class MyBatisConfig {
  private final TenantProperties tenantProperties;
  /**
   * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
   * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
   */
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    // 分页插件
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    // 防全表更新与删除插件 https://baomidou.com/pages/333106/#blockattackinnerinterceptor
    interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
    // 多租户
    if (tenantProperties.getEnabled()) {
      interceptor.addInnerInterceptor(new TenantInnerInterceptor(tenantProperties));
    }

    return interceptor;
  }

  /**
   * 添加批量插入，仅支持mysql
   *
   * @return defaultSqlInjector
   */
  @Bean
  public DefaultSqlInjector dataScopeSqlInjector() {
    return new DefaultSqlInjector() {
      @Override
      public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
      }
    };
  }
}
