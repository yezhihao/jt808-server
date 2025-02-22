package org.yzh.commons.util;

/**
 * 几何图形工具类
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class GeomUtils {

    /** 长半轴（赤道半径,单位米,WGS-84） */
    public static final double A = 6378137;

    /** 弧度 */
    public static final double RADIAN = Math.PI / 180.0;

    public static double[] calculatePoint(double lng1, double lat1, double lng2, double lat2, double distance) {
        double radLat1 = lat1 * RADIAN;
        double radLat2 = lat2 * RADIAN;
        double deltaLat = (lat2 - lat1) * RADIAN;
        double deltaLng = (lng2 - lng1) * RADIAN;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = A * c;

        double lat = lat1 + (distance / d) * (lat2 - lat1);
        double lng = lng1 + (distance / d) * (lng2 - lng1);
        return new double[]{lng, lat};
    }

    /** 计算球面点到点距离(单位米) */
    public static double distance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * RADIAN;
        double radLat2 = lat2 * RADIAN;
        double a = radLat1 - radLat2;
        double b = lng1 * RADIAN - lng2 * RADIAN;

        double distance = A * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        distance = Math.round(distance * 10000) / 10000D;
        return distance;
    }

    /** 计算平面点到点距离(单位米) */
    public static double distance_(double x1, double y1, double x2, double y2) {
        double a = x1 - x2;
        double b = y1 - y2;
        double distance = Math.sqrt(Math.abs((a * a) + (b * b)));
        return distance * 100000;
    }

    /** 计算点到线距离(单位米) */
    public static double distancePointToLine(double x1, double y1, double x2, double y2, double x0, double y0) {
        double a = distance_(x1, y1, x2, y2);
        double b = distance_(x1, y1, x0, y0);
        double c = distance_(x2, y2, x0, y0);
        if (c <= 0.001 || b <= 0.001) {
            return 0.0;
        }
        if (a <= 0.001) {
            return b;
        }

        double aa = a * a;
        double bb = b * b;
        double cc = c * c;
        if (cc >= aa + bb) {
            return b;
        }
        if (bb >= aa + cc) {
            return c;
        }
        double p = (a + b + c) / 2D;
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        return 2D * s / a;
    }

    /** 判断坐标是否在矩形内 */
    public static boolean inside(double x, double y, double minX, double minY, double maxX, double maxY) {
        return (x >= minX && x <= maxX &&
                y >= minY && y <= maxY);
    }

    /** 判断坐标是否在多边形内部 */
    public static boolean inside(double x, double y, double[] points) {
        boolean oddNodes = false;

        double ret;
        for (int i = 0, j = points.length - 2; i < points.length; i += 2) {
            double x1 = points[i];
            double y1 = points[i + 1];
            double x2 = points[j];
            double y2 = points[j + 1];

            if ((y1 < y && y2 >= y) || (y2 < y && y1 >= y)) {
                ret = x1 + (y - y1) / (y2 - y1) * (x2 - x1);
                if (ret < x)
                    oddNodes = !oddNodes;
            }
            j = i;
        }
        return oddNodes;
    }

    /** 计算三个坐标角度 */
    public static double getDegree(double lng1, double lat1, double lng2, double lat2, double lng3, double lat3) {
        return getRadian(lng1, lat1, lng2, lat2, lng3, lat3) * (180 / Math.PI);
    }

    /** 计算三个坐标弧度 */
    public static double getRadian(double lng1, double lat1, double lng2, double lat2, double lng3, double lat3) {
        double[] p1 = ball2xyz(lng1, lat1);
        double[] p2 = ball2xyz(lng2, lat2);
        double[] p3 = ball2xyz(lng3, lat3);

        double abx = p1[x] - p2[x];
        double aby = p1[y] - p2[y];
        double abz = p1[z] - p2[z];

        double cbx = p3[x] - p2[x];
        double cby = p3[y] - p2[y];
        double cbz = p3[z] - p2[z];

        double p1p2 = Math.sqrt(abx * abx + aby * aby + abz * abz);
        double p2p3 = Math.sqrt(cbx * cbx + cby * cby + cbz * cbz);

        double p = abx * cbx + aby * cby + abz * cbz;
        double a = Math.acos(p / (p1p2 * p2p3));

        double c = abx * cby - cbx * aby;
        return (c > 0) ? a : -a;
    }

    private static final int x = 0;
    private static final int y = 1;
    private static final int z = 2;

    private static double[] ball2xyz(double lng, double lat) {
        lat = lat * RADIAN;
        lng = lng * RADIAN;

        double[] p = new double[3];

        double t = Math.cos(lat) * 64;
        p[x] = Math.cos(lng) * t;
        p[y] = Math.sin(lng) * t;
        p[z] = Math.sin(lat) * 64;
        return p;
    }
}