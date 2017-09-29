package com.wzdxt.texas.model;

import com.wzdxt.texas.Constants;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/8/28.
 */
public class CardTest {

    @Test
    public void ofString() throws Exception {
        assertEquals("♠10", Card.of("♠10").toString());
    }

}