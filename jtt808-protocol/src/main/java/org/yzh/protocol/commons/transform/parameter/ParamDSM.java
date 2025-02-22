package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.annotation.Field;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 驾驶员状态监测系统参数
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class ParamDSM {

    public static final Integer key = 0xF365;

    @Field(desc = "报警判断速度阈值 BYTE")
    private byte p00 = -1;
    @Field(desc = "报警音量 BYTE")
    private byte p01 = -1;
    @Field(desc = "主动拍照策略 BYTE")
    private byte p02 = -1;
    @Field(desc = "主动定时拍照时间间隔 WORD")
    private short p03 = -1;
    @Field(desc = "主动定距拍照距离间隔 WORD")
    private short p05 = -1;
    @Field(desc = "单次主动拍照张数 BYTE")
    private byte p07 = -1;
    @Field(desc = "单次主动拍照时间间隔 BYTE")
    private byte p08 = -1;
    @Field(desc = "拍照分辨率 BYTE")
    private byte p09 = -1;
    @Field(desc = "视频录制分辨率 BYTE")
    private byte p10 = -1;
    @Field(desc = "报警使能 DWORD")
    private int p11 = -1;
    @Field(desc = "事件使能 DWORD")
    private int p15 = -1;
    @Field(desc = "吸烟报警判断时间间隔 WORD")
    private short p19 = -1;
    @Field(desc = "接打电话报警判断时间间隔 WORD")
    private short p21 = -1;
    @Field(length = 3, desc = "预留字段 BYTE[3]")
    private byte[] p23 = new byte[3];
    @Field(desc = "疲劳驾驶报警分级速度阈值 BYTE")
    private byte p26 = -1;
    @Field(desc = "疲劳驾驶报警前后视频录制时间 BYTE")
    private byte p27 = -1;
    @Field(desc = "疲劳驾驶报警拍照张数 BYTE")
    private byte p28 = -1;
    @Field(desc = "疲劳驾驶报警拍照间隔时间 BYTE")
    private byte p29 = -1;
    @Field(desc = "接打电话报警分级速度阈值 BYTE")
    private byte p30 = -1;
    @Field(desc = "接打电话报警前后视频录制时间 BYTE")
    private byte p31 = -1;
    @Field(desc = "接打电话报警拍驾驶员面部特征照片张数 BYTE")
    private byte p32 = -1;
    @Field(desc = "接打电话报警拍驾驶员面部特征照片间隔时间 BYTE")
    private byte p33 = -1;
    @Field(desc = "抽烟报警分级车速阈值 BYTE")
    private byte p34 = -1;
    @Field(desc = "抽烟报警前后视频录制时间 BYTE")
    private byte p35 = -1;
    @Field(desc = "抽烟报警拍驾驶员面部特征照片张数 BYTE")
    private byte p36 = -1;
    @Field(desc = "抽烟报警拍驾驶员面部特征照片间隔时间 BYTE")
    private byte p37 = -1;
    @Field(desc = "分神驾驶报警分级车速阈值 BYTE")
    private byte p38 = -1;
    @Field(desc = "分神驾驶报警前后视频录制时间 BYTE")
    private byte p39 = -1;
    @Field(desc = "分神驾驶报警拍照张数 BYTE")
    private byte p40 = -1;
    @Field(desc = "分神驾驶报警拍照间隔时间 BYTE")
    private byte p41 = -1;
    @Field(desc = "驾驶行为异常分级速度阈值 BYTE")
    private byte p42 = -1;
    @Field(desc = "驾驶行为异常视频录制时间 BYTE")
    private byte p43 = -1;
    @Field(desc = "驾驶行为异常抓拍照片张数 BYTE")
    private byte p44 = -1;
    @Field(desc = "驾驶行为异常拍照间隔 BYTE")
    private byte p45 = -1;
    @Field(desc = "驾驶员身份识别触发 BYTE")
    private byte p46 = -1;
    @Field(desc = "摄像机遮挡报警分级速度阈值(粤标)")
    private byte p47 = -1;
    @Field(desc = "不系安全带报警分级速度阈值(粤标)")
    private byte p48 = -1;
    @Field(desc = "不系安全带报警前后视频录制时间(粤标)")
    private byte p49 = -1;
    @Field(desc = "不系安全带报警抓拍照片张数(粤标)")
    private byte p50 = -1;
    @Field(desc = "不系安全带报警抓拍照片间隔时间(粤标)")
    private byte p51 = -1;
    @Field(desc = "红外墨镜阻断失效报警分级速度阈值(粤标)")
    private byte p52 = -1;
    @Field(desc = "红外墨镜阻断失效报警前后视频录制时间(粤标)")
    private byte p53 = -1;
    @Field(desc = "红外墨镜阻断失效报警抓拍照片张数(粤标)")
    private byte p54 = -1;
    @Field(desc = "红外墨镜阻断失效报警抓拍照片间隔时间(粤标)")
    private byte p55 = -1;
    @Field(desc = "双脱把报警分级速度阈值(粤标)")
    private byte p56 = -1;
    @Field(desc = "双脱把报警前后视频录制时间(粤标)")
    private byte p57 = -1;
    @Field(desc = "双脱把报警抓拍照片张数(粤标)")
    private byte p58 = -1;
    @Field(desc = "双脱把报警抓拍照片间隔时间(粤标)")
    private byte p59 = -1;
    @Field(desc = "玩手机报警分级速度阈值(粤标)")
    private byte p60 = -1;
    @Field(desc = "玩手机报警前后视频录制时间(粤标)")
    private byte p61 = -1;
    @Field(desc = "玩手机报警抓拍照片张数(粤标)")
    private byte p62 = -1;
    @Field(desc = "玩手机报警拍抓拍，照片间隔时间(粤标)")
    private byte p63 = -1;
    @Field(desc = "保留字段(粤标)")
    private byte p64 = -1;

}