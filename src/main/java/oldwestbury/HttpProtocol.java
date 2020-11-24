package oldwestbury;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class HttpProtocol {

	static String path = "/Users/user/eclipse-workspace/oldwestbury/http/";

	static String split(String s) throws InvalidRequest {
		String[] parts = s.split(" ");
		if (parts.length != 3)
			throw new InvalidRequest();
		if (!parts[2].toLowerCase().startsWith("http"))
			throw new InvalidRequest();
		if (!parts[0].equalsIgnoreCase("get"))
			throw new InvalidRequest();

		return parts[1];
	}

	static void process(BufferedReader in, OutputStream out) throws IOException {
		// reading the request
		String get_request = "";

		for (int i = 0;; i++) {
			String l = in.readLine();
			if (i == 0)
				get_request = l;
			if (l.trim().length() == 0)
				break;
		}

		System.out.print(get_request);

		try {
			String filepath = split(get_request);
			System.out.println("--" + filepath + " ");
			File f = new File(path + filepath);
			if (f.exists() && f.isFile()) {
				int len = 0;
				byte[] bytes = Files.readAllBytes(Paths.get(path + filepath));
				byte[] header = "HTTP/1.1 200 OK\r\n".getBytes();
				byte[] tail = "\r\n\r\n".getBytes();
				byte[] buf = new byte[bytes.length + header.length + tail.length];

				System.arraycopy(header, 0, buf, len, header.length);
				len = len + header.length;
				System.arraycopy(bytes, 0, buf, len, bytes.length);
				len = len + bytes.length;
				System.arraycopy(tail, 0, buf, len, tail.length);

				out.write(buf);
				out.flush();

			} else {
				System.out.println("File does not exists");
				String response = "HTTP/1.1 404 NOT FOUND\r\n";
				out.write(response.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void process2(BufferedReader in, OutputStream out) throws IOException {
		// reading the request
		String get_request = "";

		for (int i = 0;; i++) {
			String l = in.readLine();
			if (i == 0)
				get_request = l;
			if (l.trim().length() == 0)
				break;
		}

		System.out.print(get_request);

		try {
			String filepath = split(get_request);
			System.out.println("--" + filepath + " ");
			File f = new File(path + filepath);
			if (f.exists() && f.isFile()) {
				Path p = Paths.get(path, filepath);
				String contentType = guessContentType(p);
				sendResponse(out, "200 OK", contentType, Files.readAllBytes(p));

			} else {
				System.out.println("File does not exists");
				String response = "HTTP/1.1 404 NOT FOUND\r\n";
				out.write(response.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void process3(BufferedReader in, OutputStream out) throws IOException {
		// reading the request
		String get_request = "";

		for (int i = 0;; i++) {
			String l = in.readLine();
			if (i == 0)
				get_request = l;
			if (l.trim().length() == 0)
				break;
		}

		System.out.print(get_request);

		try {
			String filepath = split(get_request);
			System.out.println("--" + filepath + " ");
			File f = new File(path + filepath);
			if (f.exists() && f.isFile()) {
				Path p = Paths.get(path, filepath);
				// String contentType = guessContentType(p);
				// sendResponse(out, "200 OK", contentType, Files.readAllBytes(p));
				out.write(("HTTP/1.1 " + "200 OK" + "\r\n").getBytes());
				// out.write(("ContentType: " + contentType + "\r\n").getBytes());
				out.write("\r\n".getBytes());
				out.write(Files.readAllBytes(p));
				out.write("\r\n\r\n".getBytes());
				out.flush();

			} else {
				System.out.println("File does not exists");
				String response = "HTTP/1.1 404 NOT FOUND\r\n";
				out.write(response.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String guessContentType(Path filePath) throws IOException {
		return Files.probeContentType(filePath);
	}

	private static void sendResponse(OutputStream out, String status, String contentType, byte[] content)
			throws IOException {
		out.write(("HTTP/1.1 " + status + "\r\n").getBytes());
		// out.write(("ContentType: " + contentType + "\r\n").getBytes());
		out.write("\r\n".getBytes());
		out.write(content);
		out.write("\r\n\r\n".getBytes());
		out.flush();

	}

	// this works in a browser
	// use out for header
	// and o to binary data
	static void process(BufferedReader in, OutputStream o, PrintWriter out) throws IOException {
		// reading the request
		String get_request = "";

		for (int i = 0;; i++) {
			String l = in.readLine();
			if (i == 0)
				get_request = l;
			if (l.trim().length() == 0)
				break;
		}

		System.out.print(get_request);

		try {
			String filepath = split(get_request);
			System.out.println("--" + filepath + " ");
			File f = new File(path + filepath);
			if (f.exists() && f.isFile()) {
				System.out.println("File exists");
				String response = "HTTP/1.0 200 OK\r\n";
				out.print(response);

				byte[] bytes = Files.readAllBytes(Paths.get(path + filepath));
				o.write(bytes);
				o.flush();

			} else {
				System.out.println("File does not exists");
				String response = "HTTP/1.1 404 NOT FOUND\r\n";
				out.println(response);
				out.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
