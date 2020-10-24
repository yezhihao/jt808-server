package org.yzh.protocol.commons.transform.parameter;

import io.netty.buffer.ByteBuf;

/**
 * 音视频参数设置，
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ParamVideo {

    public static final int id = 0x0075;

    public static int id() {
        return id;
    }

    private byte realtimeEncode;
    private byte realtimeResolution;
    private short realtimeFrameInterval;
    private byte realtimeFrameRate;
    private int realtimeBitRate;

    private byte storageEncode;
    private byte storageResolution;
    private short storageFrameInterval;
    private byte storageFrameRate;
    private int storageBitRate;

    private short odsConfig;
    private byte audioEnable;

    public ParamVideo() {
    }

    public byte getRealtimeEncode() {
        return realtimeEncode;
    }

    public void setRealtimeEncode(byte realtimeEncode) {
        this.realtimeEncode = realtimeEncode;
    }

    public byte getRealtimeResolution() {
        return realtimeResolution;
    }

    public void setRealtimeResolution(byte realtimeResolution) {
        this.realtimeResolution = realtimeResolution;
    }

    public short getRealtimeFrameInterval() {
        return realtimeFrameInterval;
    }

    public void setRealtimeFrameInterval(short realtimeFrameInterval) {
        this.realtimeFrameInterval = realtimeFrameInterval;
    }

    public byte getRealtimeFrameRate() {
        return realtimeFrameRate;
    }

    public void setRealtimeFrameRate(byte realtimeFrameRate) {
        this.realtimeFrameRate = realtimeFrameRate;
    }

    public int getRealtimeBitRate() {
        return realtimeBitRate;
    }

    public void setRealtimeBitRate(int realtimeBitRate) {
        this.realtimeBitRate = realtimeBitRate;
    }

    public byte getStorageEncode() {
        return storageEncode;
    }

    public void setStorageEncode(byte storageEncode) {
        this.storageEncode = storageEncode;
    }

    public byte getStorageResolution() {
        return storageResolution;
    }

    public void setStorageResolution(byte storageResolution) {
        this.storageResolution = storageResolution;
    }

    public short getStorageFrameInterval() {
        return storageFrameInterval;
    }

    public void setStorageFrameInterval(short storageFrameInterval) {
        this.storageFrameInterval = storageFrameInterval;
    }

    public byte getStorageFrameRate() {
        return storageFrameRate;
    }

    public void setStorageFrameRate(byte storageFrameRate) {
        this.storageFrameRate = storageFrameRate;
    }

    public int getStorageBitRate() {
        return storageBitRate;
    }

    public void setStorageBitRate(int storageBitRate) {
        this.storageBitRate = storageBitRate;
    }

    public short getOdsConfig() {
        return odsConfig;
    }

    public void setOdsConfig(short odsConfig) {
        this.odsConfig = odsConfig;
    }

    public byte getAudioEnable() {
        return audioEnable;
    }

    public void setAudioEnable(byte audioEnable) {
        this.audioEnable = audioEnable;
    }

    public static class Schema implements org.yzh.framework.orm.Schema<ParamVideo> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
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
            message.audioEnable = input.readByte();
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
            output.writeByte(message.audioEnable);
        }
    }

    public static class Schema2 implements org.yzh.framework.orm.Schema<ParamVideo> {

        public static final Schema2 INSTANCE = new Schema2();

        private Schema2() {
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