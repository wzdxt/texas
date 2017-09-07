package com.wzdxt.texas.business.display.util;

import cn.easyproject.easyocr.EasyOCR;

import java.awt.image.BufferedImage;

/**
 * Created by dai_x on 17-9-7.
 */
public class OcrUtil {
    static EasyOCR ocr = new EasyOCR();
    static {
        ocr.setTesseractOptions(EasyOCR.OPTION_LANG_ENG);
    }

    public static String recognize(String filepath) {
        return ocr.discern(filepath);
    }
}
