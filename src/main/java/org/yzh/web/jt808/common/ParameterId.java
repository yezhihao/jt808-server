package org.yzh.web.jt808.common;

import org.yzh.framework.enums.DataType;

public enum ParameterId {

    C0x0001(0x0001, DataType.DWORD, "0x0001:终端心跳发送间隔，单位为秒(s)"),
    C0x0002(0x0002, DataType.DWORD, "0x0002:TCP消息应答超时时间，单位为秒(s)"),
    C0x0003(0x0003, DataType.DWORD, "0x0003:TCP消息重传次数"),
    C0x0004(0x0004, DataType.DWORD, "0x0004:UDP消息应答超时时间，单位为秒(s)"),
    C0x0005(0x0005, DataType.DWORD, "0x0005:UDP消息重传次数"),
    C0x0006(0x0006, DataType.DWORD, "0x0006:SMS消息应答超时时间，单位为秒(s)"),
    C0x0007(0x0007, DataType.DWORD, "0x0007:SMS消息重传次数"),

    C0x0010(0x0010, DataType.STRING, "0x0010:主服务器APN，无线通信拨号访问点。若网络制式为CDMA，则该处为PPP拨号号码"),
    C0x0011(0x0011, DataType.STRING, "0x0011:主服务器无线通信拨号用户名"),
    C0x0012(0x0012, DataType.STRING, "0x0012:主服务器无线通信拨号密码"),
    C0x0013(0x0013, DataType.STRING, "0x0013:主服务器地址，IP或域名"),
    C0x0014(0x0014, DataType.STRING, "0x0014:备份服务器APN，无线通信拨号访问点"),
    C0x0015(0x0015, DataType.STRING, "0x0015:备份服务器无线通信拨号用户名"),
    C0x0016(0x0016, DataType.STRING, "0x0016:备份服务器无线通信拨号密码"),
    C0x0017(0x0017, DataType.STRING, "0x0017:备份服务器地址，IP或域名"),
    C0x0018(0x0018, DataType.DWORD, "0x0018:服务器TCP端口"),
    C0x0019(0x0019, DataType.DWORD, "0x0019:服务器UDP端口"),

    C0x0020(0x0020, DataType.DWORD, "0x0020:位置汇报策略，0：定时汇报；1：定距汇报；2：定时和定距汇报"),
    C0x0021(0x0021, DataType.DWORD, "0x0021:位置汇报方案，0：根据ACC状态；1：根据登录状态和ACC状态，先判断登录状态，若登录再根据ACC状态"),
    C0x0022(0x0022, DataType.DWORD, "0x0022:驾驶员未登录汇报时间间隔，单位为秒(s),>0"),

    C0x0027(0x0027, DataType.DWORD, "0x0027:休眠时汇报时间间隔，单位为秒(s),>0"),
    C0x0028(0x0028, DataType.DWORD, "0x0028:紧急报警时汇报时间间隔，单位为秒(s),>0"),
    C0x0029(0x0029, DataType.DWORD, "0x0029:缺省时间汇报间隔，单位为秒(s),>0"),

    C0x002C(0x002C, DataType.DWORD, "0x002C:缺省距离汇报间隔，单位为米(m),>0"),
    C0x002D(0x002D, DataType.DWORD, "0x002D:驾驶员未登录汇报距离间隔，单位为米(m),>0"),
    C0x002E(0x002E, DataType.DWORD, "0x002E:休眠时汇报距离间隔，单位为米(m),>0"),
    C0x002F(0x002F, DataType.DWORD, "0x002F:紧急报警时汇报距离间隔，单位为米(m),>0"),
    C0x0030(0x0030, DataType.DWORD, "0x0030:拐点补传角度，<180°"),

    C0x0040(0x0040, DataType.STRING, "0x0040:监控平台电话号码"),
    C0x0041(0x0041, DataType.STRING, "0x0041:复位电话号码，可采用此电话号码拨打终端电话让终端复位"),
    C0x0042(0x0042, DataType.STRING, "0x0042:恢复出厂设置电话号码，可采用此电话号码拨打终端电话让终端恢复出厂设置"),
    C0x0043(0x0043, DataType.STRING, "0x0043:监控平台SMS电话号码"),
    C0x0044(0x0044, DataType.STRING, "0x0044:接收终端SMS文本报警号码"),
    C0x0045(0x0045, DataType.DWORD, "0x0045:终端电话接听策略，0：自动接听；1：ACC ON时自动接听，OFF时手动接听"),
    C0x0046(0x0046, DataType.DWORD, "0x0046:每次最长通话时间，单位为秒(s),0为不允许通话，0xFFFFFFFF为不限制"),
    C0x0047(0x0047, DataType.DWORD, "0x0047:当月最长通话时间，单位为秒(s),0为不允许通话，0xFFFFFFFF为不限制"),
    C0x0048(0x0048, DataType.STRING, "0x0048:监听电话号码"),
    C0x0049(0x0049, DataType.STRING, "0x0049:监管平台特权短信号码"),

    C0x0050(0x0050, DataType.DWORD, "0x0050:报警屏蔽字。与位置信息汇报消息中的报警标志相对应，相应位为1则相应报警被屏蔽"),
    C0x0051(0x0051, DataType.DWORD, "0x0051:报警发送文本SMS开关，与位置信息汇报消息中的报警标志相对应，相应位为1则相应报警时发送文本SMS"),
    C0x0052(0x0052, DataType.DWORD, "0x0052:报警拍摄开关，与位置信息汇报消息中的报警标志相对应，相应位为1则相应报警时摄像头拍摄"),
    C0x0053(0x0053, DataType.DWORD, "0x0053:报警拍摄存储标志，与位置信息汇报消息中的报警标志相对应，相应位为1则对相应报警时牌的照片进行存储，否则实时长传"),
    C0x0054(0x0054, DataType.DWORD, "0x0054:关键标志，与位置信息汇报消息中的报警标志相对应，相应位为1则对相应报警为关键报警"),
    C0x0055(0x0055, DataType.DWORD, "0x0055:最高速度，单位为公里每小时(km/h)"),
    C0x0056(0x0056, DataType.DWORD, "0x0056:超速持续时间，单位为秒(s)"),
    C0x0057(0x0057, DataType.DWORD, "0x0057:连续驾驶时间门限，单位为秒(s)"),
    C0x0058(0x0058, DataType.DWORD, "0x0058:当天累计驾驶时间门限，单位为秒(s)"),
    C0x0059(0x0059, DataType.DWORD, "0x0059:最小休息时间，单位为秒(s)"),
    C0x005A(0x005A, DataType.DWORD, "0x005A:最长停车时间，单位为秒(s)"),

    C0x0070(0x0070, DataType.DWORD, "0x0070:图像/视频质量，1-10,1最好"),
    C0x0071(0x0071, DataType.DWORD, "0x0071:亮度，0-255"),
    C0x0072(0x0072, DataType.DWORD, "0x0072:对比度，0-127"),
    C0x0073(0x0073, DataType.DWORD, "0x0073:饱和度，0-127"),
    C0x0074(0x0074, DataType.DWORD, "0x0074:色度，0-255"),

    C0x0080(0x0080, DataType.DWORD, "0x0080:车辆里程表读数，1/10km"),
    C0x0081(0x0081, DataType.WORD, "0x0081:车辆所在的省域ID"),
    C0x0082(0x0082, DataType.WORD, "0x0082:车辆所在的市域ID"),
    C0x0083(0x0083, DataType.STRING, "0x0083:公安交通管理部门颁发的机动车号牌"),
    C0x0084(0x0084, DataType.BYTE, "0x0084:车牌颜色，按照JT/T415-2006的5.4.12"),

    C0x0090(0x0090, DataType.BYTE, "GNSS 定位模式"),
    C0x0091(0x0091, DataType.BYTE, "GNSS 波特率"),
    C0x0092(0x0092, DataType.BYTE, "GNSS 模块详细定位数据输出频率"),
    C0x0093(0x0093, DataType.DWORD, "GNSS 模块详细定位数据采集频率，单位为秒，默认为 1。"),
    C0x0094(0x0094, DataType.BYTE, "GNSS 模块详细定位数据上传方式"),
    C0x0095(0x0095, DataType.DWORD, "GNSS 模块详细定位数据上传设置"),
    C0x0100(0x0100, DataType.DWORD, "CAN 总线通道 1 采集时间间隔(ms)，0 表示不采集"),
    C0x0101(0x0101, DataType.WORD, "CAN 总线通道 1 上传时间间隔(s)，0 表示不上传"),
    C0x0102(0x0102, DataType.DWORD, "CAN 总线通道 2 采集时间间隔(ms)，0 表示不采集"),
    C0x0103(0x0103, DataType.WORD, "CAN 总线通道 2 上传时间间隔(s)，0 表示不上传"),
    C0x0110(0x0110, DataType.BYTES, "CAN 总线 ID 单独采集设置");

    public final int value;
    public final DataType type;
    public final String desc;

    ParameterId(int value, DataType type, String desc) {
        this.value = value;
        this.type = type;
        this.desc = desc;
    }

    public static ParameterId toEnum(int value) {
        for (ParameterId type : ParameterId.values())
            if (value == type.value)
                return type;
        return null;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}