package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.annotation.Field;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 高级驾驶辅助系统参数
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class ParamADAS {

    public static final Integer key = 0xF364;

    @Field(desc = "报警判断速度阈值 BYTE")
    private byte p00 = -1;
    @Field(desc = "报警提示音量 BYTE")
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
    @Field(desc = "预留字段 BYTE")
    private byte p19 = -1;
    @Field(desc = "障碍物报警距离阈值 BYTE")
    private byte p20 = -1;
    @Field(desc = "障碍物报警分级速度阈值 BYTE")
    private byte p21 = -1;
    @Field(desc = "障碍物报警前后视频录制时间 BYTE")
    private byte p22 = -1;
    @Field(desc = "障碍物报警拍照张数 BYTE")
    private byte p23 = -1;
    @Field(desc = "障碍物报警拍照间隔 BYTE")
    private byte p24 = -1;
    @Field(desc = "频繁变道报警判断时间段 BYTE")
    private byte p25 = -1;
    @Field(desc = "频繁变道报警判断次数 BYTE")
    private byte p26 = -1;
    @Field(desc = "频繁变道报警分级速度阈值 BYTE")
    private byte p27 = -1;
    @Field(desc = "频繁变道报警前后视频录制时间 BYTE")
    private byte p28 = -1;
    @Field(desc = "频繁变道报警拍照张数 BYTE")
    private byte p29 = -1;
    @Field(desc = "频繁变道报警拍照间隔 BYTE")
    private byte p30 = -1;
    @Field(desc = "车道偏离报警分级速度阈值 BYTE")
    private byte p31 = -1;
    @Field(desc = "车道偏离报警前后视频录制时间 BYTE")
    private byte p32 = -1;
    @Field(desc = "车道偏离报警拍照张数 BYTE")
    private byte p33 = -1;
    @Field(desc = "车道偏离报警拍照间隔 BYTE")
    private byte p34 = -1;
    @Field(desc = "前向碰撞报警时间阈值 BYTE")
    private byte p35 = -1;
    @Field(desc = "前向碰撞报警分级速度阈值 BYTE")
    private byte p36 = -1;
    @Field(desc = "前向碰撞报警前后视频录制时间 BYTE")
    private byte p37 = -1;
    @Field(desc = "前向碰撞报警拍照张数 BYTE")
    private byte p38 = -1;
    @Field(desc = "前向碰撞报警拍照间隔 BYTE")
    private byte p39 = -1;
    @Field(desc = "行人碰撞报警时间阈值 BYTE")
    private byte p40 = -1;
    @Field(desc = "行人碰撞报警使能速度阈值 BYTE")
    private byte p41 = -1;
    @Field(desc = "行人碰撞报警前后视频录制时间 BYTE")
    private byte p42 = -1;
    @Field(desc = "行人碰撞报警拍照张数 BYTE")
    private byte p43 = -1;
    @Field(desc = "行人碰撞报警拍照间隔 BYTE")
    private byte p44 = -1;
    @Field(desc = "车距监控报警距离阈值 BYTE")
    private byte p45 = -1;
    @Field(desc = "车距监控报警分级速度阈值 BYTE")
    private byte p46 = -1;
    @Field(desc = "车距过近报警前后视频录制时间 BYTE")
    private byte p47 = -1;
    @Field(desc = "车距过近报警拍照张数 BYTE")
    private byte p48 = -1;
    @Field(desc = "车距过近报警拍照间隔 BYTE")
    private byte p49 = -1;
    @Field(desc = "道路标志识别拍照张数 BYTE")
    private byte p50 = -1;
    @Field(desc = "道路标志识别拍照间隔 BYTE")
    private byte p51 = -1;
    @Field(desc = "实线变道报警分级速度阈值 BYTE(粤标)")
    private byte p52 = -1;
    @Field(desc = "实线变道报警前后视频录制时间 BYTE(粤标)")
    private byte p53 = -1;
    @Field(desc = "实线变道报警拍照张数 BYTE(粤标)")
    private byte p54 = -1;
    @Field(desc = "实线变道报警拍照间隔 BYTE(粤标)")
    private byte p55 = -1;
    @Field(desc = "车厢过道行人检测报警分级速度阈值 BYTE(粤标)")
    private byte p56 = -1;

}