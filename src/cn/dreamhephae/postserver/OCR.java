package cn.dreamhephae.postserver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCR {
   static String FindOCR(String srImage) throws TesseractException, IOException {
       
           System.out.println("start");
           double start=System.currentTimeMillis();
           File imageFile = new File(srImage);
           if (!imageFile.exists()) {
               return "Õº∆¨≤ª¥Ê‘⁄";
           }
           BufferedImage textImage = ImageIO.read(imageFile);
           Tesseract instance=Tesseract.getInstance();
           instance.setDatapath("E:\\eclipse-workspace\\PostServer\\tessdata");//…Ë÷√—µ¡∑ø‚
           String result = null;
           result = instance.doOCR(textImage);
           double end=System.currentTimeMillis();
           System.out.println("∫ƒ ±"+(end-start)/1000+" s");
           return result;
       
   }
//   public static void main(String[] args) throws Exception {
//       String result=FindOCR("E:\\eclipse-workspace\\PostServer\\cache\\20171220161953.jpg");
//       System.out.println(result);
//   }
}
