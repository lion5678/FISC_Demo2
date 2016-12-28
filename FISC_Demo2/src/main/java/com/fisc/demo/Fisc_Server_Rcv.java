package com.fisc.demo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.net.SocketServer;

public class Fisc_Server_Rcv extends Thread{

	private static Logger log = Logger.getLogger(Fisc_Server_Rcv.class);
	private int portNum;
	private String addr;
	ServerSocket server;
	
	public Fisc_Server_Rcv() {
		Properties cfg = new Properties();
		try {
			cfg.loadFromXML(this.getClass().getResourceAsStream("/fisc_cfg.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			this.portNum = Integer.parseInt(cfg.getProperty("RcvPort"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		log.debug("Fisc_Server start...");
		this.addr = cfg.getProperty("RcvAddr");
		log.debug("addr: " + addr + ", portNum: " + portNum);
	}
	
	@Override
	public void run() {
		super.run();
		try {
			server = new ServerSocket(portNum);
			log.debug("等待連線中...");
			while (true) {
				Socket conn = server.accept();
				log.debug(conn.getRemoteSocketAddress().toString()+" 已連線...");
				DataInputStream dis = new DataInputStream(conn.getInputStream());
//				byte[] c = new byte[4];
//				dis.read(c);
//				log.debug("dataLen: "+dataLen);
				byte[] b = new byte[8];
				StringBuffer data = new StringBuffer();
				int len;
				int dataLen = 0 ;
				while((len = dis.read(b)) != -1){
					log.debug("prot: " + portNum +",接收資料");
					if(dataLen == 0){
						dataLen = Integer.parseInt(new String(b).substring(0, 4));
						log.debug(new String(b).substring(0, 4));
						log.debug("dataLen: "+dataLen);
						data.append(new String(b, 4, len - 4));
						log.debug(data);
					}else{
						data.append(new String(b, 0, len));
					}
//					data.append(new String(b, 0, len));
					log.debug(dataLen + ", " + data.toString().getBytes().length +","+ data.toString().length());
					log.debug(data);
//					log.debug(javax.xml.bind.DatatypeConverter.printHexBinary(b));
					if(dataLen == data.toString().length()){
						log.debug("CallProcess");
						CallProcess.process(data.toString());
						data.setLength(0);
						dataLen = 0 ;
						continue;
					}
				}
				dis.close();
				log.debug(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
