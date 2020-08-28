package org.yzh.protocol.commons;

/**
 * 江苏省团体标准
 * 道路运输车辆主动安全智能防控系统(通讯协议规范)
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface JSATL12 {

    int 报警附件上传指令 = 0x9208;
    int 文件上传完成消息应答 = 0x9212;
    int 报警附件信息消息 = 0x1210;
    int 文件信息上传 = 0x1211;
    int 文件上传完成消息 = 0x1212;
    int 文件数据上传 = 0x30316364;

}