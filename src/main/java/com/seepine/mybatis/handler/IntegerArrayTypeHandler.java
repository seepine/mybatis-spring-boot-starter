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
@MappedTypes(value = {Integer[].class})
@MappedJdbcTypes(value = JdbcType.VARCHAR)
public class IntegerArrayTypeHandler extends BaseTypeHandler<Integer[]> {

  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int i, Integer[] parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, ArrayUtil.join(parameter));
  }

  @Override
  @SneakyThrows
  public Integer[] getNullableResult(ResultSet rs, String columnName) {
    return ArrayUtil.toIntArray(rs.getString(columnName));
  }

  @Override
  @SneakyThrows
  public Integer[] getNullableResult(ResultSet rs, int columnIndex) {
    return ArrayUtil.toIntArray(rs.getString(columnIndex));
  }

  @Override
  @SneakyThrows
  public Integer[] getNullableResult(CallableStatement cs, int columnIndex) {
    return ArrayUtil.toIntArray(cs.getString(columnIndex));
  }
}
