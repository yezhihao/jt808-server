package org.yzh.component.area.model.domain;

import org.yzh.commons.util.GeomUtils;

/**
 * 圆形
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Circle implements Geometry {

    private final double x;
    private final double y;
    private final double radius;

    public Circle(double... values) {
        this(values[0], values[1], values[2]);
    }

    public Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = Math.abs(radius);
    }

    @Override
    public boolean contains(double x, double y) {
        double distance = GeomUtils.distance(this.x, this.y, x, y);
        return distance <= radius;
    }
}