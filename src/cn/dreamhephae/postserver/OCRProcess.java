package cn.dreamhephae.postserver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.*;
import net.sourceforge.tess4j.util.*;
public class OCRProcess {

	public static String process(String path) {
		try {
			ITesseract tess = new Tesseract();
			tess.setDatapath("E:\\eclipse-workspace\\PostServer\\tessdata");
			tess.setLanguage("eng");
			File imagefile = new File(path);
			BufferedImage img = ImageIO.read(imagefile); 
			String result = tess.doOCR(img);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return "erreor happend in doOCR";
		}
		
	}
}
