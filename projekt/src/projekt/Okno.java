package projekt;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Okno extends JFrame

{

public Okno()
    {
   	 super("Wojna");
   	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	 setVisible(true);
   	 setResizable(false);
   	 setSize(816,839);
   	 
   	 PanelUno panelUno = new PanelUno();
   	 add(panelUno);

   	 
   	 //pack();
    }
    
}

