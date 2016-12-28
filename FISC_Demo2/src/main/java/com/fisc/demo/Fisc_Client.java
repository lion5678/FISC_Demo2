package com.fisc.demo;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class Fisc_Client extends Thread{

	private static Logger log = Logger.getLogger(Fisc_Client.class);
	private int portNum;
	private String addr;
	Socket socket;
	
	public Fisc_Client() {
		Properties cfg = new Properties();
		try {
			cfg.loadFromXML(this.getClass().getResourceAsStream("/fiscDemo_cfg.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			this.portNum = Integer.parseInt(cfg.getProperty("port"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		log.debug("Fisc_Client start...");
		this.addr = cfg.getProperty("addr");
		log.debug("addr: " + addr + ", portNum: " + portNum);
	}
	
	@Override
	public void run() {
		super.run();
		try {
			socket = new Socket(addr, portNum);
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			byte[] b = new byte[1024];
			StringBuffer data = new StringBuffer();
			while(dis.read(b) != -1){
				data.append(new String(b));
			}
			log.debug(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		new Fisc_Client().start();
		
	}
}
