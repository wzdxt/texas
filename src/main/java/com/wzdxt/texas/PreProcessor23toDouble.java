package com.wzdxt.texas;

import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.service.Calculator;
import com.wzdxt.texas.service.CalculatorFactory;
import com.wzdxt.texas.service.LevelDB;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import java.util.BitSet;
import java.util.List;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.wzdxt.texas"})
public class PreProcessor23toDouble implements CommandLineRunner {
    @Autowired
    private LevelDB levelDB;

    public void process() {
        BitSet selected = new BitSet(Constants.TOTAL_CARD);
        CardSet mySet = new CardSet();
        // my1
        for (int my1 = 0; my1 < Constants.TOTAL_CARD; my1++) {
            Card myCard1 = Card.of(my1);
            mySet.add(myCard1);
            selected.set(my1);
            // my2
            for (int my2 = my1 + 1; my2 < Constants.TOTAL_CARD; my2++) {
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

                                        // process
                                        process(mySet, commonSet);

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

    private boolean start = false;
    private int proceed = 0;

    public void process(CardSet my, CardSet common) {
        if (!start) {
            start = levelDB.get23toDouble((int) my.getId(), (int) common.getId()) == null;
        }

        if (start) {
            Calculator calc = CalculatorFactory.getCalculatorRaw(common);
            List<Double> possibility = calc.calculate(my, common);
            levelDB.put23toDouble((int) my.getId(), (int) common.getId(), possibility);
        }
        proceed++;
        if (proceed % 1 == 0) {
            log.info("proceed {}, started: {}, my {}, common {}", proceed, start, my, common);
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(PreProcessor23toDouble.class).headless(false).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.process();
    }
}

