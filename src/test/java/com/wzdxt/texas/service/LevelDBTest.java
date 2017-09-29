package com.wzdxt.texas.service;

import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.model.CardSetTest;
import com.wzdxt.texas.model.hand.AbsHand;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-25.
 */
public class LevelDBTest {
    private LevelDB levelDB;

    @Before
    public void setUp() {
        levelDB = new LevelDB();
    }

    @Test
    public void get7to5() throws Exception {
        CardSet set;
        Long targetId;

        set = CardSetTest.royalFlush();
        targetId = levelDB.get7to5(set.serialize());
        assertEquals(AbsHand.from7Raw(set), AbsHand.of(targetId));

        set = CardSetTest.threeR();
        targetId = levelDB.get7to5(set.serialize());
        assertEquals(AbsHand.from7Raw(set), AbsHand.of(targetId));
    }

    @Test
    @Ignore
    public void get23toDouble() throws Exception {
    }

}