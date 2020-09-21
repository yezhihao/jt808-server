package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.internal.ObjectUtil;

import java.util.List;

import static io.netty.util.internal.ObjectUtil.checkPositive;
import static org.yzh.framework.commons.transform.ByteBufUtils.indexOf;

public class DelimiterBasedFrameDecoder extends ByteToMessageDecoder {

    private final Delimiter[] delimiters;
    private final int maxFrameLength;
    private final boolean failFast;
    private boolean discardingTooLongFrame;
    private int tooLongFrameLength;

    /**
     * Creates a new instance.
     * @param maxFrameLength the maximum length of the decoded frame.
     *                       A {@link TooLongFrameException} is thrown if
     *                       the length of the frame exceeds this value.
     * @param delimiters     the delimiters
     */
    public DelimiterBasedFrameDecoder(int maxFrameLength, Delimiter... delimiters) {
        this(maxFrameLength, true, delimiters);
    }

    /**
     * Creates a new instance.
     * @param maxFrameLength the maximum length of the decoded frame.
     *                       A {@link TooLongFrameException} is thrown if
     *                       the length of the frame exceeds this value.
     * @param failFast       If <tt>true</tt>, a {@link TooLongFrameException} is
     *                       thrown as soon as the decoder notices the length of the
     *                       frame will exceed <tt>maxFrameLength</tt> regardless of
     *                       whether the entire frame has been read.
     *                       If <tt>false</tt>, a {@link TooLongFrameException} is
     *                       thrown after the entire frame that exceeds
     *                       <tt>maxFrameLength</tt> has been read.
     * @param delimiters     the delimiters
     */
    public DelimiterBasedFrameDecoder(int maxFrameLength, boolean failFast, Delimiter... delimiters) {
        validateMaxFrameLength(maxFrameLength);
        ObjectUtil.checkNonEmpty(delimiters, "delimiters");

        this.delimiters = delimiters;
        this.maxFrameLength = maxFrameLength;
        this.failFast = failFast;
    }

    @Override
    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    /**
     * Create a frame out of the {@link ByteBuf} and return it.
     * @param ctx    the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param buffer the {@link ByteBuf} from which to read data
     * @return frame           the {@link ByteBuf} which represent the frame or {@code null} if no frame could
     * be created.
     */
    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        // Try all delimiters and choose the delimiter which yields the shortest frame.
        int minFrameLength = Integer.MAX_VALUE;
        Delimiter minDelim = null;
        for (Delimiter delim : delimiters) {
            int frameLength = indexOf(buffer, delim.value);
            if (frameLength >= 0 && frameLength < minFrameLength) {
                minFrameLength = frameLength;
                minDelim = delim;
            }
        }

        if (minDelim != null) {
            int minDelimLength = minDelim.value.length;
            ByteBuf frame;

            if (discardingTooLongFrame) {
                // We've just finished discarding a very large frame.
                // Go back to the initial state.
                discardingTooLongFrame = false;
                buffer.skipBytes(minFrameLength + minDelimLength);

                int tooLongFrameLength = this.tooLongFrameLength;
                this.tooLongFrameLength = 0;
                if (!failFast) {
                    fail(tooLongFrameLength);
                }
                return null;
            }

            if (minFrameLength > maxFrameLength) {
                // Discard read frame.
                buffer.skipBytes(minFrameLength + minDelimLength);
                fail(minFrameLength);
                return null;
            }

            if (minDelim.strip) {
                frame = buffer.readRetainedSlice(minFrameLength);
                buffer.skipBytes(minDelimLength);
            } else {
                frame = buffer.readRetainedSlice(minFrameLength + minDelimLength);
            }

            return frame;
        } else {
            if (!discardingTooLongFrame) {
                if (buffer.readableBytes() > maxFrameLength) {
                    // Discard the content of the buffer until a delimiter is found.
                    tooLongFrameLength = buffer.readableBytes();
                    buffer.skipBytes(buffer.readableBytes());
                    discardingTooLongFrame = true;
                    if (failFast) {
                        fail(tooLongFrameLength);
                    }
                }
            } else {
                // Still discarding the buffer since a delimiter is not found.
                tooLongFrameLength += buffer.readableBytes();
                buffer.skipBytes(buffer.readableBytes());
            }
            return null;
        }
    }

    private void fail(long frameLength) {
        if (frameLength > 0) {
            throw new TooLongFrameException("frame length exceeds " + maxFrameLength + ": " + frameLength + " - discarded");
        } else {
            throw new TooLongFrameException("frame length exceeds " + maxFrameLength + " - discarding");
        }
    }

    private static void validateMaxFrameLength(int maxFrameLength) {
        checkPositive(maxFrameLength, "maxFrameLength");
    }
}