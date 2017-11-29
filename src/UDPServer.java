import java.io.*;
import java.net.*;

class UDPServer {
	
	public static void main(String args[]) throws Exception {
		
		DatagramSocket serverSocket = new DatagramSocket(9876);
		
		int expectedSeqNo = 0;
		
		
		while (true) {
			
			int randomNumber = (int) (Math.random()*20);
			System.out.println("random number: " + randomNumber);
			
			byte[] receiveBytes = new byte[1024];
			byte[] responseBytes = new byte[1024];
			
			DatagramPacket recievedPacket = new DatagramPacket(receiveBytes, receiveBytes.length);

			serverSocket.receive(recievedPacket);

			String packetInString = new String(recievedPacket.getData());
			
			//System.out.println(packetInString);
			
			if(randomNumber > 10)
			{
				for(int i=0; i< packetInString.length(); i++) {
					
					if(packetInString.contains("@@"+Integer.toString(expectedSeqNo)) ){
						
						System.out.println(packetInString);
						
						expectedSeqNo++;
						
						responseBytes = "ACK".getBytes();

						DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length,
								recievedPacket.getAddress(), recievedPacket.getPort());

						serverSocket.send(responsePacket);
						
						break;
						
					}
				}
			
			}
			
			else {
				System.out.println("Corrupt packet recieved!");
				
				responseBytes = "NAK".getBytes();

				DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length,
						recievedPacket.getAddress(), recievedPacket.getPort());

				serverSocket.send(responsePacket);

				
			}

			
		}
	}
}
