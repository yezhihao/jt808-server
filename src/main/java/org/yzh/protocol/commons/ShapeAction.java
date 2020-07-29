package org.yzh.protocol.commons;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public interface ShapeAction {

    /** 更新（先清空，后追加） */
    int Update = 0;
    /** 追加 */
    int Append = 1;
    /** 修改 */
    int Modify = 2;
}