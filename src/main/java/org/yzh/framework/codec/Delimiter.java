package org.yzh.framework.codec;

public class Delimiter {
    protected final byte[] value;
    protected final boolean strip;

    public Delimiter(byte[] value) {
        this(value, true);
    }

    /**
     * @param value the value
     * @param strip whether the decoded frame should strip out the value or not
     */
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