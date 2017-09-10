package com.wzdxt.texas.business.display.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-7.
 */
@Slf4j
public class OcrUtilTest {
    @Test
    public void recognize() throws Exception {
        log.info("result");
        log.info(OcrUtil.recognize(this.getClass().getClassLoader().getResource("image/save.bmp").getPath(), null));
    }

}