package com.wzdxt.texas.model;

import com.wzdxt.texas.Constants;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/8/28.
 */
public class CardTest {
    @Test
    public void of() throws Exception {
        for (int i = 0; i < Constants.TOTAL_CARD; i++) {
            assertEquals(i, Card.of(i).getId());
        }
    }

}