package eOwl.client;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientEOWL {


	public static void main(String[] args) {
		Client client= new Client("Benjamin");
		/*try {
			Client client= new Client("Benjamin_Clement",InetAddress.getByName("pc-de-clement"));
			//Client client= new Client("Benjamin_Clement",InetAddress.getLocalHost());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
