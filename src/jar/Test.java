package jar;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Test {
	public static void main(String[] args) {
		try {
			List<String> readLines = FileUtils.readLines(new File("C:/Users/dell/Desktop/中国汉字大全.txt"));
			System.out.println("============"+readLines.toString());
			List<String> list = FileUtils.readLines(new File("E:/anzhuang/tesseract-ocr/Tesseract-OCR/tesseract/.text"));
			System.out.println(list.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
