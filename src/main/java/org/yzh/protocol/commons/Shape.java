package org.yzh.protocol.commons;

import org.yzh.commons.model.APIException;
import org.yzh.commons.model.ResultCodes;

/**
 * 区域类型
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public final class Shape {

    /** 圆形 */
    public static final int Circle = 1;
    /** 矩形 */
    public static final int Rectangle = 2;
    /** 多边形 */
    public static final int Polygon = 3;
    /** 路线 */
    public static final int Route = 4;

    /**
     * @param type 区域类型：1.圆形 2.矩形 3.多边形 4.路线
     */
    public static int toMessageId(int type) {
        switch (type) {
            case Shape.Circle:
                return JT808.删除圆形区域;
            case Shape.Rectangle:
                return JT808.删除矩形区域;
            case Shape.Polygon:
                return JT808.删除多边形区域;
            case Shape.Route:
                return JT808.删除路线;
            default:
                throw new APIException(ResultCodes.InvalidParameter);
        }
    }
}