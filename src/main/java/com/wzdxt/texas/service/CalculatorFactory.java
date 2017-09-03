package com.wzdxt.texas.service;

import com.wzdxt.texas.model.Card;

import java.util.Collection;

/**
 * Created by wzdxt on 2017/9/3.
 */
public class CalculatorFactory {
    private static FlopImpl flop = new FlopImpl();
    private static TurnImpl turn = new TurnImpl();
    private static RiverImpl river = new RiverImpl();
    public static Calculator getCalculator(Collection<Card> common) {
        switch (common.size()) {
            case 3:
                return flop;
            case 4:
                return turn;
            case 5:
                return river;
            default:
                throw new IllegalArgumentException();
        }
    }
}
