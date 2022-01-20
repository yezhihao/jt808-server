package org.yzh.commons.util;

/**
 * 几何图形工具类
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class GeomUtils {

    /** 地球半径,单位米（WGS-84 长半轴） */
    private static final double RADIUS = 6378137;

    /** 球面距离计算 点到点(单位米) */
    public static double distance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lng1) - Math.toRadians(lng2);

        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        distance = distance * RADIUS;
        distance = Math.round(distance * 10000) / 10000D;
        return distance;
    }

    /** 平面距离计算 点到点(单位米) */
    public static double distance_(double x1, double y1, double x2, double y2) {
        double a = x1 - x2;
        double b = y1 - y2;
        double distance = Math.sqrt(Math.abs((a * a) + (b * b)));
        return distance * 100000;
    }

    /** 距离计算 点到线(单位米) */
    public static double distancePointToLine(double x1, double y1, double x2, double y2, double x0, double y0) {
        double space;
        double a = distance_(x1, y1, x2, y2);
        double b = distance_(x1, y1, x0, y0);
        double c = distance_(x2, y2, x0, y0);
        if (c <= 0.000001 || b <= 0.000001) {
            space = 0.0;
            return space;
        }
        if (a <= 0.000001) {
            space = b;
            return space;
        }
        if (c * c >= a * a + b * b) {
            space = b;
            return space;
        }
        if (b * b >= a * a + c * c) {
            space = c;
            return space;
        } else {
            double p = (a + b + c) / 2d;
            double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));
            space = 2d * s / a;
            return space;
        }
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

    /** 判断坐标是否在多边形内部 */
    public static boolean inPolygon(double x, double y, double[] points) {
        double sum = 0;
        int length = points.length - 2;
        for (int i = 0; i < length; i += 2) {
            double sx = points[i];
            double sy = points[i + 1];
            double tx = points[i + 2];
            double ty = points[i + 3];

            //点与多边形顶点重合或在多边形的边上
            if ((sx - x) * (x - tx) >= 0 &&
                    (sy - y) * (y - ty) >= 0 &&
                    (x - sx) * (ty - sy) == (y - sy) * (tx - sx))
                return true;
            //点与相邻顶点连线的夹角
            double angle = Math.atan2(sy - y, sx - x) - Math.atan2(ty - y, tx - x);

            //确保夹角不超出取值范围（-π 到 π）
            if (angle >= Math.PI)
                angle = angle - Math.PI * 2;
            else if (angle <= -Math.PI)
                angle = angle + Math.PI * 2;
            sum += angle;
        }
        //计算回转数并判断点和多边形的几何关系
        return (int) (sum / Math.PI) != 0;
    }
}