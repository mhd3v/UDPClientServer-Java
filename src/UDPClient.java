import java.io.IOException;
import java.net.*;

import java.util.Scanner;

public class UDPClient {

	public static void main(String[] args) throws IOException {
		
		DatagramSocket clientSocket = new DatagramSocket();
		
		InetAddress IPAddress = InetAddress.getByName("localhost");
		
		Scanner sp = new Scanner(System.in);
		
		//boolean acknowledged = false;
		
		boolean nak, acknowledged = false;
			
		for(int i = 0; i < 10; i++) {
			
			acknowledged =false;
			nak = false;
			
			String clientMessage = "";
			byte[] clientMessageInBytes = new byte[1024];
			
			//boolean acknowledged = false;
			
			while(!acknowledged) { //keep running until packet successfully transmitted
				
				if(nak != true) { //if NAK was received don't take input again
					
					System.out.println("Enter data for packet: " + i);
					
					clientMessage = sp.next();
					
					clientMessage = clientMessage + "@@" + Integer.toString(i);
					
					clientMessageInBytes = clientMessage.getBytes();
				}
				
				//send packet
				DatagramPacket sendPacket = new DatagramPacket(clientMessageInBytes, clientMessageInBytes.length, IPAddress, 9876);
				clientSocket.send(sendPacket);
				System.out.println("Packet sent!");
				
				//receive response
				byte[] responsePacketInBytes = new byte[1024];
				DatagramPacket responsePacket = new DatagramPacket(responsePacketInBytes, responsePacketInBytes.length);
				clientSocket.receive(responsePacket);
				
				String responsePacketInString = new String(responsePacket.getData());
				
				if(responsePacketInString.trim().equals("ACK")) {
					System.out.println("ACK Recieved for packet no." + i + ", sending next packet!");
					acknowledged = true;
				}
				
				if(responsePacketInString.trim().equals("NAK")) {
					System.out.println("NAK Recieved for packet no." + i + ", sending packet again!");
					nak = true;
				}
					
				
			}
			
			System.out.println("=======");
			
			
			
			
		}
		
		clientSocket.close();
		

	}

}
