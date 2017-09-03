package com.wzdxt.texas.service;

import com.wzdxt.texas.model.Card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.wzdxt.texas.util.C.C;

/**
 * Created by wzdxt on 2017/9/1.
 */
public class RiverImpl extends AbsCalculator implements Calculator {
    @Override
    public List<Double> calculate(Collection<Card> my, Collection<Card> river) {
        List<Double> ret = new ArrayList<>();
        int all = C(45, 2);
        int larger = composer.largerHandsAfterRiver(river, my).size();
        double p = 1 - larger * 1.0 / all;
        for (int i = 0; i < 101; i++) {
            ret.add(p);
        }
        return ret;
    }
}
