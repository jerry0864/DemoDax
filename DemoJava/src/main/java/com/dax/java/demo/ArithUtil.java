package com.dax.java.demo;

import java.math.BigDecimal;

/**
 * Desc:提供精确的浮点数运算(包括加、减、乘、除、四舍五入)工具类
 * Created by liuxiong on 2017/6/26.
 */
public class ArithUtil {

    // 除法运算默认精度
    private static final int DEF_DIV_SCALE = 10;

    private ArithUtil() {

    }

    public static double add(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.add(b2).doubleValue();
    }

    public static double sub(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.subtract(b2).doubleValue();
    }

    public static double mul(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.multiply(b2).doubleValue();
    }

    public static double div(double value1, double value2) throws IllegalAccessException {
        return div(value1, value2, DEF_DIV_SCALE);
    }

    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        if(scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round(double v, int scale) throws IllegalAccessException {
        return div(v, 1, scale);
    }

    public static boolean equalTo(BigDecimal b1, BigDecimal b2) {
        if(b1 == null || b2 == null) {
            return false;
        }
        return 0 == b1.compareTo(b2);
    }
}