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

    synchronized
    public static String recognize(String filepath, String options) {
        try {
            ocr.setTesseractOptions(options == null ? EasyOCR.OPTION_LANG_ENG : options);
            return ocr.discern(filepath);
        } finally {
            ocr.setTesseractOptions(EasyOCR.OPTION_LANG_ENG);
        }
    }

    public static String recognize(BufferedImage image, String options) {
        return (String)TempFileUtil.withTempFile(image, filePath -> recognize(filePath, options));
    }

    public static String recognize(BufferedImage image) {
        return (String)TempFileUtil.withTempFile(image, filePath -> recognize(filePath, null));
    }
}

