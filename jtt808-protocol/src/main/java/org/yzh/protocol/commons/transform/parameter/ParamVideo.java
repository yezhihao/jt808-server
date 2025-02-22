package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.annotation.Field;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 音视频参数设置
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class ParamVideo {

    public static final Integer key = 0x0075;

    protected static final Schema<ParamVideo> SCHEMA_2 = new ParamVideoSchema2();

    @Field(desc = "实时流编码模式：0.CBR(固定码率) 1.VBR(可变码率) 2.ABR(平均码率) 100~ 127.自定义")
    private byte realtimeEncode;
    @Field(desc = "实时流分辨率：0.QCIF 1.CIF 2.WCIF 3.D1 4.WD1 5.720P 6.1080P 100~127.自定义")
    private byte realtimeResolution;
    @Field(desc = "实时流关键帧间隔(1~1000帧)")
    private short realtimeFrameInterval;
    @Field(desc = "实时流目标帧率(1~120帧)")
    private byte realtimeFrameRate;
    @Field(desc = "实时流目标码率(kbps)")
    private int realtimeBitRate;

    @Field(desc = "存储流编码模式：0.CBR(固定码率) 1.VBR(可变码率) 2.ABR(平均码率) 100~ 127.自定义")
    private byte storageEncode;
    @Field(desc = "存储流分辨率：0.QCIF 1.CIF 2.WCIF 3.D1 4.WD1 5.720P 6.1080P 100~127.自定义")
    private byte storageResolution;
    @Field(desc = "存储流关键帧间隔(1~1000帧)")
    private short storageFrameInterval;
    @Field(desc = "存储流目标帧率(1~120帧)")
    private byte storageFrameRate;
    @Field(desc = "存储流目标码率(kbps)")
    private int storageBitRate;

    @Field(desc = "OSD字幕叠加设置(按位,0.表示不叠加 1.表示叠加)：" +
            " [0]日期和时间" +
            " [1]车牌号码" +
            " [2]逻辑通道号" +
            " [3]经纬度" +
            " [4]行驶记录速度" +
            " [5]卫星定位速度" +
            " [6]连续驾驶时间" +
            " [7~l0]保留" +
            " [11~l5]自定义")
    private short odsConfig;
    @Field(desc = "是否启用音频输出：0.不启用 1.启用")
    private byte audioEnabled;

    public static class ParamVideoSchema2 implements Schema<ParamVideo> {

        private ParamVideoSchema2() {
        }

        @Override
        public ParamVideo readFrom(ByteBuf input) {
            ParamVideo message = new ParamVideo();
            message.realtimeEncode = input.readByte();
            message.realtimeResolution = input.readByte();
            message.realtimeFrameInterval = input.readShort();
            message.realtimeFrameRate = input.readByte();
            message.realtimeBitRate = input.readInt();

            message.storageEncode = input.readByte();
            message.storageResolution = input.readByte();
            message.storageFrameInterval = input.readShort();
            message.storageFrameRate = input.readByte();
            message.storageBitRate = input.readInt();

            message.odsConfig = input.readShort();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamVideo message) {
            output.writeByte(message.realtimeEncode);
            output.writeByte(message.realtimeResolution);
            output.writeShort(message.realtimeFrameInterval);
            output.writeByte(message.realtimeFrameRate);
            output.writeInt(message.realtimeBitRate);

            output.writeByte(message.storageEncode);
            output.writeByte(message.storageResolution);
            output.writeShort(message.storageFrameInterval);
            output.writeByte(message.storageFrameRate);
            output.writeInt(message.storageBitRate);

            output.writeShort(message.odsConfig);
        }
    }
}