package com.wzdxt.texas.business.master;

import com.wzdxt.texas.config.MasterConfigure;
import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CommonCard;
import com.wzdxt.texas.model.MyCard;
import com.wzdxt.texas.service.Calculator;
import com.wzdxt.texas.service.CalculatorFactory;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/3.
 */
@Slf4j
@Component
public class TexasMasterImpl implements TexasMaster {
    @Autowired
    MasterConfigure configuration;
    private Beginner beginner = new Beginner();

    @Override
    public MasterDecision suggest(MyCard my, CommonCard common) {
        if (common.isEmpty()) {
            Iterator<Card> iter = my.iterator();
            BeginnerResult result = beginner.evaluate(iter.next(), iter.next());
            switch (result.level) {
            case RED:
                return MasterDecision.BET_2_10;
            case YELLOW:
                return MasterDecision.CALL_5;
            case BLUE:
            case GREEN:
                return MasterDecision.CALL_2;
            }
        } else {
            Calculator calc = CalculatorFactory.getCalculator(common);
            List<Float> possibility = calc.calculate(my, common);
            log.debug("possibility:[{}][{}]:{}", my, common, possibility);
            MasterConfigure.CheckConf[] checkConfs;
            switch (common.size()) {
            case 3:
                checkConfs = configuration.getFlop();
                break;
            case 4:
                checkConfs = configuration.getTurn();
                break;
            case 5:
                checkConfs = configuration.getRiver();
                break;
            default:
                throw new IllegalArgumentException();
            }
            for (MasterConfigure.CheckConf checkConf : checkConfs) {
                double rate = getPossibility(possibility, checkConf.getCheck());
                for (MasterConfigure.DecideConf decideConf : checkConf.getDecides()) {
                    if (rate * 100 >= decideConf.getRate()) {
                        log.debug("possibility: {}, result: {} decide by: {}", rate, decideConf.getBet(), checkConf);
                        return MasterDecision.of(decideConf.getBet());
                    }
                }
            }
            return MasterDecision.CHECK_OR_FOLD;
        }
        return MasterDecision.CHECK_OR_FOLD;
    }

    private float getPossibility(List<Float> poss, int check) {
        int idx = poss.size() * check / 100;
        float total = 0;
        for (int i = 0; i < idx; i++) {
            total += poss.get(i);
        }
        return total / (idx);
    }

    private class Beginner {
        BeginnerResult evaluate(Card c1, Card c2) {
            if (c1.getRank() < c2.getRank()) {
                Card t = c1;
                c1 = c2;
                c2 = t;
            }
            int score = c1.getRank() + c2.getRank();
            if (c1.getRank() == c2.getRank()) {
                if (c1.getRank() >= 7) return new BeginnerResult(BeginnerLevel.RED, score);
                if (c1.getRank() >= 5) return new BeginnerResult(BeginnerLevel.YELLOW, score);
                return new BeginnerResult(BeginnerLevel.BLUE, score);
            } else if (c1.getSuit() == c2.getSuit()) {
                switch (c1.getRank()) {
                case Constants.RANK_A:
                    if (c2.getRank() >= 10) return new BeginnerResult(BeginnerLevel.RED, score);
                    if (c2.getRank() >= 6) return new BeginnerResult(BeginnerLevel.YELLOW, score);
                    return new BeginnerResult(BeginnerLevel.BLUE, score);
                case Constants.RANK_K:
                    if (c2.getRank() >= 10) return new BeginnerResult(BeginnerLevel.RED, score);
                    if (c2.getRank() >= 9) return new BeginnerResult(BeginnerLevel.YELLOW, score);
                    return new BeginnerResult(BeginnerLevel.BLUE, score);
                case Constants.RANK_Q:
                    if (c2.getRank() >= 10) return new BeginnerResult(BeginnerLevel.RED, score);
                    if (c2.getRank() >= 8) return new BeginnerResult(BeginnerLevel.YELLOW, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_J:
                    if (c2.getRank() >= 9) return new BeginnerResult(BeginnerLevel.RED, score);
                    if (c2.getRank() >= 8) return new BeginnerResult(BeginnerLevel.YELLOW, score);
                    if (c2.getRank() >= 7) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_10:
                    if (c2.getRank() >= 9) return new BeginnerResult(BeginnerLevel.RED, score);
                    if (c2.getRank() >= 8) return new BeginnerResult(BeginnerLevel.YELLOW, score);
                    if (c2.getRank() >= 7) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_9:
                    if (c2.getRank() >= 8) return new BeginnerResult(BeginnerLevel.YELLOW, score);
                    if (c2.getRank() >= 6) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_8:
                    if (c2.getRank() >= 6) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_7:
                    if (c2.getRank() >= 5) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_6:
                    if (c2.getRank() >= 5) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_5:
                    if (c2.getRank() >= 4) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                default:
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                }
            } else {
                switch (c1.getRank()) {
                case Constants.RANK_A:
                    if (c2.getRank() >= 10) return new BeginnerResult(BeginnerLevel.RED, score);
                    if (c2.getRank() >= 7) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_K:
                    if (c2.getRank() >= 11) return new BeginnerResult(BeginnerLevel.RED, score);
                    if (c2.getRank() >= 10) return new BeginnerResult(BeginnerLevel.YELLOW, score);
                    if (c2.getRank() >= 9) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_Q:
                    if (c2.getRank() >= 10) return new BeginnerResult(BeginnerLevel.YELLOW, score);
                    if (c2.getRank() >= 9) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_J:
                    if (c2.getRank() >= 10) return new BeginnerResult(BeginnerLevel.YELLOW, score);
                    if (c2.getRank() >= 8) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_10:
                    if (c2.getRank() >= 8) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_9:
                    if (c2.getRank() >= 7) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                case Constants.RANK_8:
                    if (c2.getRank() >= 7) return new BeginnerResult(BeginnerLevel.BLUE, score);
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                default:
                    return new BeginnerResult(BeginnerLevel.GREEN, score);
                }
            }
        }
    }

    @Value
    private class BeginnerResult {
        BeginnerLevel level;
        int score;
    }

    private enum BeginnerLevel {
        RED, YELLOW, BLUE, GREEN
    }
}
