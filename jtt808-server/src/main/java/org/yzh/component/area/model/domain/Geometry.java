package org.yzh.component.area.model.domain;

/**
 * 几何图形
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public interface Geometry {

    int Circle = 1;
    int Rectangle = 2;
    int Polygon = 3;
    int Lines = 4;

    /**
     * @param x 经度
     * @param y 纬度
     * @return
     */
    boolean contains(double x, double y);

    default boolean contains(double... xy) {
        return this.contains(xy[0], xy[1]);
    }
}