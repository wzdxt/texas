package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.logic.ImageComparator;
import com.wzdxt.texas.business.display.util.LineUtil;
import com.wzdxt.texas.config.DisplayerConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by dai_x on 17-9-5.
 */
@Slf4j
@Component
public class AnchorMatcherImpl implements AnchorMatcher {
    @Autowired
    private DisplayerConfigure configure;
    @Autowired
    private ImageComparator imageComparator;

    @Override
    public Result match(BufferedImage screen) {
        BufferedImage anchor = AnchorHolder.getAnchor();
        int width = anchor.getWidth();
        int height = anchor.getHeight();
        Result result = new Result();
        result.setMistake((int) 1e10);
        int xTo = screen.getWidth() - width;
        int yTo = screen.getHeight() - height;
        for (int left = 0; left < xTo; left++) {
            for (int top = 0; top < yTo; top++) {
                int mistake = calcMistake(screen.getSubimage(left, top, width, height), anchor);
                if (result.getMistake() > mistake) {
                    result.setX1(left);
                    result.setY1(top);
                    result.setX2(left + width);
                    result.setY2(top + height);
                    result.setMistake(mistake);
                }
            }
        }
        return result;
    }

    /**
     *
     * @param checkImage
     * @param anchor
     * @return mistake sum, not average
     */
    int calcMistake(BufferedImage checkImage, BufferedImage anchor) {
        int mistake = 0;
        for (Map<String, int[]> method : configure.getAnchor().getMethods()) {
            if (method.containsKey("line")) {
                int[] coodinate = method.get("line");
                mistake += imageComparator.calcLineMistake(checkImage, anchor,
                        coodinate[0], coodinate[1], coodinate[2], coodinate[3]);
            } else if (method.containsKey("area")) {
                int[] coordinate = method.get("area");
                mistake += imageComparator.calcLineMistake(checkImage, anchor,
                        coordinate[0], coordinate[1], coordinate[2], coordinate[3]);
            } else if (method.containsKey("horizon")) {
                int[] coodinate = method.get("horizon");
                mistake += imageComparator.calcLineMistake(checkImage, anchor,
                        0, coodinate[0], anchor.getWidth(), coodinate[0]);
            } else if (method.containsKey("vertical")) {
                int[] coodinate = method.get("vertical");
                mistake += imageComparator.calcLineMistake(checkImage, anchor,
                        coodinate[0], 0, coodinate[0], anchor.getHeight());
            }
        }
        return mistake;
    }
}
