package com.seepine.mybatis.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;

import java.util.List;

/**
 * @author seepine
 * @param <T> T
 */
public interface MpBaseMapper<T> extends BaseMapper<T> {
  /**
   * 批量插入 仅适用于 mysql
   *
   * @param entityList 实体列表
   * @return 影响行数
   */
  Integer insertBatchSomeColumn(List<T> entityList);
  /**
   * 丰富接口支持自定义lambda查询
   *
   * @return LambdaQueryChainWrapper
   */
  default LambdaQueryChainWrapper<T> lambdaQuery() {
    return ChainWrappers.lambdaQueryChain(this);
  }

  /**
   * 丰富接口支持自定义lambda更新
   *
   * @return LambdaQueryChainWrapper
   */
  default LambdaUpdateChainWrapper<T> lambdaUpdate() {
    return ChainWrappers.lambdaUpdateChain(this);
  }
}
