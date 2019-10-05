package server;

import java.net.InetAddress;

public class serverclient {
	public String name;
	public InetAddress address;
	public int port;
	private final int ID;
	public int attempt=0;
	
	public serverclient(String name,InetAddress address,int port,final int ID)
	{
		this.ID=ID;
		this.address=address;
		this.name=name;
		this.port=port;
	}
	public int getID()
	{
		return ID;
	}
}
