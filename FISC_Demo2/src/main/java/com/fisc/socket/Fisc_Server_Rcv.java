package com.fisc.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fisc.process.CallProcess;

public class Fisc_Server_Rcv extends Thread{

	private static Logger log = Logger.getLogger(Fisc_Server_Rcv.class);
	private int portNum;
	private String addr;
//	private ServerSocket server;
	
	private int sendPortNum;
	private String sendAddr;
	
	
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
			this.sendPortNum = Integer.parseInt(cfg.getProperty("SendPort"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		this.addr = cfg.getProperty("RcvAddr");
		log.debug("addr: " + addr + ", portNum: " + portNum);
		this.sendAddr = cfg.getProperty("SendAddr");
		log.debug("addr: " + sendAddr + ", portNum: " + sendPortNum);
	}
	
	@SuppressWarnings("resource")
	@Override
	public void run() {
		super.run();
//		new Fisc_Server_Send().start();
		try {
			log.debug("Fisc_Server start...");
			ServerSocket serverRcv = new ServerSocket(portNum);
			log.debug(portNum+" 等待連線中...");
			
			ServerSocket serverSend = new ServerSocket(sendPortNum);
			log.debug(sendPortNum+" 等待連線中...");
			
			while (true) {
				Socket connRcv = serverRcv.accept();
				log.debug(connRcv.getRemoteSocketAddress().toString()+" 已連線...");
				
				Socket connSend = serverSend.accept();
				log.debug(connSend.getRemoteSocketAddress().toString()+" 已連線...");
				
				DataInputStream dis = new DataInputStream(connRcv.getInputStream());
//				byte[] c = new byte[4];
//				dis.read(c);
//				log.debug("dataLen: "+dataLen);
				int buf = 8;
				byte[] b = new byte[buf];
				int len;
				int dataLen = 0;
				byte[] dataBytes = null;
				int dataPos = 0;
				while((len = dis.read(b)) != -1){
					log.debug("prot: " + portNum +",接收資料");
					if(dataLen == 0){
						try {
							dataLen = Integer.parseInt(new String(Arrays.copyOfRange(b, 0, 4))); //資料總長度
						} catch (NumberFormatException e) {
							e.printStackTrace();
							log.debug("標頭長度解析失敗，請檢查並重啟...");
							return;
						}
						dataBytes = new byte[dataLen];
						System.arraycopy(b, 4, dataBytes, dataPos, len - 4);
						dataPos += len - 4;
						log.debug("dataLen: " + dataLen);
						log.debug(new String(dataBytes));
					}else{
						System.arraycopy(b, 0, dataBytes, dataPos, len);
						dataPos += len;
					}
					log.debug(len +","+ dataLen + ", " + dataPos);
					log.debug(new String(dataBytes));
//					log.debug(javax.xml.bind.DatatypeConverter.printHexBinary(b));
					if(dataLen == dataPos){
						log.debug("CallProcess");
						dataLen = 0;
						dataPos = 0;
						CallProcess.process(connSend, dataBytes);
//						continue;
					}
				}
				dis.close();
				log.debug("dis.close");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendMsg(Socket connSend, byte[] msg){
		try {
			DataOutputStream dos = new DataOutputStream(connSend.getOutputStream());
			dos.write(msg);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
