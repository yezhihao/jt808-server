package org.yzh.protocol.commons.transform.parameter;

import io.netty.buffer.ByteBuf;
import io.swagger.annotations.ApiModelProperty;

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

    @ApiModelProperty("报警判断速度阈值 BYTE")
    private byte p00;
    @ApiModelProperty("报警音量 BYTE")
    private byte p01;
    @ApiModelProperty("主动拍照策略 BYTE")
    private byte p02;
    @ApiModelProperty("主动定时拍照时间间隔 WORD")
    private short p03;
    @ApiModelProperty("主动定距拍照距离间隔 WORD")
    private short p05;
    @ApiModelProperty("单次主动拍照张数 BYTE")
    private byte p07;
    @ApiModelProperty("单次主动拍照时间间隔 BYTE")
    private byte p08;
    @ApiModelProperty("拍照分辨率 BYTE")
    private byte p09;
    @ApiModelProperty("视频录制分辨率 BYTE")
    private byte p10;
    @ApiModelProperty("报警使能 DWORD")
    private int p11;
    @ApiModelProperty("事件使能 DWORD")
    private int p15;
    @ApiModelProperty("吸烟报警判断时间间隔 WORD")
    private short p19;
    @ApiModelProperty("接打电话报警判断时间间隔 WORD")
    private short p21;
    @ApiModelProperty("预留字段 BYTE[3]")
    private byte[] p23 = new byte[3];
    @ApiModelProperty("疲劳驾驶报警分级速度阈值 BYTE")
    private byte p26;
    @ApiModelProperty("疲劳驾驶报警前后视频录制时间 BYTE")
    private byte p27;
    @ApiModelProperty("疲劳驾驶报警拍照张数 BYTE")
    private byte p28;
    @ApiModelProperty("疲劳驾驶报警拍照间隔时间 BYTE")
    private byte p29;
    @ApiModelProperty("接打电话报警分级速度阈值 BYTE")
    private byte p30;
    @ApiModelProperty("接打电话报警前后视频录制时间 BYTE")
    private byte p31;
    @ApiModelProperty("接打电话报警拍驾驶员面部特征照片张数 BYTE")
    private byte p32;
    @ApiModelProperty("接打电话报警拍驾驶员面部特征照片间隔时间 BYTE")
    private byte p33;
    @ApiModelProperty("抽烟报警分级车速阈值 BYTE")
    private byte p34;
    @ApiModelProperty("抽烟报警前后视频录制时间 BYTE")
    private byte p35;
    @ApiModelProperty("抽烟报警拍驾驶员面部特征照片张数 BYTE")
    private byte p36;
    @ApiModelProperty("抽烟报警拍驾驶员面部特征照片间隔时间 BYTE")
    private byte p37;
    @ApiModelProperty("分神驾驶报警分级车速阈值 BYTE")
    private byte p38;
    @ApiModelProperty("分神驾驶报警前后视频录制时间 BYTE")
    private byte p39;
    @ApiModelProperty("分神驾驶报警拍照张数 BYTE")
    private byte p40;
    @ApiModelProperty("分神驾驶报警拍照间隔时间 BYTE")
    private byte p41;
    @ApiModelProperty("驾驶行为异常分级速度阈值 BYTE")
    private byte p42;
    @ApiModelProperty("驾驶行为异常视频录制时间 BYTE")
    private byte p43;
    @ApiModelProperty("驾驶行为异常抓拍照片张数 BYTE")
    private byte p44;
    @ApiModelProperty("驾驶行为异常拍照间隔 BYTE")
    private byte p45;
    @ApiModelProperty("驾驶员身份识别触发 BYTE")
    private byte p46;
    @ApiModelProperty("保留字段 BYTE[2]")
    private short p47;

    public ParamDSM() {
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

    public short getP19() {
        return p19;
    }

    public void setP19(short p19) {
        this.p19 = p19;
    }

    public short getP21() {
        return p21;
    }

    public void setP21(short p21) {
        this.p21 = p21;
    }

    public byte[] getP23() {
        return p23;
    }

    public void setP23(byte[] p23) {
        this.p23 = p23;
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

    public short getP47() {
        return p47;
    }

    public void setP47(short p47) {
        this.p47 = p47;
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<ParamDSM> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public ParamDSM readFrom(ByteBuf input) {
            ParamDSM message = new ParamDSM();
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
            message.p19 = input.readShort();
            message.p21 = input.readShort();
            input.readBytes(message.p23);
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
            message.p47 = input.readShort();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamDSM message) {
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
            output.writeShort(message.p19);
            output.writeShort(message.p21);
            output.writeBytes(message.p23);
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
            output.writeShort(message.p47);
        }
    }
}