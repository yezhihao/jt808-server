package org.yzh.protocol.commons.transform;

import io.swagger.annotations.ApiModelProperty;
import org.yzh.protocol.commons.transform.parameter.*;

import java.util.Map;
import java.util.TreeMap;

public class Parameter {

    @ApiModelProperty(value = "数值型参数列表", position = 1)
    private Map<Integer, Integer> parametersInt;
    @ApiModelProperty(value = "字符型参数列表", position = 2)
    private Map<Integer, String> parametersStr;
    @ApiModelProperty(value = "图像分析报警参数设置", position = 3)
    private ParamImageIdentifyAlarm paramImageIdentifyAlarm;
    @ApiModelProperty(value = "特殊报警录像参数设置", position = 4)
    private ParamVideoSpecialAlarm paramVideoSpecialAlarm;
    @ApiModelProperty(value = "音视频通道列表设置", position = 5)
    private ParamChannels paramChannels;
    @ApiModelProperty(value = "终端休眠唤醒模式设置数据格式", position = 6)
    private ParamSleepWake paramSleepWake;
    @ApiModelProperty(value = "音视频参数设置", position = 7)
    private ParamVideo paramVideo;
    @ApiModelProperty(value = "单独视频通道参数设置", position = 8)
    private ParamVideoSingle paramVideoSingle;
    @ApiModelProperty(value = "盲区监测系统参数", position = 9)
    private ParamBSD paramBSD;
    @ApiModelProperty(value = "胎压监测系统参数", position = 10)
    private ParamTPMS paramTPMS;
    @ApiModelProperty(value = "驾驶员状态监测系统参数", position = 11)
    private ParamDSM paramDSM;
    @ApiModelProperty(value = "高级驾驶辅助系统参数", position = 12)
    private ParamADAS paramADAS;

    public Map<Integer, Integer> getParametersInt() {
        return parametersInt;
    }

    public void setParametersInt(Map<Integer, Integer> parametersInt) {
        this.parametersInt = parametersInt;
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

        if (parametersInt != null)
            map.putAll(parametersInt);

        if (parametersStr != null)
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