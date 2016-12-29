package com.fisc.process;

import org.junit.Test;

public class CallProcessTests {

	@Test
	public void testCallprocess(){
		new CallProcess().process("abc".getBytes());
	}
}
