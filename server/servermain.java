package server;

public class servermain {
	private int port;
	public servermain(int port)
	{
		this.port=port;
		new Server(port);
	}
	public static void main(String[] args) {
	
		int port;
		if(args.length!=1)
		{
			System.out.println("Usage: Java-jar server.jar [port]");
			return;
		}
		port=Integer.parseInt(args[0]);
		new servermain(port);
		
	}

}
