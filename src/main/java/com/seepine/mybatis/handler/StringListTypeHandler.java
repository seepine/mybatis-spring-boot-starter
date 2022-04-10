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
import java.util.List;

/**
 * Mybatis数组,符串互转
 *
 * <p>MappedJdbcTypes 数据库中的数据类型 MappedTypes java中的的数据类型
 *
 * @author seepine
 */
@MappedTypes(value = {List.class})
@MappedJdbcTypes(value = JdbcType.VARCHAR)
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {
  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, ArrayUtil.join(parameter));
  }

  @Override
  @SneakyThrows
  public List<String> getNullableResult(ResultSet rs, String columnName) {
    return ArrayUtil.convert(rs.getString(columnName));
  }

  @Override
  @SneakyThrows
  public List<String> getNullableResult(ResultSet rs, int columnIndex) {
    return ArrayUtil.convert(rs.getString(columnIndex));
  }

  @Override
  @SneakyThrows
  public List<String> getNullableResult(CallableStatement cs, int columnIndex) {
    return ArrayUtil.convert(cs.getString(columnIndex));
  }
}
