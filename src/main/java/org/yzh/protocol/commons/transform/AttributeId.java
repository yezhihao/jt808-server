package org.yzh.protocol.commons.transform;

/**
 * 位置附加信息
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface AttributeId {
    int Mileage = 0x01;                   //里程，DWORD，1/10km，对应车上里程表读数
    int Oil = 0x02;                       //油量，WORD，1/10L，对应车上油量表读数
    int Speed = 0x03;                     //行驶记录功能获取的速度，WORD，1/10km/h
    int AlarmEventId = 0x04;              //需要人工确认报警事件的 ID，WORD，从 1 开始计数
    int TirePressure = 0x05;              //胎压
    int CarriageTemperature = 0x06;       //车厢温度, 单位摄氏度
    int OverSpeedAlarm = 0x11;            //超速报警附加信息见表 28
    int InOutAreaAlarm = 0x12;            //进出区域/路线报警附加信息见表 29
    int RouteDriveTimeAlarm = 0x13;       //路段行驶时间不足/过长报警附加信息见表 30
    int Signal = 0x25;                    //扩展车辆信号状态位，定义见表 31
    int IoState = 0x2a;                   //IO 状态位，定义见表 32
    int AnalogQuantity = 0x2b;            //模拟量，bit0-15，AD0；bit16-31，AD1。
    int SignalStrength = 0x30;            //无线通信网络信号强度
    int GnssCount = 0x31;                 //GNSS 定位卫星数
    int AlarmADAS = 0x64;                 //高级驾驶辅助系统报警
    int AlarmBSD = 0x67;                  //盲点监测
    int AlarmDSM = 0x65;                  //驾驶员状态监测
    int AlarmTPMS = 0x66;                 //轮胎气压监测系统
}