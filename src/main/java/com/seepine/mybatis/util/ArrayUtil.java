package com.seepine.mybatis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author seepine
 */
public class ArrayUtil {
  private static final String COMMA = ",";

  public static String joinOfLong(List<Long> list) {
    if (list == null || list.isEmpty()) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    for (Object item : list) {
      sb.append(item).append(COMMA);
    }
    return sb.toString();
  }

  public static String join(List<String> list) {
    if (list == null || list.isEmpty()) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    for (Object item : list) {
      sb.append(item).append(COMMA);
    }
    return sb.toString();
  }

  public static String join(String[] arr) {
    return arr == null ? null : join(Arrays.asList(arr));
  }

  public static String join(Long[] arr) {
    return arr == null
        ? null
        : join(Arrays.stream(arr).map(Object::toString).collect(Collectors.toList()));
  }

  public static String join(Integer[] arr) {
    return arr == null
        ? null
        : join(Arrays.stream(arr).map(Object::toString).collect(Collectors.toList()));
  }

  public static String[] toStrArray(String str) {
    return convert(str).toArray(new String[0]);
  }

  public static Long[] toLongArray(String str) {
    return convert(str).stream().map(Long::valueOf).toArray(Long[]::new);
  }

  public static Integer[] toIntArray(String str) {
    return convert(str).stream().map(Integer::valueOf).toArray(Integer[]::new);
  }

  public static List<String> convert(String str) {
    if (isBlank(str)) {
      return new ArrayList<>();
    }
    String[] arr = str.split(COMMA);
    return Arrays.asList(arr);
  }

  public static List<Long> convertToLong(String str) {
    if (isBlank(str)) {
      return new ArrayList<>();
    }
    String[] arr = str.split(COMMA);
    return Arrays.stream(arr).map(Long::new).collect(Collectors.toList());
  }

  public static boolean isBlank(String str) {
    return str == null || "".equals(str);
  }
}
