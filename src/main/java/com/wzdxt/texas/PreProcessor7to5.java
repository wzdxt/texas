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

    public void process(byte[] selected, byte idx, byte from) {
        if (idx == 7) {
            process(selected);
            return;
        }
        for (byte i = from; i < Constants.TOTAL_CARD - 6 + idx; i++) {
            selected[idx] = i;
            process(selected, (byte) (idx + 1), (byte) (i + 1));
        }
    }

    private boolean start = false;
    private LevelDB levelDB;
    private static int proceed = 0;
    private long lastTime = 0;

    public void process(byte[] selected) {
        CardSet cardSet = new CardSet();
        for (byte i : selected) {
            cardSet.add(Card.of(i));
        }
        if (!start) {
            start = levelDB.get7to5(cardSet.serialize()) == null;
        }
        if (start) {
            AbsHand hand = AbsHand.from7Raw(cardSet);
            levelDB.put7to5(cardSet.serialize(), hand.serialize());
        }
        proceed++;
        if (proceed % 100000 == 0) {
            long now = System.currentTimeMillis();
            log.info("proceed {}, cost {}, started: {}, last is {}", proceed, now - lastTime, start, selected);
            lastTime = now;
        }
    }

    public static void main(String[] args) throws Exception {
        PreProcessor7to5 pre = new PreProcessor7to5();
        pre.process(new byte[7], (byte) 0, (byte) 3);
    }

}

