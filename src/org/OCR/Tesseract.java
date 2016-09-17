package org.OCR;

import java.io.File;
import net.sourceforge.tess4j.*;

public class Tesseract {

    public String check() {
        File imageFile = new File("/home/vindula/Medical-Portal/web/images/test.png");
        ITesseract instance = new net.sourceforge.tess4j.Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        instance.setDatapath("/home/vindula/Medical-Portal/tessdata");


        try {
            String result = instance.doOCR(imageFile);
            return result;
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }
}