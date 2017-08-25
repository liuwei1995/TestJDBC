package jar;

import java.io.InputStream;
/**
 * 控制台输出日志
 * @author lw
 * 编写时间: 2016年10月19日下午2:45:45
 * 类说明 :
 */
public class SyncPipe extends Thread
{
	public interface interfaceSyncPipe{
		void ok(boolean isOk);
	}
    public SyncPipe(InputStream istrm,interfaceSyncPipe iPipe,int type,String charset) {
        istrm_ = istrm;
        type_ = type;
        iPipe_ = iPipe;
        if(charset != null && !"".equals(charset))
        charset_ = charset;
    }
    public void run() {
    	try{
    		final byte[] buffer = new byte[1024];
    		if(type_ < 0){
    			synchronized (iPipe_) {
    			iPipe_.ok(true);
    			iPipe_.notifyAll();
    			}
    			try {
					for (int length = 0; (length = istrm_.read(buffer)) != -1;){
						String str1 = new String(buffer, charset_);//to UTF-8 string  
						System.err.println(str1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}else {
    				try {
						String s  = "";
						for (int length = 0; (length = istrm_.read(buffer)) != -1;){
							String str1 = new String(buffer, charset_);//to UTF-8 string 
							s+=str1;
						}
						System.out.println(s);
						synchronized (iPipe_) {
		    				iPipe_.ok(true);
		    				iPipe_.notifyAll();
		    			}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
        }
        catch (Exception e) {
            throw new RuntimeException("处理命令出现错误："+e.getMessage());
        }
    }
    private final interfaceSyncPipe iPipe_;
	private final int type_;
	private final InputStream istrm_;
	private  String charset_ = "GBK";
//	private  String charset_ = "UTF-8";
	public String getCharset_() {
		return charset_;
	}
	public void setCharset_(String charset_) {
		this.charset_ = charset_;
	}
	
}