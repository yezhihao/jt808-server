package org.yzh.component.area;

/**
 * 坐标系转换
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@FunctionalInterface
public interface Converter {

    double[] translate(double x, double y);
}