import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Classe permettant d'envoyer des messages aux sites qui sont
 * listés dans le configFile.
 * Envoie de message pendant 2minutes toutes les 100ms.
 * Les messages envoyés sont de la forme:
 *	monId+timestampSend+numSeq -> qui sera ensuite complété par le siteServeur
 */
public class UDPClient implements Runnable {
	
    private DatagramSocket udpSocket;
    private BufferedReader in;
   
    private int port; /* port d'envoie */
    private int numSeq; /* numéro de séquence (+1 à chaque envoie) */
    private Integer id; /* id du site */
    private HashMap<Integer, String> sites; /* Map contenant les infos des sites */
    private String myHostname; /* hostname du site */
    private long globalClock; /* horloge globale */
    private long timeStart; /* temps au moment où le client a été lancé*/
      
    
    public UDPClient(long globalClock, long timeStart) throws IOException {
        this.udpSocket = new DatagramSocket(this.port);
        this.sites = new HashMap<>();
        this.myHostname = InetAddress.getLocalHost().getHostName();
        this.port = 7076;
        this.numSeq = 0;
        this.globalClock = globalClock;
        this.timeStart = timeStart;
       
        //_________________INITIALISATION MAP__________________
        
        this.in = new BufferedReader(new InputStreamReader(
        	    this.getClass().getResourceAsStream("configFile.txt")));
        String node;
		while ((node = in.readLine()) != null){
			String[] info = node.split(" ");
			System.out.println(node);
			if(!(info[1].equals(myHostname))) {
				sites.put(Integer.parseInt(info[0]), info[1]);
				System.out.println("VOISIN " +info[1]);
			}else {
				this.id = Integer.parseInt(info[0]);
				System.out.println("ID "+ info[0]);
			}
		}
		
	  //_______________________________________________________
    
    }
    

	@Override
	public void run() {
        String msg;
        System.out.println("----------- "+myHostname+ " START >> 2 MINUTES -----------");
        long timeLimit = System.currentTimeMillis()+TimeUnit.HOURS.toMillis(1);

        while(System.currentTimeMillis()< timeLimit) {
			try {
	        	//Parcours du configFile(pour recuperer la liste des sites)
	        	for(Map.Entry entry : sites.entrySet()) {
	        		long timeSend = globalClock + (System.currentTimeMillis()- timeStart);
	        		msg = id + " " + timeSend + " " + numSeq;
	        		//System.out.println(msg);
	        		
	        		//________________envoie du paquet udp_________________
	        		DatagramPacket p;
						p = new DatagramPacket(
						        msg.getBytes(), msg.getBytes().length, InetAddress.getByName((String) entry.getValue()), port);
						this.udpSocket.send(p);
	
		          //_______________________________________________________
		            
	        	}
			} catch (IOException e) {
				e.printStackTrace();
			} 
        	numSeq++;
            try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

        }
        
        //ENVOIE MESSAGE DE TERMINAISON >> Signale aux serveurs la fin de l'envoie
        for(Map.Entry entry : sites.entrySet()) {
        	 try {
	        	String terminaison = "DONE";
	    		DatagramPacket p = new DatagramPacket(
		                terminaison.getBytes(), terminaison.getBytes().length, InetAddress.getByName((String) entry.getValue()), port);
	           System.out.println("I'M DONE ! "+ id);
				this.udpSocket.send(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        System.out.println("----------- "+myHostname+" END >> 2 MINUTES -----------");
		
	}
}
