package org.yzh.framework.codec;

public class Delimiter {
    protected byte[] value;
    protected boolean strip;

    public Delimiter(byte[] value) {
        this(value, true);
    }

    public Delimiter(byte[] value, boolean strip) {
        this.value = value;
        this.strip = strip;
    }

    public byte[] getValue() {
        return value;
    }

    public boolean isStrip() {
        return strip;
    }
}