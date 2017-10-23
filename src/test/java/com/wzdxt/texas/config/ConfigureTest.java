package com.wzdxt.texas.config;

import com.wzdxt.texas.TestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-5.
 */
public class ConfigureTest extends TestBase {
    @Autowired
    private DisplayerConfigure displayerConfigure;
    @Autowired
    private MasterConfigure masterConfigure;

    @Test
    public void test() {
        assertNotNull(displayerConfigure.getOcrArea().getMyCard()[1].getRank());
        assertNotNull(displayerConfigure.getOcrPix().get("2")[1]);
        assertNotNull(masterConfigure.getRiver()[0].decides[0].bet);
        assertNotNull(displayerConfigure.getOperation().get("check_or_call").get(0).getCheck().get(0).getPoint());
    }
}