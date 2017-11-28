import java.io.*;
import java.net.*;

class UDPServer {
	
	public static void main(String args[]) throws Exception {
		
		DatagramSocket serverSocket = new DatagramSocket(9876);
		

		while (true) {
			
			byte[] receiveBytes = new byte[1024];
			byte[] responseBytes = new byte[1024];
			
			DatagramPacket recievedPacket = new DatagramPacket(receiveBytes, receiveBytes.length);

			serverSocket.receive(recievedPacket);

			System.out.println(new String(recievedPacket.getData()));

			responseBytes = "Your data was recieved!".getBytes();

			DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length,
					recievedPacket.getAddress(), recievedPacket.getPort());

			serverSocket.send(responsePacket);

		}
	}
}