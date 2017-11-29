
package GoBackN;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class UDPServer {
	static DatagramPacket dataPacket;
	static DatagramSocket dataSocket;
	static byte[] byteArray;
	static int ack = 0;
	static int seqNo = 0;
	static int packetNo;
	
	public static void main(String[] args) {
		while(true) {
			recieveResponse();
			sendAck();
		}
	}
	
	private static void recieveResponse() {
		try {
			int rand = (int)(Math.random()*100);
			dataSocket = new DatagramSocket(1214);
			byteArray = new byte[17];
			dataPacket = new DatagramPacket(byteArray, byteArray.length);
			dataSocket.receive(dataPacket);
			if((byteArray[0]-48) == seqNo && rand > 10) {
				System.out.println("Random Number: " + rand);
				packetNo = byteArray[byteArray.length - 1] - 48;
				ack = 1;
			}
			else {
				System.out.println("Corrupt!" + " Random Number: " + rand);
				packetNo = byteArray[byteArray.length - 1] - 48;
				ack = 0;
			}
			if(seqNo == 0)
				seqNo = 1;
			else
				seqNo = 0;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void sendAck() {
		try {
			if(ack == 1) {
				String result = ack + "" + packetNo;
				byteArray = result.getBytes();
				InetAddress address = dataPacket.getAddress();
				dataPacket = new DatagramPacket(byteArray, byteArray.length, address, dataPacket.getPort());
				dataSocket.send(dataPacket);
				dataSocket.close();
			}
			else {
				String result = ack + "" + packetNo;
				byteArray = result.getBytes();
				InetAddress address = dataPacket.getAddress();
				dataPacket = new DatagramPacket(byteArray, byteArray.length, address, dataPacket.getPort());
				dataSocket.send(dataPacket);
				dataSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
