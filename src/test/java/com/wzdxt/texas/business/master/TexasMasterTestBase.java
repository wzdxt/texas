package com.wzdxt.texas.business.master;

import com.wzdxt.texas.model.Card;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by dai_x on 17-9-19.
 */
public class TexasMasterTestBase {
    @Rule
    public ErrorCollector collector = new ErrorCollector();
    @Autowired
    protected TexasMaster texasMaster;

    protected void test(String myStr, String commonStr, MasterDecision expected) {
        List<Card> my = new ArrayList<>(myStr.length() / 2);
        List<Card> common = new ArrayList<>(commonStr.length() / 2);
        int step;
        for (int i = 0; i < myStr.length(); i+=step) {
            step = 2;
            if (myStr.charAt(i+1) == '1')
                step = 3;
            my.add(Card.of(myStr.substring(i, i+step)));
        }
        for (int i = 0; i < commonStr.length(); i+=step) {
            step = 2;
            if (commonStr.charAt(i+1) == '1')
                step = 3;
            common.add(Card.of(commonStr.substring(i, i+step)));
        }
        collector.checkThat(texasMaster.suggest(my, common), equalTo(expected));
    }

}