package com.wzdxt.texas.business.master;

import com.wzdxt.texas.TestBase;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CommonCard;
import com.wzdxt.texas.model.MyCard;
import org.junit.Ignore;
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
@Ignore
public class TexasMasterTestBase extends TestBase {
    @Rule
    public ErrorCollector collector = new ErrorCollector();
    @Autowired
    protected TexasMaster texasMaster;

    protected void test(String myStr, String commonStr, MasterDecision expected) {
        MyCard my = new MyCard();
        CommonCard common = new CommonCard();
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