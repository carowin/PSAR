import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Info du site dans un fichier spécifié
 */
public class SiteData {
	private int id; /* id du site que l'on souhaite avoir des infos */
	
	public SiteData(int id) {
		this.id = id;
	}
	
	
	/**
	 * Récupère tout les messages de id présent dans le fichier qu'on peut lire dans in
	 */
/*	public Map<Integer, Integer> getListMessage(BufferedReader in) throws NumberFormatException, IOException{
		String data;
		Map<Integer, Integer> result = new HashMap<>();
		int nbSeq = -1, nbMsg = 0;
		while((data = in.readLine()) != null){
			String[] info = data.split(" ");
			if(Integer.valueOf(info[0]) == id) {
				if(Integer.valueOf(info[2])==nbSeq+1) {
					nbMsg++;
					result.put(Integer.valueOf(info[2]),nbMsg);
					nbSeq ++;
				}else {
					while(nbSeq < Integer.valueOf(info[2])) {
						result.put(nbSeq,nbMsg);
						nbSeq ++;
					}nbMsg++;
					result.put(Integer.valueOf(info[2]),nbMsg);
					nbSeq++;
				}
			}
		}
		return result;
	}*/
	public Map<Integer, Integer> getListMessage(BufferedReader in) throws NumberFormatException, IOException{
		String data;
		Map<Integer, Integer> result = new HashMap<>();
		int nbMsg = 0;
		while((data = in.readLine()) != null){
			String[] info = data.split(" ");
			if(Integer.valueOf(info[0]) == id) {
				nbMsg++;
				System.out.println(Integer.valueOf(info[3])+" "+ nbMsg);
				result.put(nbMsg, Integer.valueOf(info[3]));
			}
		}
		return result;
	}
	

	
	/**
	 * Recupère tout les messages qu'il a envoyé dans les differents sites
	 */
	public Map<Integer, Integer> getEveryMessagesSend(List<BufferedReader> in) throws IOException {
		Map<Integer, Integer> messageSent = new HashMap<>();
		int i =1;
		int nbseq=0;;
		for(BufferedReader br : in) {
			String data;
			int nbSeq =0;
			while((data = br.readLine()) != null) {
				String[] info = data.split(" ");
				if(Integer.valueOf(info[0]) == id) {
					int msgSeqnumber = Integer.valueOf(info[2]);
					if(i==1 && !messageSent.containsKey(nbseq) ) {
						messageSent.put(nbseq, 0);
						nbseq++;
					}
					if(messageSent.containsKey(msgSeqnumber)){
						messageSent.put(msgSeqnumber, messageSent.get(msgSeqnumber)+1);
					}else {
						messageSent.put(msgSeqnumber, 1);
					}
				}
			}i++;
		}
		return messageSent;
	}

	
	/**
	 * Calcul de la valeur de l'écart type des msg du site id dans le fichier se trouvant dans
	 * le bufferedReader
	*/
	public float ecartType(BufferedReader in) throws IOException {
		String data;
		int nbMsg = this.nbMsgInFile(in);
		long moy = this.moyenne(in);
		long somme = 0;
		while((data = in.readLine()) != null) {
			String[] info = data.split(" ");
			if(Integer.valueOf(info[0])==id) {
				long diff = Long.getLong(info[3])-Long.getLong(info[3]);
				somme+= Math.pow(diff-moy, 2);
			}
		}
		return somme/nbMsg;
	}
	
	public Map<Integer,Long> perteMessage(BufferedReader in) throws NumberFormatException, IOException {
		Map<Integer,Long> timeMsg = new HashMap<>(); //<numSeq,temps envoi>
		String data;
		int nbMessage = 0;
		while((data = in.readLine()) != null) {
			String[] info = data.split(" ");
			if(Integer.valueOf(info[0])==id) {
				long diff = Long.getLong(info[3])-Long.getLong(info[3]);
				timeMsg.put(Integer.valueOf(info[2]), diff);
			}
		}
		return timeMsg;
	}
	
	public void afficheMesPertes(BufferedReader in, float rangeMin, float rangeMax) throws NumberFormatException, IOException {
		Map<Integer,Long> timeMsg = this.perteMessage(in);
		float ecart_type = this.ecartType(in);
		for(Entry<Integer,Long> e: timeMsg.entrySet()) {
		}
	}
	
	/**
	 * Nombre de message du site id dans le fichier in
	 */
	public int nbMsgInFile(BufferedReader in) throws IOException {
		String data;
		int nbMessage = 0;
		while((data = in.readLine()) != null) {
			String[] info = data.split(" ");
			if(Integer.valueOf(info[0])==id) {
				nbMessage++;
			}
		}
		return nbMessage;
		
	}
	
	public long moyenne(BufferedReader in) throws IOException {
		long moy = 0;
		int nbMsg = this.nbMsgInFile(in);
		String data;
		while((data = in.readLine()) != null) {
			String[] info = data.split(" ");
			moy+= (Long.getLong(info[3])-Long.getLong(info[3]));
		}	
		System.out.println("Moyenne du temps d'envoie "+ moy/nbMsg);
		return moy/nbMsg;
	}
	
	
}
