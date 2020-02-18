import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Trace {
	
	
	public void send() {
		
	}
	
	public void receive() {
		
	}
	
	public static void main(String[]args) {
		/*
		 * INITIALISATION INFOS DU SITE
		 */
		int numSeq = 0; // à incrémenter à chaque envoi
		int numSite = 0; // à initialiser ! Reflechir comment recuperer le num
		boolean end = false; //signale la fin du prog
		String filename = "fichierLog"+ Integer.toString(numSite)+".txt";
		try {
			FileOutputStream fic=new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}   
		/*
		 * FIN INITIALISATION
		 */
		

		//-------GESTION DU TEMPS-------
		int timeMinute = 2; //temps pendant lequel on execute le programme
		Chrono chrono = new Chrono();
		chrono.start();
		
		while(chrono.getDureeSec() < timeMinute * 60) {
			
		}
		
		chrono.stop();
	}
}
