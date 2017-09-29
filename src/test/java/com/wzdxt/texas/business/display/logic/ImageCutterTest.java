package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.TestBase;
import com.wzdxt.texas.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/6.
 */
public class ImageCutterTest extends TestBase {
    @Autowired
    ImageCutter imageCutter;

    @Test
    public void cutEdge() throws Exception {
        BufferedImage bi = ImageIO.read(this.getClass().getResourceAsStream("/image/save.bmp"));
        BufferedImage cutEdge = imageCutter.cutEdge(bi);
        TestUtil.save(cutEdge, "cutEdge");
    }

    @Test
    public void cutCharacters() throws Exception {
        BufferedImage bi2 = ImageIO.read(this.getClass().getResourceAsStream("image/cutEdge.bmp"));
        List<BufferedImage> list = imageCutter.cutCharacters(bi2);
        for (int i = 0; i < list.size(); i++) {
            TestUtil.save(list.get(i), String.valueOf(i));
        }
    }

}