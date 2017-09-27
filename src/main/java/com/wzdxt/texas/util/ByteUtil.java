package com.wzdxt.texas.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
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

    public static byte[] build(List<Float> list) {
        ByteBuffer buffer = ByteBuffer.allocate(list.size() * Float.BYTES);
        list.forEach(buffer::putFloat);
        return buffer.array();
    }

    public static long parseToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.getLong();
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
