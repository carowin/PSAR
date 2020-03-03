import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.sql.Timestamp;

/**
 * Each site is both a client and a server.
 * This class allows every sites to collect data from every other sites.
 * The data will be store in a file, which name will be "file+site id".
 * Once the data collected, the server must add to the message the actual 
 * timestamp and the number of hop that it took to get to this site.
 * In this case, we assume that every Server port number is 35207.
 */
public class Server {
	private Integer id;
	private String site;//pas besoin peut etre
	private int port = 35207;
	private File file;
	private DataOutputStream outputFile;
	private ServerSocket sSocket;
	private DatagramSocket dSocket;
	private Boolean serverRunning;//allows server to stop
	
	public Server(Integer id, String site) {
		this.id = id;
		this.site = site;
		file = new File("file"+id+".txt");
		this.serverRunning = true;
		try {
			file.createNewFile();
			outputFile = new DataOutputStream(new FileOutputStream(file));
			dSocket = new DatagramSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return id;
	}
	
	public int getPort() {
		return port;
	}
	
	public void stop() {
		this.serverRunning = false;
	}
	
	public void receive() {
		//in a thread because of infinite loop
		serverRunning = true;
		Thread thread = new Thread(new Runnable(){
			public void run() {
				System.out.println(" >> ID="+id+" PORT:"+port);
				System.out.println("i want to receive a message");
				while(serverRunning) {
					byte msg[] = new byte[50];
					System.out.println("1");
					DatagramPacket packet = new DatagramPacket(msg, 50);
					try {
						System.out.println("2");
						dSocket.receive(packet);
						
						//a partir de là ca ne passe plus
						
						System.out.println("3");
						String fileLine = new String(msg);
						fileLine += " " + System.currentTimeMillis();
						System.out.println( "FILELINE >>" +fileLine);
						outputFile.writeUTF(fileLine);
						System.out.println("4");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}	
	
	/*
	SI PB AVEC LES STRING
	byte[]tab -> BigInteger en utilisant new BigInteger(tab)
	la valeur envoyé comme un string

	coté serveur

	byte [] tab =new BigInteger (str_clé_recu).toByteArray();
	*/
	
}
