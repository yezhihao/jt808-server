package org.yzh.web.model.vo;

import io.github.yezhihao.netmc.util.AdapterMap;
import io.swagger.v3.oas.annotations.media.Schema;
import org.yzh.protocol.commons.transform.parameter.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class Parameters {

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

    public Map<Integer, Integer> getParametersInt() {
        return parametersInt;
    }

    public void setParametersInt(Map<Integer, Integer> parametersInt) {
        this.parametersInt = parametersInt;
    }

    public Map<Integer, String> getParametersLong() {
        return parametersLong;
    }

    public void setParametersLong(Map<Integer, String> parametersLong) {
        this.parametersLong = parametersLong;
    }

    public Map<Integer, String> getParametersStr() {
        return parametersStr;
    }

    public void setParametersStr(Map<Integer, String> parametersStr) {
        this.parametersStr = parametersStr;
    }

    public ParamADAS getParamADAS() {
        return paramADAS;
    }

    public void setParamADAS(ParamADAS paramADAS) {
        this.paramADAS = paramADAS;
    }

    public ParamBSD getParamBSD() {
        return paramBSD;
    }

    public void setParamBSD(ParamBSD paramBSD) {
        this.paramBSD = paramBSD;
    }

    public ParamChannels getParamChannels() {
        return paramChannels;
    }

    public void setParamChannels(ParamChannels paramChannels) {
        this.paramChannels = paramChannels;
    }

    public ParamDSM getParamDSM() {
        return paramDSM;
    }

    public void setParamDSM(ParamDSM paramDSM) {
        this.paramDSM = paramDSM;
    }

    public ParamImageIdentifyAlarm getParamImageIdentifyAlarm() {
        return paramImageIdentifyAlarm;
    }

    public void setParamImageIdentifyAlarm(ParamImageIdentifyAlarm paramImageIdentifyAlarm) {
        this.paramImageIdentifyAlarm = paramImageIdentifyAlarm;
    }

    public ParamSleepWake getParamSleepWake() {
        return paramSleepWake;
    }

    public void setParamSleepWake(ParamSleepWake paramSleepWake) {
        this.paramSleepWake = paramSleepWake;
    }

    public ParamTPMS getParamTPMS() {
        return paramTPMS;
    }

    public void setParamTPMS(ParamTPMS paramTPMS) {
        this.paramTPMS = paramTPMS;
    }

    public ParamVideo getParamVideo() {
        return paramVideo;
    }

    public void setParamVideo(ParamVideo paramVideo) {
        this.paramVideo = paramVideo;
    }

    public ParamVideoSingle getParamVideoSingle() {
        return paramVideoSingle;
    }

    public void setParamVideoSingle(ParamVideoSingle paramVideoSingle) {
        this.paramVideoSingle = paramVideoSingle;
    }

    public ParamVideoSpecialAlarm getParamVideoSpecialAlarm() {
        return paramVideoSpecialAlarm;
    }

    public void setParamVideoSpecialAlarm(ParamVideoSpecialAlarm paramVideoSpecialAlarm) {
        this.paramVideoSpecialAlarm = paramVideoSpecialAlarm;
    }

    public Map<Integer, Object> toMap() {
        Map<Integer, Object> map = new TreeMap<>();

        if (parametersInt != null && !parametersInt.isEmpty())
            map.putAll(parametersInt);

        if (parametersLong != null && !parametersLong.isEmpty())
            map.putAll(new AdapterMap(parametersLong, (Function<String, Long>) Long::parseLong));

        if (parametersStr != null && !parametersStr.isEmpty())
            map.putAll(parametersStr);

        if (paramADAS != null)
            map.put(paramADAS.id, paramADAS);
        if (paramBSD != null)
            map.put(paramBSD.id, paramBSD);
        if (paramChannels != null)
            map.put(paramChannels.id, paramChannels);
        if (paramDSM != null)
            map.put(paramDSM.id, paramDSM);
        if (paramImageIdentifyAlarm != null)
            map.put(paramImageIdentifyAlarm.id, paramImageIdentifyAlarm);
        if (paramSleepWake != null)
            map.put(paramSleepWake.id, paramSleepWake);
        if (paramTPMS != null)
            map.put(paramTPMS.id, paramTPMS);
        if (paramVideo != null)
            map.put(paramVideo.id, paramVideo);
        if (paramVideoSingle != null)
            map.put(paramVideoSingle.id, paramVideoSingle);
        if (paramVideoSpecialAlarm != null)
            map.put(paramVideoSpecialAlarm.id, paramVideoSpecialAlarm);
        return map;
    }
}