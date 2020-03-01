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
	
	public Client(Integer id, String site) {
		this.id = id;
		this.site = site;
		this.numSeq = 0;
	}
	
	public int getId() {
		return id;
	}
	
	public void send() {
		
	}

}
