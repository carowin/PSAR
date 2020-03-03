import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Each site is both a client and a server.
 * This class allows each site to send a heartbeat to every other sites that is in the 
 * configfile. The heartbeat is a message which contains: site id + seqnum + timestamp.
 * The hearbeat is sent every 100ms.
 */
public class Client {
	private Integer id;
	private String site;
	private int numSeq;
	private DatagramSocket dSocket;
	
	HashMap<Integer, String> sites;//sites to send heartbeat
	
	public Client(Integer id, String site) {
		this.id = id;
		this.site = site;
		this.numSeq = 0;
		sites = new HashMap<Integer, String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader("configFile.txt"));
			String node;
			while ((node=in.readLine()) != null){
				String[] info = node.split(" ");
				if(!info[0].equals(id.toString())) {
					sites.put(Integer.parseInt(info[0]), info[1]);
				}
			}//map configure	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public int getId() {
		return id;
	}
	
	public void send() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		for(Map.Entry mapentry : sites.entrySet()) {
			String msg = mapentry.getKey().toString() + " " +timestamp.toString() + " " + numSeq;
			byte[] line = msg.getBytes();
			InetAddress localhost;
			try {
				localhost = InetAddress.getByName(mapentry.getValue().toString());
				DatagramPacket out = new DatagramPacket(line, 0, line.length, localhost, 8080);
				numSeq++;
				dSocket.send(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
