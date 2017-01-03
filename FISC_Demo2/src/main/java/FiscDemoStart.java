import org.apache.log4j.Logger;

import com.fisc.socket.Fisc_Server_Rcv;
import com.fisc.socket.Fisc_Server_Send;

public class FiscDemoStart {
	public static Logger log = Logger.getLogger(FiscDemoStart.class);
	
	public static void main(String[] args){
		new Fisc_Server_Rcv().start();
	}
	

}
