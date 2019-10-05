package chat;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textName;
	private JLabel lblIpAddress;
	private JTextField textIPaddress;
	private JLabel lblPort;
	private JTextField textPort;

	public login() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setResizable(false);
		setTitle("Login Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 287, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textName = new JTextField();
		textName.setBounds(72, 36, 136, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		
		JLabel lbname = new JLabel("Name");
		lbname.setHorizontalAlignment(SwingConstants.CENTER);
		lbname.setBounds(116, 11, 48, 14);
		contentPane.add(lbname);
		
		lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblIpAddress.setBounds(104, 79, 73, 14);
		contentPane.add(lblIpAddress);
		
		textIPaddress = new JTextField();
		textIPaddress.setColumns(10);
		textIPaddress.setBounds(72, 104, 136, 20);
		contentPane.add(textIPaddress);
		
		lblPort = new JLabel("Port");
		lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblPort.setBounds(116, 150, 48, 14);
		contentPane.add(lblPort);
		
		textPort = new JTextField();
		textPort.setColumns(10);
		textPort.setBounds(72, 180, 136, 20);
		contentPane.add(textPort);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name=textName.getText();
				String ipaddress=textIPaddress.getText();
				int port=Integer.parseInt(textPort.getText());
				loginscreen(name,ipaddress,port);
				
			}
		});
		btnLogin.setBounds(96, 223, 89, 23);
		contentPane.add(btnLogin);
	}
	private void loginscreen(String name,String address,int port) 
	{
		dispose();
		new Clientwindow(name,address,port);
		
	} 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
