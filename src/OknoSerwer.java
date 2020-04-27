

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class OknoSerwer extends JFrame {

	public ImageIcon obraz;
	
	public OknoSerwer() {
		super("Serwer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.RED);
		Font font = new Font("Gill Sans Ultra Bold", Font.BOLD, 24);
		JLabel label = new JLabel();
		label.setFont(font);
		label.setText("Serwer uruchomiony.");
		add(label);
		pack();
	}
	

}
