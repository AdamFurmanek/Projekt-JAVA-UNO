
import javax.swing.JFrame;

public class Okno extends JFrame {

	public static PanelUno panelUno;

	public Okno() {
		super("Uno - gracz " + Klient.numerGracza);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setSize(800, 823);
		setLocationRelativeTo(null);
		panelUno = new PanelUno();
		add(panelUno);
	}
	
	
}
