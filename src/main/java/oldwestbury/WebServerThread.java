package oldwestbury;

import java.io.*;
import java.net.*;

public class WebServerThread extends Thread {
  private Socket socket = null;

  public WebServerThread(Socket socket) {
    super("WebServerThread");
    this.socket = socket;
  }

  public void run() {
	
    try (OutputStream out = socket.getOutputStream();
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
   for(;;) {  
	   socket.setSoTimeout(1000);
	   System.out.println("  waiting");
	   HttpProtocol.process(in, out);
   	}
   
   //  socket.close();
    }

    catch (IOException e) {
      e.printStackTrace();
    }
  }
}