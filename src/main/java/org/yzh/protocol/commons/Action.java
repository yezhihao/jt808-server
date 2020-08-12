package org.yzh.protocol.commons;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface Action {

    /** 清空 */
    int Clear = 0;
    /** 更新（先清空，后追加） */
    int Update = 1;
    /** 追加 */
    int Append = 2;
    /** 修改 */
    int Modify = 3;
    /** 指定删除 */
    int Delete = 4;
}