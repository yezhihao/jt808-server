package org.yzh.protocol.commons.transform;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.PrepareLoadStrategy;
import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.converter.MapConverter;
import io.github.yezhihao.protostar.schema.StringSchema;
import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.parameter.*;

/**
 * 终端参数项转换器
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class ParameterConverter extends MapConverter<Integer, Object> {

    @Override
    protected void addSchemas(PrepareLoadStrategy schemaRegistry) {
        Schema<String> stringSchema = StringSchema.Chars.getInstance((byte) 0, "gbk");
        schemaRegistry
                .addSchema(0x0001, DataType.DWORD)//终端心跳发送间隔,单位为秒(s)
                .addSchema(0x0002, DataType.DWORD)//TCP消息应答超时时间,单位为秒(s)
                .addSchema(0x0003, DataType.DWORD)//TCP消息重传次数
                .addSchema(0x0004, DataType.DWORD)//UDP消息应答超时时间,单位为秒(s)
                .addSchema(0x0005, DataType.DWORD)//UDP消息重传次数
                .addSchema(0x0006, DataType.DWORD)//SMS消息应答超时时间,单位为秒(s)
                .addSchema(0x0007, DataType.DWORD)//SMS消息重传次数

                .addSchema(0x0010, stringSchema)//主服务器APN,无线通信拨号访问点.若网络制式为CDMA,则该处为PPP拨号号码
                .addSchema(0x0011, stringSchema)//主服务器无线通信拨号用户名
                .addSchema(0x0012, stringSchema)//主服务器无线通信拨号密码
                .addSchema(0x0013, stringSchema)//主服务器地址,IP或域名
                .addSchema(0x0014, stringSchema)//备份服务器APN,无线通信拨号访问点
                .addSchema(0x0015, stringSchema)//备份服务器无线通信拨号用户名
                .addSchema(0x0016, stringSchema)//备份服务器无线通信拨号密码
                .addSchema(0x0017, stringSchema)//备份服务器地址,IP或域名(2019版以冒号分割主机和端口,多个服务器使用分号分隔)
                .addSchema(0x0018, DataType.DWORD)//服务器TCP端口(2013版)
                .addSchema(0x0019, DataType.DWORD)//服务器UDP端口(2013版)


                .addSchema(0x001A, stringSchema)//道路运输证IC卡认证主服务器IP地址或域名
                .addSchema(0x001B, DataType.DWORD)//道路运输证IC卡认证主服务器TCP端口
                .addSchema(0x001C, DataType.DWORD)//道路运输证IC卡认证主服务器UDP端口
                .addSchema(0x001D, stringSchema)//道路运输证IC卡认证主服务器IP地址或域名,端口同主服务器

                .addSchema(0x0020, DataType.DWORD)//位置汇报策略：0.定时汇报 1.定距汇报 2.定时和定距汇报
                .addSchema(0x0021, DataType.DWORD)//位置汇报方案：0.根据ACC状态 1.根据登录状态和ACC状态,先判断登录状态,若登录再根据ACC状态
                .addSchema(0x0022, DataType.DWORD)//驾驶员未登录汇报时间间隔,单位为秒(s),>0

                //JT808 2019
                .addSchema(0x0023, stringSchema)//从服务器APN.该值为空时,终端应使用主服务器相同配置
                .addSchema(0x0024, stringSchema)//从服务器无线通信拨号用户名.该值为空时,终端应使用主服务器相同配置
                .addSchema(0x0025, stringSchema)//从服务器无线通信拨号密码.该值为空时,终端应使用主服务器相同配置
                .addSchema(0x0026, stringSchema)//从服务器备份地址、IP或域名.主服务器IP地址或域名,端口同主服务器
                //JT808 2019

                .addSchema(0x0027, DataType.DWORD)//休眠时汇报时间间隔,单位为秒(s),>0
                .addSchema(0x0028, DataType.DWORD)//紧急报警时汇报时间间隔,单位为秒(s),>0
                .addSchema(0x0029, DataType.DWORD)//缺省时间汇报间隔,单位为秒(s),>0

                .addSchema(0x002C, DataType.DWORD)//缺省距离汇报间隔,单位为米(m),>0
                .addSchema(0x002D, DataType.DWORD)//驾驶员未登录汇报距离间隔,单位为米(m),>0
                .addSchema(0x002E, DataType.DWORD)//休眠时汇报距离间隔,单位为米(m),>0
                .addSchema(0x002F, DataType.DWORD)//紧急报警时汇报距离间隔,单位为米(m),>0
                .addSchema(0x0030, DataType.DWORD)//拐点补传角度,<180°
                .addSchema(0x0031, DataType.WORD)//电子围栏半径,单位为米
                //JT808 2019
                .addSchema(0x0032, TimeRange.Schema.INSTANCE)//违规行驶时段范围,精确到分

                .addSchema(0x0040, stringSchema)//监控平台电话号码
                .addSchema(0x0041, stringSchema)//复位电话号码,可采用此电话号码拨打终端电话让终端复位
                .addSchema(0x0042, stringSchema)//恢复出厂设置电话号码,可采用此电话号码拨打终端电话让终端恢复出厂设置
                .addSchema(0x0043, stringSchema)//监控平台SMS电话号码
                .addSchema(0x0044, stringSchema)//接收终端SMS文本报警号码
                .addSchema(0x0045, DataType.DWORD)//终端电话接听策略,0.自动接听 1.ACC ON时自动接听,OFF时手动接听
                .addSchema(0x0046, DataType.DWORD)//每次最长通话时间,单位为秒(s),0为不允许通话,0xFFFFFFFF为不限制
                .addSchema(0x0047, DataType.DWORD)//当月最长通话时间,单位为秒(s),0为不允许通话,0xFFFFFFFF为不限制
                .addSchema(0x0048, stringSchema)//监听电话号码
                .addSchema(0x0049, stringSchema)//监管平台特权短信号码

                .addSchema(0x0050, DataType.DWORD)//报警屏蔽字.与位置信息汇报消息中的报警标志相对应,相应位为1则相应报警被屏蔽
                .addSchema(0x0051, DataType.DWORD)//报警发送文本SMS开关,与位置信息汇报消息中的报警标志相对应,相应位为1则相应报警时发送文本SMS
                .addSchema(0x0052, DataType.DWORD)//报警拍摄开关,与位置信息汇报消息中的报警标志相对应,相应位为1则相应报警时摄像头拍摄
                .addSchema(0x0053, DataType.DWORD)//报警拍摄存储标志,与位置信息汇报消息中的报警标志相对应,相应位为1则对相应报警时牌的照片进行存储,否则实时长传
                .addSchema(0x0054, DataType.DWORD)//关键标志,与位置信息汇报消息中的报警标志相对应,相应位为1则对相应报警为关键报警
                .addSchema(0x0055, DataType.DWORD)//最高速度,单位为公里每小时(km/h)
                .addSchema(0x0056, DataType.DWORD)//超速持续时间,单位为秒(s)
                .addSchema(0x0057, DataType.DWORD)//连续驾驶时间门限,单位为秒(s)
                .addSchema(0x0058, DataType.DWORD)//当天累计驾驶时间门限,单位为秒(s)
                .addSchema(0x0059, DataType.DWORD)//最小休息时间,单位为秒(s)
                .addSchema(0x005A, DataType.DWORD)//最长停车时间,单位为秒(s)

                .addSchema(0x005B, DataType.WORD)//超速预警差值
                .addSchema(0x005C, DataType.WORD)//疲劳驾驶预警插值
                .addSchema(0x005D, DataType.WORD)//碰撞报警参数
                .addSchema(0x005E, DataType.WORD)//侧翻报警参数

                .addSchema(0x0064, DataType.DWORD)//定时拍照参数
                .addSchema(0x0065, DataType.DWORD)//定距拍照参数

                .addSchema(0x0070, DataType.DWORD)//图像/视频质量,1~10,1最好
                .addSchema(0x0071, DataType.DWORD)//亮度,0~255
                .addSchema(0x0072, DataType.DWORD)//对比度,0~127
                .addSchema(0x0073, DataType.DWORD)//饱和度,0~127
                .addSchema(0x0074, DataType.DWORD)//色度,0~255

                //JT1078 start
                .addSchema(ParamVideo.id, ParamVideo.S.INSTANCE)//音视频参数设置,描述见表2
                .addSchema(ParamChannels.id, ParamChannels.S.INSTANCE)//音视频通道列表设置,描述见表3
                .addSchema(ParamVideoSingle.id, ParamVideoSingle.S.INSTANCE)//单独视频通道参数设置,描述见表5
                .addSchema(ParamVideoSpecialAlarm.id, ParamVideoSpecialAlarm.S.INSTANCE)//特殊报警录像参数设置,描述见表7
                .addSchema(0x007A, DataType.DWORD)//视频相关报警屏蔽字,和表13的视频报警标志位定义相对应,相应位为1则相应类型的报警被屏蔽
                .addSchema(ParamImageIdentifyAlarm.id, ParamImageIdentifyAlarm.S.INSTANCE)// 图像分析报警参数设置描述见表8
                .addSchema(ParamSleepWake.id, ParamSleepWake.S.INSTANCE)//终端休眠唤醒模式设置,描述见表9
                //JT1078 end

                .addSchema(0x0080, DataType.DWORD)//车辆里程表读数,1/10km
                .addSchema(0x0081, DataType.WORD)//车辆所在的省域ID
                .addSchema(0x0082, DataType.WORD)//车辆所在的市域ID
                .addSchema(0x0083, stringSchema)//公安交通管理部门颁发的机动车号牌
                .addSchema(0x0084, DataType.BYTE)//车牌颜色,按照JT/T415-2006的5.4.12

                .addSchema(0x0090, DataType.BYTE)//定位模式
                .addSchema(0x0091, DataType.BYTE)//波特率
                .addSchema(0x0092, DataType.BYTE)//模块详细定位数据输出频率
                .addSchema(0x0093, DataType.DWORD)//模块详细定位数据采集频率,单位为秒,默认为1
                .addSchema(0x0094, DataType.BYTE)//模块详细定位数据上传方式
                .addSchema(0x0095, DataType.DWORD)//模块详细定位数据上传设置
                .addSchema(0x0100, DataType.DWORD)//总线通道1 采集时间间隔(ms),0 表示不采集
                .addSchema(0x0101, DataType.WORD)//总线通道1 上传时间间隔(s),0 表示不上传
                .addSchema(0x0102, DataType.DWORD)//总线通道2 采集时间间隔(ms),0 表示不采集
                .addSchema(0x0103, DataType.WORD)//总线通道2 上传时间间隔(s),0 表示不上传
                .addSchema(0x0110, DataType.BYTES)//总线ID 单独采集设置

                //JSATL12 start
                .addSchema(ParamADAS.id, ParamADAS.S.INSTANCE)//高级驾驶辅助系统参数,见表4-10
                .addSchema(ParamDSM.id, ParamDSM.S.INSTANCE)//驾驶员状态监测系统参数,见表4-11
                .addSchema(ParamTPMS.id, ParamTPMS.S.INSTANCE)//胎压监测系统参数,见表4-12
                .addSchema(ParamBSD.id, ParamBSD.S.INSTANCE)//盲区监测系统参数,见表4-13
                //粤标
                .addSchema(0xF370, DataType.BYTE)//智能视频协议版本信息,引入此智能视频协议版本信息方便平台进行版本控制初始版本是1,每次修订版本号都会递增（注：只支持获取,不支持设置）
        ;
    }

    @Override
    protected Integer readKey(ByteBuf input) {
        return input.readInt();
    }

    @Override
    protected void writeKey(ByteBuf output, Integer key) {
        output.writeInt(key);
    }

    @Override
    protected int valueSize() {
        return 1;
    }
}