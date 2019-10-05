package chat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class Clientwindow extends JFrame implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultCaret caret;
	private JTextArea histroy;
	private JTextField textFieldmsg;
	
	private Client client;
	boolean running=false;
	private Thread listen,run;
	
	public Clientwindow(String name,String address,int port) {
		
		setTitle("Client");
		client = new Client(name,address,port);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880, 550);
		setLocationRelativeTo(null);
		boolean connect=client.openconnection(address,port);
		if(!connect)
		{
			System.err.println("Connection failed !");
			console("Connection Failed !");
		}
		createwindow();
		console("Connecting to "+address+"...");
		String connection="/c/"+name;
		client.send(connection.getBytes());
		run=new Thread(this,"Running");
		running=true;
		run.start();
	}
	private void createwindow(){
		contentPane = new JPanel();
		setVisible(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{17,800,30,3};
		gbl_contentPane.rowHeights = new int[]{53,437,60};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		histroy = new JTextArea();
		histroy.setEditable(false);
		caret=(DefaultCaret)histroy.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scroll=new JScrollPane(histroy);
		GridBagConstraints gbc_textAreaHistroy = new GridBagConstraints();
		gbc_textAreaHistroy.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaHistroy.fill = GridBagConstraints.BOTH;
		gbc_textAreaHistroy.gridx = 1;
		gbc_textAreaHistroy.gridy = 1;
		gbc_textAreaHistroy.gridwidth = 2;
		contentPane.add(scroll, gbc_textAreaHistroy);
		
		textFieldmsg = new JTextField();
		textFieldmsg.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					send(textFieldmsg.getText()); 
				}
			}
		});
		GridBagConstraints gbc_textFieldmsg = new GridBagConstraints();
		gbc_textFieldmsg.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldmsg.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldmsg.gridx = 1;
		gbc_textFieldmsg.gridy = 2;
		contentPane.add(textFieldmsg, gbc_textFieldmsg);
		textFieldmsg.setColumns(10);
		textFieldmsg.requestFocus();
		
		JButton btnsend = new JButton("Send");
		btnsend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send(textFieldmsg.getText());
			}
		});
		GridBagConstraints gbc_btnsend = new GridBagConstraints();
		gbc_btnsend.insets = new Insets(0, 0, 0, 5);
		gbc_btnsend.gridx = 2;
		gbc_btnsend.gridy = 2;
		contentPane.add(btnsend, gbc_btnsend);
	}
	public void run()
	{		
		listen();
	}
	public void listen()
	{
		listen = new Thread("Listen") {
			public void run() {
		while(running) {
			String message=client.receive();
			if(message.startsWith("/c/"))
			{
				client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
				console("Successfully Connected to server !");
			}
			else if(message.startsWith("/m/"))
			{
				console(message.substring(3,message.length()));
			}
			else
				System.out.println(message);
			}
		  }
		};
		listen.start();
	}
	public void send(String message)
	{
		if(message.equals(""))
			return;
		//console(client.getname()+" : "+message);
		message="/m/"+client.getname()+" : "+message;
		client.send(message.getBytes());
		textFieldmsg.setText("");
	}
	public void console(String message) {
		histroy.append(message+"\n");
		//textFieldmsg.setCaretPosition(histroy.getDocument().getLength());
	}
}
