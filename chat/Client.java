package chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client{
	String name,ipaddress;
	private DatagramSocket socket;
	private InetAddress ip;
	private Thread send;
	private int port;
	private int ID=-1;
	
	public String getname()
	{
		return name;
	}
	public int getport()
	{
		return port;
	}
	public String getaddress()
	{
		return ipaddress;
	}
	
	public Client(String name,String address,int port) {
		this.name=name;
		this.port=port;
		this.ipaddress=address;
	}
	
	public boolean openconnection(String address,int port) {
		try {
			socket = new DatagramSocket();
			ip=InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public String receive() {
		byte[] data = new byte[1024];
	DatagramPacket packet= new DatagramPacket(data,data.length);
	try {
		socket.receive(packet);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String message= new String(packet.getData());
	return message;
	}
	
	public void send(final byte[] data) {
		send=new Thread("send") {
		public void run() {
			DatagramPacket packet=new DatagramPacket(data,data.length,ip,port);
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		};
		send.start();
	}
	public void setID(int ID) {
		this.ID=ID;
	}

}
