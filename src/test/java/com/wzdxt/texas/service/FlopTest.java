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
        Calculator calc  = new FlopImpl();
        List<Float> list = calc.calculate(Arrays.asList(
                new Card(0, Constants.RANK_10),
                new Card(0, Constants.RANK_J)
        ), Arrays.asList(
                new Card(0, Constants.RANK_3),
                new Card(1, Constants.RANK_A),
                new Card(0, Constants.RANK_9)
        ));
        assertEquals(C(47, 2), list.size());
        System.out.println(list);
    }

}