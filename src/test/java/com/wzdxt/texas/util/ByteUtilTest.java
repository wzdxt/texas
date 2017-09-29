package com.wzdxt.texas.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-25.
 */
public class ByteUtilTest {
    @Test
    public void longLong() throws Exception {
        assertEquals(Tuple.of(10L, 20L), ByteUtil.parseToLongLong(ByteUtil.build(10L, 20L)));
    }

    @Test
    public void testLong() throws Exception {
        long l = 9016293145L;
        byte[] bytes = new byte[]{25, -73, 105, 25, 2};
        assertEquals(l, ByteUtil.parseToLong(bytes));
    }

}