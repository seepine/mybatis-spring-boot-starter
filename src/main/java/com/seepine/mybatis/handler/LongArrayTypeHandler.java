package com.seepine.mybatis.handler;

import com.seepine.mybatis.util.ArrayUtil;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mybatis数组,符串互转
 *
 * <p>MappedJdbcTypes 数据库中的数据类型 MappedTypes java中的的数据类型
 *
 * @author seepine
 */
@MappedTypes(value = {Long[].class})
@MappedJdbcTypes(value = JdbcType.VARCHAR)
public class LongArrayTypeHandler extends BaseTypeHandler<Long[]> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Long[] parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, ArrayUtil.join(parameter));
  }

  @Override
  @SneakyThrows
  public Long[] getNullableResult(ResultSet rs, String columnName) {
    return ArrayUtil.toLongArray(rs.getString(columnName));
  }

  @Override
  @SneakyThrows
  public Long[] getNullableResult(ResultSet rs, int columnIndex) {
    return ArrayUtil.toLongArray(rs.getString(columnIndex));
  }

  @Override
  @SneakyThrows
  public Long[] getNullableResult(CallableStatement cs, int columnIndex) {
    return ArrayUtil.toLongArray(cs.getString(columnIndex));
  }
}
