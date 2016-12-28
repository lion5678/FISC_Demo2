package com.fisc.demo;

import org.apache.log4j.Logger;

public class CallProcess {

	private static Logger log = Logger.getLogger(CallProcess.class);
	
	public static void process(String msg){
		log.debug("get: " + msg);
	}
}
