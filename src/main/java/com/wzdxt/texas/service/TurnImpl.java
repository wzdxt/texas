package com.wzdxt.texas.service;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.model.hand.AbsHand;
import com.wzdxt.texas.model.hand.HighCard;

import java.util.*;

import static com.wzdxt.texas.util.C.C;

/**
 * Created by wzdxt on 2017/9/1.
 */
public class TurnImpl extends AbsCalculator implements Calculator {
    @Override
    public List<Double> calculate(Collection<Card> my, Collection<Card> turn) {
        List<Double> ret = new ArrayList<>();
        int total = C(45, 2);
        BitSet used = new BitSet();
        my.forEach(card -> used.set(card.getId()));
        turn.forEach(card -> used.set(card.getId()));
        CardSet all = new CardSet(turn);
        all.addAll(my);
        for (int i = 0; i < Constants.TOTAL_CARD; i++) {
            if (!used.get(i)) {
                all.add(Card.of(i));
                AbsHand myHand = composer.composeHand(all);
                all.remove(Card.of(i));
                if (!(myHand instanceof HighCard)) {
                    CardSet river = new CardSet(turn);
                    river.add(Card.of(i));
                    int larger = composer.largerHandsAfterRiver(river, my, myHand).size();
                    ret.add(1 - larger * 1.0 / total);
                } else {
                    ret.add(0.0);
                }
            }
        }
        Collections.sort(ret);
        Collections.reverse(ret);
        return ret;
    }
}
