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
        BitSet used = new BitSet();
        my.forEach(card -> used.set(card.getId()));
        flop.forEach(card -> used.set(card.getId()));
        CardSet all = new CardSet(flop);
        all.addAll(my);
        for (int i = 0; i < Constants.TOTAL_CARD; i++) {
            if (!used.get(i)) {
                all.add(Card.of(i));
                CardSet river = new CardSet(flop);
                river.add(Card.of(i));
                for (int j = i + 1; j < Constants.TOTAL_CARD; j++) {
                    if (!used.get(j)) {
                        all.add(Card.of(j));
                        AbsHand myHand = composer.composeHand(all);
                        if (!(myHand instanceof HighCard)) {
                            river.add(Card.of(j));
                            int larger = composer.largerHandsAfterRiver(river, my, myHand).size();
                            float rate = 1 - larger * 1f / total;
//                            log.debug("{}, {}", rate, river);
                            ret.add(rate);
                            river.remove(Card.of(j));
                        } else {
                            ret.add(0f);
                        }
                        all.remove(Card.of(j));
                    }
                }
                river.remove(Card.of(i));
                all.remove(Card.of(i));
            }
        }
        Collections.sort(ret);
        Collections.reverse(ret);
        return ret;
    }
}
