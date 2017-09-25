package com.wzdxt.texas.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-25.
 */
public class KeyUtilTest {
    @Test
    public void longLong() throws Exception {
        assertEquals(Tuple.of(10L, 20L), ByteUtil.parseToLongLong(ByteUtil.build(10L, 20L)));
    }

}