package com.fisc.process;

import org.apache.log4j.Logger;

import com.fisc.socket.Fisc_Server_Send;

public class CallProcess {

	private static Logger log = Logger.getLogger(CallProcess.class);
	
	public static void process(byte[] msgByte){
		log.debug("get: " + new String(msgByte));
		Fisc_Server_Send.sendMsg(msgByte);
	}
}
