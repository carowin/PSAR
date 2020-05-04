import java.io.IOException;

public class MainTrace {
	
	public static void main(String[] args) throws IOException {
		
		
		Thread client = new Thread(new UDPClient());
		Thread server = new Thread(new UDPServer());
		
		server.start();
		client.start();
		
	}
}
