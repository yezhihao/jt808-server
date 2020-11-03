package org.yzh.protocol.commons.transform.parameter;

import io.netty.buffer.ByteBuf;

/**
 * 驾驶员状态监测系统参数
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ParamDSM {

    public static final int id = 0xF365;

    public static int id() {
        return id;
    }

    public byte 报警判断速度阈值;
    public byte 报警音量;
    public byte 主动拍照策略;
    public short 主动定时拍照时间间隔;
    public short 主动定距拍照距离间隔;
    public byte 单次主动拍照张数;
    public byte 单次主动拍照时间间隔;
    public byte 拍照分辨率;
    public byte 视频录制分辨率;
    public int 报警使能;
    public int 事件使能;
    public short 吸烟报警判断时间间隔;
    public short 接打电话报警判断时间间隔;
    public byte[] 预留字段 = new byte[3];
    public byte 疲劳驾驶报警分级速度阈值;
    public byte 疲劳驾驶报警前后视频录制时间;
    public byte 疲劳驾驶报警拍照张数;
    public byte 疲劳驾驶报警拍照间隔时间;
    public byte 接打电话报警分级速度阈值;
    public byte 接打电话报警前后视频录制时间;
    public byte 接打电话报警拍驾驶员面部特征照片张数;
    public byte 接打电话报警拍驾驶员面部特征照片间隔时间;
    public byte 抽烟报警分级车速阈值;
    public byte 抽烟报警前后视频录制时间;
    public byte 抽烟报警拍驾驶员面部特征照片张数;
    public byte 抽烟报警拍驾驶员面部特征照片间隔时间;
    public byte 分神驾驶报警分级车速阈值;
    public byte 分神驾驶报警前后视频录制时间;
    public byte 分神驾驶报警拍照张数;
    public byte 分神驾驶报警拍照间隔时间;
    public byte 驾驶行为异常分级速度阈值;
    public byte 驾驶行为异常视频录制时间;
    public byte 驾驶行为异常抓拍照片张数;
    public byte 驾驶行为异常拍照间隔;
    public byte 驾驶员身份识别触发;
    public short 保留字段;

    public static class Schema implements io.github.yezhihao.protostar.Schema<ParamDSM> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public ParamDSM readFrom(ByteBuf input) {
            ParamDSM message = new ParamDSM();
            message.报警判断速度阈值 = input.readByte();
            message.报警音量 = input.readByte();
            message.主动拍照策略 = input.readByte();
            message.主动定时拍照时间间隔 = input.readShort();
            message.主动定距拍照距离间隔 = input.readShort();
            message.单次主动拍照张数 = input.readByte();
            message.单次主动拍照时间间隔 = input.readByte();
            message.拍照分辨率 = input.readByte();
            message.视频录制分辨率 = input.readByte();
            message.报警使能 = input.readInt();
            message.事件使能 = input.readInt();
            message.吸烟报警判断时间间隔 = input.readShort();
            message.接打电话报警判断时间间隔 = input.readShort();
            input.readBytes(message.预留字段);
            message.疲劳驾驶报警分级速度阈值 = input.readByte();
            message.疲劳驾驶报警前后视频录制时间 = input.readByte();
            message.疲劳驾驶报警拍照张数 = input.readByte();
            message.疲劳驾驶报警拍照间隔时间 = input.readByte();
            message.接打电话报警分级速度阈值 = input.readByte();
            message.接打电话报警前后视频录制时间 = input.readByte();
            message.接打电话报警拍驾驶员面部特征照片张数 = input.readByte();
            message.接打电话报警拍驾驶员面部特征照片间隔时间 = input.readByte();
            message.抽烟报警分级车速阈值 = input.readByte();
            message.抽烟报警前后视频录制时间 = input.readByte();
            message.抽烟报警拍驾驶员面部特征照片张数 = input.readByte();
            message.抽烟报警拍驾驶员面部特征照片间隔时间 = input.readByte();
            message.分神驾驶报警分级车速阈值 = input.readByte();
            message.分神驾驶报警前后视频录制时间 = input.readByte();
            message.分神驾驶报警拍照张数 = input.readByte();
            message.分神驾驶报警拍照间隔时间 = input.readByte();
            message.驾驶行为异常分级速度阈值 = input.readByte();
            message.驾驶行为异常视频录制时间 = input.readByte();
            message.驾驶行为异常抓拍照片张数 = input.readByte();
            message.驾驶行为异常拍照间隔 = input.readByte();
            message.驾驶员身份识别触发 = input.readByte();
            message.保留字段 = input.readShort();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamDSM message) {
            output.writeByte(message.报警判断速度阈值);
            output.writeByte(message.报警音量);
            output.writeByte(message.主动拍照策略);
            output.writeShort(message.主动定时拍照时间间隔);
            output.writeShort(message.主动定距拍照距离间隔);
            output.writeByte(message.单次主动拍照张数);
            output.writeByte(message.单次主动拍照时间间隔);
            output.writeByte(message.拍照分辨率);
            output.writeByte(message.视频录制分辨率);
            output.writeInt(message.报警使能);
            output.writeInt(message.事件使能);
            output.writeShort(message.吸烟报警判断时间间隔);
            output.writeShort(message.接打电话报警判断时间间隔);
            output.writeBytes(message.预留字段);
            output.writeByte(message.疲劳驾驶报警分级速度阈值);
            output.writeByte(message.疲劳驾驶报警前后视频录制时间);
            output.writeByte(message.疲劳驾驶报警拍照张数);
            output.writeByte(message.疲劳驾驶报警拍照间隔时间);
            output.writeByte(message.接打电话报警分级速度阈值);
            output.writeByte(message.接打电话报警前后视频录制时间);
            output.writeByte(message.接打电话报警拍驾驶员面部特征照片张数);
            output.writeByte(message.接打电话报警拍驾驶员面部特征照片间隔时间);
            output.writeByte(message.抽烟报警分级车速阈值);
            output.writeByte(message.抽烟报警前后视频录制时间);
            output.writeByte(message.抽烟报警拍驾驶员面部特征照片张数);
            output.writeByte(message.抽烟报警拍驾驶员面部特征照片间隔时间);
            output.writeByte(message.分神驾驶报警分级车速阈值);
            output.writeByte(message.分神驾驶报警前后视频录制时间);
            output.writeByte(message.分神驾驶报警拍照张数);
            output.writeByte(message.分神驾驶报警拍照间隔时间);
            output.writeByte(message.驾驶行为异常分级速度阈值);
            output.writeByte(message.驾驶行为异常视频录制时间);
            output.writeByte(message.驾驶行为异常抓拍照片张数);
            output.writeByte(message.驾驶行为异常拍照间隔);
            output.writeByte(message.驾驶员身份识别触发);
            output.writeShort(message.保留字段);
        }
    }
}