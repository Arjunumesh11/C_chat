package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Server implements Runnable{
	
	private List<serverclient> clients=new ArrayList<serverclient>();
	
	private int port;
	private DatagramSocket socket;
	private Thread run,manage,send,recieve;
	private boolean running;
	public Server(int port) {
		
		this.port=port;
		try {
			socket=new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		run = new Thread(this,"Server");
		run.start();
	}
	public void run() {
		running =true;
		System.out.println("server is running in "+port);
		manageclients();
		recieve();
		
	}
	private void manageclients() {
		manage =new Thread("Manage") {
			public void run() {
				while(running) {
					//managing
					
				}
			}
		};
		manage.start();
}
	private void recieve() {
	recieve = new Thread("Recieve") {
		public void run(){
			while(running) {
				//Receive
				System.out.println(clients.size());
				byte[] data=new byte[1024];
				DatagramPacket packet=new DatagramPacket(data,data.length);
				try {
					socket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
				process(packet);
				}
			}
		};
	 recieve.start();
	}
	private void sendtoall(String message)
	{
		for(int i=0;i<clients.size();i++)
		{
			serverclient client = clients.get(i);
			send(message.getBytes(),client.address,client.port);
			
		}
	}
	private void send(final byte[] data,final InetAddress address,final int port)
	{
		send = new Thread("send") {
			public void run()
			{
				DatagramPacket packet=new DatagramPacket(data,data.length,address,port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
	public void process(DatagramPacket packet) {
		String data=new String(packet.getData());
		//UUID id=UUID.randomUUID();
		if(data.startsWith("/c/"))
		{
			int id=Uniqueid.getid();
			clients.add(new serverclient(data.substring(3,data.length()),packet.getAddress(),packet.getPort(),id));
			String ID="/c/"+id+"/e/";
			send(ID.getBytes(),packet.getAddress(),packet.getPort());
			//System.out.println(data.substring(3,data.length()));
		}
		else if(data.startsWith("/m/")){
			sendtoall(data);
			}
			System.out.println(data);
	}
	
}
