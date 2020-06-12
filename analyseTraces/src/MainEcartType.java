import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MainEcartType {
	
	private Map<Integer,BufferedReader> listFic;
	private int id;
	
	public MainEcartType(int id) {
		this.id =id;
		this.listFic = new HashMap<>();
		BufferedReader in = new BufferedReader(new InputStreamReader(
	    	    this.getClass().getResourceAsStream("configFile.txt")));
		String data; 
		try {
			while((data=in.readLine()) != null){
				String[] info = data.split(" ");
				if(Integer.valueOf(info[0])!=id) {
					File file = new File("src/logFile"+info[0]+".txt");
					if(file.getAbsoluteFile().exists()) {
						  BufferedReader bur = new BufferedReader(new InputStreamReader(
								  this.getClass().getResourceAsStream("logFile"+info[0]+".txt")));
						  listFic.put(Integer.valueOf(info[0]),bur);
					  }
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public Map<Integer,BufferedReader> getMap(){
		return listFic;
	}

	  public static void main(String[] args) {
		  int id = 0;
		  MainEcartType m = new MainEcartType(id);
		  Map<Integer,BufferedReader> br = m.getMap();
		  try {
			  SiteData data1 = new SiteData(id);
			  for(Entry<Integer, BufferedReader> e : br.entrySet()) {
				System.out.println("____________ LATENCE DES MESSAGES SUR LE SITE "+id+" SUR LE FICHIER"+ e.getKey()+"____________");
				Map<Integer, Integer> map = data1.tempsEnvoiMessage(e.getValue());
				int ecartType = data1.ecartType(e.getValue(), map);
				float moyenne = data1.moyenne(e.getValue(), map);
				int rangeMin = (int) (moyenne-ecartType);
				int rangeMax = (int) (moyenne+ecartType);
				System.out.println("Le temps d'envoi d'un message doit être compris dans l'intervalle ["+rangeMin+","+rangeMax+"]");
				data1.afficheMesPertes(e.getValue(), moyenne-ecartType, moyenne+ecartType, map);
			  }
				  
		} catch (IOException e1) {
			e1.printStackTrace();
		}

			
			
			

		  
	  }
	
}
