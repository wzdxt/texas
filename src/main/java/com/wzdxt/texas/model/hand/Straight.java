package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wzdxt on 2017/8/27.
 */
public class Straight extends AbsHand {
    protected Straight(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 4;
    }

    /**
     * @return -1 if A2345
     */
    @Override
    public long getValue() {
        return rank == Constants.RANK_A ? 1 : rank + 2;
    }

    public static Straight of(Collection<Card> c) {
        Straight s = new Straight(c);
        s.rank = s.first().getRank();
        if (s.rank == Constants.RANK_2 && s.last().getRank() == Constants.RANK_A) {
            s.rank = Constants.RANK_A;
        }
        if (s.size() != 5) {
            throw new IllegalArgumentException();
        }
        return s;
    }

    public static Straight compose(Collection<Card> c) {
        Straight s = Straight.of(c);
        int idx = 0;
        if (s.rank == Constants.RANK_A) {
            int rank = Constants.RANK_2;
            for (Card card : s) {
                if (card.getRank() != rank && !(rank == 4 && card.getRank() == Constants.RANK_A)) {
                    return null;
                }
                rank++;
            }
        } else {
            for (Card card : s) {
                if (card.getRank() - s.rank != idx++) {
                    return null;
                }
            }
        }
        return s;
    }

    /**
     * @param c can receive any size
     * @return
     */
    public static Straight compose7(CardSet c) {
        List<Card> fin = new ArrayList<>(5);
        Card prev = null;
        Card a = null;
        for (Card card : c.descendingSet()) {
            if (card.getRank() == Constants.RANK_A) a = card;
            if (prev == null) {
                prev = card;
                fin.add(card);
                continue;
            }
            if (card.getRank() == prev.getRank()) continue;
            if (card.getRank() == prev.getRank() - 1) {
                fin.add(card);
                if (card.getRank() == Constants.RANK_2 && a != null) fin.add(a);
                if (fin.size() == 5) {
                    return Straight.of(fin);
                }
            } else {
                fin.clear();
                fin.add(card);
            }
            prev = card;
        }
        return null;
    }

}
