package it.micronixnetwork.gaf.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketOut {

	public static void sendFileToPrinter(String fileName) throws IOException{

		sendFileToHost(fileName, "localhost", 9100);

	}

	public static void sendFileToPrinter(String fileName, String printer) throws IOException{

		sendFileToHost(fileName, printer, 9100);
	}

	public static void sendFileToHost(String fileName, String host, int port) throws IOException{


		byte buffer[];
		buffer = FileUtil.getBytesFromFile(new File(fileName));
		OutputStream os;
		Socket out = new Socket(host, port);
		os = out.getOutputStream();

		os.write(buffer, 0, buffer.length);

		os.flush();
		os.close();
		out.close();

	}
}
