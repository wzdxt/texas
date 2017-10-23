package com.wzdxt.texas.service;

import com.wzdxt.texas.ApplicationContextHolder;
import com.wzdxt.texas.model.Card;

import java.util.Collection;

/**
 * Created by wzdxt on 2017/9/3.
 */
public class CalculatorFactory {
    private static FlopImpl flop;
    private static TurnImpl turn;
    private static RiverImpl river;
    static {
        flop = ApplicationContextHolder.get().getBean(FlopImpl.class);
        turn = ApplicationContextHolder.get().getBean(TurnImpl.class);
        river = ApplicationContextHolder.get().getBean(RiverImpl.class);
    }

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

    public static Calculator getCalculatorRaw(Collection<Card> common) {
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
