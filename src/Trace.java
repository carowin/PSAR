import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class Trace {
	
	
	public static void main(String[]args) {
		
		//-----VARIABLES-----
		HashMap<Integer, String> config; //HashMap<ID site, NOM site>
		BufferedReader in; //Permet la lecture d'un fichier
		ArrayList<Client> clientList; //Stocke l'ensemble des clients
		ArrayList<Server> serverList; //Stocke l'ensemble des serveurs
		
		//-----SITES INFOS-----
		config = new HashMap<Integer, String>();
		try {
			in = new BufferedReader(new FileReader("configFile.txt"));
			String site;
			while ((site=in.readLine()) != null){
				String[] info = site.split(" ");
				config.put(Integer.parseInt(info[0]), info[1]);
				System.out.println(info[0] + " " + info[1]);
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
		long timeLimit = System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(4);
		//long tempsLimite = System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(2);
		while( System.currentTimeMillis()< timeLimit) {
			System.out.println("aaa");
			for(int i=0; i<config.size(); i++) {
				clientList.get(i).send();
				serverList.get(i).receive();
			}
		}
		
		System.out.println("----------- END >> 2 MINUTES -----------");
	}
}
