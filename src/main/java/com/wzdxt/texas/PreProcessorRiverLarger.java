package com.wzdxt.texas;

import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.model.hand.AbsHand;
import com.wzdxt.texas.service.Composer;
import com.wzdxt.texas.service.LevelDB;
import com.wzdxt.texas.util.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import java.util.BitSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.wzdxt.texas"})
public class PreProcessorRiverLarger implements CommandLineRunner {
    @Autowired
    private LevelDB levelDB;
    @Autowired
    private Composer composer;

    private volatile long proceed = 0;

    private AtomicInteger threadCnt = new AtomicInteger(0);

    public void process() {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(processors + 1);

        BitSet selected = new BitSet(Constants.TOTAL_CARD);
        CardSet mySet = new CardSet();
        // start from 0 5
        // my1
        for (int my1 = 0; my1 < Constants.TOTAL_CARD; my1++) {
            Card myCard1 = Card.of(my1);
            mySet.add(myCard1);
            selected.set(my1);
            // my2
            for (int my2 = my1 + 5; my2 < Constants.TOTAL_CARD; my2++) {
                Card myCard2 = Card.of(my2);
                mySet.add(myCard2);
                selected.set(my2);

                CardSet commonSet = new CardSet();
                // common1
                for (int common1 = 0; common1 < Constants.TOTAL_CARD; common1++) {
                    if (!selected.get(common1)) {
                        Card commonCard1 = Card.of(common1);
                        commonSet.add(commonCard1);
                        selected.set(common1);
                        // common2
                        for (int common2 = common1 + 1; common2 < Constants.TOTAL_CARD; common2++) {
                            if (!selected.get(common2)) {
                                Card commonCard2 = Card.of(common2);
                                commonSet.add(commonCard2);
                                selected.set(common2);
                                // common3
                                for (int common3 = common2 + 1; common3 < Constants.TOTAL_CARD; common3++) {
                                    if (!selected.get(common3)) {
                                        Card commonCard3 = Card.of(common3);
                                        commonSet.add(commonCard3);
                                        selected.set(common3);
                                        // common4
                                        for (int common4 = common3 + 1; common4 < Constants.TOTAL_CARD; common4++) {
                                            if (!selected.get(common4)) {
                                                Card commonCard4 = Card.of(common4);
                                                commonSet.add(commonCard4);
                                                selected.set(common4);
                                                // common5
                                                for (int common5 = common4 + 1; common5 < Constants.TOTAL_CARD; common5++) {
                                                    if (!selected.get(common5)) {
                                                        Card commonCard5 = Card.of(common5);
                                                        commonSet.add(commonCard5);

                                                        // process
                                                        process(mySet, commonSet, pool);

                                                        commonSet.remove(commonCard5);
                                                    }
                                                }
                                                selected.clear(common4);
                                                commonSet.remove(commonCard4);
                                            }
                                        }
                                        selected.clear(common3);
                                        commonSet.remove(commonCard3);
                                    }
                                }
                                selected.clear(common2);
                                commonSet.remove(commonCard2);
                            }
                        }
                        selected.clear(common1);
                        commonSet.remove(commonCard1);
                    }
                }
                selected.clear(my2);
                mySet.remove(myCard2);
            }
            selected.clear(my1);
            mySet.remove(myCard1);
        }
    }

    public void process(CardSet my, CardSet common, ExecutorService pool) {
        int c = 1;
        while (threadCnt.get() > 500) try {
            Thread.sleep(10 * c++);
        } catch (InterruptedException ignored) {
        }
        CardSet myCard = (CardSet) my.clone();
        CardSet commonCard = (CardSet) common.clone();
        pool.submit(() -> {
                    long st = System.nanoTime();
                    long spro = proceed;
                    if (levelDB.getRiverLarger((int) myCard.getId(), (int) commonCard.getId()) == null) {
                        CardSet all = (CardSet) myCard.clone();
                        all.addAll(commonCard);
                        AbsHand myHand = AbsHand.from7(all);
                        Tuple<List<CardSet>, List<CardSet>> t = composer.getLargerEqual(commonCard, myCard, myHand);
                        levelDB.putRiverLarger((int) myCard.getId(), (int) commonCard.getId(), t.left.size(), t.right.size());
                    }
                    proceed++;
                    long cost = System.nanoTime() - st;
                    long pro = proceed - spro;
                    if (proceed % 1000 == 0)
                        log.info("proceed {}, per-cost {}, my {}, common {}",
                                proceed, cost / pro, myCard, commonCard);
                    threadCnt.decrementAndGet();
                }
        );
        threadCnt.incrementAndGet();
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(PreProcessorRiverLarger.class).headless(false).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
//        this.process();
    }
}

