package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageCutterTest {
    @Autowired
    ImageCutter imageCutter;

    BufferedImage bi;

    @Before
    public void setUp() throws Exception {
        bi = ImageIO.read(new File("save.bmp"));
    }

    @Test
    public void cutEdge() throws Exception {
        BufferedImage cutEdge = imageCutter.cutEdge(bi);
        TestUtil.save(cutEdge, "cutEdge");
    }

    @Test
    public void cutCharactors() throws Exception {
        BufferedImage bi2 = ImageIO.read(new File(this.getClass().getClassLoader().getResource("image/cutEdge.bmp").getPath()));
        List<BufferedImage> list = imageCutter.cutCharactors(bi2);
        for (int i = 0; i < list.size(); i++) {
            TestUtil.save(list.get(i), String.valueOf(i));
        }
    }

}