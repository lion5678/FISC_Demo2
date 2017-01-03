package com.fisc.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.net.SocketServer;


public class Fisc_Server_Send extends Thread{

	private static Logger log = Logger.getLogger(Fisc_Server_Send.class);
	private int sendPortNum;
	private String sendAddr;
	ServerSocket server;
	private Socket conn;
	private DataOutputStream dos;
	
	public Fisc_Server_Send() {
		Properties cfg = new Properties();
		try {
			cfg.loadFromXML(this.getClass().getResourceAsStream("/fisc_cfg.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			this.sendPortNum = Integer.parseInt(cfg.getProperty("SendPort"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		this.sendAddr = cfg.getProperty("SendAddr");
		log.debug("addr: " + sendAddr + ", portNum: " + sendPortNum);
	}
	
	@Override
	public void run() {
		super.run();
		try {
			log.debug("Fisc_Server start...");
			server = new ServerSocket(sendPortNum);
			log.debug("等待連線中...");
			while (true) {
				conn = server.accept();
				log.debug(conn.getRemoteSocketAddress().toString()+" 已連線...");
				
				while(true){
					Thread.sleep(5000);
				}
				
//				log.debug("send data:["++"]");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendMsg(){
		
	}

}
