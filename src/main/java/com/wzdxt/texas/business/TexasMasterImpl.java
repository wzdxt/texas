package com.wzdxt.texas.business;

import com.wzdxt.texas.Configuration;
import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.service.Calculator;
import com.wzdxt.texas.service.CalculatorFactory;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/3.
 */
public class TexasMasterImpl implements TexasMaster {
    @Autowired
    Configuration configuration;
    Beginner beginner = new Beginner();

    @Override
    public MasterResult suggest(Collection<Card> my, Collection<Card> common) {
        if (common.isEmpty()) {
            Iterator<Card> iter = my.iterator();
            BeginnerResult result = beginner.evaluate(iter.next(), iter.next());
            switch (result.level) {
                case RED:
                    return MasterResult.BET_TWO_FIVE;
                case YELLOW:
                    return MasterResult.CALL_TWO;
                case BLUE:
                case GREEN:
                    return MasterResult.CALL_ONE;
            }
        } else {
            Calculator calc = CalculatorFactory.getCalculator(common);
            List<Double> possibility = calc.calculate(my, common);
            if (common.size() == 3) {
            } else if (common.size() == 4) {
            } else if (common.size() == 5) {
            }
        }
    }

    private Double getPossibility(List<Double> poss, int check) {
        check = 100 - check;
        int idx = poss.size() * check / 100;
        return poss.get(idx);
    }

    private class Beginner {
        BeginnerResult evaluate(Card c1, Card c2) {
            if (c1.compareTo(c2) > 0) {
                Card t = c1;
                c1 = c2;
                c2 = t;
            }
            int score = c1.getRank() + c2.getRank();
            if (c1.compareTo(c2) == 0) {
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
