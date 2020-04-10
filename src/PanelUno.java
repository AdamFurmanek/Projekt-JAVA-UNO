

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
    public static String[] tablica = new String[108];
    int licznikDoku=14;
    int ruchX=0, ruchY=0;
    int ruchX2=0, ruchPole=0;

    
    public PanelUno() {
        
        for(int i =0;i<108;i++){
        	tablica[i]= new String();
            tablica[i]="n";
            }

         addMouseListener(this);
         addMouseMotionListener(this);

         
         	tablica[0]="z7";
         	tablica[1]="y4";
         	tablica[2]="z1";
         	tablica[3]="zz";
         	tablica[4]="n0";
         	tablica[5]="bz";
         	tablica[6]="c5";
         	tablica[7]="n7";
         	tablica[8]="c4";
         	tablica[9]="y2";
         	tablica[10]="zt";
         	tablica[11]="z8";
         	tablica[12]="z2";
         	tablica[13]="y0";
         	tablica[14]="np";
         	tablica[15]="bf";
         	tablica[16]="z8";
         	tablica[17]="y5";
        repaint();
    }
    
    
    public void paint(Graphics g) {
        
        karta = new ImageIcon("src/png/stol.png");
        karta.paintIcon(this, g, 0, 0);
        
        String sciezka = new String();

        for(int i=licznikDoku-14, j=0;i<licznikDoku; i++, j++) {
            sciezka="src/png/"+tablica[i]+".png";
            karta = new ImageIcon(sciezka);
            //karta = new ImageIcon(new ImageIcon(sciezka).getImage().getScaledInstance(107, 153, java.awt.Image.SCALE_SMOOTH));
            if(ruchPole==1&&ruchX==j)
                karta.paintIcon(this, g, j*30+47, 480);	
            else
            	karta.paintIcon(this, g, j*30+47, 600);
            if(tablica[14]!="n") {
            	if(ruchPole==2)
            		karta = new ImageIcon("src/png/strzalka2.png");
            	else
            		karta = new ImageIcon("src/png/strzalka1.png");
            	karta.paintIcon(this, g, 47, 753);	
            }
            if(ruchPole==3)
            	karta = new ImageIcon("src/png/uno2.png");
            else
            	karta = new ImageIcon("src/png/uno3.png");
        	karta.paintIcon(this, g, 600, 593);
        }
    }
    
    @Override
     public void mouseDragged(MouseEvent arg0) {
     }
 
     @Override
     public void mouseMoved(MouseEvent arg0) {
    	 
         ruchX2=arg0.getX();
         ruchY=arg0.getY();
         if(ruchY>753&&ruchY<780&&ruchX2>47&&ruchX2<97) {
        	 if(ruchPole!=2) {
        		 ruchPole=2;
        		 repaint();
        	 }
         }
         else if(ruchY>560&&ruchY<753&&ruchX2>610&&ruchX2<770) {
        	 if(ruchPole!=3) {
        		 ruchPole=3;
        		 repaint();
        	 }
         }
         else if(ruchY>560&&ruchY<753) {
        	 if(ruchPole!=1) {
        		 ruchPole=1;
        		 repaint();
        	 }
        	 ruchX2=(ruchX2-47)/30;
        	 if(ruchX2!=ruchX) {
        		 ruchX=ruchX2;
        		 int ileWyswietla=0;
        		 for(int i=licznikDoku-14;i<licznikDoku;i++) {
        			 if(tablica[i]!="n")
        				 ileWyswietla++;
        		 }
        		 if(ruchX>=ileWyswietla&&ruchX<ileWyswietla+3)
        			 ruchX=ileWyswietla-1;
        		 repaint();
        	 }
         }

         else {
        	 if(ruchPole!=0) {
        		 ruchPole=0;
        		 repaint();
        	 }
         }
     }
 
     @Override
     public void mouseClicked(MouseEvent e) {
    	 if(ruchX>=0&&ruchX<=13&&ruchPole==1)
    		 System.out.println(tablica[ruchX+licznikDoku-14]);
    	 
    	 if(tablica[14]!="n"&&ruchPole==2) {
    		if(tablica[licznikDoku+1]=="n")
        	 	licznikDoku=14;
         	else
         		licznikDoku=licznikDoku+14;
            repaint();
    	 }
     }
 
     @Override
     public void mouseEntered(MouseEvent e) {
     }
 
     @Override
     public void mouseExited(MouseEvent e) {
     }
 
     @Override
     public void mousePressed(MouseEvent e) {
     }
 
     @Override
     public void mouseReleased(MouseEvent e) {
     }
     
}

