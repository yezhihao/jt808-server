package org.yzh.web.commons;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class CoordUtils {

    /** 地球半径,单位米 */
    private static final double EARTH_RADIUS = 6378137d;

    /** 距离计算 */
    public static double distance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lng1) - Math.toRadians(lng2);

        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000d) / 10000d;
        return distance;
    }

    /**
     * 判断坐标是否在围栏内
     * coords [lng, lat, lng, lat, lng, lat ...]
     * coords.length = 3 圆形 最后一位为距离
     * coords.length = 4 矩形
     * coords.length > 4 多边形
     */
    public static boolean inside(double lng, double lat, double... coords) {
        if (coords.length == 3) { // 如果围栏是圆形
            double distance = distance(lng, lat, coords[0], coords[1]);
            return distance <= coords[2];
        }
        if (coords.length == 4) {
            return (coords[0] <= lng && coords[1] >= lat &&
                    coords[2] >= lng && coords[3] <= lat);
        }
        return insidePolygon(lng, lat, coords) <= 0;
    }

    /**
     * 判断坐标是否在多边形内部
     * @return -1.在多边形内部，0.点与多边形顶点重合或在多边形的边上，1.在多边形外部
     */
    public static int insidePolygon(double px, double py, double[] coords) {
        double sum = 0;
        int length = coords.length - 2;
        for (int i = 0; i < length; i += 2) {
            double sx = coords[i];
            double sy = coords[i + 1];
            double tx = coords[i + 2];
            double ty = coords[i + 3];

            //点与多边形顶点重合或在多边形的边上
            if ((sx - px) * (px - tx) >= 0 &&
                    (sy - py) * (py - ty) >= 0 &&
                    (px - sx) * (ty - sy) == (py - sy) * (tx - sx))
                return 0;
            //点与相邻顶点连线的夹角
            double angle = Math.atan2(sy - py, sx - px) - Math.atan2(ty - py, tx - px);

            //确保夹角不超出取值范围（-π 到 π）
            if (angle >= Math.PI)
                angle = angle - Math.PI * 2;
            else if (angle <= -Math.PI)
                angle = angle + Math.PI * 2;
            sum += angle;
        }
        //计算回转数并判断点和多边形的几何关系
        if ((int) (sum / Math.PI) == 0)
            return 1;
        return -1;
    }
}