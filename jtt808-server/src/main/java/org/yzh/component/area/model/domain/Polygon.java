package org.yzh.component.area.model.domain;

import org.yzh.commons.util.GeomUtils;

/**
 * 多边形
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Polygon implements Geometry {

    private final double[] points;

    public Polygon(double... points) {
        if (points.length % 2 != 0)
            throw new RuntimeException("数组长度不是偶数");
        if (points.length < 6)
            throw new RuntimeException("至少三个坐标点");
        this.points = points.clone();
    }

    @Override
    public boolean contains(double x, double y) {
        return GeomUtils.inside(x, y, points);
    }
}