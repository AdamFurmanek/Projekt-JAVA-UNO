
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Okno extends JFrame

{
	public static PanelUno panelUno;
public Okno()
    {
   	 super("Uno - gracz "+Klient.gracz);
   	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	 setVisible(true);
   	 setResizable(false);
   	 setSize(800,823);
   	 panelUno = new PanelUno();
   	 add(panelUno);
   	 
   	 //pack();
    }
    
}

