package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.business.display.PhaseManager;
import com.wzdxt.texas.business.display.ScreenTestBase;
import com.wzdxt.texas.model.Card;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by dai_x on 17-9-22.
 */
@SpringBootTest
public class RankOcrTest extends ScreenTestBase {
    @Rule
    public ErrorCollector collector = new ErrorCollector();
    @Autowired
    private PhaseManager phaseManager;

    @Test
    public void testR1() throws Exception {
        test("r-1.bmp", new String[]{"♦10", "♥2", "♣5"}, new String[]{});
    }

    @Test
    public void testR2() throws Exception {
        test("r-2.bmp", new String[]{"♥8", "♠A", "♦A", "♦3"}, new String[]{});
    }

    @Test
    public void testR3() throws Exception {
        test("r-3.bmp", new String[]{"♠A", "♣K", "♠K"}, new String[]{});
    }

    @Test
    public void testR4() throws Exception {
        test("r-4.bmp", new String[]{"♣2", "♦Q", "♦5", "♦2"}, new String[]{});
    }

    @Test
    public void testR6() throws Exception {
        test("r-6.bmp", new String[]{"♥7", "♠Q", "♠J", "♦3", "♦7"}, new String[]{});
    }

    @Test
    public void testR8() throws Exception {
        test("r-8.bmp", new String[]{"♦4", "♦6", "♦9", "♦2", "♦7"}, new String[]{});
    }

    @Test
    public void testR9() throws Exception {
        test("r-9.bmp", new String[]{"♥9", "♠8", "♠4"}, new String[]{"♦8", "♦5"});
    }

    @Test
    public void testR10() throws Exception {
        test("r-10.bmp", new String[]{}, new String[]{"♣5", "♠4"});
    }

    @Test
    public void testR11() throws Exception {
        test("r-11.bmp", new String[]{"♣9", "♣J", "♣A", "♦A", "♣4"}, new String[]{"♥10", "♠K"});
    }

    @Test
    public void testR13() throws Exception {
        test("r-13.bmp", new String[]{"♦5", "♦8", "♣A", "♦A", "♦Q"}, new String[]{"♥3", "♣Q"});
    }

    @Test
    public void testR14() throws Exception {
        test("r-14.bmp", new String[]{"♥6", "♣K", "♣2", "♦3", "♣7"}, new String[]{"♠10", "♦9"});
    }

    @Test
    public void testR15() throws Exception {
        test("r-15.bmp", new String[]{"♠3", "♣6", "♠J", "♠K"}, new String[]{"♠A", "♥5"});
    }

    @Test
    public void testR16() throws Exception {
        // ♠ ♣ ♥ ♦
        test("r-16.bmp", new String[]{"♠4", "♣Q", "♦9", "♥9", "♥2"}, new String[]{"♥3", "♥10"});
    }

    @Test
    public void testR17() throws Exception {
        // ♠ ♣ ♥ ♦
        test("r-17.bmp", new String[]{"♦2", "♣10", "♦A", "♦6", "♥9"}, new String[]{"♥K", "♥8"});
    }

    @Test
    public void testR18() throws Exception {
        // ♠ ♣ ♥ ♦
        test("r-18.bmp", new String[]{"♥4", "♣3", "♦J", "♥A"}, new String[]{"♣K", "♥8"});
    }

    @Test
    public void testR19() throws Exception {
        // ♠ ♣ ♥ ♦
        test("r-19.bmp", new String[]{"♦9", "♠A", "♦J"}, new String[]{"♥J", "♠Q"});
    }

    private void test(String pic, String[] common, String[] my) throws Exception {
        switchTo(pic);
        if (common.length > 0) {
            List<Card> list = phaseManager.getCommonCard();
            for (int i = 0; i < common.length; i++) {
                collector.checkThat(list.get(i), equalTo(Card.of(common[i])));
            }
        }
        if (my.length > 0) {
            List<Card> list = phaseManager.getMyCard();
            for (int i = 0; i < my.length; i++) {
                collector.checkThat(list.get(i), equalTo(Card.of(my[i])));
            }
        }
    }

}

/**
 * total: 83
 * tess errors:8
 * calc errors:0!!!!!
 */