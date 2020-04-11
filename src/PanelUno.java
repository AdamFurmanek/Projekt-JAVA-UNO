

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
import java.awt.Graphics;

public class PanelUno extends JPanel implements MouseListener, MouseMotionListener{
    
    public ImageIcon karta;
    int licznikDoku=14;
    int ruchX=0, ruchY=0;
    int ruchX2=0, ruchPole=0;
    int ileWyswietla;

    
    public PanelUno() {
    	repaint();
    	addMouseListener(this);
        addMouseMotionListener(this);

    }
    
    
    public void paint(Graphics g) {
        
    	////////////////////NALOZENIE TLA (STOLU)////////////////////
        karta = new ImageIcon("src/png/stol1.png");
        karta.paintIcon(this, g, 0, 0);
        
        ////////////////////NALOZENIE STRZALEK KIERUNKOWYCH////////////////////
        if(Klient.kierunek==1) {
        	karta = new ImageIcon("src/png/zegar1.png");
        	karta.paintIcon(this, g, 210, 210);
        }
        else if(Klient.kierunek==2) {
        	karta = new ImageIcon("src/png/zegar2.png");
        	karta.paintIcon(this, g, 210, 210);
        }
        else {}
        
        ////////////////////NALOZENIE KART GRACZA////////////////////
        String sciezka = new String();
        for(int i=licznikDoku-14, j=0;i<licznikDoku; i++, j++) {
        	
            sciezka="src/png/"+Klient.tablica[i]+".png";
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
        if(Klient.tablica[14]!="n") {
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
        		 ileWyswietla=0;
        		 for(int i=licznikDoku-14;i<licznikDoku;i++) {
        			 if(Klient.tablica[i]!="n")
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
 
     public boolean sprawdzenie() {
    	 //TEN SAM ZNAK
    	 if(Klient.kartaStol.charAt(1)==Klient.tablica[ruchX+licznikDoku-14].charAt(1))
    		 return true;
    	 
    	 //TEN SAM KOLOR
    	 else if (Klient.kartaStol.charAt(0)==Klient.tablica[ruchX+licznikDoku-14].charAt(0)) {
    		 //JEŒLI GRACZ MA DOBRAÆ
    		 if(Klient.dobranie>0) {
    			 //JEŒLI GRACZ MA TEN SAM ZNAK(+2 lub +4)
    			 if(Klient.kartaStol.charAt(1)==Klient.tablica[ruchX+licznikDoku-14].charAt(1))
    				 return true;
    			 else
    				 return false;
    		 }
    		 //JEŒLI NIE MUSI DOBRAÆ
    		 else
    			 return true;
    	 }
    	 //ZMIANA KOLORU
    	 else if(Klient.tablica[ruchX+licznikDoku-14].charAt(0)=='b'&&Klient.dobranie==0)
    		 return true;
    		 
    	 //ZMIANA KOLORU +4
    	 else if(Klient.tablica[ruchX+licznikDoku-14]=="bf"&&Klient.kartaStol.charAt(1)!='t')
    		 return true;
    		 
    	 //INNE
    	 else
    		 return false;
     }
     
     @Override
     public void mouseClicked(MouseEvent e) {
    	 
    	 ////////////////////GRACZ KLIKNAL WLASNA KARTE////////////////////
    	 if((ruchX>=0)&&(ruchX<=ileWyswietla)&&(ruchPole==1)) {
    		if(sprawdzenie())
    			//WYSLIJ NA SERWER
    			System.out.println("Wybieram: "+Klient.tablica[ruchX+licznikDoku-14]);
    	 }
    	 
    	 ////////////////////GRACZ KLIKNAL WLASNA STRZALKE////////////////////
    	 if(Klient.tablica[14]!="n"&&ruchPole==2) {
    		System.out.println("teraz:" + Klient.tablica[licznikDoku]);
    		if(Klient.tablica[licznikDoku+1]=="n")
        	 	licznikDoku=14;
         	else
         		licznikDoku=licznikDoku+14;
            repaint();
            System.out.println("licznik: " + licznikDoku);
    	 }
    	 
    	 ////////////////////GRACZ KLIKNAL STOS KART////////////////////
    	 if(ruchPole==3) {
    		 if(Klient.dobranie==0) {
    			 //WYŒLIJ NA SERWER
    			 System.out.println("dobieram 1");
    		 }
    		 else {
    			 //WYŒLIJ NA SERWER
    			 System.out.println("dobieram "+Klient.dobranie); 
    		 }
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

