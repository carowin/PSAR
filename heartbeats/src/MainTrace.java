import java.io.IOException;

public class MainTrace {
	
	public static void main(String[] args) throws IOException {
		long globalClock = Long.parseLong(args[0]);
		long timeStart = System.currentTimeMillis();
		System.out.println("Horloge globale" + globalClock);
		Thread server = new Thread(new UDPServer(globalClock, timeStart));
		server.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread client = new Thread(new UDPClient(globalClock, timeStart));
		client.start();
		
	}
}
