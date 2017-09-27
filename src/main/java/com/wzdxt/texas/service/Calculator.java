package com.wzdxt.texas.service;

import com.wzdxt.texas.model.Card;

import java.util.Collection;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/1.
 */
public interface Calculator {
    /**
     * @param my
     * @param common
     * @return from 0 to 100 percent, the possibility to win
     */
    List<Float> calculate(Collection<Card> my, Collection<Card> common);
}
