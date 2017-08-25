package jar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessOutputThread extends Thread
{
    private InputStream is;
    private List<String> outputList;
    public ProcessOutputThread(InputStream is) throws IOException
    {
        if(null == is)
        {
            throw new IOException("the provided InputStream is null");
        }
        this. is = is;
        this.outputList = new ArrayList<String>();
    }
    public List<String> getOutputList()
    {
        return this. outputList;
    }
    @Override
    public void run()
    {
        InputStreamReader ir = null;
        BufferedReader br = null;
        try
        {
            ir = new InputStreamReader(this.is);
            br = new BufferedReader(ir);
            String output = null;
            while(null != (output = br.readLine()))
            {
                System.out.println(output);
                this.outputList.add(output);
            }
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }
        finally
        {
            try
            {
                if(null != br)
                {
                    br.close();
                }
                if(null != ir)
                {
                    ir.close();
                }
                if(null != this.is)
                {
                    this.is.close();
                }
            }
            catch(IOException e)
            {
                System.out.println(e.toString());
            }
        }
    }
}
