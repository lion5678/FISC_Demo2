import com.fisc.demo.Fisc_Server_Rcv;
import com.fisc.demo.Fisc_Server_Send;

public class FiscDemoStart {
	public static void main(String[] args){
		new Fisc_Server_Send().start();
		new Fisc_Server_Rcv().start();
		
	}
}
