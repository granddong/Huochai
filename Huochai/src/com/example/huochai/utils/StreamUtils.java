package com.example.huochai.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ��ȡ���Ĺ���
 * ����������ȡ��String�󷵻�
 * @author dhw
 *
 */
public class StreamUtils {

	public static String readFormStream(InputStream in) throws IOException
	{
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		int length=0;
		byte[] buffer=new byte[1024];
		while((length=in.read(buffer))!=-1 )
		{
			out.write(buffer,0,length);
		}
		String result=out.toString();
		in.close();
		out.close();
		return result;
	}
}
