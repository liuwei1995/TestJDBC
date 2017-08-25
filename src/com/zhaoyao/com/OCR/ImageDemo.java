package com.zhaoyao.com.OCR;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageDemo {

	public void binaryImage(File file) throws IOException {
		BufferedImage image = ImageIO.read(file);

		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage grayImage = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_BINARY);// 重点，技巧在这个参数BufferedImage.TYPE_BYTE_BINARY
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}
		String fileName = file.getName();
		File newFile = new File(file.getParent(), fileName);
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		ImageIO.write(grayImage, prefix, newFile);
	}

	/**
	 * 变灰色
	 * 
	 * @throws IOException
	 */
	public void grayImage(File file) throws IOException {
		// File file = new
		// File(System.getProperty("user.dir")+"/src/1chi_sim.font.exp0.png");
		BufferedImage image = ImageIO.read(file);

		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage grayImage = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);// 重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}
		String fileName = file.getName();
		File newFile = new File(file.getParent(), fileName);
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		ImageIO.write(grayImage, prefix, newFile);
	}

	public static void main(String[] args) throws IOException {
		ImageDemo demo = new ImageDemo();
		// demo.binaryImage();
		String fil = "C:/Users/dell/Desktop/注册资料/chi_sim.font.exp0.jpg";
		demo.grayImage(new File(fil));
	}

}
