package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.business.display.util.OcrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

/**
 * Created by dai_x on 17-9-22.
 */
@Component
public class RankTessOcr {
    @Autowired
    ImageComparator imageComparator;
    @Autowired
    ImageCutter imageCutter;

    public String ocr(BufferedImage bi) {
        int background = imageComparator.getBackgroundRgb(bi);
        bi = imageCutter.cutEdge(bi);
        if (bi != null)
            imageCutter.digCharacterInner(bi, background);
        return bi == null ? null : OcrUtil.recognize(bi, "-l texas-rank -psm 10");
    }
}
