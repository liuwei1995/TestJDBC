package jar;

import jar.SyncPipe.interfaceSyncPipe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * OCR BOX 	文件合并 
 * @author lw
 * 编写时间: 2016年10月20日下午3:17:44
 * 类说明 :
 */
public class OCRMergeBOX {
//	创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。示例代码如下：
	public static final Map<String, String> mapfont_properties = new HashMap<String, String>();
	private static Map<Object, File> mapBox = new HashMap<Object, File>();
	private static Map<Object, File> mapTr = new HashMap<Object, File>();
	private String lang = "chi_sim";
	private static Map<Object, File> mapImag = new HashMap<Object, File>();
	private static File dataDir = null;//"C:/Users/dell/Desktop/OCR项目/ceshi";
	private static File[] dataDirShuZu = null;//"C:/Users/dell/Desktop/OCR项目/ceshi";
	private static File canDir = null;// "C:/Users/dell/Desktop/OCR项目/can";
	private boolean isCopy = false;
	private boolean isLog = false;
	private interfaceSyncPipe pipe;
	private int type_;
	/**
	 * 
	 * @param filePath
	 * @param type  1  box  2  tr
	 */
	public OCRMergeBOX(int type,String ...filePath) {
		if(filePath == null || filePath.length <= 0)return;
		dataDirShuZu = new File[filePath.length];
		for (int i = 0; i < filePath.length; i++) {
			dataDirShuZu[i] = new File(filePath[i]);
		}
		for (File f : dataDirShuZu) {
			if(f.exists() && f.isDirectory())
			{
				dataDir = dataDirShuZu[0];
			}else {
				System.err.println("文件夹："+f.getPath()+"  不存在");
			}
		}
		if(dataDir == null){
			System.err.println("要合并的文件夹不存在");
			return;
		}
		if((type_= type) == 2){
//			copyTrFile();
		}else if ((type_= type) == 1) {
			copyBoxFile();
		}
	}
	public OCRMergeBOX(int type,java.util.List<File> filePath) {
		if(filePath == null || filePath.isEmpty())return;
		dataDirShuZu = new File[filePath.size()];
		for (int i = 0; i < filePath.size(); i++) {
			dataDirShuZu[i] = filePath.get(i);
		}
		for (File f : dataDirShuZu) {
			if(f.exists() && f.isDirectory())
			{
				dataDir = f;
				break;
			}else {
				System.err.println("文件夹："+f.getPath()+"  不存在");
			}
		}
		if(dataDir == null){
			System.err.println("要合并的文件夹不存在");
			return;
		}
		if((type_= type) == 2){
			copyTrFile();
		}else if ((type_= type) == 1) {
			copyBoxFile();
		}
	}
	public OCRMergeBOX(int type,File outPath,java.util.Map<String, File> mapAddCreateBOXPath) {
		if(mapAddCreateBOXPath == null || mapAddCreateBOXPath.isEmpty())return;
		dataDirShuZu = new File[mapAddCreateBOXPath.size()];
		int i = 0;
		for (String key : mapAddCreateBOXPath.keySet()) {
			dataDirShuZu[i] = new File(key);
			++i;
		}
		if(outPath == null || !outPath.exists() || !outPath.isDirectory())
		for (File f : dataDirShuZu) {
			if(f.exists() && f.isDirectory())
			{
				dataDir = f;
				break;
			}else {
				System.err.println("文件夹："+f.getPath()+"  不存在");
			}
		}
		else dataDir = outPath;
		if(dataDir == null){
			System.err.println("要合并的文件夹不存在");
			return;
		}
		if((type_= type) == 2){
			copyTrFile();
		}else if ((type_= type) == 1) {
			copyBoxFile();
		}
	}
	public OCRMergeBOX(int type,File filePath) {
		this(type,filePath.getPath());
	}
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public boolean isLog() {
		return isLog;
	}
	public void setLog(boolean isLog) {
		this.isLog = isLog;
	}
	private  void copyBoxFile() {
		Map<Object, File> box = new HashMap<Object, File>();
		Map<Object, File> Imag = new HashMap<Object, File>();
		for (File f : dataDirShuZu) {
			if(!f.exists() || !f.isDirectory()){
				System.err.println("文件夹："+f.getPath()+"  不存在");
				continue;
			}
			for (File file : f.listFiles())  
			{  
				if(!file.isFile())continue;
				String name = file.getName();
				if(name.indexOf(".") == -1)continue;
				String substring = name.substring(name.lastIndexOf(".")+1, name.length());
				String first = name.substring(0, name.lastIndexOf("."));
				if(substring != null && (substring.equals("png") || substring.equals("jpg") || substring.equals("bmp") || substring.equals("tif") || substring.equals("box"))){
					if(substring.equals("box")){
						box.put(first, file);
					}else {
						Imag.put(first, file);
					}
				}
			}  
		}
		 for (Object key : box.keySet()) {
			if(!Imag.containsKey(key))
			{
				System.out.println(box.get(key).getParent()+"   没有 "+key+"图片");
				continue;
			}
			mapBox.put(key, box.get(key));
//				throw new NullPointerException(mapBox.get(key).getParent()+"   没有 "+key+"图片");
		}
		 for (Object key : Imag.keySet()) {
			 if(!box.containsKey(key))
				 {
				 System.out.println(Imag.get(key).getParent()+"   没有 "+key+".box 文件");
				 continue ;
				 }
			 mapImag.put(key, Imag.get(key));
//			 throw new NullPointerException(mapImag.get(key).getParent()+"   没有 "+key+".box 文件");
		 }
		 if(mapBox.isEmpty() || mapImag.isEmpty())
		 {
			 System.out.println(("box or image isEmpty"));
			 return ;
		 }
//			 throw new NullPointerException("box or image isEmpty");
		 if(mapBox.size() != mapImag.size()){
			 System.out.println(("box image 数量不相等"));
//			 throw new NumberFormatException("box image 数量不相等");
			 return;
		 }
		 File canDirectory = new File(dataDir, "."+dataDir.getName());
		 if(!canDirectory.exists() || !canDirectory.isDirectory())
		 canDirectory.mkdirs();
		 for (Object key : box.keySet()) {
			 File from = box.get(key);
			 File to = new File(canDirectory,from.getName());
			 if(to.exists() && to.isFile())continue;
			 try {
				 FileUtils.copyFile(from, to);
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 for (Object key : Imag.keySet()) {
			 File from = Imag.get(key);
			 File to = new File(canDirectory,from.getName());
			 if(to.exists() && to.isFile())continue;
			 try {
				 FileUtils.copyFile(from, to);
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 canDir = canDirectory;
		 isCopy = true;
	}
	private  void copyTrFile() {
		for (File f : dataDirShuZu) {
			for (File file : f.listFiles())  
			{  
				if(!file.isFile())continue;
				String name = file.getName();
				if(name.indexOf(".") == -1)continue;
				String substring = name.substring(name.lastIndexOf(".")+1, name.length());
				String first = name.substring(0, name.lastIndexOf("."));
				if(substring != null && (substring.equals("tr") || substring.equals("box"))){
					if(substring.equals("box")){
						mapBox.put(first, file);
					}else {
						mapTr.put(first, file);
					}
				}
			}  
		}
		if(mapTr.isEmpty())throw new NullPointerException(".tr isEmpty");
		if(mapBox.isEmpty() || mapTr.isEmpty())throw new NullPointerException("box or Tr isEmpty");
		if(mapBox.size() != mapTr.size())throw new NumberFormatException("box tr 数量不相等");
		for (Object key : mapBox.keySet()) {
			if(!mapTr.containsKey(key)){
				System.err.println("box tr 名字前缀存在不匹配");
				mapBox.remove(key);
			}
		}
		for (Object key : mapTr.keySet()) {
			if(!mapBox.containsKey(key))
				{
				System.err.println("box tr 名字前缀存在不匹配");
				mapTr.remove(key);
				}
		}
		if(mapBox.isEmpty() || mapTr.isEmpty())throw new NullPointerException("box or Tr isEmpty");
		File canDirectory = new File(dataDir, "."+dataDir.getName());
		if(!canDirectory.exists() || !canDirectory.isDirectory())
			canDirectory.mkdirs();
		for (Object key : mapBox.keySet()) {
			File from = mapBox.get(key);
			File to = new File(canDirectory,from.getName());
			if(to.exists() && to.isFile())continue;
			try {
				FileUtils.copyFile(from, to);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (Object key : mapTr.keySet()) {
			File from = mapTr.get(key);
			File to = new File(canDirectory,from.getName());
			if(to.exists() && to.isFile())continue;
			try {
				FileUtils.copyFile(from, to);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		canDir = canDirectory;
		isCopy = true;
	}
	/**
	 * 1、先生成相对应的 .tr 文件   chi_sim.font.exp0.box  fuhao.arial.exp0.box
	 */
	public void start() {
		if(!isCopy)throw new NullPointerException("  文件没有拷贝完成");
		 String[] command =  
			{  
				"cmd",  
			}; 
		 Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		 PrintWriter stdin = null;
		 
		try {
			stdin = new PrintWriter(new OutputStreamWriter(p.getOutputStream(),"GBK"));
			pipe = new SyncPipe.interfaceSyncPipe() {
				@Override
				public void ok(boolean isOk) {
					isOk_ = isOk;
				}
			};
			if(isLog){
				new SyncPipe(p.getErrorStream(),pipe,-1,"GBK").start();
				new SyncPipe(p.getInputStream(),pipe,1,"GBK").start();
			}
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		} 
		File traineddata = new File(canDir,lang+".traineddata");
		if(traineddata.exists())traineddata.renameTo(new File(canDir,lang+"(旧"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+").traineddata"));
		cmd(stdin,p,"cd\\");
		cmd(stdin,p,""+canDir.getPath().charAt(0)+":");
		 String[] split = canDir.getPath().toString().replace("\\", "__").split("__");
		 for (int i = 1; i < split.length; i++) {
//			 切换到工作目录
			 cmd(stdin,p,"cd "+split[i]);
		}
		 if(type_ == 1)
		 for (Object key : mapImag.keySet()) {
			 File file = new File(canDir,key+".tr");
			 if(!file.exists() || !file.isFile()){
//				 1、先生成相对应的 .tr 文件   chi_sim.font.exp0.box  fuhao.arial.exp0.box
//					tesseract chi_sim.font.exp0.png chi_sim.font.exp0 nobatch box.train  
//					tesseract fuhao.arial.exp0.tif fuhao.arial.exp0 nobatch box.train 
				 String cmd = "tesseract "+mapImag.get(key).getName()+" "+key+" nobatch box.train";
				 cmd(stdin,p,cmd);
			 }
		}
//		 2从所有文件中提取字符unicharset_extractor chi_sim.font.exp0.box fuhao.arial.exp0.box  
		 File chi_simUnicharset = new File(canDir,lang+".unicharset");
		 if(!chi_simUnicharset.exists() || !chi_simUnicharset.isFile()){
			 File unicharset = new File(canDir,"unicharset");
			 if(!unicharset.exists() || !unicharset.isFile()){
				 String unicharset_extractor = "unicharset_extractor ";
				 for (Object key : mapBox.keySet()) {
					 unicharset_extractor += " "+mapBox.get(key).getName();
				 }
				 cmd(stdin,p,unicharset_extractor);
			 }
		 }
		 try {
			 File file = new File(System.getProperty("user.dir")+"/src/font_properties");
			 FileInputStream fis = new FileInputStream(file);
			 BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			 String s = "";
			 while ((s = br.readLine()) != null) {
				 s = s.trim();
				 char[] charArray = s.toCharArray();
				 for (int i = 0; i < charArray.length; i++) {
					if(charArray[i] == ' ' || charArray[i] == '\t'){
						mapfont_properties.put(s.substring(0,i).trim(), s);
						break;
					}
				}
			}
			 fis.close();
			 br.close();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
//		 3、生成字体特征文件
//		 新建的font_properties文件中 把所有box文件对应的 字体特征都加进去
//		 font 0 0 0 0 0
//		 arial 0 0 0 0 0
		 StringBuffer font_properties = new StringBuffer();
		 File file_font_properties = new File(canDir,"font_properties");
		 for (Object key : mapBox.keySet()) {
			 String name = mapBox.get(key).getName();
			 String[] sp = name.replace(".", "_____").split("_____");
			 if(file_font_properties.exists() && file_font_properties.isFile())break;
			 if(mapfont_properties.containsKey(sp[1])){
				 font_properties.append(mapfont_properties.get(sp[1])+"\n");
			 }else
			 font_properties.append(sp[1]+" 0 0 0 0 0\n");
		 } 
		 if(!file_font_properties.exists() && !file_font_properties.isFile())
		 {
			 FileOutputStream fos = null;
			 try {
				 fos = new FileOutputStream(file_font_properties);
				 byte[] bytes = font_properties.toString().getBytes();
				 fos.write(bytes, 0, bytes.length);
				 fos.close();
			 } catch (FileNotFoundException e1) {
				 e1.printStackTrace();
			 } catch (IOException e1) {
				 e1.printStackTrace();
			 }finally{
				 if(fos != null)
					 try {
						 fos.close();
					 } catch (IOException e) {
						 e.printStackTrace();
					 }
			 }
		 }
		 String shapeclustering = "shapeclustering -F font_properties -U unicharset ";
		 File chi_simShapetable = new File(canDir,lang+".shapetable");
		 if(!chi_simShapetable.exists() || !chi_simShapetable.isFile()){
			 File shapetable = new File(canDir,"shapetable");
			 if(!shapetable.exists() || !shapetable.isFile()){
				 for (Object key : mapBox.keySet()) {
					 shapeclustering += " "+key+".tr";
				 }
				 cmd(stdin,p,shapeclustering);
			 }
		 }
//		 mftraining -F font_properties -U unicharset -O chi_sim.unicharset chi_sim.font.exp0.tr fuhao.arial.exp0.tr
		 String mftraining = "mftraining -F font_properties -U unicharset -O "+lang+".unicharset ";
		 File chi_sim_unicharset = new File(canDir,lang+".unicharset");
		 if(!chi_sim_unicharset.exists() || !chi_sim_unicharset.isFile()){
			 for (Object key : mapBox.keySet()) {
				 mftraining += " "+key+".tr";
			 }
			 cmd(stdin,p,mftraining);
		 }
		 
//		 4 、聚集所有.tr 文件 cntraining chi_sim.font.exp0.tr fuhao.arial.exp0.tr
		 File chi_sim_normproto = new File(canDir,lang+".normproto");
		 if(!chi_sim_normproto.exists() || !chi_sim_normproto.isFile()){
			 String tr = "cntraining ";
			 for (Object key : mapBox.keySet()) {
				 tr += " "+key+".tr";
			 }
			 cmd(stdin,p,tr);
		 }
//		 6 、重命名文件，我把unicharset, inttemp, normproto, pfftable 这几个文件加了前缀chi_sim.
		 File chi_sim_shapetable = new File(canDir,lang+".shapetable");
		 if(!chi_sim_shapetable.exists() || !chi_sim_shapetable.isFile())
			 cmd(stdin,p,"rename shapetable "+lang+".shapetable");
		 File normproto = new File(canDir,lang+".normproto");
		 if(!normproto.exists() || !normproto.isFile())
			 cmd(stdin,p,"rename normproto "+lang+".normproto");
		 File inttemp = new File(canDir,lang+".inttemp");
		 if(!inttemp.exists() || !inttemp.isFile())
			 cmd(stdin,p,"rename inttemp "+lang+".inttemp");
		 File pffmtable = new File(canDir,lang+".pffmtable");
		 if(!pffmtable.exists() || !pffmtable.isFile())
			 cmd(stdin,p,"rename pffmtable "+lang+".pffmtable");
		 
//		 7、合并所有文件 生成一个大的字库文件combine_tessdata chi_sim.
		 cmd(stdin,p,"combine_tessdata "+lang+".");
		 cmd(stdin,p,"dir");
		 stdin.close();
		 System.out.println(sb.toString());
		synchronized (pipe) {
			try {
				File from = new File(canDir,lang+".traineddata");
				while (!from.exists() || !from.isFile()) {
					try {
						pipe.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				File to = new File(dataDir,lang+".traineddata");
				if(to.exists() && to.isFile())to = new File(dataDir,lang+"(副本"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+").traineddata");
				try {
					FileUtils.copyFile(from, to);
				} catch (IOException e) {
					e.printStackTrace();
				}
				for (File file : canDir.listFiles()) {
					String name = file.getName();
					if(name.indexOf(".") == -1)continue;
					int lastIndexOf = name.lastIndexOf(".")+1;
					if(name.substring(lastIndexOf).equals("tr")
							||name.substring(name.lastIndexOf(".")+1).equals("traineddata")){
						continue;
					}
					file.delete();
				}
				System.out.println("合并完成");
				System.out.println("输入命令:");
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				pipe.notifyAll();
			}
		}
	}
	boolean dir = false;
	boolean isOk_ = true;
	private StringBuffer sb = new StringBuffer();
	
	public void cmd(final PrintWriter stdin,Process p,final String cmd) {
		synchronized (pipe) {
			try {
				while (!isOk_) {
					pipe.wait();
					continue;
				}
//				isOk_ = false;
				stdin.println(cmd);
				stdin.flush();
				System.out.println(cmd);
				sb.append(cmd+"\t\n");
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				pipe.notifyAll();
			}
		}
	}
}
