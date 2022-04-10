package com.seepine.mybatis.util;

/**
 * @author seepine
 */
public class StringUtil {
  /**
   * 将驼峰式命名的字符串转换为使用符号连接方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。
   *
   * @param str 转换前的驼峰式命名的字符串，也可以为符号连接形式
   * @param symbol 连接符
   * @return 转换后符号连接方式命名的字符串
   * @since 4.0.10
   */
  public static String toSymbolCase(CharSequence str, char symbol) {
    if (str == null) {
      return null;
    }

    final int length = str.length();
    final StringBuilder sb = new StringBuilder();
    char c;
    for (int i = 0; i < length; i++) {
      c = str.charAt(i);
      if (Character.isUpperCase(c)) {
        final Character preChar = (i > 0) ? str.charAt(i - 1) : null;
        final Character nextChar = (i < str.length() - 1) ? str.charAt(i + 1) : null;

        if (null != preChar) {
          if (symbol == preChar) {
            // 前一个为分隔符
            if (null == nextChar || Character.isLowerCase(nextChar)) {
              // 普通首字母大写，如_Abb -> _abb
              c = Character.toLowerCase(c);
            }
            // 后一个为大写，按照专有名词对待，如_AB -> _AB
          } else if (Character.isLowerCase(preChar)) {
            // 前一个为小写
            sb.append(symbol);
            if (null == nextChar || Character.isLowerCase(nextChar) || isNumber(nextChar)) {
              // 普通首字母大写，如aBcc -> a_bcc
              c = Character.toLowerCase(c);
            }
            // 后一个为大写，按照专有名词对待，如aBC -> a_BC
          } else {
            // 前一个为大写
            if (null == nextChar || Character.isLowerCase(nextChar)) {
              // 普通首字母大写，如ABcc -> A_bcc
              sb.append(symbol);
              c = Character.toLowerCase(c);
            }
            // 后一个为大写，按照专有名词对待，如ABC -> ABC
          }
        } else {
          // 首字母，需要根据后一个判断是否转为小写
          if (null == nextChar || Character.isLowerCase(nextChar)) {
            // 普通首字母大写，如Abc -> abc
            c = Character.toLowerCase(c);
          }
          // 后一个为大写，按照专有名词对待，如ABC -> ABC
        }
      }
      sb.append(c);
    }
    return sb.toString();
  }

  public static boolean isNumber(char ch) {
    return ch >= '0' && ch <= '9';
  }
}
