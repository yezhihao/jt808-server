package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.annotation.Field;
import io.netty.buffer.ByteBuf;

/**
 * 高级驾驶辅助系统参数
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class ParamADAS {

    public static final Schema<ParamADAS> SCHEMA = new ParamADASSchema();

    public static final int id = 0xF364;

    public static int id() {
        return id;
    }

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

    public ParamADAS() {
    }

    public byte getP00() {
        return p00;
    }

    public void setP00(byte p00) {
        this.p00 = p00;
    }

    public byte getP01() {
        return p01;
    }

    public void setP01(byte p01) {
        this.p01 = p01;
    }

    public byte getP02() {
        return p02;
    }

    public void setP02(byte p02) {
        this.p02 = p02;
    }

    public short getP03() {
        return p03;
    }

    public void setP03(short p03) {
        this.p03 = p03;
    }

    public short getP05() {
        return p05;
    }

    public void setP05(short p05) {
        this.p05 = p05;
    }

    public byte getP07() {
        return p07;
    }

    public void setP07(byte p07) {
        this.p07 = p07;
    }

    public byte getP08() {
        return p08;
    }

    public void setP08(byte p08) {
        this.p08 = p08;
    }

    public byte getP09() {
        return p09;
    }

    public void setP09(byte p09) {
        this.p09 = p09;
    }

    public byte getP10() {
        return p10;
    }

    public void setP10(byte p10) {
        this.p10 = p10;
    }

    public int getP11() {
        return p11;
    }

    public void setP11(int p11) {
        this.p11 = p11;
    }

    public int getP15() {
        return p15;
    }

    public void setP15(int p15) {
        this.p15 = p15;
    }

    public byte getP19() {
        return p19;
    }

    public void setP19(byte p19) {
        this.p19 = p19;
    }

    public byte getP20() {
        return p20;
    }

    public void setP20(byte p20) {
        this.p20 = p20;
    }

    public byte getP21() {
        return p21;
    }

    public void setP21(byte p21) {
        this.p21 = p21;
    }

    public byte getP22() {
        return p22;
    }

    public void setP22(byte p22) {
        this.p22 = p22;
    }

    public byte getP23() {
        return p23;
    }

    public void setP23(byte p23) {
        this.p23 = p23;
    }

    public byte getP24() {
        return p24;
    }

    public void setP24(byte p24) {
        this.p24 = p24;
    }

    public byte getP25() {
        return p25;
    }

    public void setP25(byte p25) {
        this.p25 = p25;
    }

    public byte getP26() {
        return p26;
    }

    public void setP26(byte p26) {
        this.p26 = p26;
    }

    public byte getP27() {
        return p27;
    }

    public void setP27(byte p27) {
        this.p27 = p27;
    }

    public byte getP28() {
        return p28;
    }

    public void setP28(byte p28) {
        this.p28 = p28;
    }

    public byte getP29() {
        return p29;
    }

    public void setP29(byte p29) {
        this.p29 = p29;
    }

    public byte getP30() {
        return p30;
    }

    public void setP30(byte p30) {
        this.p30 = p30;
    }

    public byte getP31() {
        return p31;
    }

    public void setP31(byte p31) {
        this.p31 = p31;
    }

    public byte getP32() {
        return p32;
    }

    public void setP32(byte p32) {
        this.p32 = p32;
    }

    public byte getP33() {
        return p33;
    }

    public void setP33(byte p33) {
        this.p33 = p33;
    }

    public byte getP34() {
        return p34;
    }

    public void setP34(byte p34) {
        this.p34 = p34;
    }

    public byte getP35() {
        return p35;
    }

    public void setP35(byte p35) {
        this.p35 = p35;
    }

    public byte getP36() {
        return p36;
    }

    public void setP36(byte p36) {
        this.p36 = p36;
    }

    public byte getP37() {
        return p37;
    }

    public void setP37(byte p37) {
        this.p37 = p37;
    }

    public byte getP38() {
        return p38;
    }

    public void setP38(byte p38) {
        this.p38 = p38;
    }

    public byte getP39() {
        return p39;
    }

    public void setP39(byte p39) {
        this.p39 = p39;
    }

    public byte getP40() {
        return p40;
    }

    public void setP40(byte p40) {
        this.p40 = p40;
    }

    public byte getP41() {
        return p41;
    }

    public void setP41(byte p41) {
        this.p41 = p41;
    }

    public byte getP42() {
        return p42;
    }

    public void setP42(byte p42) {
        this.p42 = p42;
    }

    public byte getP43() {
        return p43;
    }

    public void setP43(byte p43) {
        this.p43 = p43;
    }

    public byte getP44() {
        return p44;
    }

    public void setP44(byte p44) {
        this.p44 = p44;
    }

    public byte getP45() {
        return p45;
    }

    public void setP45(byte p45) {
        this.p45 = p45;
    }

    public byte getP46() {
        return p46;
    }

    public void setP46(byte p46) {
        this.p46 = p46;
    }

    public byte getP47() {
        return p47;
    }

    public void setP47(byte p47) {
        this.p47 = p47;
    }

    public byte getP48() {
        return p48;
    }

    public void setP48(byte p48) {
        this.p48 = p48;
    }

    public byte getP49() {
        return p49;
    }

    public void setP49(byte p49) {
        this.p49 = p49;
    }

    public byte getP50() {
        return p50;
    }

    public void setP50(byte p50) {
        this.p50 = p50;
    }

    public byte getP51() {
        return p51;
    }

    public void setP51(byte p51) {
        this.p51 = p51;
    }

    public byte getP52() {
        return p52;
    }

    public void setP52(byte p52) {
        this.p52 = p52;
    }

    public byte getP53() {
        return p53;
    }

    public void setP53(byte p53) {
        this.p53 = p53;
    }

    public byte getP54() {
        return p54;
    }

    public void setP54(byte p54) {
        this.p54 = p54;
    }

    public byte getP55() {
        return p55;
    }

    public void setP55(byte p55) {
        this.p55 = p55;
    }

    public byte getP56() {
        return p56;
    }

    public void setP56(byte p56) {
        this.p56 = p56;
    }

    private static class ParamADASSchema implements Schema<ParamADAS> {

        private ParamADASSchema() {
        }

        @Override
        public ParamADAS readFrom(ByteBuf input) {
            ParamADAS message = new ParamADAS();
            message.p00 = input.readByte();
            message.p01 = input.readByte();
            message.p02 = input.readByte();
            message.p03 = input.readShort();
            message.p05 = input.readShort();
            message.p07 = input.readByte();
            message.p08 = input.readByte();
            message.p09 = input.readByte();
            message.p10 = input.readByte();
            message.p11 = input.readInt();
            message.p15 = input.readInt();
            message.p19 = input.readByte();
            message.p20 = input.readByte();
            message.p21 = input.readByte();
            message.p22 = input.readByte();
            message.p23 = input.readByte();
            message.p24 = input.readByte();
            message.p25 = input.readByte();
            message.p26 = input.readByte();
            message.p27 = input.readByte();
            message.p28 = input.readByte();
            message.p29 = input.readByte();
            message.p30 = input.readByte();
            message.p31 = input.readByte();
            message.p32 = input.readByte();
            message.p33 = input.readByte();
            message.p34 = input.readByte();
            message.p35 = input.readByte();
            message.p36 = input.readByte();
            message.p37 = input.readByte();
            message.p38 = input.readByte();
            message.p39 = input.readByte();
            message.p40 = input.readByte();
            message.p41 = input.readByte();
            message.p42 = input.readByte();
            message.p43 = input.readByte();
            message.p44 = input.readByte();
            message.p45 = input.readByte();
            message.p46 = input.readByte();
            message.p47 = input.readByte();
            message.p48 = input.readByte();
            message.p49 = input.readByte();
            message.p50 = input.readByte();
            message.p51 = input.readByte();
            message.p52 = input.readByte();
            message.p53 = input.readByte();
            message.p54 = input.readByte();
            message.p55 = input.readByte();
            if (input.isReadable())
                message.p56 = input.readByte();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamADAS message) {
            output.writeByte(message.p00);
            output.writeByte(message.p01);
            output.writeByte(message.p02);
            output.writeShort(message.p03);
            output.writeShort(message.p05);
            output.writeByte(message.p07);
            output.writeByte(message.p08);
            output.writeByte(message.p09);
            output.writeByte(message.p10);
            output.writeInt(message.p11);
            output.writeInt(message.p15);
            output.writeByte(message.p19);
            output.writeByte(message.p20);
            output.writeByte(message.p21);
            output.writeByte(message.p22);
            output.writeByte(message.p23);
            output.writeByte(message.p24);
            output.writeByte(message.p25);
            output.writeByte(message.p26);
            output.writeByte(message.p27);
            output.writeByte(message.p28);
            output.writeByte(message.p29);
            output.writeByte(message.p30);
            output.writeByte(message.p31);
            output.writeByte(message.p32);
            output.writeByte(message.p33);
            output.writeByte(message.p34);
            output.writeByte(message.p35);
            output.writeByte(message.p36);
            output.writeByte(message.p37);
            output.writeByte(message.p38);
            output.writeByte(message.p39);
            output.writeByte(message.p40);
            output.writeByte(message.p41);
            output.writeByte(message.p42);
            output.writeByte(message.p43);
            output.writeByte(message.p44);
            output.writeByte(message.p45);
            output.writeByte(message.p46);
            output.writeByte(message.p47);
            output.writeByte(message.p48);
            output.writeByte(message.p49);
            output.writeByte(message.p50);
            output.writeByte(message.p51);
            output.writeByte(message.p52);
            output.writeByte(message.p53);
            output.writeByte(message.p54);
            output.writeByte(message.p55);
            output.writeByte(message.p56);
        }
    }
}