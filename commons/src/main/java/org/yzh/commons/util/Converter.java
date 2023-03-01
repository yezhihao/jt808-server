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

}