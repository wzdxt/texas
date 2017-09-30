package com.wzdxt.texas;

import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.model.CommonCard;
import com.wzdxt.texas.model.MyCard;
import com.wzdxt.texas.service.Calculator;
import com.wzdxt.texas.service.CalculatorFactory;
import com.wzdxt.texas.service.LevelDB;
import com.wzdxt.texas.util.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.wzdxt.texas"})
@Profile("23toList")
public class PreProcessor23toList implements CommandLineRunner {
    @Autowired
    private LevelDB levelDB;

    private volatile int proceed = 1;
    private volatile int totalProceed = 0;
    private long time100last;
    private long time100;

    private AtomicInteger threadCnt = new AtomicInteger(0);

    public void process() {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(processors + 1);

        BitSet selected = new BitSet(Constants.TOTAL_CARD);
        MyCard myCard = new MyCard();
        // my1
        for (int my1 = 0; my1 < Constants.TOTAL_CARD; my1++) {
            Card myCard1 = Card.CARD_LIST.get(my1);
            myCard.add(myCard1);
            selected.set(my1);
            // my2
            for (int my2 = my1 + 1; my2 < Constants.TOTAL_CARD; my2++) {
                Card myCard2 = Card.CARD_LIST.get(my2);
                myCard.add(myCard2);
                selected.set(my2);

                CommonCard commonCard = new CommonCard();
                // common1
                for (int common1 = 0; common1 < Constants.TOTAL_CARD; common1++) {
                    if (!selected.get(common1)) {
                        Card commonCard1 = Card.CARD_LIST.get(common1);
                        commonCard.add(commonCard1);
                        // common2
                        for (int common2 = common1 + 1; common2 < Constants.TOTAL_CARD; common2++) {
                            if (!selected.get(common2)) {
                                Card commonCard2 = Card.CARD_LIST.get(common2);
                                commonCard.add(commonCard2);
                                // common3
                                for (int common3 = common2 + 1; common3 < Constants.TOTAL_CARD; common3++) {
                                    if (!selected.get(common3)) {
                                        Card commonCard3 = Card.CARD_LIST.get(common3);
                                        commonCard.add(commonCard3);

                                        // process
                                        process(myCard, commonCard, pool);

                                        commonCard.remove(commonCard3);
                                    }
                                }

                                commonCard.remove(commonCard2);
                            }
                        }

                        commonCard.remove(commonCard1);
                    }
                }

                selected.clear(my2);
                myCard.remove(myCard2);
            }
            selected.clear(my1);
            myCard.remove(myCard1);
        }
    }

    public void process(MyCard my, CommonCard common, ExecutorService pool) {
        int c = 1;
        while (threadCnt.get() > 50) try {
            Thread.sleep(10 * c++);
        } catch (InterruptedException ignored) {
        }
        Tuple<MyCard, CommonCard> t = CardSet.uniform(my, common);
        MyCard myCard = t.left;
        CommonCard commonCard = t.right;
        MyCard myCardOrigin = (MyCard) my.clone();
        CommonCard commonCardOrigin = (CommonCard) common.clone();
        if (myCard.equals(myCardOrigin) && commonCard.equals(commonCardOrigin)) {
            pool.submit(() -> {
                        long st = System.currentTimeMillis();
                        int spro = proceed;
                        if (levelDB.get23toList(myCard.serialize(), commonCard.serialize()) == null) {
                            Calculator calc = CalculatorFactory.getCalculatorRaw(commonCard);
                            List<Float> possibility = calc.calculate(myCard, commonCard);
                            levelDB.put23toList(myCard.serialize(), commonCard.serialize(), possibility);
                            proceed++;
                        }

                        totalProceed++;
                        long perCost = System.currentTimeMillis() - st;
                        int perPro = proceed - spro;
                        if (perPro == 0) perPro = 1;    // avoid divide 0
                        long now = System.currentTimeMillis();
                        if (totalProceed % 100 == 0) {
                            time100 = (now - time100last) / 100;
                            time100last = now;
                        }
                        log.info("proceed {}/{}, per-cost {}/{}, my {}, common {}",
                                proceed, totalProceed,
                                perCost / perPro, (time100),
                                myCardOrigin, commonCardOrigin);

                        threadCnt.decrementAndGet();
                    }
            );
            threadCnt.incrementAndGet();
        } else {
            totalProceed++;
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(PreProcessor23toList.class).headless(false).profiles("23toList").run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.process();
    }
}

