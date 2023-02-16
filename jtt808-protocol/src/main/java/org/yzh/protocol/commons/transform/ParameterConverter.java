package org.yzh.protocol.commons.transform;

import io.github.yezhihao.protostar.PrepareLoadStrategy;
import io.github.yezhihao.protostar.schema.ArraySchema;
import io.github.yezhihao.protostar.schema.MapSchema;
import io.github.yezhihao.protostar.schema.NumberSchema;
import io.github.yezhihao.protostar.schema.StringSchema;
import org.yzh.protocol.commons.transform.parameter.*;

/**
 * 终端参数项转换器
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class ParameterConverter extends MapSchema<Number, Object> {

    public ParameterConverter() {
        super(NumberSchema.DWORD_INT, 1);
    }

    @Override
    protected void addSchemas(PrepareLoadStrategy<Number> schemaRegistry) {
        schemaRegistry
                .addSchema(0x0001, NumberSchema.DWORD_LONG)//终端心跳发送间隔,单位为秒(s)
                .addSchema(0x0002, NumberSchema.DWORD_LONG)//TCP消息应答超时时间,单位为秒(s)
                .addSchema(0x0003, NumberSchema.DWORD_LONG)//TCP消息重传次数
                .addSchema(0x0004, NumberSchema.DWORD_LONG)//UDP消息应答超时时间,单位为秒(s)
                .addSchema(0x0005, NumberSchema.DWORD_LONG)//UDP消息重传次数
                .addSchema(0x0006, NumberSchema.DWORD_LONG)//SMS消息应答超时时间,单位为秒(s)
                .addSchema(0x0007, NumberSchema.DWORD_LONG)//SMS消息重传次数

                .addSchema(0x0010, StringSchema.GBK)//主服务器APN,无线通信拨号访问点.若网络制式为CDMA,则该处为PPP拨号号码
                .addSchema(0x0011, StringSchema.GBK)//主服务器无线通信拨号用户名
                .addSchema(0x0012, StringSchema.GBK)//主服务器无线通信拨号密码
                .addSchema(0x0013, StringSchema.GBK)//主服务器地址,IP或域名
                .addSchema(0x0014, StringSchema.GBK)//备份服务器APN,无线通信拨号访问点
                .addSchema(0x0015, StringSchema.GBK)//备份服务器无线通信拨号用户名
                .addSchema(0x0016, StringSchema.GBK)//备份服务器无线通信拨号密码
                .addSchema(0x0017, StringSchema.GBK)//备份服务器地址,IP或域名(2019版以冒号分割主机和端口,多个服务器使用分号分隔)
                .addSchema(0x0018, NumberSchema.DWORD_LONG)//服务器TCP端口(2013版)
                .addSchema(0x0019, NumberSchema.DWORD_LONG)//服务器UDP端口(2013版)


                .addSchema(0x001A, StringSchema.GBK)//道路运输证IC卡认证主服务器IP地址或域名
                .addSchema(0x001B, NumberSchema.DWORD_LONG)//道路运输证IC卡认证主服务器TCP端口
                .addSchema(0x001C, NumberSchema.DWORD_LONG)//道路运输证IC卡认证主服务器UDP端口
                .addSchema(0x001D, StringSchema.GBK)//道路运输证IC卡认证主服务器IP地址或域名,端口同主服务器

                .addSchema(0x0020, NumberSchema.DWORD_LONG)//位置汇报策略：0.定时汇报 1.定距汇报 2.定时和定距汇报
                .addSchema(0x0021, NumberSchema.DWORD_LONG)//位置汇报方案：0.根据ACC状态 1.根据登录状态和ACC状态,先判断登录状态,若登录再根据ACC状态
                .addSchema(0x0022, NumberSchema.DWORD_LONG)//驾驶员未登录汇报时间间隔,单位为秒(s),>0

                //JT808 2019
                .addSchema(0x0023, StringSchema.GBK)//从服务器APN.该值为空时,终端应使用主服务器相同配置
                .addSchema(0x0024, StringSchema.GBK)//从服务器无线通信拨号用户名.该值为空时,终端应使用主服务器相同配置
                .addSchema(0x0025, StringSchema.GBK)//从服务器无线通信拨号密码.该值为空时,终端应使用主服务器相同配置
                .addSchema(0x0026, StringSchema.GBK)//从服务器备份地址、IP或域名.主服务器IP地址或域名,端口同主服务器
                //JT808 2019

                .addSchema(0x0027, NumberSchema.DWORD_LONG)//休眠时汇报时间间隔,单位为秒(s),>0
                .addSchema(0x0028, NumberSchema.DWORD_LONG)//紧急报警时汇报时间间隔,单位为秒(s),>0
                .addSchema(0x0029, NumberSchema.DWORD_LONG)//缺省时间汇报间隔,单位为秒(s),>0

                .addSchema(0x002C, NumberSchema.DWORD_LONG)//缺省距离汇报间隔,单位为米(m),>0
                .addSchema(0x002D, NumberSchema.DWORD_LONG)//驾驶员未登录汇报距离间隔,单位为米(m),>0
                .addSchema(0x002E, NumberSchema.DWORD_LONG)//休眠时汇报距离间隔,单位为米(m),>0
                .addSchema(0x002F, NumberSchema.DWORD_LONG)//紧急报警时汇报距离间隔,单位为米(m),>0
                .addSchema(0x0030, NumberSchema.DWORD_LONG)//拐点补传角度,<180°
                .addSchema(0x0031, NumberSchema.WORD_INT)//电子围栏半径,单位为米
                //JT808 2019
                .addSchema(0x0032, TimeRange.SCHEMA)//违规行驶时段范围,精确到分

                .addSchema(0x0040, StringSchema.GBK)//监控平台电话号码
                .addSchema(0x0041, StringSchema.GBK)//复位电话号码,可采用此电话号码拨打终端电话让终端复位
                .addSchema(0x0042, StringSchema.GBK)//恢复出厂设置电话号码,可采用此电话号码拨打终端电话让终端恢复出厂设置
                .addSchema(0x0043, StringSchema.GBK)//监控平台SMS电话号码
                .addSchema(0x0044, StringSchema.GBK)//接收终端SMS文本报警号码
                .addSchema(0x0045, NumberSchema.DWORD_LONG)//终端电话接听策略,0.自动接听 1.ACC ON时自动接听,OFF时手动接听
                .addSchema(0x0046, NumberSchema.DWORD_LONG)//每次最长通话时间,单位为秒(s),0为不允许通话,0xFFFFFFFF为不限制
                .addSchema(0x0047, NumberSchema.DWORD_LONG)//当月最长通话时间,单位为秒(s),0为不允许通话,0xFFFFFFFF为不限制
                .addSchema(0x0048, StringSchema.GBK)//监听电话号码
                .addSchema(0x0049, StringSchema.GBK)//监管平台特权短信号码

                .addSchema(0x0050, NumberSchema.DWORD_LONG)//报警屏蔽字.与位置信息汇报消息中的报警标志相对应,相应位为1则相应报警被屏蔽
                .addSchema(0x0051, NumberSchema.DWORD_LONG)//报警发送文本SMS开关,与位置信息汇报消息中的报警标志相对应,相应位为1则相应报警时发送文本SMS
                .addSchema(0x0052, NumberSchema.DWORD_LONG)//报警拍摄开关,与位置信息汇报消息中的报警标志相对应,相应位为1则相应报警时摄像头拍摄
                .addSchema(0x0053, NumberSchema.DWORD_LONG)//报警拍摄存储标志,与位置信息汇报消息中的报警标志相对应,相应位为1则对相应报警时牌的照片进行存储,否则实时长传
                .addSchema(0x0054, NumberSchema.DWORD_LONG)//关键标志,与位置信息汇报消息中的报警标志相对应,相应位为1则对相应报警为关键报警
                .addSchema(0x0055, NumberSchema.DWORD_LONG)//最高速度,单位为公里每小时(km/h)
                .addSchema(0x0056, NumberSchema.DWORD_LONG)//超速持续时间,单位为秒(s)
                .addSchema(0x0057, NumberSchema.DWORD_LONG)//连续驾驶时间门限,单位为秒(s)
                .addSchema(0x0058, NumberSchema.DWORD_LONG)//当天累计驾驶时间门限,单位为秒(s)
                .addSchema(0x0059, NumberSchema.DWORD_LONG)//最小休息时间,单位为秒(s)
                .addSchema(0x005A, NumberSchema.DWORD_LONG)//最长停车时间,单位为秒(s)

                .addSchema(0x005B, NumberSchema.WORD_INT)//超速预警差值
                .addSchema(0x005C, NumberSchema.WORD_INT)//疲劳驾驶预警插值
                .addSchema(0x005D, NumberSchema.WORD_INT)//碰撞报警参数
                .addSchema(0x005E, NumberSchema.WORD_INT)//侧翻报警参数

                .addSchema(0x0064, NumberSchema.DWORD_LONG)//定时拍照参数
                .addSchema(0x0065, NumberSchema.DWORD_LONG)//定距拍照参数

                .addSchema(0x0070, NumberSchema.DWORD_LONG)//图像/视频质量,1~10,1最好
                .addSchema(0x0071, NumberSchema.DWORD_LONG)//亮度,0~255
                .addSchema(0x0072, NumberSchema.DWORD_LONG)//对比度,0~127
                .addSchema(0x0073, NumberSchema.DWORD_LONG)//饱和度,0~127
                .addSchema(0x0074, NumberSchema.DWORD_LONG)//色度,0~255

                //JT1078 start
                .addSchema(ParamVideo.key, ParamVideo.SCHEMA)//音视频参数设置,描述见表2
                .addSchema(ParamChannels.key, ParamChannels.SCHEMA)//音视频通道列表设置,描述见表3
                .addSchema(ParamVideoSingle.key, ParamVideoSingle.SCHEMA)//单独视频通道参数设置,描述见表5
                .addSchema(ParamVideoSpecialAlarm.key, ParamVideoSpecialAlarm.SCHEMA)//特殊报警录像参数设置,描述见表7
                .addSchema(0x007A, NumberSchema.DWORD_LONG)//视频相关报警屏蔽字,和表13的视频报警标志位定义相对应,相应位为1则相应类型的报警被屏蔽
                .addSchema(ParamImageIdentifyAlarm.key, ParamImageIdentifyAlarm.SCHEMA)// 图像分析报警参数设置描述见表8
                .addSchema(ParamSleepWake.key, ParamSleepWake.SCHEMA)//终端休眠唤醒模式设置,描述见表9
                //JT1078 end

                .addSchema(0x0080, NumberSchema.DWORD_LONG)//车辆里程表读数,1/10km
                .addSchema(0x0081, NumberSchema.WORD_INT)//车辆所在的省域ID
                .addSchema(0x0082, NumberSchema.WORD_INT)//车辆所在的市域ID
                .addSchema(0x0083, StringSchema.GBK)//公安交通管理部门颁发的机动车号牌
                .addSchema(0x0084, NumberSchema.BYTE_INT)//车牌颜色,按照JT/T415-2006的5.4.12

                .addSchema(0x0090, NumberSchema.BYTE_INT)//定位模式
                .addSchema(0x0091, NumberSchema.BYTE_INT)//波特率
                .addSchema(0x0092, NumberSchema.BYTE_INT)//模块详细定位数据输出频率
                .addSchema(0x0093, NumberSchema.DWORD_LONG)//模块详细定位数据采集频率,单位为秒,默认为1
                .addSchema(0x0094, NumberSchema.BYTE_INT)//模块详细定位数据上传方式
                .addSchema(0x0095, NumberSchema.DWORD_LONG)//模块详细定位数据上传设置
                .addSchema(0x0100, NumberSchema.DWORD_LONG)//总线通道1 采集时间间隔(ms),0 表示不采集
                .addSchema(0x0101, NumberSchema.WORD_INT)//总线通道1 上传时间间隔(s),0 表示不上传
                .addSchema(0x0102, NumberSchema.DWORD_LONG)//总线通道2 采集时间间隔(ms),0 表示不采集
                .addSchema(0x0103, NumberSchema.WORD_INT)//总线通道2 上传时间间隔(s),0 表示不上传
                .addSchema(0x0110, ArraySchema.BYTES)//总线ID 单独采集设置

                //JSATL12 start
                .addSchema(ParamADAS.key, ParamADAS.SCHEMA)//高级驾驶辅助系统参数,见表4-10
                .addSchema(ParamDSM.key, ParamDSM.SCHEMA)//驾驶员状态监测系统参数,见表4-11
                .addSchema(ParamTPMS.key, ParamTPMS.SCHEMA)//胎压监测系统参数,见表4-12
                .addSchema(ParamBSD.key, ParamBSD.SCHEMA)//盲区监测系统参数,见表4-13
                //粤标
                .addSchema(0xF370, NumberSchema.BYTE_INT)//智能视频协议版本信息,引入此智能视频协议版本信息方便平台进行版本控制初始版本是1,每次修订版本号都会递增（注：只支持获取,不支持设置）
        ;
    }
}