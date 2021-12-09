package org.yzh.component.area.model.domain;

import org.yzh.commons.util.GeomUtils;

/**
 * 矩形
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class Rectangle implements Geometry {

    private final double minX;
    private final double minY;
    private final double maxX;
    private final double maxY;

    public Rectangle(double... values) {
        this(values[0], values[1], values[2], values[3]);
    }

    public Rectangle(double x1, double y1, double x2, double y2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
    }

    @Override
    public boolean contains(double x, double y) {
        return GeomUtils.inside(x, y, minX, minY, maxX, maxY);
    }
}