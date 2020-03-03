import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 
 *
 */
public class Trace {
	static int i;
	static int j;
		
	public static void main(String[]args) {
		
		//-----VARIABLES-----
		HashMap<Integer, String> config; //HashMap<ID site, NOM site>
		BufferedReader in; //file reader
		ArrayList<Client> clientList; 
		ArrayList<Server> serverList;
		Timer timer = new Timer();
		
		//-----SITES INFOS-----
		config = new HashMap<Integer, String>();
		try {
			in = new BufferedReader(new FileReader("configFile.txt"));
			String site;
			while ((site=in.readLine()) != null){
				String[] info = site.split(" ");
				config.put(Integer.parseInt(info[0]), info[1]);
			}//map configure	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		//-------INSTANTIATION CLIENT/SERVER-------
		 
		clientList = new ArrayList<Client>();
		serverList = new ArrayList<Server>();
		
		for(Map.Entry mapentry : config.entrySet()) {
			Client client = new Client((Integer)mapentry.getKey(),(String)mapentry.getValue());
			Server serveur = new Server((Integer)mapentry.getKey(),(String)mapentry.getValue());
			clientList.add(client);
			serverList.add(serveur);
		}
		
		
		System.out.println("----------- START >> 2 MINUTES -----------");
		for(i=0; i<config.size(); i++) {
			serverList.get(i).receive();		
		}
		//long tempsLimite = System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(2);
		long timeLimit = System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(4);
		while(System.currentTimeMillis()< timeLimit) {
			try {
				clientList.get(i).send();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			/*timer.schedule(new TimerTask() {
			    public void run() {
			    	System.out.println("aa");
					for(int i=0; i<config.size(); i++) {
						clientList.get(i).send();
					}
			    }
			}, 0, 100);*/
		}
		
		System.out.println("----------- END >> 2 MINUTES -----------");
		
		//Once finished we stop server side
		for(int i=0; i<serverList.size(); i++) {
			serverList.get(i).stop();
		}
	}
}
