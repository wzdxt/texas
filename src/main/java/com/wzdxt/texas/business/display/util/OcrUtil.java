package com.wzdxt.texas.business.display.util;

import cn.easyproject.easyocr.EasyOCR;
import cn.easyproject.easyocr.ImageType;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by dai_x on 17-9-7.
 */
@Slf4j
public class OcrUtil {
    static EasyOCR ocr = new EasyOCR();

    static {
        ocr.setTesseractOptions(EasyOCR.OPTION_LANG_CHI_SIM);
    }

    synchronized
    public static String recognize(String filepath, String options) {
        try {
            if (options == null)
                options = "-l texas -psm 7";  // 6 or 7
            ocr.setTesseractOptions(options);
            String s = ocr.discern(filepath);
            log.debug("Ocr result: {}", s);
//            try {
//                Charset cs = Charset.defaultCharset();
//                if (!cs.toString().equalsIgnoreCase("UTF-8")) {
//                    s = new String(s.getBytes(cs), "utf-8");
//                    log.info("Encoding is {}, convert to UTF-8, result: {}", cs, s);
//                }
//            } catch (UnsupportedEncodingException ignored) {
//            }
            return s;
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

