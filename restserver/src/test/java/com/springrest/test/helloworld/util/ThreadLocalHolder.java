package com.springrest.test.helloworld.util;

import java.text.SimpleDateFormat;

public class ThreadLocalHolder {
	
	private static ThreadLocal<SimpleDateFormat> threadLocalSimpleDateFormat = new ThreadLocal<SimpleDateFormat>();


	public static SimpleDateFormat getSimpleDateFormat() {
		if(threadLocalSimpleDateFormat.get()==null){
			threadLocalSimpleDateFormat.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		}
		return threadLocalSimpleDateFormat.get();
	}
	
}
