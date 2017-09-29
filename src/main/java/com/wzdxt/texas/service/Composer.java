package com.wzdxt.texas.service;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.model.hand.*;
import com.wzdxt.texas.util.Tuple;
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
        return getLargerEqual(river, mine, myHand).left;
    }

    List<CardSet> largerHandsAfterRiver(final Collection<Card> river, final Collection<Card> mine, final AbsHand myHand) {
        return getLargerEqual(river, mine, myHand).left;
    }

    public Tuple<List<CardSet>, List<CardSet>> getLargerEqual(final Collection<Card> river, final Collection<Card> except, final AbsHand myHand) {
        List<CardSet> larger = new ArrayList<>();
        List<CardSet> equal = new ArrayList<>();
        boolean[] used = new boolean[64];
        river.forEach(card -> used[card.getId()] = true);
        except.forEach(card -> used[card.getId()] = true);
        int b = 1;
        CardSet set = new CardSet(river);
        for (int i = 0; i < Constants.TOTAL_CARD; i++) {
            if (!used[i]) {
                Card card1 = Card.CARD_LIST.get(i);
                set.add(card1);
                for (int j = i + 1; j < Constants.TOTAL_CARD; j++) {
                    if (!used[j]) {
                        Card card2 = Card.CARD_LIST.get(j);
                        set.add(card2);
                        AbsHand hand = composeHand(set);
                        int compRes = hand.compareTo(myHand);
                        if (compRes > 0) {
                            larger.add(set);
                        } else if (compRes == 0) {
                            equal.add(set);
                        }
                        set.remove(card2);
                    }
                }
                set.remove(card1);
            }
        }
        return Tuple.of(larger, equal);
    }

    public AbsHand composeHand(CardSet all) {
        if (all.size() != 7) {
            throw new IllegalArgumentException();
        }
        return AbsHand.from7(all);
    }
}
