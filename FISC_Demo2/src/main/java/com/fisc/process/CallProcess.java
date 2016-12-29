package com.fisc.process;

import org.apache.log4j.Logger;

public class CallProcess {

	private static Logger log = Logger.getLogger(CallProcess.class);
	
	public static void process(byte[] msgByte){
		log.debug("get: " + new String(msgByte));
		
	}
}
