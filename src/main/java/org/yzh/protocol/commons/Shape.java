package org.yzh.protocol.commons;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public interface Shape {

    /** 查询圆形区域数据 */
    int Round = 1;
    /** 查询矩形区城数据 */
    int Rectangle = 2;
    /** 查询多边形区域激据 */
    int Polygon = 3;
    /** 查询线路数据 */
    int Route = 4;
}