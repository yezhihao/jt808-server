package org.yzh.protocol.commons.transform.parameter;

import io.netty.buffer.ByteBuf;

/**
 * 高级驾驶辅助系统参数
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ParamADAS {

    public static final int id = 0xF364;

    public static int id() {
        return id;
    }

    public byte 报警判断速度阈值;
    public byte 报警提示音量;
    public byte 主动拍照策略;
    public short 主动定时拍照时间间隔;
    public short 主动定距拍照距离间隔;
    public byte 单次主动拍照张数;
    public byte 单次主动拍照时间间隔;
    public byte 拍照分辨率;
    public byte 视频录制分辨率;
    public int 报警使能;
    public int 事件使能;
    public byte 预留字段;
    public byte 障碍物报警距离阈值;
    public byte 障碍物报警分级速度阈值;
    public byte 障碍物报警前后视频录制时间;
    public byte 障碍物报警拍照张数;
    public byte 障碍物报警拍照间隔;
    public byte 频繁变道报警判断时间段;
    public byte 频繁变道报警判断次数;
    public byte 频繁变道报警分级速度阈值;
    public byte 频繁变道报警前后视频录制时间;
    public byte 频繁变道报警拍照张数;
    public byte 频繁变道报警拍照间隔;
    public byte 车道偏离报警分级速度阈值;
    public byte 车道偏离报警前后视频录制时间;
    public byte 车道偏离报警拍照张数;
    public byte 车道偏离报警拍照间隔;
    public byte 前向碰撞报警时间阈值;
    public byte 前向碰撞报警分级速度阈值;
    public byte 前向碰撞报警前后视频录制时间;
    public byte 前向碰撞报警拍照张数;
    public byte 前向碰撞报警拍照间隔;
    public byte 行人碰撞报警时间阈值;
    public byte 行人碰撞报警使能速度阈值;
    public byte 行人碰撞报警前后视频录制时间;
    public byte 行人碰撞报警拍照张数;
    public byte 行人碰撞报警拍照间隔;
    public byte 车距监控报警距离阈值;
    public byte 车距监控报警分级速度阈值;
    public byte 车距过近报警前后视频录制时间;
    public byte 车距过近报警拍照张数;
    public byte 车距过近报警拍照间隔;
    public byte 道路标志识别拍照张数;
    public byte 道路标志识别拍照间隔;
    public int 保留字段;

    public ParamADAS() {
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<ParamADAS> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public ParamADAS readFrom(ByteBuf input) {
            ParamADAS message = new ParamADAS();
            message.报警判断速度阈值 = input.readByte();
            message.报警提示音量 = input.readByte();
            message.主动拍照策略 = input.readByte();
            message.主动定时拍照时间间隔 = input.readShort();
            message.主动定距拍照距离间隔 = input.readShort();
            message.单次主动拍照张数 = input.readByte();
            message.单次主动拍照时间间隔 = input.readByte();
            message.拍照分辨率 = input.readByte();
            message.视频录制分辨率 = input.readByte();
            message.报警使能 = input.readInt();
            message.事件使能 = input.readInt();
            message.预留字段 = input.readByte();
            message.障碍物报警距离阈值 = input.readByte();
            message.障碍物报警分级速度阈值 = input.readByte();
            message.障碍物报警前后视频录制时间 = input.readByte();
            message.障碍物报警拍照张数 = input.readByte();
            message.障碍物报警拍照间隔 = input.readByte();
            message.频繁变道报警判断时间段 = input.readByte();
            message.频繁变道报警判断次数 = input.readByte();
            message.频繁变道报警分级速度阈值 = input.readByte();
            message.频繁变道报警前后视频录制时间 = input.readByte();
            message.频繁变道报警拍照张数 = input.readByte();
            message.频繁变道报警拍照间隔 = input.readByte();
            message.车道偏离报警分级速度阈值 = input.readByte();
            message.车道偏离报警前后视频录制时间 = input.readByte();
            message.车道偏离报警拍照张数 = input.readByte();
            message.车道偏离报警拍照间隔 = input.readByte();
            message.前向碰撞报警时间阈值 = input.readByte();
            message.前向碰撞报警分级速度阈值 = input.readByte();
            message.前向碰撞报警前后视频录制时间 = input.readByte();
            message.前向碰撞报警拍照张数 = input.readByte();
            message.前向碰撞报警拍照间隔 = input.readByte();
            message.行人碰撞报警时间阈值 = input.readByte();
            message.行人碰撞报警使能速度阈值 = input.readByte();
            message.行人碰撞报警前后视频录制时间 = input.readByte();
            message.行人碰撞报警拍照张数 = input.readByte();
            message.行人碰撞报警拍照间隔 = input.readByte();
            message.车距监控报警距离阈值 = input.readByte();
            message.车距监控报警分级速度阈值 = input.readByte();
            message.车距过近报警前后视频录制时间 = input.readByte();
            message.车距过近报警拍照张数 = input.readByte();
            message.车距过近报警拍照间隔 = input.readByte();
            message.道路标志识别拍照张数 = input.readByte();
            message.道路标志识别拍照间隔 = input.readByte();
            message.保留字段 = input.readInt();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamADAS message) {
            output.writeByte(message.报警判断速度阈值);
            output.writeByte(message.报警提示音量);
            output.writeByte(message.主动拍照策略);
            output.writeShort(message.主动定时拍照时间间隔);
            output.writeShort(message.主动定距拍照距离间隔);
            output.writeByte(message.单次主动拍照张数);
            output.writeByte(message.单次主动拍照时间间隔);
            output.writeByte(message.拍照分辨率);
            output.writeByte(message.视频录制分辨率);
            output.writeInt(message.报警使能);
            output.writeInt(message.事件使能);
            output.writeByte(message.预留字段);
            output.writeByte(message.障碍物报警距离阈值);
            output.writeByte(message.障碍物报警分级速度阈值);
            output.writeByte(message.障碍物报警前后视频录制时间);
            output.writeByte(message.障碍物报警拍照张数);
            output.writeByte(message.障碍物报警拍照间隔);
            output.writeByte(message.频繁变道报警判断时间段);
            output.writeByte(message.频繁变道报警判断次数);
            output.writeByte(message.频繁变道报警分级速度阈值);
            output.writeByte(message.频繁变道报警前后视频录制时间);
            output.writeByte(message.频繁变道报警拍照张数);
            output.writeByte(message.频繁变道报警拍照间隔);
            output.writeByte(message.车道偏离报警分级速度阈值);
            output.writeByte(message.车道偏离报警前后视频录制时间);
            output.writeByte(message.车道偏离报警拍照张数);
            output.writeByte(message.车道偏离报警拍照间隔);
            output.writeByte(message.前向碰撞报警时间阈值);
            output.writeByte(message.前向碰撞报警分级速度阈值);
            output.writeByte(message.前向碰撞报警前后视频录制时间);
            output.writeByte(message.前向碰撞报警拍照张数);
            output.writeByte(message.前向碰撞报警拍照间隔);
            output.writeByte(message.行人碰撞报警时间阈值);
            output.writeByte(message.行人碰撞报警使能速度阈值);
            output.writeByte(message.行人碰撞报警前后视频录制时间);
            output.writeByte(message.行人碰撞报警拍照张数);
            output.writeByte(message.行人碰撞报警拍照间隔);
            output.writeByte(message.车距监控报警距离阈值);
            output.writeByte(message.车距监控报警分级速度阈值);
            output.writeByte(message.车距过近报警前后视频录制时间);
            output.writeByte(message.车距过近报警拍照张数);
            output.writeByte(message.车距过近报警拍照间隔);
            output.writeByte(message.道路标志识别拍照张数);
            output.writeByte(message.道路标志识别拍照间隔);
            output.writeInt(message.保留字段);
        }
    }
}