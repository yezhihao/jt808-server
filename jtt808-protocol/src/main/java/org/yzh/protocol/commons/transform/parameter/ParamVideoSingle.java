package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.annotation.Field;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.TreeMap;

/**
 * 单独视频通道参数设置
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class ParamVideoSingle {

    public static final Integer key = 0x0077;

    public static final Schema<ParamVideoSingle> SCHEMA = new ParamVideoSingleSchema();

    @Field(desc = "单独通道视频参数设置列表")
    private Map<Integer, ParamVideo> paramVideos = new TreeMap<>();

    private static class ParamVideoSingleSchema implements Schema<ParamVideoSingle> {

        private ParamVideoSingleSchema() {
        }

        @Override
        public ParamVideoSingle readFrom(ByteBuf input) {
            byte total = input.readByte();
            Map<Integer, ParamVideo> paramVideos = new TreeMap<>();
            for (int i = 0; i < total; i++) {
                byte channelNo = input.readByte();
                ParamVideo paramVideo = ParamVideo.SCHEMA_2.readFrom(input);
                paramVideos.put((int) channelNo, paramVideo);
            }
            return new ParamVideoSingle().setParamVideos(paramVideos);
        }

        @Override
        public void writeTo(ByteBuf output, ParamVideoSingle message) {
            Map<Integer, ParamVideo> paramVideos = message.paramVideos;
            output.writeByte(message.paramVideos.size());
            for (Map.Entry<Integer, ParamVideo> videoEntry : paramVideos.entrySet()) {
                output.writeByte(videoEntry.getKey());
                ParamVideo.SCHEMA_2.writeTo(output, videoEntry.getValue());
            }
        }
    }
}