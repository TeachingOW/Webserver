package oldwestbury;

import java.net.*;
import java.io.*;

public class WebServerThread extends Thread {
	private Socket socket = null;

	public WebServerThread(Socket socket) {
		super("WebServerThread");
		this.socket = socket;
	}

	public void run() {

		try (OutputStream out = socket.getOutputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			HttpProtocol.process(in, out);

			socket.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

}