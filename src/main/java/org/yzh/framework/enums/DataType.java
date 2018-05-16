package org.yzh.framework.enums;

public enum DataType {

    //无符号单字节整型（字节，8位）
    BYTE(1),
    //无符号双字节整型（字节，16位）
    WORD(2),
    //无符号四字节整型（字节，32位）
    DWORD(4),
    //N字节
    BYTES(-1),
    //8421码，N字节
    BCD8421(-1),
    //字符串，若无数据置空
    STRING(-1),
    //对象
    OBJ(-1),
    //列表
    LIST(-1);

    public int length;

    DataType(int length) {
        this.length = length;
    }

}