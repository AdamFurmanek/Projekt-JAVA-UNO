

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
    	addMouseListener(this);
        addMouseMotionListener(this);
        
        for(int i =0;i<108;i++){
        	tablica[i]= new String();
            tablica[i]="n";
        }
         
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
        
    	////////////////////NALOZENIE TLA (STOLU)////////////////////
        karta = new ImageIcon("src/png/stol1.png");
        karta.paintIcon(this, g, 0, 0);
        
        ////////////////////NALOZENIE STRZALEK KIERUNKOWYCH////////////////////
        if(Klient.kierunek==1)
        	karta = new ImageIcon("src/png/zegar1.png");
        else
        	karta = new ImageIcon("src/png/zegar2.png");
        karta.paintIcon(this, g, 210, 210);
        
        ////////////////////NALOZENIE KART GRACZA////////////////////
        String sciezka = new String();
        for(int i=licznikDoku-14, j=0;i<licznikDoku; i++, j++) {
        	
            sciezka="src/png/"+tablica[i]+".png";
            karta = new ImageIcon(sciezka);

            if(ruchPole==1&&ruchX==j)
                karta.paintIcon(this, g, j*30+47, 480);	
            else
            	karta.paintIcon(this, g, j*30+47, 600);
        }
        
    	////////////////////NALOZENIE KART PRZECIWNIKOW////////////////////
    	int j=Klient.graczLewo/2;
    	if(j>6)
    		j=6;
    	karta = new ImageIcon("src/png/unoLewo.png");
    	for(int i=0;i<Klient.graczLewo&&i<14;i++) {
        	karta.paintIcon(this, g, 47, 130+(i+6-j)*20);
    	}
    	
    	j=Klient.graczGora/2;
    	if(j>6)
    		j=6;
    	karta = new ImageIcon("src/png/unoGora.png");
    	for(int i=0;i<Klient.graczGora&&i<14;i++) {
        	karta.paintIcon(this, g, 476-(i+6-j)*20, 47);
    	}
    	
    	j=Klient.graczPrawo/2;
    	if(j>6)
    		j=6;
    	karta = new ImageIcon("src/png/unoPrawo.png");
    	for(int i=0;i<Klient.graczPrawo&&i<14;i++) {
        	karta.paintIcon(this, g, 605, 390-(i+6-j)*20);
    	}
        
        ////////////////////NALOZENIE STRZALEK////////////////////
        if(tablica[14]!="n") {
        	if(ruchPole==2)
        		karta = new ImageIcon("src/png/strzalka2.png");
        	else
        		karta = new ImageIcon("src/png/strzalka1.png");
        	karta.paintIcon(this, g, 47, 753);	
        }
        
        if(Klient.graczLewo>14) {
        	karta = new ImageIcon("src/png/strzalkaLewo.png");
			karta.paintIcon(this, g, 15, 130);	
        }
        if(Klient.graczGora>14) {
        	karta = new ImageIcon("src/png/strzalkaGora.png");
			karta.paintIcon(this, g, 528, 15);	
        }
        if(Klient.graczPrawo>14) {
        	karta = new ImageIcon("src/png/strzalkaPrawo.png");
			karta.paintIcon(this, g, 756, 442);	
        }
        
        ////////////////////NALOZENIE STOSU KART////////////////////
        if(ruchPole==3)
        	karta = new ImageIcon("src/png/uno2.png");
        else
        	karta = new ImageIcon("src/png/uno3.png");
    	karta.paintIcon(this, g, 600, 593);
    	////////////////////NALOZENIE KARTY NA STOLE////////////////////
    	sciezka="src/png/"+Klient.kartaStol+".png";
    	karta = new ImageIcon(sciezka);
    	karta.paintIcon(this, g, 349, 327);
 
    }
    
    @Override
     public void mouseDragged(MouseEvent arg0) {
     }
 
     @Override
     public void mouseMoved(MouseEvent arg0) {

         ruchX2=arg0.getX();
         ruchY=arg0.getY();
    	 ////////////////////GRACZ WJECHAL NA WLASNA STRZALKE////////////////////
         if(ruchY>753&&ruchY<780&&ruchX2>47&&ruchX2<97) {
        	 if(ruchPole!=2) {
        		 ruchPole=2;
        		 repaint();
        	 }
         }
         
    	 ////////////////////GRACZ WJECHAL NA STOS KART////////////////////
         else if(ruchY>560&&ruchY<753&&ruchX2>610&&ruchX2<770) {
        	 if(ruchPole!=3) {
        		 ruchPole=3;
        		 repaint();
        	 }
         }
         
    	 ////////////////////GRACZ WJECHAL NA WLASNY DOK////////////////////
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
         
    	 ////////////////////GRACZ NIE WJECHAL NA ZADNE POLE////////////////////
         else {
        	 if(ruchPole!=0) {
        		 ruchPole=0;
        		 repaint();
        	 }
         }
     }
 
     @Override
     public void mouseClicked(MouseEvent e) {
    	 //if(ruchX>=0&&ruchX<=13&&ruchPole==1)
    		 //System.out.println(tablica[ruchX+licznikDoku-14]);
    	 
    	 ////////////////////GRACZ KLIKNAL WLASNA STRZALKE////////////////////
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

