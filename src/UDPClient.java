import java.io.IOException;
import java.net.*;

import java.util.Scanner;

public class UDPClient {

	public static void main(String[] args) throws IOException {
		
		DatagramSocket clientSocket = new DatagramSocket();
		
		InetAddress IPAddress = InetAddress.getByName("localhost");
		
		Scanner sp = new Scanner(System.in);
		
		String clientMessage = sp.next();
		
		byte[] clientMessageInBytes = clientMessage.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(clientMessageInBytes, clientMessageInBytes.length, IPAddress, 9876);
		
		clientSocket.send(sendPacket);
		 
		clientSocket.close();

	}

}

