import java.io.IOException;

public class MainTrace {
	
	public static void main(String[] args) throws IOException {
		Thread server = new Thread(new UDPServer());
		server.start();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread client = new Thread(new UDPClient());
		client.start();
		
	}
}
