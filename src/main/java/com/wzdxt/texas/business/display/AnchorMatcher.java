package com.wzdxt.texas.business.display;

import lombok.Data;

import java.awt.image.BufferedImage;

/**
 * Created by dai_x on 17-9-5.
 */
public interface AnchorMatcher {
    Result match(BufferedImage screen);

    @Data
    class Result {
        int x1, x2, y1, y2;
        int mistake;
    }
}
