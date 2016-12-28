package com.fisc.demo;

public class FiscDemoStart {
	public static void main(String[] args){
		new Fisc_Server_Send().start();
		new Fisc_Server_Rcv().start();
		
	}
}
