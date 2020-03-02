import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

/**
 * Each site is both a client and a server.
 * This class allows every sites to collect data from every other sites.
 * The data will be store in a file, which name will be "file+site id".
 * Once the data collected, the server must add to the message the actual 
 * timestamp and the number of hop that it took to get to this site.
 * In this case, we assume that every Server port number is 8080.
 */
public class Server {
	private Integer id;
	private String site;//pas besoin peut etre
	private int port;
	private File file;
	private DataOutputStream outputFile;
	private ServerSocket sSocket;
	private  DatagramSocket dSocket;
	
	public Server(Integer id, String site) {
		this.id = id;
		this.site = site;
		file = new File("file"+id);
		try {
			file.createNewFile();
			outputFile = new DataOutputStream(new FileOutputStream(file));
			sSocket = new ServerSocket(port);// à revoir 
			dSocket = new DatagramSocket(port);//àrevoir
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
	
	public void receive() {
		System.out.println("-Je suis id="+id+" du port d'ecoute:"+port);
		System.out.println("je veux recevoir les messages");
		while(true) {
			Socket c;
			try {
				c = sSocket.accept();
				DataInputStream is = new DataInputStream(c.getInputStream());
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				String fileLine = is.readUTF()+ " " + timestamp;
				System.out.println(fileLine);
				outputFile.writeUTF(fileLine);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}	
	
}
