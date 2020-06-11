

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.Clock;
import java.util.ArrayList;
/**
 * Pour accepter plusieurs données de différents site à la fois on va créer
 * n port d'écoute pour n sites, du coup le numéro de port de chaque site c'est: 
 * 7070+id
 * Pour que les clients puissent communiquer en meme temps
 */



/**
 * Classe permettant de récuperer les messages recu par le site
 * et de les insérer dans le fichier log du site (nom: logFile+id.txt)
 * On suppose ici que tout les serveurs ont le même port d'écoute
 */
public class UDPServer implements Runnable {

    private DatagramSocket udpSocket;
	private DataOutputStream outputFile;
	private BufferedReader in;
	private File file; 
	
	private int id; /* id du site */
	private String myHostname;/* hostname du site */
    private int port;/* port d'ecoute */
    private boolean run = true; /* var de sortie de boucle */
    private int nbClientDone = 0;/* nombre de client ayant fini l'envoi */
    private int nbClient;/* nombre de client total */
    private ArrayList<Thread> threadWorker = new ArrayList<>();
    private long globalClock;/* horloge globale */
    private long timeStart;
    private Object mutex = new Object();
     
    public UDPServer(long globalClock, long timeStart) throws SocketException, IOException {
        this.port = 7076;
        this.udpSocket = new DatagramSocket(this.port);
        this.myHostname = InetAddress.getLocalHost().getHostName();
        this.globalClock = globalClock;
        this.timeStart = timeStart;
        
        
      //________________RÉCUPÉRATION DE L'ID________________
        
        this.in = new BufferedReader(new InputStreamReader(
        	    this.getClass().getResourceAsStream("configFile.txt")));
        String node;
		while ((node = in.readLine()) != null){
			String[] info = node.split(" ");
			if((info[1].equals(myHostname))) {
				this.id = Integer.parseInt(info[0]);
			}else {
				nbClient ++;
				System.out.println(("NOMBRE DE CLIENT  >> " + nbClient));
			}
		}
		
		this.file = new File("logFile"+ id+ ".txt");
		this.outputFile = new DataOutputStream(new FileOutputStream(file));
		
	 //_______________________________________________________
    }
    

	@Override
	public void run() {
        try {
			System.out.println("-- Running Server at " + InetAddress.getLocalHost() + "--");
	        String msg;
	        
	        while (run) {
	            
	            byte[] buf = new byte[256];
	            DatagramPacket packet = new DatagramPacket(buf, buf.length);
	            udpSocket.receive(packet);//bloqué tant que rien est envoyé
	            msg = new String(packet.getData()).trim();
	            
	            if(msg.equals("DONE")) {//si on recoit un msg de terminaison
	            	nbClientDone ++;
	            }else {
		            Thread t = new Thread(new ServerWorker(msg, System.currentTimeMillis()));
		            threadWorker.add(t);
		            t.start();
	            }
	      
	            if(nbClientDone == nbClient) {
	            	run = false; //sortie de boucle
	            }
	        }
        }catch (IOException e) {
			e.printStackTrace();
		}
        
        for(Thread t : threadWorker) {
        	try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
       System.out.println("-- Server is done");
    }
        
	/**
	 * Thread worker appelé par le serveur qui va récupérer le message recu par le serveur
	 * et qui va placer ce message dans le fichier log du site.
	 */
	public class ServerWorker implements Runnable {
		String msg;
		long time;
		public ServerWorker(String msg, long time) {
			this.msg = msg;
			this.time = time;
		}
		
		public void ajoutDansFichier(String msg) throws IOException {
			synchronized (mutex) {
				long timereceiver = globalClock +(time-timeStart);
				msg += " " + timereceiver + "\n";
				System.out.println("RECU "+msg);
				outputFile.writeBytes(msg);
			}
		}
		
		@Override
		public void run() {
			try {
				ajoutDansFichier(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}       
		}
	}
}
