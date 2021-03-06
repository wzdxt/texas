package com.wzdxt.texas.service;

import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CommonCard;
import com.wzdxt.texas.model.MyCard;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.wzdxt.texas.util.C.C;

/**
 * Created by wzdxt on 2017/9/1.
 */
@Component
public class RiverImpl extends AbsCalculator implements Calculator {
    @Override
    public List<Float> calculate(Collection<Card> my, Collection<Card> river) {
        List<Float> ret = new ArrayList<>();
        int all = C(45, 2);
        int larger = composer.largerHandsAfterRiver(river, my).size();
        float p = 1 - larger * 1f / all;
        for (int i = 0; i < 101; i++) {
            ret.add(p);
        }
        return ret;
    }

    @Override
    public List<Float> calculate(MyCard my, CommonCard common) {
        return calculate(new ArrayList<>(my), common);
    }
}
