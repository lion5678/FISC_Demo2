package com.fisc.process;

import java.net.Socket;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.fisc.socket.Fisc_Server_Rcv;
import com.fisc.socket.Fisc_Server_Send;

public class CallProcess {

	private static Logger log = Logger.getLogger(CallProcess.class);
	
	public static void process(Socket connSend, byte[] inMsgByte){
		log.debug("get: " + new String(inMsgByte));
		
		new DecimalFormat("0000").format(inMsgByte.length);
		String.format("%04d", inMsgByte.length);
		
		byte[] outMsgByte = new byte[inMsgByte.length+4];
		String.format("%04d", inMsgByte.length);
		
		System.arraycopy(String.format("%04d", inMsgByte.length).getBytes(), 0, outMsgByte, 0, 4);
		System.arraycopy(inMsgByte, 0, outMsgByte, 4, inMsgByte.length);
		System.out.println(String.format("%04d", inMsgByte.length).getBytes().length);
		
		System.out.println(new String(outMsgByte));
		Fisc_Server_Rcv.sendMsg(connSend, outMsgByte);
	}
	
	
}
