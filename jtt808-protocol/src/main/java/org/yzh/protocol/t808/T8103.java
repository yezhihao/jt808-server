package org.yzh.protocol.t808;

import io.github.yezhihao.netmc.util.AdapterMap;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.transform.ParameterConverter;
import org.yzh.protocol.commons.transform.parameter.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.设置终端参数)
public class T8103 extends JTMessage {

    @Field(totalUnit = 1, desc = "参数项列表", converter = ParameterConverter.class)
    private Map<Integer, Object> parameters;

    public T8103 addParameter(Integer key, Object value) {
        if (parameters == null)
            parameters = new TreeMap<>();
        parameters.put(key, value);
        return this;
    }

    @Schema(description = "数值型参数列表(BYTE、WORD)")
    private Map<Integer, Integer> parametersInt;
    @Schema(description = "数值型参数列表(DWORD、QWORD)")
    private Map<Integer, String> parametersLong;
    @Schema(description = "字符型参数列表")
    private Map<Integer, String> parametersStr;
    @Schema(description = "图像分析报警参数设置(1078)")
    private ParamImageIdentifyAlarm paramImageIdentifyAlarm;
    @Schema(description = "特殊报警录像参数设置(1078)")
    private ParamVideoSpecialAlarm paramVideoSpecialAlarm;
    @Schema(description = "音视频通道列表设置(1078)")
    private ParamChannels paramChannels;
    @Schema(description = "终端休眠唤醒模式设置数据格式(1078)")
    private ParamSleepWake paramSleepWake;
    @Schema(description = "音视频参数设置(1078)")
    private ParamVideo paramVideo;
    @Schema(description = "单独视频通道参数设置(1078)")
    private ParamVideoSingle paramVideoSingle;
    @Schema(description = "盲区监测系统参数(苏标)")
    private ParamBSD paramBSD;
    @Schema(description = "胎压监测系统参数(苏标)")
    private ParamTPMS paramTPMS;
    @Schema(description = "驾驶员状态监测系统参数(苏标)")
    private ParamDSM paramDSM;
    @Schema(description = "高级驾驶辅助系统参数(苏标)")
    private ParamADAS paramADAS;

    public T8103 build() {
        Map<Integer, Object> map = new TreeMap<>();

        if (parametersInt != null && !parametersInt.isEmpty())
            map.putAll(parametersInt);

        if (parametersLong != null && !parametersLong.isEmpty())
            map.putAll(new AdapterMap(parametersLong, (Function<String, Long>) Long::parseLong));

        if (parametersStr != null && !parametersStr.isEmpty())
            map.putAll(parametersStr);

        if (paramADAS != null)
            map.put(paramADAS.key, paramADAS);
        if (paramBSD != null)
            map.put(paramBSD.key, paramBSD);
        if (paramChannels != null)
            map.put(paramChannels.key, paramChannels);
        if (paramDSM != null)
            map.put(paramDSM.key, paramDSM);
        if (paramImageIdentifyAlarm != null)
            map.put(paramImageIdentifyAlarm.key, paramImageIdentifyAlarm);
        if (paramSleepWake != null)
            map.put(paramSleepWake.key, paramSleepWake);
        if (paramTPMS != null)
            map.put(paramTPMS.key, paramTPMS);
        if (paramVideo != null)
            map.put(paramVideo.key, paramVideo);
        if (paramVideoSingle != null)
            map.put(paramVideoSingle.key, paramVideoSingle);
        if (paramVideoSpecialAlarm != null)
            map.put(paramVideoSpecialAlarm.key, paramVideoSpecialAlarm);

        this.parameters = map;
        return this;
    }
}