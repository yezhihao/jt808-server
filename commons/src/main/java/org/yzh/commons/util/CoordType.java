package org.yzh.commons.util;

import static org.yzh.commons.util.CoordTransform.*;

/**
 * 坐标系枚举
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public enum CoordType {

    wgs84(
            p -> p,
            p -> wgs84togcj02(p),
            p -> gcj02tobd09(wgs84togcj02(p))
    ),
    gcj02(
            p -> gcj02towgs84(p),
            p -> p,
            p -> gcj02tobd09(p)
    ),
    bd09(
            p -> gcj02towgs84(bd09togcj02(p)),
            p -> bd09togcj02(p),
            p -> p
    );

    public final Converter WGS84;
    public final Converter GCJ02;
    public final Converter BD09;

    private static final Converter DEFAULT = p -> p;

    public Converter to(CoordType type) {
        switch (type) {
            case wgs84:
                return this.WGS84;
            case gcj02:
                return this.GCJ02;
            case bd09:
                return this.BD09;
            default:
                return DEFAULT;
        }
    }

    CoordType(Converter WGS84, Converter GCJ02, Converter BD09) {
        this.WGS84 = WGS84;
        this.GCJ02 = GCJ02;
        this.BD09 = BD09;
    }
}