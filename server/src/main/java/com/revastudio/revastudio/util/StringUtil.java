package com.revastudio.revastudio.util;

/**
 * Usage:
 * StringUtil.isBlank(null);           // true
 * StringUtil.isBlank("");             // true
 * StringUtil.isBlank("   ");          // true
 * StringUtil.isBlank("hello");        // false
 * trim(): removes leading and trailing whitespaces
 * isEmpty(): returns true only if length == 0
 */
public class StringUtil {
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
