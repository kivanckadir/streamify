
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame{
	
	JLabel logoLabel = new JLabel();
	JLabel textLabel = new JLabel("Scan the Generated QR Code with Streamify Mobile App to Connect the Computer");
	JLabel qrLabel = new JLabel();
	
	public MainFrame() throws IOException {
		super();
		
		BgPanel bgPanel = new BgPanel();
		
		setSize(670, 380);
		setResizable(false);
		setTitle("Streamify");
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
				System.exit(0);
			}
		});
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(bgPanel);

		bgPanel.setLayout(new MigLayout("align center","[grow, align center]","20[]40[]25[]")); //layout const, column const, row const.

		logoLabel.setIcon(new ImageIcon("src/logo.png"));
		
		textLabel.setFont(new Font("Segoe UI Light", Font.BOLD, 16));
		textLabel.setForeground(Color.BLACK);
		
		try {
			qrLabel.setIcon(new QRCodeGenerator(InetAddress.getLocalHost().getHostAddress().toString() + ":" + Server.mPort).getQRAsImageIcon());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		qrLabel.setBorder(new LineBorder(Color.BLACK));
							
		bgPanel.add(logoLabel, "wrap");
		bgPanel.add(textLabel, "wrap");
		bgPanel.add(qrLabel, "wrap");
		

	}
	
	public class BgPanel extends JPanel{
		public BgPanel() {
			super();
			setBackground(Color.WHITE);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon image = new ImageIcon("src/background.png");
			g.drawImage(image.getImage(), 0, 0, null);
		}
	}
}
