package org.yzh.commons.util;

/**
 * 坐标系转换器
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@FunctionalInterface
public interface Converter {

    Converter DEFAULT = p -> p;

    /** @return lngLat */
    double[] convert(double... lngLat);

    default double[] batchConvert(double[] coords) {
        int length = coords.length - (coords.length & 1);
        for (int i = 0; i < length; ) {
            double[] xy = convert(coords[i], coords[i + 1]);
            coords[i++] = xy[0];
            coords[i++] = xy[1];
        }
        return coords;
    }
}