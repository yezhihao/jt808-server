package org.yzh.component.area;

import org.yzh.component.area.model.domain.*;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class GeometryFactory {

    private Converter converter;

    public GeometryFactory() {
    }

    public GeometryFactory(Converter converter) {
        this.converter = converter;
    }

    public Geometry getInstance(int type, String text) {
        return getInstance(type, toDoubles(text));
    }

    public Geometry getInstance(int type, double... points) {
        if (converter != null) {
            int length = points.length - (points.length % 2);
            for (int i = 0; i < length; ) {
                double[] xy = converter.translate(points[i], points[i + 1]);
                points[i++] = xy[0];
                points[i++] = xy[1];
            }
        }
        switch (type) {
            case 1:
                return new Circle(points);
            case 2:
                return new Rectangle(points);
            case 3:
                return new Polygon(points);
            case 4:
                return new Lines(points);
            default:
                throw new RuntimeException("不支持的几何图形type=" + type);
        }
    }


    public double[] toDoubles(String str) {
        String[] split = str.split(",");
        double[] result = new double[split.length];
        for (int i = 0; i < split.length; i++)
            result[i] = Double.parseDouble(split[i]);
        return result;
    }
}