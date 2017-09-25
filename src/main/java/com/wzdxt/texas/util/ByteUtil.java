package com.wzdxt.texas.util;

import java.nio.ByteBuffer;

/**
 * Created by dai_x on 17-9-25.
 */
public class ByteUtil {
    public static byte[] build(long l1, long l2) {
        ByteBuffer buffer = ByteBuffer.allocate(2 * Long.BYTES);
        buffer.putLong(l1);
        buffer.putLong(l2);
        return buffer.array();
    }

    public static byte[] build(int i, long l) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + Long.BYTES);
        buffer.putInt(i);
        buffer.putLong(l);
        return buffer.array();
    }

    public static byte[] build(long l) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(l);
        return buffer.array();
    }

    public static long parseToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    public static Tuple<Long, Long> parseToLongLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(2 * Long.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return Tuple.of(buffer.getLong(), buffer.getLong());
    }

}
