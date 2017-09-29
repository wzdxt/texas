package com.wzdxt.texas.service;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.model.hand.AbsHand;
import com.wzdxt.texas.model.hand.HighCard;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.wzdxt.texas.util.C.C;

/**
 * Created by wzdxt on 2017/9/1.
 */
@Slf4j
public class FlopImpl extends AbsCalculator implements Calculator {
    @Override
    public List<Float> calculate(Collection<Card> my, Collection<Card> flop) {
        List<Float> ret = new ArrayList<>();
        int total = C(45, 2);
        boolean[] used = new boolean[64];
        my.forEach(card -> used[card.getId()] = true);
        flop.forEach(card -> used[card.getId()] = true);
        CardSet all = new CardSet(flop);
        all.addAll(my);
        for (int i = 0; i < Constants.TOTAL_CARD; i++) {
            if (!used[i]) {
                Card common4 = Card.CARD_LIST.get(i);
                all.add(common4);
                CardSet river = new CardSet(flop);
                river.add(common4);
                for (int j = i + 1; j < Constants.TOTAL_CARD; j++) {
                    if (!used[j]) {
                        Card common5 = Card.CARD_LIST.get(j);
                        all.add(common5);
                        AbsHand myHand = composer.composeHand(all);
                        if (!(myHand instanceof HighCard)) {
                            river.add(common5);
                            int larger = composer.largerHandsAfterRiver(river, my, myHand).size();
                            float rate = 1 - larger * 1f / total;
//                            log.debug("{}, {}", rate, river);
                            ret.add(rate);
                            river.remove(common5);
                        } else {
                            ret.add(0f);
                        }
                        all.remove(common5);
                    }
                }
                river.remove(common4);
                all.remove(common4);
            }
        }
        Collections.sort(ret);
        Collections.reverse(ret);
        return ret;
    }
}
