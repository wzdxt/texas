package com.wzdxt.texas.business.display;

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
public class DisplayerImplTest {
    @Autowired
    DisplayerImpl displayer;
    @Test
    public void matchAnchor() throws Exception {
        displayer.matchAnchor();
    }

}