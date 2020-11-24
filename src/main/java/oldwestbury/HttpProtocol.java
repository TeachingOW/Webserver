package oldwestbury;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpProtocol {

	static String path = "/Users/user/eclipse-workspace/oldwestbury/http";
	
	
	static String split(String s) throws InvalidRequest {
		String []parts=s.split(" ");
		if(parts.length!=3) throw new InvalidRequest() ; 
		if(!parts[2].toLowerCase().startsWith("http")) throw new InvalidRequest();
		if(!parts[0].equalsIgnoreCase("get")) throw new InvalidRequest();
		
		return parts[1];		
	}
	static void process(BufferedReader in, PrintWriter out) throws IOException {
		//reading the request
		String get_request = "";
		
		
		for (int i=0;;i++) {
			String l = in.readLine();
			if(i==0) 
			get_request =  l;
			if (l.trim().length() == 0)
				break;
		}

		System.out.print(get_request);
		
		try {
		String filepath=split(get_request);
		System.out.println("--"+filepath+" ");
		File f=new File(path+filepath);
		if(f.exists() && f.isFile()) {
			System.out.println("File exists");
			String response="HTTP/1 200 OK\r\n";
			out.println(response);
			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			     out.println(line);
			    }
			}
			
		} else {
			System.out.println("File does exists");
			String response="HTTP/1 404 NOT FOUND\r\n";
			out.println(response);
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//check if the file exists
		
	}
}
