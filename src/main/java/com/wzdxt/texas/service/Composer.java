package com.wzdxt.texas.service;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.model.hand.*;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by wzdxt on 2017/9/1.
 */
@Component
public class Composer {
    protected Random rand = new Random();

    List<CardSet> largerHandsAfterRiver(final Collection<Card> river, final Collection<Card> mine) {
        final CardSet myAll = new CardSet(river);
        myAll.addAll(mine);
        AbsHand myHand = composeHand(myAll);
        return getLarger(river, mine, myHand);
    }

    List<CardSet> largerHandsAfterRiver(final Collection<Card> river, final Collection<Card> mine, final AbsHand myHand) {
        return getLarger(river, mine, myHand);
    }

    List<CardSet> getLarger(final Collection<Card> river, final Collection<Card> except, final AbsHand myHand) {
        List<CardSet> ret = new ArrayList<>();
        Set<Integer> used = new HashSet<>();
        river.forEach(card -> used.add(card.getId()));
        except.forEach(card -> used.add(card.getId()));
        int b = 1;
        CardSet set = new CardSet(river);
        for (int i = 0; i < Constants.TOTAL_CARD; i++) {
            if (!used.contains(i)) {
                Card card1 = Card.of(i);
                set.add(card1);
                for (int j = i + 1; j < Constants.TOTAL_CARD; j++) {
                    if (!used.contains(j)) {
                        Card card2 = Card.of(j);
                        set.add(card2);
                        AbsHand hand = composeHand(set);
                        if (hand.compareTo(myHand) > 0) {
                            ret.add(set);
                        } else if (hand.compareTo(myHand) == 0) {
                            // consider as win
                            // larger is considered in other case
                            // even if equal with one, I can win other's money
                        }
                        set.remove(card2);
                    }
                }
                set.remove(card1);
            }
        }
        return ret;
    }

    public AbsHand composeHand(CardSet all) {
        if (all.size() != 7) {
            throw new IllegalArgumentException();
        }
        return AbsHand.from7(all);
    }
}
