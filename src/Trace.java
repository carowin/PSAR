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
		ArrayList<Client> listeClient; //Stocke l'ensemble des clients
		ArrayList<Serveur> listeServeur; //Stocke l'ensemble des serveurs
		
		//-----RÉCUPÉRATION INFOS SITES-----
		config = new HashMap<Integer, String>();
		try {
			in = new BufferedReader(new FileReader("ficConfig.txt"));
			String site;
			while ((site=in.readLine()) != null){
				String[] infos = site.split(" ");
				config.put(Integer.parseInt(infos[0]), infos[1]);
				System.out.println(infos[0] + " " + infos[1]);
			}//map configuré		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		//-------INSTANCIATION CLIENT/SERVEUR
		listeClient = new ArrayList<Client>();
		listeServeur = new ArrayList<Serveur>();
		
		for(Map.Entry mapentry : config.entrySet()) {
			Client client = new Client((Integer)mapentry.getKey(),(String)mapentry.getValue());
			Serveur serveur = new Serveur((Integer)mapentry.getKey(),(String)mapentry.getValue());
			listeClient.add(client);
			listeServeur.add(serveur);
		}
		
		System.out.println("----------- DÉBUT DES 2 MINUTES -----------");
		long tempsLimite = System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(4);
		//long tempsLimite = System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(2);
		while( System.currentTimeMillis()< tempsLimite) {
			System.out.println("aaa");
			for(int i=0; i<config.size(); i++) {
				listeClient.get(i).send();
				listeServeur.get(i).receive();
			}
		}
		
		System.out.println("----------- FIN DES 2 MINUTES -----------");
	}
}
