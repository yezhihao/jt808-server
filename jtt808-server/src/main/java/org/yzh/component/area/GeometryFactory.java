package org.yzh.component.area;

import org.yzh.commons.util.Converter;
import org.yzh.commons.util.StrUtils;
import org.yzh.component.area.model.domain.*;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class GeometryFactory {

    public static Geometry build(int type, String text) {
        return build(null, type, text);
    }

    public static Geometry build(Converter converter, int type, String text) {
        return build(converter, type, StrUtils.toDoubles(text, ","));
    }

    public static Geometry build(Converter converter, int type, double... points) {
        if (converter != null) {
            int length = points.length - (points.length % 2);
            for (int i = 0; i < length; ) {
                double[] xy = converter.convert(points[i], points[i + 1]);
                points[i++] = xy[0];
                points[i++] = xy[1];
            }
        }
        switch (type) {
            case Geometry.Circle:
                return new Circle(points);
            case Geometry.Rectangle:
                return new Rectangle(points);
            case Geometry.Polygon:
                return new Polygon(points);
            case Geometry.Lines:
                return new Lines(points);
            default:
                throw new IllegalArgumentException("不支持的几何图形type=" + type);
        }
    }
}