package org.yzh.protocol.commons;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
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