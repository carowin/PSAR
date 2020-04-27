import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

/**
 * This is the Main class. Each site wille execute
 * this code. It recovers the site ip addresse
 * and with this ip adress we can find the site id 
 * based on the configfile
 * It instanciate Client and Server and run them.
 */
public class Trace2 {
	public static void main(String[]args) {
		int id=0; /* identifier of the site */
		String site = null;/* site url */
		Thread client;
		Thread server;
		BufferedReader in; /* file reader */
		
		//________________MY IP ADDRESS________________
		
		
		//Recovers id of the current site
		try {
			site = InetAddress.getLocalHost().getHostName();
			in = new BufferedReader(new FileReader("configFile.txt"));
			String line;
			while ((line=in.readLine()) != null){
				System.out.println("y");
				String[] info = line.split(" ");
				if(info[1].equals(site)) {
					id = Integer.parseInt(info[0]);
				}
			}
		
			//________________INSTANCIATE-RUN C/S_________________
			System.out.println("--------Instanciation du site "+ site+"--------");
			client = new Thread(new Client(id, site));
			server = new Thread(new Server(id, site));
			
			System.out.println(" >> START: C/S");
			client.start();
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
