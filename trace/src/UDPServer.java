

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


/**
 * Classe permettant de récuperer les messages recu par le site
 * et de les insérer dans le fichier log du site (nom: logFile+id.txt)
 * On suppose ici que tout les serveurs ont le même port d'écoute
 */
public class UDPServer {

    private DatagramSocket udpSocket;
	private DataOutputStream outputFile;
	private BufferedReader in;
	private File file; 
	
	private int id; /* id du site */
	private String myHostname;/* hostname du site */
    private int port;/* port d'ecoute */
    private boolean run = true; /* var de sortie de boucle */
 
    public UDPServer() throws SocketException, IOException {
        this.port = 7077;
        this.udpSocket = new DatagramSocket(this.port);
        this.myHostname = InetAddress.getLocalHost().getHostName();
        
        
      //________________RÉCUPÉRATION DE L'ID________________
        
        this.in = new BufferedReader(new InputStreamReader(
        	    this.getClass().getResourceAsStream("configFile.txt")));
        String node;
		while ((node = in.readLine()) != null){
			String[] info = node.split(" ");
			if((info[1].equals(myHostname))) {
				this.id = Integer.parseInt(info[0]);
				break;
			}
		}
		
		this.file = new File("logFile"+ id+ ".txt");
		this.outputFile = new DataOutputStream(new FileOutputStream(file));
		
	 //_______________________________________________________
    }
    
    
    private void listen() throws Exception {
        System.out.println("-- Running Server at " + InetAddress.getLocalHost() + "--");
        String msg;
        
        while (run) {
            
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            udpSocket.receive(packet);//bloqué tant que rien est envoyé
            
            //____________RECUPERATION MSG______________
            msg = new String(packet.getData()).trim();
            if(msg.equals("DONE")) {//si on recoit un msg de terminaison
            	run = false;//sortie de boucle
            }else {
				msg += " " + System.currentTimeMillis() + "\n";
				System.out.println("RECU "+msg);
				//ajout du message recu dans le fichier
				outputFile.writeUTF(msg);
            }
			//___________________________________________
        }
    }
    
    
    public static void main(String[] args) throws Exception {
        UDPServer client = new UDPServer();
        client.listen();
    }
}
