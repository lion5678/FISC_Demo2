package com.fisc.demo;

import java.io.DataInputStream;
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
	private int portNum;
	private String addr;
	ServerSocket server;
	
	public Fisc_Server_Send() {
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
			this.portNum = Integer.parseInt(cfg.getProperty("SendPort"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		log.debug("Fisc_Server start...");
		this.addr = cfg.getProperty("SendAddr");
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
				byte[] b = new byte[1024];
				StringBuffer data = new StringBuffer();
				int len;
				while((len = dis.read(b)) != -1){
					log.debug(len);
					data.append(new String(b, 0, len));
				}
				dis.close();
				log.debug(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
