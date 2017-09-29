package com.wzdxt.texas.model.predefined;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.hand.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzdxt on 2017/8/27.
 */
@Slf4j
public class AllHands {
    public static int ROYAL_FLUSH_SIZE = (4);
    public static int STRAIGHT_FLUSH_SIZE = (36);
    public static int FOUR_RANK_SIZE = (624);
    public static int FULL_HOUSE_SIZE = (3744);
    public static int FLUSH_SIZE = (5108);
    public static int STRAIGHT_SIZE = (10200);
    public static int THREE_RANK_SIZE = (54912);
    public static int TWO_PAIR_SIZE = (123552);
    public static int ONE_PAIR_SIZE = (1098240);

    public static List<RoyalFlush> ROYAL_FLUSH = new ArrayList<>(ROYAL_FLUSH_SIZE);
    public static List<StraightFlush> STRAIGHT_FLUSH = new ArrayList<>(STRAIGHT_FLUSH_SIZE);
    public static List<FourRank> FOUR_RANK = new ArrayList<>(FOUR_RANK_SIZE);
    public static List<FullHouse> FULL_HOUSE = new ArrayList<>(FULL_HOUSE_SIZE);
    public static List<Flush> FLUSH = new ArrayList<>(FLUSH_SIZE);
    public static List<Straight> STRAIGHT = new ArrayList<>(STRAIGHT_SIZE);
    public static List<ThreeRank> THREE_RANK = new ArrayList<>(THREE_RANK_SIZE);
    public static List<TwoPair> TWO_PAIR = new ArrayList<>(TWO_PAIR_SIZE);
    public static List<OnePair> ONE_PAIR = new ArrayList<>(ONE_PAIR_SIZE);

    private static int proceed = 0;

    public static void init() {
        ROYAL_FLUSH.clear();
        STRAIGHT_FLUSH .clear();
        FOUR_RANK .clear();
        FULL_HOUSE .clear();
        FLUSH .clear();
        STRAIGHT .clear();
        THREE_RANK .clear();
        TWO_PAIR .clear();
        ONE_PAIR .clear();
        process(new int[5], 0, 0);
        log.info(String.format("proceed %d composition", proceed));
    }

    static void process(int[] selected, int idx, int from) {
        if (idx == 5) {
            process(selected);
            return;
        }
        for (int i = from; i < Constants.TOTAL_CARD - 4 + idx; i++) {
            selected[idx] = i;
            process(selected, idx + 1, i + 1);
        }
    }

    static void process(int[] selected) {
        List<Card> cardList = new ArrayList<>(5);
        for (int i : selected) {
            cardList.add(Card.CARD_LIST.get(i));
        }
        AbsHand hand;
        if ((hand = RoyalFlush.compose(cardList)) != null) {
            ROYAL_FLUSH.add((RoyalFlush)hand);
        } else if ((hand = StraightFlush.compose(cardList)) != null) {
            STRAIGHT_FLUSH.add((StraightFlush)hand);
        } else if ((hand = FourRank.compose(cardList)) != null) {
            FOUR_RANK.add((FourRank)hand);
        } else if ((hand = FullHouse.compose(cardList)) != null) {
            FULL_HOUSE.add((FullHouse)hand);
        } else if ((hand = Flush.compose(cardList)) != null) {
            FLUSH.add((Flush)hand);
        } else if ((hand = Straight.compose(cardList)) != null) {
            STRAIGHT.add((Straight)hand);
        } else if ((hand = ThreeRank.compose(cardList)) != null) {
            THREE_RANK.add((ThreeRank)hand);
        } else if ((hand = TwoPair.compose(cardList)) != null) {
            TWO_PAIR.add((TwoPair)hand);
        } else if ((hand = OnePair.compose(cardList)) != null) {
            ONE_PAIR.add((OnePair)hand);
        }
        proceed++;
    }

}
