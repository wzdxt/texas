package com.wzdxt.texas.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DisplayerConfigureTest {
    @Autowired
    private DisplayerConfigure configure;

    @Test
    public void test() {
        assertNotNull(configure.getOcrArea().getMyCard()[1].getRank());
//        System.out.println(configure);
    }
}