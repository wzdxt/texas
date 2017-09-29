package com.wzdxt.texas.business.display;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/8.
 */
public class AnchorHolderTest {
    @Test
    public void getAnchor() throws Exception {
        BufferedImage bi = AnchorHolder.getAnchor();
        assertEquals(1088, bi.getWidth());
        assertEquals(614, bi.getHeight());
    }

    @Test
    public void testAnchorTransform() throws Exception {
        File f = new File(AnchorHolder.class.getClassLoader().getResource("static/anchor.bmp").getPath());
        BufferedImage biRobot = ImageIO.read(f);
        f = new File(AnchorHolder.class.getClassLoader().getResource("static/screen/1.PNG").getPath());
        BufferedImage biWinCap = ImageIO.read(f);
        BufferedImage biTrans = transform(biWinCap);
        assertEquals(biRobot.getType(), biTrans.getType());
        assertEquals(biRobot.getColorModel().getPixelSize(), biTrans.getColorModel().getPixelSize());
//        assertEquals(biRobot.getRGB(10, 2), biTrans.getRGB(10, 2));
        ImageIO.write(biTrans, "bmp", new File(AnchorHolder.class.getClassLoader().getResource("static/train").getPath() + "/1.bmp"));
    }

    BufferedImage transform(BufferedImage sourceImg) throws Exception {
        int h = sourceImg.getHeight(), w = sourceImg.getWidth();
        int[] pixel = new int[w * h];
        PixelGrabber pixelGrabber = new PixelGrabber(sourceImg, 0, 0, w, h, pixel, 0, w);
        pixelGrabber.grabPixels();

        MemoryImageSource m = new MemoryImageSource(w, h, pixel, 0, w);
        Image image = Toolkit.getDefaultToolkit().createImage(m);
        BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        buff.createGraphics().drawImage(image, 0, 0, null);
        return buff;
    }

}