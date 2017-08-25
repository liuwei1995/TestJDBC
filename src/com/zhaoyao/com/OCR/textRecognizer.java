package com.zhaoyao.com.OCR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class textRecognizer{
    
    private String textResult;
    /**
     * 输出的结果
     */
    private final String EOL = System.getProperty("line.separator");
    //回车    
    private String tessPath = "E://anzhuang/tesseract-ocr/Tesseract-OCR";
    //tessocr程序所在目录        
    public  textRecognizer(String path)
    {
        try
        {
            File imagefile = new File(path);
            textResult = this.recognizeText(imagefile);    
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getResult(){
        return textResult;
    }
        
    private String recognizeText(File imageFile) throws Exception
    {
        /**
         * 设置输出文件的保存的文件目录
         */
        File outputFile = new File(imageFile.getParentFile(),"output");
        StringBuffer strB = new StringBuffer();
        
        //设置cmd命令行字符串形式
        List<String> cmd = new ArrayList<String>();        
        cmd.add(tessPath + "\\tesseract");
        cmd.add(imageFile.getName());
        cmd.add(outputFile.getName());
        cmd.add("-l");
        cmd.add("chi_sim");
//        cmd.add("eng");
        
        //启动exe进程
        ProcessBuilder pb = new ProcessBuilder();        
        pb.directory(imageFile.getParentFile());
        pb.command(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        //等待此进程完成
        int w = process.waitFor();
        if (w == 0){// 0代表正常退出        
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath()+ ".txt"),"UTF-8"));
            String str;
            while ((str = in.readLine()) != null)
            {
                strB.append(str).append(EOL);
            }
            in.close();
        } else{
            String msg;
            switch (w){
                case 1:
                    msg = "Errors accessing files. There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recognize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath()+ ".txt").delete();
        /**
         * 如果做验证码
         * return strB.toString().replaceAll("\\s*", "");
         */
        return strB.toString();
    }
    public static void main(String[] args) {
		new textRecognizer("E:/anzhuang/tesseract-ocr/Tesseract-OCR/mypic.bmp");
	}
}