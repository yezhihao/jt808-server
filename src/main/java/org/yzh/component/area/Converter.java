package org.yzh.component.area;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@FunctionalInterface
public interface Converter {

    double[] translate(double x, double y);
}