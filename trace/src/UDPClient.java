import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
public class UDPClient {
	
    private DatagramSocket udpSocket;
    private BufferedReader in;
   
    private int port; /* port d'envoie */
    private int numSeq; /* numéro de séquence (+1 à chaque envoie) */
    private Integer id; /* id du site */
    private HashMap<Integer, String> sites; /* Map contenant les infos des sites */
    private String myHostname; /* hostname du site */
    
    
    private UDPClient() throws IOException {
        this.udpSocket = new DatagramSocket(this.port);
        this.sites = new HashMap<>();
        this.myHostname = InetAddress.getLocalHost().getHostName();
        this.port = 7077;
        this.numSeq = 0;
        
       
        //_________________INITIALISATION MAP__________________
        
        this.in = new BufferedReader(new InputStreamReader(
        	    this.getClass().getResourceAsStream("configFile.txt")));
        String node;
		while ((node = in.readLine()) != null){
			String[] info = node.split(" ");
			System.out.println(node);
			if(!(info[1].equals(myHostname))) {
				sites.put(Integer.parseInt(info[0]), info[1]);
				System.out.println("VOISIN" +info[1]);
			}else {
				this.id = Integer.parseInt(info[0]);
				System.out.println("ID"+ info[0]);
			}
		}
		
	  //_______________________________________________________
    
    }
    

    public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException { 
    	UDPClient sender = new UDPClient();
        System.out.println("-- Running UDP Client --");
        sender.start();
    }
    
    
    
    private void start() throws IOException, InterruptedException {
        String msg;
        System.out.println("----------- "+myHostname+ " START >> 2 MINUTES -----------");
        long timeLimit = System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(10);

        while(System.currentTimeMillis()< timeLimit) {
        	
        	//Parcours du configFile(pour recuperer la liste des sites)
        	for(Map.Entry entry : sites.entrySet()) {
        		
        		msg = id + " " + System.currentTimeMillis() + " " + numSeq;
        		System.out.println(msg);
        		
        		//________________envoie du paquet udp_________________
        		DatagramPacket p = new DatagramPacket(
    	                msg.getBytes(), msg.getBytes().length, InetAddress.getByName((String) entry.getValue()), port);
	            this.udpSocket.send(p); 
	          //_______________________________________________________
	            
        	}
        	numSeq++;
            Thread.sleep(100);
        }
        
        //ENVOIE MESSAGE DE TERMINAISON >> Signale aux serveurs la fin de l'envoie
        for(Map.Entry entry : sites.entrySet()) {
        	String terminaison = "DONE";
    		DatagramPacket p = new DatagramPacket(
	                terminaison.getBytes(), terminaison.getBytes().length, InetAddress.getByName((String) entry.getValue()), port);
            this.udpSocket.send(p); 
        }
        
        System.out.println("----------- "+myHostname+" END >> 2 MINUTES -----------");
    }
}
