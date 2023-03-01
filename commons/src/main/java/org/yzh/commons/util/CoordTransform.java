package org.yzh.commons.util;

/**
 * WGS-84 GPS坐标（谷歌地图国外）
 * GCJ-02 国测局坐标（谷歌地图国内、高德地图、腾讯地图）
 * BD-09 百度坐标（百度地图）
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class CoordTransform {

    /** 长半轴（赤道半径,单位米,克拉索夫斯基椭球） */
    public static final double A = 6378245;

    /** 第一偏心率平方 */
    private static final double EE = 0.00669342162296594323;

    private static final double PI = Math.PI;

    private static final double X_PI = Math.PI * 3000.0 / 180.0;

    public static double[] wgs84tobd09(double[] lngLat) {
        return wgs84tobd09(lngLat[0], lngLat[1], new double[2]);
    }

    public static double[] wgs84tobd09(double lng, double lat) {
        return wgs84tobd09(lng, lat, new double[2]);
    }

    /** WGS-84 转 BD-09 */
    public static double[] wgs84tobd09(double lng, double lat, double[] result) {
        wgs84togcj02(lng, lat, result);
        gcj02tobd09(result[0], result[1], result);
        return result;
    }

    public static double[] bd09towgs84(double[] lngLat) {
        return bd09towgs84(lngLat[0], lngLat[1], new double[2]);
    }

    public static double[] bd09towgs84(double lng, double lat) {
        return bd09towgs84(lng, lat, new double[2]);
    }

    /** BD-09 转 WGS-84 */
    public static double[] bd09towgs84(double lng, double lat, double[] result) {
        bd09togcj02(lng, lat, result);
        gcj02towgs84(result[0], result[1], result);
        return result;
    }

    public static double[] bd09togcj02(double[] lngLat) {
        return bd09togcj02(lngLat[0], lngLat[1], new double[2]);
    }

    public static double[] bd09togcj02(double lng, double lat) {
        return bd09togcj02(lng, lat, new double[2]);
    }

    /** BD-09 转 GCJ-02 */
    public static double[] bd09togcj02(double lng, double lat, double[] result) {
        double x = lng - 0.0065;
        double y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        result[0] = z * Math.cos(theta);
        result[1] = z * Math.sin(theta);
        return result;
    }

    public static double[] gcj02tobd09(double[] lngLat) {
        return gcj02tobd09(lngLat[0], lngLat[1], new double[2]);
    }

    public static double[] gcj02tobd09(double lng, double lat) {
        return gcj02tobd09(lng, lat, new double[2]);
    }

    /** GCJ-02 转 BD-09 */
    public static double[] gcj02tobd09(double lng, double lat, double[] result) {
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI);
        result[0] = z * Math.cos(theta) + 0.0065;
        result[1] = z * Math.sin(theta) + 0.006;
        return result;
    }

    public static double[] wgs84togcj02(double[] lngLat) {
        return wgs84togcj02(lngLat[0], lngLat[1], new double[2]);
    }

    public static double[] wgs84togcj02(double lng, double lat) {
        return wgs84togcj02(lng, lat, new double[2]);
    }

    /** WGS-84 转 GCJ-02 */
    public static double[] wgs84togcj02(double lng, double lat, double[] result) {
        if (inChina(lng, lat)) {
            double dlat = transformlat(lng - 105.0, lat - 35.0);
            double dlng = transformlng(lng - 105.0, lat - 35.0);
            double radlat = lat * (PI / 180.0);
            double magic = Math.sin(radlat);
            magic = 1 - EE * magic * magic;
            double sqrtmagic = Math.sqrt(magic);
            dlat = (dlat * 180.0) / ((A * (1 - EE)) * PI / (magic * sqrtmagic));
            dlng = (dlng * 180.0) / (A * PI / sqrtmagic * Math.cos(radlat));
            result[0] = lng + dlng;
            result[1] = lat + dlat;
            return result;
        } else {
            result[0] = lng;
            result[1] = lat;
            return result;
        }
    }

    public static double[] gcj02towgs84(double[] lngLat) {
        return gcj02towgs84(lngLat[0], lngLat[1], new double[2]);
    }

    public static double[] gcj02towgs84(double lng, double lat) {
        return gcj02towgs84(lng, lat, new double[2]);
    }

    /** GCJ-02 转 WGS-84 */
    public static double[] gcj02towgs84(double lng, double lat, double[] result) {
        if (inChina(lng, lat)) {
            double dlat = transformlat(lng - 105.0, lat - 35.0);
            double dlng = transformlng(lng - 105.0, lat - 35.0);
            double radlat = lat * (PI / 180.0);
            double magic = Math.sin(radlat);
            magic = 1 - EE * magic * magic;
            double sqrtmagic = Math.sqrt(magic);
            dlat = (dlat * 180.0) / ((A * (1 - EE)) * PI / (magic * sqrtmagic));
            dlng = (dlng * 180.0) / (A * PI / sqrtmagic * Math.cos(radlat));
            result[0] = lng - dlng;
            result[1] = lat - dlat;
            return result;
        } else {
            result[0] = lng;
            result[1] = lat;
            return result;
        }
    }

    private static double transformlat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(PI * 6.0 * lng) + 20.0 * Math.sin(PI * 2.0 * lng)) * (2.0 / 3.0);
        ret += (20.0 * Math.sin(PI * lat) + 40.0 * Math.sin(PI / 3.0 * lat)) * (2.0 / 3.0);
        ret += (160.0 * Math.sin(lat / (12.0 / PI)) + 320 * Math.sin(lat * (PI / 30.0))) * (2.0 / 3.0);
        return ret;
    }

    private static double transformlng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(PI * 6.0 * lng) + 20.0 * Math.sin(PI * 2.0 * lng)) * (2.0 / 3.0);
        ret += (20.0 * Math.sin(PI * lng) + 40.0 * Math.sin(PI / 3.0 * lng)) * (2.0 / 3.0);
        ret += (150.0 * Math.sin(lng / (12.0 / PI)) + 300.0 * Math.sin(lng * (PI / 30.0))) * (2.0 / 3.0);
        return ret;
    }

    /** 判断是否在国内，不在国内则不做偏移 */
    public static boolean inChina(double lng, double lat) {
        // 纬度3.86~53.55,经度73.66~135.05
        return (lng > 73.66 && lng < 135.05 && lat > 3.86 && lat < 53.55);
    }
}