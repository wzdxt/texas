package com.wzdxt.texas.business.display;

import java.awt.image.BufferedImage;

/**
 * Created by dai_x on 17-9-5.
 */
public interface AnchorMatcher {
    Result match(BufferedImage screen);

    class Result {
        int x1, x2, y1, y2;
        double mistake;
    }
}
