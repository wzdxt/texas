package com.wzdxt.texas.business.display.util;

import cn.easyproject.easyocr.EasyOCR;

import java.awt.image.BufferedImage;

/**
 * Created by dai_x on 17-9-7.
 */
public class OcrUtil {
    static EasyOCR ocr = new EasyOCR();

    static {
        ocr.setTesseractOptions(EasyOCR.OPTION_LANG_CHI_SIM);
    }

    synchronized
    public static String recognize(String filepath, String options) {
        try {
            options = "-l texas -psm 7";  // 6 or 7
            options += " texas";
            ocr.setTesseractOptions(options);
            return ocr.discern(filepath);
        } finally {
            ocr.setTesseractOptions(EasyOCR.OPTION_LANG_CHI_SIM);
        }
    }

    public static String recognize(BufferedImage image, String options) {
        return (String)TempFileUtil.withTempFile(image, filePath -> recognize(filePath, options));
    }

    public static String recognize(BufferedImage image) {
        return (String)TempFileUtil.withTempFile(image, filePath -> recognize(filePath, null));
    }
}

