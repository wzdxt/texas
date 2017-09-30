package com.wzdxt.texas.service;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.wzdxt.texas.util.C.C;
import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/3.
 */
public class FlopTest {
    @Test
    public void calculate() throws Exception {
        Calculator calc = new FlopImpl();
        List<Float> list = calc.calculate(Arrays.asList(
                Card.of((byte) 0, Constants.RANK_10),
                Card.of((byte) 0, Constants.RANK_J)
        ), Arrays.asList(
                Card.of((byte) 0, Constants.RANK_3),
                Card.of((byte) 1, Constants.RANK_A),
                Card.of((byte) 0, Constants.RANK_9)
        ));
//        assertEquals(C(47, 2), list.size());
        assertEquals(100, list.size());
        System.out.println(list);
    }

}