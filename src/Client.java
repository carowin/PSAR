import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {
	
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
			dSocket = new DatagramSocket();
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
	
	public void run() {
		System.out.println("----------- START >> 2 MINUTES -----------");
		long timeLimit = System.currentTimeMillis()+TimeUnit.SECONDS.toMillis(4);
		while(System.currentTimeMillis()< timeLimit) {
			try {
				for(Map.Entry mapentry : sites.entrySet()) {
					String msg = mapentry.getKey().toString() + " " + System.currentTimeMillis() + " " + numSeq;
					byte[] line = msg.getBytes();
					System.out.println(msg);
					InetAddress localhost;
					try {
						localhost = InetAddress.getByName(mapentry.getValue().toString());
						DatagramPacket out = new DatagramPacket(line, 0, line.length, localhost, 8080);
						dSocket.send(out);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				numSeq ++;
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("----------- END >> 2 MINUTES -----------");
	}

}
