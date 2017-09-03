package com.wzdxt.texas.service;

import com.wzdxt.texas.model.Card;

import java.util.Collection;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/2.
 */
abstract public class AbsCalculator implements Calculator {
    public Composer composer = new Composer();

    @Override
    abstract public List<Double> calculate(Collection<Card> my, Collection<Card> river);
}
