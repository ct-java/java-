package com.common.utils;

public class StringUtils {

	public static long[] stringToLong(String ids)
	{
		String[] idss=ids.split(",");
		long[] idds=new long[idss.length];
		for(int i=0;i<idss.length;i++){
			idds[i]=Long.parseLong(idss[i]);
		}
		return idds;
	}
}
