package com.seepine.mybatis.tenant;

import com.seepine.mybatis.properties.TenantProperties;
import com.seepine.mybatis.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author seepine
 */
@Slf4j
@Component
@AllArgsConstructor
public class TenantApplicationRunner implements ApplicationRunner {
  private final TenantProperties tenantProperties;

  @Override
  public void run(ApplicationArguments applicationArguments) {
    if (Boolean.TRUE.equals(tenantProperties.getEnabled())) {
      log.info("===   start scan tenant table name   ===");
      // spring工具类，可以获取指定路径下的全部类
      for (String scanPackage : tenantProperties.getScanPackages()) {
        addIgnoredTables(scanPackage);
      }
      log.info("{}", TenantUtil.getTables().toString());
      log.info("===   tenant table name finish   ===");
    }
  }

  private void addIgnoredTables(String scanPackage) {
    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    try {
      String pattern =
          ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
              + ClassUtils.convertClassNameToResourcePath(scanPackage)
              + tenantProperties.getResourcePattern();
      Resource[] resources = resourcePatternResolver.getResources(pattern);
      // MetadataReader 的工厂类
      MetadataReaderFactory readerFactory =
          new CachingMetadataReaderFactory(resourcePatternResolver);
      for (Resource resource : resources) {
        // 用于读取类信息
        MetadataReader reader = readerFactory.getMetadataReader(resource);
        // 扫描到的class
        String classname = reader.getClassMetadata().getClassName();
        Class<?> clazz = Class.forName(classname);
        // 判断
        if (hasField(clazz.getSuperclass())) {
          TenantUtil.addIgnoreTable(rebuildClassName(classname));
        } else if (hasField(clazz)) {
          TenantUtil.addIgnoreTable(rebuildClassName(classname));
        }
      }
    } catch (IOException | ClassNotFoundException ignored) {
    }
  }

  boolean hasField(Class<?> clazz) {
    if (clazz == null) {
      return false;
    }
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      if (tenantProperties.getEntityField().equals(field.getName())) {
        return true;
      }
    }
    return false;
  }

  String rebuildClassName(String className) {
    return StringUtil.toSymbolCase(className.substring(className.lastIndexOf(".") + 1), '_');
  }
}
