package org.yzh.commons.util;

/**
 * WGS-84 GPS坐标（谷歌地图国外）
 * GCJ-02 国测局坐标（谷歌地图国内，高德地图）
 * BD-09 百度坐标（百度地图）
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class CoordTransform {

    /** 地球半径,单位米（北京54 长半轴） */
    private static final double RADIUS = 6378245;

    /** 扁率 */
    private static final double EE = 0.00669342162296594323;

    private static final double PI = Math.PI;

    private static final double X_PI = Math.PI * 3000.0 / 180.0;

    public static double[] bd09togcj02(double[] bd_lngLat) {
        return bd09togcj02(bd_lngLat[0], bd_lngLat[1]);
    }

    /** BD-09 转 GCJ-02 */
    public static double[] bd09togcj02(double bd_lng, double bd_lat) {
        double x = bd_lng - 0.0065;
        double y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new double[]{gg_lng, gg_lat};
    }

    public static double[] gcj02tobd09(double[] lngLat) {
        return gcj02tobd09(lngLat[0], lngLat[1]);
    }

    /** GCJ-02 转 BD-09 */
    public static double[] gcj02tobd09(double lng, double lat) {
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new double[]{bd_lng, bd_lat};
    }

    public static double[] wgs84togcj02(double[] lngLat) {
        return wgs84togcj02(lngLat[0], lngLat[1]);
    }

    /** WGS-84 转 GCJ-02 */
    public static double[] wgs84togcj02(double lng, double lat) {
        if (inChina(lng, lat)) {
            double dlat = transformlat(lng - 105.0, lat - 35.0);
            double dlng = transformlng(lng - 105.0, lat - 35.0);
            double radlat = lat / 180.0 * PI;
            double magic = Math.sin(radlat);
            magic = 1 - EE * magic * magic;
            double sqrtmagic = Math.sqrt(magic);
            dlat = (dlat * 180.0) / ((RADIUS * (1 - EE)) / (magic * sqrtmagic) * PI);
            dlng = (dlng * 180.0) / (RADIUS / sqrtmagic * Math.cos(radlat) * PI);
            double mglat = lat + dlat;
            double mglng = lng + dlng;
            return new double[]{mglng, mglat};
        } else {
            return new double[]{lng, lat};
        }
    }

    public static double[] gcj02towgs84(double[] lngLat) {
        return gcj02towgs84(lngLat[0], lngLat[1]);
    }

    /** GCJ-02 转 WGS-84 */
    public static double[] gcj02towgs84(double lng, double lat) {
        if (inChina(lng, lat)) {
            double dlat = transformlat(lng - 105.0, lat - 35.0);
            double dlng = transformlng(lng - 105.0, lat - 35.0);
            double radlat = lat / 180.0 * PI;
            double magic = Math.sin(radlat);
            magic = 1 - EE * magic * magic;
            double sqrtmagic = Math.sqrt(magic);
            dlat = (dlat * 180.0) / ((RADIUS * (1 - EE)) / (magic * sqrtmagic) * PI);
            dlng = (dlng * 180.0) / (RADIUS / sqrtmagic * Math.cos(radlat) * PI);
            double mglat = lat + dlat;
            double mglng = lng + dlng;
            return new double[]{lng * 2 - mglng, lat * 2 - mglat};
        } else {
            return new double[]{lng, lat};
        }
    }

    private static double transformlat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformlng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    /** 判断是否在国内，不在国内则不做偏移 */
    public static boolean inChina(double lng, double lat) {
        // 纬度3.86~53.55,经度73.66~135.05
        return (lng > 73.66 && lng < 135.05 && lat > 3.86 && lat < 53.55);
    }
}