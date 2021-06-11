package org.yzh.protocol.commons;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface ShapeAction {

    /** 更新(先清空,后追加) */
    int Update = 0;
    /** 追加 */
    int Append = 1;
    /** 修改 */
    int Modify = 2;
}