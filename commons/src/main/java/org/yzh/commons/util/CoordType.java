package org.yzh.commons.util;

/**
 * 坐标系枚举
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public enum CoordType {

    wgs84(
            Converter.DEFAULT,
            CoordTransform::wgs84togcj02,
            CoordTransform::wgs84tobd09
    ),
    gcj02(
            CoordTransform::gcj02towgs84,
            Converter.DEFAULT,
            CoordTransform::gcj02tobd09
    ),
    bd09(
            CoordTransform::bd09towgs84,
            CoordTransform::bd09togcj02,
            Converter.DEFAULT
    );

    public final Converter WGS84;
    public final Converter GCJ02;
    public final Converter BD09;

    public Converter to(CoordType type) {
        switch (type) {
            case wgs84:
                return this.WGS84;
            case gcj02:
                return this.GCJ02;
            case bd09:
                return this.BD09;
            default:
                return Converter.DEFAULT;
        }
    }

    CoordType(Converter WGS84, Converter GCJ02, Converter BD09) {
        this.WGS84 = WGS84;
        this.GCJ02 = GCJ02;
        this.BD09 = BD09;
    }
}