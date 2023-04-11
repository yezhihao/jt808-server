package org.yzh.protocol.commons.transform;

/**
 * 位置附加信息
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public interface AttributeKey {
    Integer Mileage = 1;                 // 0x01 里程,数据类型为DWORD,单位为1/10km,对应车上里程表读数
    Integer Fuel = 2;                    // 0x02 油量,数据类型为WORD,单位为1/10L,对应车上油量表读数
    Integer Speed = 3;                   // 0x03 行驶记录功能获取的速度,数据类型为WORD,单位为1/10km/h
    Integer AlarmEventId = 4;            // 0x04 需要人工确认报警事件的ID,数据类型为WORD,从1开始计数
    Integer TirePressure = 5;            // 0x05 胎压,单位为Pa,标定轮子的顺序为从车头开始从左到右顺序排列,多余的字节为0xFF,表示无效数据
    Integer CarriageTemperature = 6;     // 0x06 车厢温度,单位为摄氏度,取值范围为-32767~+32767,最高位为1表示负数
    Integer OverSpeedAlarm = 17;         // 0x11 超速报警附加信息见表28
    Integer InOutAreaAlarm = 18;         // 0x12 进出区域/路线报警附加信息见表29
    Integer RouteDriveTimeAlarm = 19;    // 0x13 路段行驶时间不足/过长报警附加信息见表30

    Integer VideoRelatedAlarm = 20;      // 0x14 视频相关报警,DWORD,按位设置,标志位定义见表14
    Integer VideoMissingStatus = 21;     // 0x15 视频信号丢失报警状态,DWORD,按位设置,bit0~bit31分别表示第1~32个逻辑通道,相应位为1则表示该逻辑通道发生视频信号丢失
    Integer VideoObscuredStatus = 22;    // 0x16 视频信号遮挡报警状态,DWORD,按位设置,bit0~bit31分别表示第1~32个逻辑通道,相应位为1则表示该逻辑通道发生视频信号遮挡
    Integer StorageFailureStatus = 23;   // 0x17 存储器故障报警状态,WORD,按位设置.bit0~bit11分别表示第1~12个主存储器.bit12~bit15分别表示第1~4个灾备存储装置,相应位为1则表示该存储器发生故障
    Integer DriverBehaviorAlarm = 24;    // 0x18 异常驾驶行为报警详细描述,WORD,定义见表15

    Integer Signal = 37;                 // 0x25 扩展车辆信号状态位,参数项格式和定义见表31
    Integer IoState = 42;                // 0x2a I0状态位,参数项格式和定义见表32
    Integer AnalogQuantity = 43;         // 0x2b 模拟量,bit[0~15],AD0;bit[l6~31],ADl
    Integer SignalStrength = 48;         // 0x30 数据类型为BYTE,无线通信网络信号强度
    Integer GnssCount = 49;              // 0x31 数据类型为BYTE,GNSS定位卫星数
    Integer AlarmADAS = 100;             // 0x64 高级驾驶辅助系统报警
    Integer AlarmDSM = 101;              // 0x65 驾驶员状态监测
    Integer AlarmTPMS = 102;             // 0x66 轮胎气压监测系统
    Integer AlarmBSD = 103;              // 0x67 盲点监测
    Integer InstallErrorMsg = 241;       // 0xF1 安装异常信息,由厂家自定义(粤标)
    Integer AlgorithmErrorMsg = 242;     // 0xF2 算法异常信息,由厂家自定义(粤标)
}