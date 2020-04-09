

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PanelUno extends JPanel implements MouseListener, MouseMotionListener{
    
    public ImageIcon karta;
    public static char[][] tablica = new char[108][2];
    public PanelUno() {
        
        for(int i =0;i<108;i++){
            tablica[i][0]='n';
            tablica[i][1]='n';
            }

         addMouseListener(this);
         addMouseMotionListener(this);
        tablica[0][0]='c';
        tablica[0][1]='0';

        tablica[1][0]='c';
        tablica[1][1]='1';

        tablica[2][0]='c';
        tablica[2][1]='2';
        
        tablica[3][0]='c';
        tablica[3][1]='0';
        
        tablica[4][0]='c';
        tablica[4][1]='1';
        
        tablica[5][0]='c';
        tablica[5][1]='0';
        
        tablica[6][0]='c';
        tablica[6][1]='2';
        
        tablica[7][0]='c';
        tablica[7][1]='0';
        
        tablica[8][0]='c';
        tablica[8][1]='0';
        
        tablica[9][0]='c';
        tablica[9][1]='0';
        
        tablica[10][0]='c';
        tablica[10][1]='0';
        
        tablica[11][0]='c';
        tablica[11][1]='0';
        

        repaint();
    }
 
    int i=0;
    
    
    public void paint(Graphics g) {
        
        karta = new ImageIcon("src/stol_gry.jpg");
        karta.paintIcon(this, g, 0, 0);
        
        String sciezka = new String();

        for(int j=i+5, k=0;i<j; i++, k++) {
            sciezka="src/"+tablica[i][0]+tablica[i][1]+".png";
            karta = new ImageIcon(sciezka);
            karta.paintIcon(this, g, k*107+84, 600);
        }
        karta = new ImageIcon("src/up.png");
        karta.paintIcon(this, g, 5*107+84, 600);
        if(tablica[i+1][0]=='n')
            i=0;
    }
    
    @Override
     public void mouseDragged(MouseEvent arg0) {
         //System.out.println("mouseDragged");
     }
 
     @Override
     public void mouseMoved(MouseEvent arg0) {
         //System.out.println("mouseMoved");
     }
 
     @Override
     public void mouseClicked(MouseEvent e) {
         System.out.println("mouseClicked");
         int x,y;
         x=e.getX();
         y=e.getY();
         if(y>600&&y<745) {
              System.out.println("Trafiles w dok krolu jebany");
         x=(x-84)/107;
             System.out.println("Brawo, kliknales karte numer:"+x);
         }
         if(x==5)
             repaint();
     }
 
     @Override
     public void mouseEntered(MouseEvent e) {
         //System.out.println("mouseEntered");
     }
 
     @Override
     public void mouseExited(MouseEvent e) {
         //System.out.println("mouseExited");
     }
 
     @Override
     public void mousePressed(MouseEvent e) {
         //System.out.println("mousePressed");
     }
 
     @Override
     public void mouseReleased(MouseEvent e) {
         //System.out.println("mouseReleased");
     }
     
}

