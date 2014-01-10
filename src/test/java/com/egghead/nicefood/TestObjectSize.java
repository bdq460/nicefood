package com.egghead.nicefood;

import java.lang.instrument.Instrumentation;


/**
 * @author zhangjun.zyk 
 * @since 2013-9-13 下午05:58:36
 * 
 */
public class TestObjectSize {

	private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }
	
	public static void main(String[] args) {
		Object object = new Object();
		System.out.println(instrumentation.getObjectSize(object));
	}
}

