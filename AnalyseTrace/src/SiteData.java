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
	private Map<Integer, Integer> evolMsg;/* <temps, nbMsg> */
	private Map<Integer, Integer> tempsEnvoi;/* <numSeq, tempsEnvoi> */
	private Map<Integer, Integer> nbSiteRecv;/* <numSeq, nbSiteRecu */
	public SiteData(int id) {
		this.id = id;
		this.evolMsg = new HashMap<>();
		this.tempsEnvoi = new HashMap<>();
		this.nbSiteRecv = new HashMap<>();
	}
	//POUR TRIER LA MAP : TreeMap<String, Employee> sorted = new TreeMap<>(map);
	
	/**
	 * Compte au fur et à mesure le nombre de msg recu au total par rapport au temps, remplit la map nbMsg
	 */
	public Map<Integer, Integer> getListMessage(BufferedReader in) throws NumberFormatException, IOException{
		String data;
		int nbMsg = 0;
		while((data = in.readLine()) != null){
			String[] info = data.split(" ");
			if(Integer.valueOf(info[0]) == id) {
				nbMsg++;
				System.out.println(Integer.valueOf(info[3])+" "+ nbMsg);
				evolMsg.put(nbMsg, Integer.valueOf(info[3]));
			}
		}
		return evolMsg;
	}
	

	
	/**
	 * Recupère tout les messages qu'il a envoyé dans les differents sites, remplit la map 
	 */
	public Map<Integer, Integer> getEveryMessagesSend(List<BufferedReader> in) throws IOException {
		int i =1;
		int nbseq=0;;
		for(BufferedReader br : in) {
			String data;
			int nbSeq =0;
			while((data = br.readLine()) != null) {
				String[] info = data.split(" ");
				if(Integer.valueOf(info[0]) == id) {
					int msgSeqnumber = Integer.valueOf(info[2]);
					if(i==1 && !nbSiteRecv.containsKey(nbseq) ) {
						nbSiteRecv.put(nbseq, 0);
						nbseq++;
					}
					if(nbSiteRecv.containsKey(msgSeqnumber)){
						nbSiteRecv.put(msgSeqnumber, nbSiteRecv.get(msgSeqnumber)+1);
					}else {
						nbSiteRecv.put(msgSeqnumber, 1);
					}
				}
			}i++;
		}
		return nbSiteRecv;
	}
	
	/* associe à chaque message son temps d'envoi */
	public Map<Integer,Integer> tempsEnvoiMessage(BufferedReader in) throws NumberFormatException, IOException {
		String data;
		int nbMessage = 0;
		while((data = in.readLine()) != null) {
			String[] info = data.split(" ");
			if(Integer.valueOf(info[0])==id) {
				int diff = Integer.valueOf(info[3])-Integer.valueOf(info[1]);
				tempsEnvoi.put(Integer.valueOf(info[2]), diff);
			}
		}
		return tempsEnvoi;
	}
	

	
	/**
	 * Calcul de la valeur de l'écart type des msg du site id dans le fichier se trouvant dans
	 * le bufferedReader
	*/
	public int ecartType(BufferedReader in,Map<Integer,Integer> map) throws IOException {
		Map<Integer, Integer> coeff = new HashMap<>(); //<tempsEnvoi, coeff>
		for(Entry<Integer,Integer> c : map.entrySet()) {
			if(coeff.containsKey(c.getValue())) {
				coeff.put(c.getValue(), coeff.get(c.getValue())+1);
			}else {
				coeff.put(c.getValue(),1);
			}
		}
		float moy = this.moyenne(in, map);
		float somme = 0;
		for(Entry<Integer, Integer> e: coeff.entrySet()) {
			somme+= (Math.pow(e.getKey()-moy, 2)*e.getValue());
		}
		return (int) (somme/map.size()+1);
	}
	
	/* affiche les messages en retard */
	public void afficheMesPertes(BufferedReader in, float rangeMin, float rangeMax,Map<Integer,Integer> map) throws NumberFormatException, IOException {
		Map<Integer,Integer> timeMsg = this.tempsEnvoiMessage(in);
		float ecart_type = this.ecartType(in, map);
		for(Entry<Integer,Integer> e: timeMsg.entrySet()) {
			if(e.getValue()> rangeMax) {
				System.out.println("LATENCE MESSAGE: "+ e.getKey()+" "+e.getValue());
			}
		}
	}
	
	/**
	 * Nombre de message du site id dans le fichier in
	 */
	public int nbMsgInFile(BufferedReader in, Map<Integer,Integer> map) throws IOException {
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
	
	public float moyenne(BufferedReader in,Map<Integer,Integer> map) throws IOException {
		int moy = 0;
		
		for(Entry<Integer,Integer> e: tempsEnvoi.entrySet()) {
			moy+= e.getValue();
		}
		return moy/map.size();
	}
	
	
}
