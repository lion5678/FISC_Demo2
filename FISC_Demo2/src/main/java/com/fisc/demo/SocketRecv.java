package com.fisc.demo;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketRecv extends java.lang.Thread {
 
//	private static Logger log = Logger.getLogger(SocketRecv.class);
	private static int port = 0;
    public void run() {
    	Socket socketIn = null;
	    DataInputStream dis = null;
	    byte[] inData = null;

	    try {
	    	System.out.println("OTH "+ port +" Start!");
	    	socketIn = new Socket("127.0.0.1", port);

	    	dis = new DataInputStream(socketIn.getInputStream());
            
	    	byte[] b = new byte[2048];

	    	String data = "";
	    	int length;
	    	
          while ((length = dis.read(b)) > 0)// <=0���ܴN�O�����F
          {
              data += new String(b, 0, length);
              
              System.out.println("OTH Get:"+"\n");
              
//              DesUtilImpl.print(log, 4,b, 0, length, "950");
              
              System.out.println("length = "+length);
              System.out.println("data = "+data);
          }

          dis.close();
          dis = null;
          socketIn.close();
            
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
 
    public static void main(String args[]) {
    	Scanner scanner = new Scanner(System.in);
    	System.out.print("Port:");
    	port = scanner.nextInt();
        (new SocketRecv()).start();
    }
 
}