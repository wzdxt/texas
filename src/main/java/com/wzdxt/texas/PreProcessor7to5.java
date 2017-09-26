package com.wzdxt.texas;

import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.model.hand.AbsHand;
import com.wzdxt.texas.service.LevelDB;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PreProcessor7to5 {

    private PreProcessor7to5() {
        levelDB = new LevelDB();
    }

    public void process(int[] selected, int idx, int from) {
        if (idx == 7) {
            process(selected);
            return;
        }
        for (int i = from; i < Constants.TOTAL_CARD - 6 + idx; i++) {
            selected[idx] = i;
            process(selected, idx + 1, i + 1);
        }
    }

    private boolean start = false;
    private LevelDB levelDB;
    private static int proceed = 0;

    public void process(int[] selected) {
        CardSet cardSet = new CardSet();
        for (int i : selected) {
            cardSet.add(Card.of(i));
        }
        if (!start) {
            start = levelDB.get7to5(cardSet.getId()) == null;
        }
        if (start) {
            AbsHand hand = AbsHand.from7Raw(cardSet);
            levelDB.put7to5(cardSet.getId(), hand.getId());
        }
        proceed++;
        if (proceed % 100000 == 0) {
            log.info("proceed {}, started: {}, last is {}", proceed, start, selected);
        }
    }

    public static void main2(String[] args) throws Exception {
        PreProcessor7to5 pre = new PreProcessor7to5();
        pre.process(new int[7], 0, 0);
    }

}

