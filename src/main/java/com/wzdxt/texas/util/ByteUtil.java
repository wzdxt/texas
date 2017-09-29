package com.wzdxt.texas.util;

import com.google.common.primitives.UnsignedBytes;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static byte[] build(int i1, int i2) {
        ByteBuffer buffer = ByteBuffer.allocate(2 * Integer.BYTES);
        buffer.putInt(i1);
        buffer.putInt(i2);
        return buffer.array();
    }

    public static byte[] build(long l) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(l);
        return buffer.array();
    }

    public static byte[] build(byte[] b1, byte[] b2) {
        ByteBuffer buffer = ByteBuffer.allocate(Byte.BYTES * (b1.length + b2.length));
        buffer.put(b1);
        buffer.put(b2);
        return buffer.array();
    }

    public static byte[] build(List<Float> list) {
        ByteBuffer buffer = ByteBuffer.allocate(list.size() * Float.BYTES);
        list.forEach(buffer::putFloat);
        return buffer.array();
    }

    public static long parseToLong(byte[] bytes) {
        long ret = 0;
        for (int i = bytes.length - 1; i >= 0; i--) {
            ret = (ret << 8) | (bytes[i] & 0xff);
        }
        return ret;
    }

    public static Tuple<Integer, Integer> parseToIntegerInteger(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return Tuple.of(buffer.getInt(), buffer.getInt());
    }

    public static Tuple<Long, Long> parseToLongLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return Tuple.of(buffer.getLong(), buffer.getLong());
    }

    public static List<Float> parseToFloatList(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        List<Float> list = new ArrayList<>(bytes.length / Float.BYTES);
        while (buffer.hasRemaining()) {
            list.add(buffer.getFloat());
        }
        return list;
    }

}
