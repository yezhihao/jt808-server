package org.yzh.framework.codec;

import static io.netty.util.internal.ObjectUtil.checkPositive;
import static io.netty.util.internal.ObjectUtil.checkPositiveOrZero;

public class LengthField {
    protected final byte[] prefix;
    protected final int lengthFieldMaxFrameLength;
    protected final int lengthFieldOffset;
    protected final int lengthFieldLength;
    protected final int lengthFieldEndOffset;
    protected final int lengthAdjustment;
    protected final int initialBytesToStrip;

    public LengthField(byte[] prefix, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        this(prefix, maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0);
    }

    public LengthField(byte[] prefix, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        checkPositive(maxFrameLength, "maxFrameLength_LengthField");
        checkPositiveOrZero(lengthFieldOffset, "lengthFieldOffset");
        checkPositiveOrZero(initialBytesToStrip, "initialBytesToStrip");
        if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
            throw new IllegalArgumentException("maxFrameLength_LengthField (" + maxFrameLength + ") must be equal to or greater than lengthFieldOffset (" + lengthFieldOffset + ") + lengthFieldLength (" + lengthFieldLength + ").");
        } else {
            this.prefix = prefix;
            this.lengthFieldMaxFrameLength = maxFrameLength;
            this.lengthFieldOffset = lengthFieldOffset;
            this.lengthFieldLength = lengthFieldLength;
            this.lengthAdjustment = lengthAdjustment;
            this.lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
            this.initialBytesToStrip = initialBytesToStrip;
        }
    }

    public byte[] getPrefix() {
        return prefix;
    }

    public int getLengthFieldMaxFrameLength() {
        return lengthFieldMaxFrameLength;
    }

    public int getLengthFieldOffset() {
        return lengthFieldOffset;
    }

    public int getLengthFieldLength() {
        return lengthFieldLength;
    }

    public int getLengthFieldEndOffset() {
        return lengthFieldEndOffset;
    }

    public int getLengthAdjustment() {
        return lengthAdjustment;
    }

    public int getInitialBytesToStrip() {
        return initialBytesToStrip;
    }
}