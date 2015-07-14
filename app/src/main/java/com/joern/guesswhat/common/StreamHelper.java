package com.joern.guesswhat.common;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by joern on 10.07.2015.
 */
public class StreamHelper {


    public static void copyStream(InputStream in, OutputStream out)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=in.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                out.write(bytes, 0, count);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}