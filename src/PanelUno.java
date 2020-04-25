

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

public class PanelUno extends JPanel implements MouseListener, MouseMotionListener{
    
    public ImageIcon karta;
    int licznikDoku=14;
    int ruchX=0, ruchY=0;
    int ruchX2=0, ruchPole=0;
    int ileWyswietla;
    int zmianaKoloru=0;
    Font[] font = new Font[3];
    FontMetrics fontMetrics;
    public PanelUno() {
    	repaint();
    	addMouseListener(this);
        addMouseMotionListener(this);
        
        
        
        font[0] = new Font("Calibri", Font.BOLD, 36);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(90), 0, 0);
        font[1] = font[0].deriveFont(affineTransform);
        affineTransform.rotate(Math.toRadians(180), 0, 0);
        font[2] = font[0].deriveFont(affineTransform);
        fontMetrics = getFontMetrics(font[0]);
    }
    
    public void paint(Graphics g) {
    	
        String sciezka = new String();
    	////////////////////NALOZENIE TLA (STOLU)////////////////////
        karta = new ImageIcon("src/png/stol1.png");
        karta.paintIcon(this, g, 0, 0);
        ////////////////////NALOZENIE PLUSOW////////////////////
        sciezka="src/png/plus"+Klient.dobranie+".png";
        karta = new ImageIcon(sciezka);
        karta.paintIcon(this, g, 0, 0);
    	////////////////////NALOZENIE CHMUREK////////////////////
        	karta = new ImageIcon("src/png/light.png");
        	if(Klient.gracz==1&&Klient.tura==2||Klient.gracz==2&&Klient.tura==3||Klient.gracz==3&&Klient.tura==4||Klient.gracz==4&&Klient.tura==1)
        		karta.paintIcon(this, g, -300, 25);
        	if(Klient.gracz==1&&Klient.tura==3||Klient.gracz==2&&Klient.tura==4||Klient.gracz==3&&Klient.tura==1||Klient.gracz==4&&Klient.tura==2)
        		karta.paintIcon(this, g, 0, -170);
        	if(Klient.gracz==1&&Klient.tura==4||Klient.gracz==2&&Klient.tura==1||Klient.gracz==3&&Klient.tura==2||Klient.gracz==4&&Klient.tura==3)
        		karta.paintIcon(this, g, 275, 33);
        	if(Klient.gracz==Klient.tura) {
        		karta.paintIcon(this, g, -200, 450);
        		karta.paintIcon(this, g, 0, 450);
        	}
        
        ////////////////////NALOZENIE STRZALEK KIERUNKOWYCH////////////////////
        karta = new ImageIcon("src/png/zegar"+Klient.kolor+""+Klient.kierunek+".png");
        karta.paintIcon(this, g, 210, 210);
        
        ////////////////////NALOZENIE KART GRACZA////////////////////

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
			karta.paintIcon(this, g, 40, 130);	
        }
        if(Klient.graczGora>14) {
        	karta = new ImageIcon("src/png/strzalkaGora.png");
			karta.paintIcon(this, g, 528, 40);	
        }
        if(Klient.graczPrawo>14) {
        	karta = new ImageIcon("src/png/strzalkaPrawo.png");
			karta.paintIcon(this, g, 731, 442);	
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
 
    	////////////////////NALOZENIE ZMIANY KOLORU////////////////////
    	if(zmianaKoloru==1||zmianaKoloru==2) {
    		karta = new ImageIcon("src/png/kolor.png");
    		karta.paintIcon(this, g, 250, 520);
    	}
        ////////////////////NALOZENIE STRZALKI POD STOSEM////////////////////
        if(Klient.czyDobralPierwsza==1) {
        	if(ruchPole==5)
        		karta = new ImageIcon("src/png/strzalka2.png");
        	else
        		karta = new ImageIcon("src/png/strzalka1.png");
        	karta.paintIcon(this, g, 700, 753);	
        }
        ////////////////////NALOZENIE OBRAZKÓW UNO////////////////////
        karta = new ImageIcon("src/png/ostatnia.png");
        if(Klient.graczLewo==1)
        	karta.paintIcon(this, g, 70,310);
        if(Klient.graczPrawo==1)
        	karta.paintIcon(this, g, 630,330);
        if(Klient.graczGora==1)
        	karta.paintIcon(this, g, 355,149);
        if(Klient.tablica[1]=="n"&&Klient.tablica[0]!="n") {
        if(ruchPole==1&&ruchX==0)
        	karta.paintIcon(this, g, 43,594);

        else
        	karta.paintIcon(this, g, 43,714);
        }
        
        g.setFont(font[0]);
    	g.drawString(Klient.nick[0]+" - "+Klient.punkty[0]+" pkt", 105, 780);
    	g.drawString(Klient.nick[2]+" - "+Klient.punkty[2]+" pkt", 400-fontMetrics.stringWidth(Klient.nick[1]+" - "+Klient.punkty[2]+" pkt")/2, 33);
        g.setFont(font[2]);
    	g.drawString(Klient.nick[1]+" - "+Klient.punkty[1]+" pkt", 33, 300+fontMetrics.stringWidth(Klient.nick[2]+" - "+Klient.punkty[1]+" pkt")/2);
        g.setFont(font[1]);
    	g.drawString(Klient.nick[3]+" - "+Klient.punkty[3]+" pkt", 760, 320-fontMetrics.stringWidth(Klient.nick[3]+" - "+Klient.punkty[3]+" pkt")/2);
    }
    
    @Override
     public void mouseDragged(MouseEvent arg0) {
     }
 
     @Override
     public void mouseMoved(MouseEvent arg0) {

         ruchX2=arg0.getX();
         ruchY=arg0.getY();
         
         ////////////////////GRACZ WJECHAL NA ZMIANE KOLORU////////////////////
         if(ruchY>520&&ruchY<580&&ruchX2>250&&ruchX2<550&&(zmianaKoloru==1||zmianaKoloru==2)) {
        	 if(ruchPole!=4) {
        		 ruchPole=4;
        		 repaint();
        	 }
         }
         
    	 ////////////////////GRACZ WJECHAL NA WLASNA STRZALKE////////////////////
         else if(ruchY>753&&ruchY<780&&ruchX2>47&&ruchX2<97) {
        	 if(ruchPole!=2) {
        		 ruchPole=2;
        		 repaint();
        	 }
         }
         
    	 ////////////////////GRACZ WJECHAL NA WLASNA STRZALKE////////////////////
         else if(ruchY>753&&ruchY<780&&ruchX2>700&&ruchX2<750&&Klient.czyDobralPierwsza==1) {
        	 if(ruchPole!=5) {
        		 ruchPole=5;
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
    	 else if (Klient.kolor==Klient.tablica[ruchX+licznikDoku-14].charAt(0)) {
    		 //JEŒLI GRACZ MA DOBRAÆ
    		 if(Klient.dobranie>0) {
    			 //JEŒLI GRACZ MA TEN SAM ZNAK(+2 lub +4)
    			 if(Klient.kartaStol.charAt(1)==Klient.tablica[ruchX+licznikDoku-14].charAt(1))
    				 return true;
    			 else
    				 return false;
    		 }
    		 //JEŒLI NIE MUSI DOBRAÆ
    		 else {
    			 return true;
    		 }
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
     public void mouseReleased(MouseEvent e) {
    	 
    	 if(Klient.wygrana==1) {
    		 System.out.println("ktos wygral");
    		 Klient.wyslanie("cokolwiek");
    		 Klient.wygrana=0;
    		 licznikDoku=14;
    	 }
    	 else {
    	 ////////////////////GRACZ KLIKNAL WLASNA STRZALKE////////////////////
    	 if(Klient.tablica[14]!="n"&&ruchPole==2) {
    		if(Klient.tablica[licznikDoku]=="n")
        	 	licznikDoku=14;
         	else
         		licznikDoku=licznikDoku+14;
            repaint();
    	 }
    	 if(Klient.tura==Klient.gracz) {
         ////////////////////GRACZ KLIKNAL WYBOR KOLORU///////////////
    	 if(ruchPole==4) {
    		 Klient.czyDobralPierwsza=0;
    		 if(ruchX2>475) {
    			 if(zmianaKoloru==1)
    				 Klient.wyslanie("bcy");
	    			 
    			 else
    				 Klient.wyslanie("bfy");
    		 }
    		 else if(ruchX2>400) {
    			 if(zmianaKoloru==1)
    				 Klient.wyslanie("bcn");
    			 else
    				 Klient.wyslanie("bfn");
    		 }
    		 else if(ruchX2>325) {
    			 if(zmianaKoloru==1)
    				 Klient.wyslanie("bcc");
    			 else
    				 Klient.wyslanie("bfc");
    		 }
    		 else {
    			 if(zmianaKoloru==1)
    				 Klient.wyslanie("bcz");
    			 else
    				 Klient.wyslanie("bfz");
    		 }
    		 zmianaKoloru=0;
    		 repaint();
    		 Klient.tura=5;
    	 }
    	 else {
    		 zmianaKoloru=0;
    		 repaint();
    	 ////////////////////GRACZ KLIKNAL WLASNA KARTE////////////////////
    	 if((ruchX>=0)&&(ruchX<=ileWyswietla)&&(ruchPole==1)) {
    		if(sprawdzenie()) {
    			if(Klient.tablica[ruchX+licznikDoku-14].charAt(0)=='b') {
    				if(Klient.tablica[ruchX+licznikDoku-14].charAt(1)=='c')
    					zmianaKoloru=1;
    				if(Klient.tablica[ruchX+licznikDoku-14].charAt(1)=='f')
    					zmianaKoloru=2;
    				repaint();
    			}
    			else {
    				Klient.czyDobralPierwsza=0;
    				Klient.wyslanie(Klient.tablica[ruchX+licznikDoku-14]);
    				Klient.tura=5;
    			}
    		}
    	}
    	 
    	 ////////////////////GRACZ KLIKNAL STOS KART////////////////////
    	 if(ruchPole==3&&Klient.czyDobralPierwsza==0) {
    		 if(Klient.dobranie==0) {
    			 Klient.czyDobralPierwsza=1;
    			 Klient.wyslanie("dx");
    		 }
    		 else {
    			 Klient.czyDobralPierwsza=1;
    			 Klient.wyslanie("dx");
    		 }
    		 Klient.tura=5;
    	 }
     }
    	 ////////////////////GRACZ KLIKNAL STRZALKE POD STOSEM////////////////////
    	 if(ruchPole==5) {
    		Klient.czyDobralPierwsza=0;
    		Klient.wyslanie("dy");
            repaint();
            Klient.tura=5;
    	 }
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
     public void mouseClicked(MouseEvent e) {
     }
     
}

