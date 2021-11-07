package org.yzh.component.area.model.domain;

import org.yzh.commons.util.GeomUtils;

import java.util.Arrays;

/**
 * 线段
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Lines implements Geometry {

    private final double[] points;
    private final double width;
    private final int size;

    public Lines(double... points) {
        this(Arrays.copyOfRange(points, 0, points.length - 1), points[points.length - 1]);
    }

    public Lines(double[] points, double width) {
        if (points.length % 2 != 0)
            throw new RuntimeException("数组长度不是偶数");
        if (points.length < 4)
            throw new RuntimeException("至少两个坐标点");

        this.points = points.clone();
        this.width = Math.abs(width);
        this.size = points.length - 2;
    }

    @Override
    public boolean contains(double x, double y) {
        for (int i = 0; i < size; ) {
            double distance = GeomUtils.distancePointToLine(
                    points[i++], points[i++],
                    points[i], points[i + 1],
                    x, y
            );
            if (distance <= width)
                return true;
        }
        return false;
    }
}