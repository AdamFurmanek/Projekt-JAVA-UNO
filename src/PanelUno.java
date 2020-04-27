
import java.awt.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

public class PanelUno extends JPanel implements MouseListener, MouseMotionListener {

	public ImageIcon obraz;

	public int wybranaKarta = -1, wybranePole = 0, myszX = 0, myszY = 0, licznikDoku = 14, ileWyswietla,
			zmianaKoloru = 0;

	public Random random = new Random();
	
	public Font[] font = new Font[3];
	public FontMetrics fontMetrics;

	public PanelUno() {
	
        
		repaint();
		addMouseListener(this);
		addMouseMotionListener(this);

		font[0] = new Font("Gill Sans Ultra Bold", Font.BOLD, 36);
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(Math.toRadians(90), 0, 0);
		font[1] = font[0].deriveFont(affineTransform);
		affineTransform.rotate(Math.toRadians(180), 0, 0);
		font[2] = font[0].deriveFont(affineTransform);
		fontMetrics = getFontMetrics(font[0]);

	}

	public void paint(Graphics g) {

		String sciezka = new String();
		//////////////////// NALOZENIE TLA (STOLU)////////////////////
		obraz = new ImageIcon("png/stol1.png");
		obraz.paintIcon(this, g, 0, 0);
		//////////////////// NALOZENIE PLUSOW////////////////////
		sciezka = "png/plus" + Klient.dobranie + ".png";
		obraz = new ImageIcon(sciezka);
		obraz.paintIcon(this, g, 0, 0);
		//////////////////// NALOZENIE CHMUREK////////////////////
		if (Klient.kartaStol.charAt(0) != 'a') {
			obraz = new ImageIcon("png/light.png");
			if (Klient.numerGracza == 1 && Klient.tura == 2 || Klient.numerGracza == 2 && Klient.tura == 3
					|| Klient.numerGracza == 3 && Klient.tura == 4 || Klient.numerGracza == 4 && Klient.tura == 1)
				obraz.paintIcon(this, g, -300, 25);
			if (Klient.numerGracza == 1 && Klient.tura == 3 || Klient.numerGracza == 2 && Klient.tura == 4
					|| Klient.numerGracza == 3 && Klient.tura == 1 || Klient.numerGracza == 4 && Klient.tura == 2)
				obraz.paintIcon(this, g, 0, -170);
			if (Klient.numerGracza == 1 && Klient.tura == 4 || Klient.numerGracza == 2 && Klient.tura == 1
					|| Klient.numerGracza == 3 && Klient.tura == 2 || Klient.numerGracza == 4 && Klient.tura == 3)
				obraz.paintIcon(this, g, 275, 33);
			if (Klient.numerGracza == Klient.tura) {
				obraz.paintIcon(this, g, -200, 450);
				obraz.paintIcon(this, g, 0, 450);
			}
		}
		//////////////////// NALOZENIE STRZALEK KIERUNKOWYCH////////////////////
		obraz = new ImageIcon("png/zegar" + Klient.kolor + "" + Klient.kierunek + ".png");
		obraz.paintIcon(this, g, 210, 210);

		//////////////////// NALOZENIE KART GRACZA////////////////////

		for (int i = licznikDoku - 14, j = 0; i < licznikDoku; i++, j++) {

			sciezka = "png/" + Klient.kartyGracza[i] + ".png";
			obraz = new ImageIcon(sciezka);

			if (wybranePole == 1 && wybranaKarta == j)
				obraz.paintIcon(this, g, j * 30 + 47, 480);
			else
				obraz.paintIcon(this, g, j * 30 + 47, 600);
		}

		//////////////////// NALOZENIE KART PRZECIWNIKOW////////////////////
		int j = Klient.iloscKart[1] / 2;
		if (j > 6)
			j = 6;
		obraz = new ImageIcon("png/unoLewo.png");
		for (int i = 0; i < Klient.iloscKart[1] && i < 14; i++) {
			obraz.paintIcon(this, g, 47, 130 + (i + 6 - j) * 20);
		}

		j = Klient.iloscKart[2] / 2;
		if (j > 6)
			j = 6;
		obraz = new ImageIcon("png/unoGora.png");
		for (int i = 0; i < Klient.iloscKart[2] && i < 14; i++) {
			obraz.paintIcon(this, g, 476 - (i + 6 - j) * 20, 47);
		}

		j = Klient.iloscKart[3] / 2;
		if (j > 6)
			j = 6;
		obraz = new ImageIcon("png/unoPrawo.png");
		for (int i = 0; i < Klient.iloscKart[3] && i < 14; i++) {
			obraz.paintIcon(this, g, 605, 390 - (i + 6 - j) * 20);
		}

		//////////////////// NALOZENIE STRZALEK////////////////////
		if (Klient.kartyGracza[14] != "n") {
			if (wybranePole == 2)
				obraz = new ImageIcon("png/strzalka2.png");
			else
				obraz = new ImageIcon("png/strzalka1.png");
			obraz.paintIcon(this, g, 47, 753);
		}

		if (Klient.iloscKart[1] > 14) {
			obraz = new ImageIcon("png/strzalkaLewo.png");
			obraz.paintIcon(this, g, 40, 130);
		}
		if (Klient.iloscKart[2] > 14) {
			obraz = new ImageIcon("png/strzalkaGora.png");
			obraz.paintIcon(this, g, 528, 40);
		}
		if (Klient.iloscKart[3] > 14) {
			obraz = new ImageIcon("png/strzalkaPrawo.png");
			obraz.paintIcon(this, g, 731, 442);
		}

		//////////////////// NALOZENIE STOSU KART////////////////////
		if (wybranePole == 3)
			obraz = new ImageIcon("png/uno2.png");
		else
			obraz = new ImageIcon("png/uno3.png");
		obraz.paintIcon(this, g, 600, 593);
		//////////////////// NALOZENIE KARTY NA STOLE////////////////////
		sciezka = "png/" + Klient.kartaStol + ".png";
		obraz = new ImageIcon(sciezka);
		obraz.paintIcon(this, g, 349, 327);

		//////////////////// NALOZENIE ZMIANY KOLORU////////////////////
		if (zmianaKoloru == 1 || zmianaKoloru == 2) {
			obraz = new ImageIcon("png/kolor.png");
			obraz.paintIcon(this, g, 250, 520);
		}
		//////////////////// NALOZENIE STRZALKI POD STOSEM////////////////////
		if (Klient.czyDobralPierwsza == 1) {
			if (wybranePole == 5)
				obraz = new ImageIcon("png/strzalka2.png");
			else
				obraz = new ImageIcon("png/strzalka1.png");
			obraz.paintIcon(this, g, 700, 753);
		}
		//////////////////// NALOZENIE OBRAZKÓW UNO////////////////////
		obraz = new ImageIcon("png/ostatnia.png");
		if (Klient.iloscKart[1] == 1)
			obraz.paintIcon(this, g, 70, 310);
		if (Klient.iloscKart[2] == 1)
			obraz.paintIcon(this, g, 355, 149);
		if (Klient.iloscKart[3] == 1)
			obraz.paintIcon(this, g, 630, 330);
		if (Klient.kartyGracza[1] == "n" && Klient.kartyGracza[0] != "n") {
			if (wybranePole == 1 && wybranaKarta == 0)
				obraz.paintIcon(this, g, 43, 594);

			else
				obraz.paintIcon(this, g, 43, 714);
		}

		g.setColor(Color.BLACK);
		g.setFont(font[0]);
		if(Klient.kartaStol.charAt(0) == 'a') {
			g.drawString(Klient.oczekiwanieNaGraczy, 105, 780);
		}
		else if (Klient.odliczanieNowejRundy == 0)
			g.drawString(Klient.nazwaGracza[0] + " - " + Klient.punkty[0] + " pkt", 105, 780);
		else
			g.drawString("Nastêpna runda za " + Klient.odliczanieNowejRundy + "...", 105, 780);
		g.drawString(Klient.nazwaGracza[2] + " - " + Klient.punkty[2] + " pkt",
				400 - fontMetrics.stringWidth(Klient.nazwaGracza[2] + " - " + Klient.punkty[2] + " pkt") / 2, 33);
		g.setFont(font[2]);
		g.drawString(Klient.nazwaGracza[1] + " - " + Klient.punkty[1] + " pkt", 33,
				300 + fontMetrics.stringWidth(Klient.nazwaGracza[1] + " - " + Klient.punkty[1] + " pkt") / 2);
		g.setFont(font[1]);
		g.drawString(Klient.nazwaGracza[3] + " - " + Klient.punkty[3] + " pkt", 760,
				320 - fontMetrics.stringWidth(Klient.nazwaGracza[3] + " - " + Klient.punkty[3] + " pkt") / 2);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

		myszX = arg0.getX();
		myszY = arg0.getY();

		//////////////////// GRACZ WJECHAL NA ZMIANE KOLORU////////////////////
		if (myszY > 520 && myszY < 580 && myszX > 250 && myszX < 550 && (zmianaKoloru == 1 || zmianaKoloru == 2)) {

			if (wybranePole != 4) {
				wybranePole = 4;
				repaint();
				Klient.dzwiek("karta1");
			}
		}

		//////////////////// GRACZ WJECHAL NA WLASNA STRZALKE////////////////////
		else if (myszY > 753 && myszY < 780 && myszX > 47 && myszX < 97) {
			if (wybranePole != 2) {
				wybranePole = 2;
				repaint();
				Klient.dzwiek("strzalka1");
			}
		}

		//////////////////// GRACZ WJECHAL NA WLASNA STRZALKE////////////////////
		else if (myszY > 753 && myszY < 780 && myszX > 700 && myszX < 750 && Klient.czyDobralPierwsza == 1) {
			if (wybranePole != 5) {
				wybranePole = 5;
				repaint();
				Klient.dzwiek("strzalka1");
			}
		}

		//////////////////// GRACZ WJECHAL NA STOS KART////////////////////
		else if (myszY > 560 && myszY < 753 && myszX > 610 && myszX < 770&&Klient.kartaStol.charAt(0) != 'a') {
			if (wybranePole != 3) {
				wybranePole = 3;
				repaint();
				Klient.dzwiek("stos1");
			}
		}

		//////////////////// GRACZ WJECHAL NA WLASNY DOK////////////////////
		else if (myszY > 560 && myszY < 753) {
			if (wybranePole != 1) {
				wybranePole = 1;
				repaint();
			}
			
			ileWyswietla = -1;
			for (int i = licznikDoku - 14; i < licznikDoku; i++) {
				if (Klient.kartyGracza[i] != "n")
					ileWyswietla++;
			}
			
			myszX = (myszX - 47) / 30;
			
			if(myszX>ileWyswietla&&myszX<=ileWyswietla+3)
				myszX=ileWyswietla;
			
			if(myszX<=ileWyswietla&&myszX>=0) {
			if (myszX != wybranaKarta) {
				wybranaKarta = myszX;

				repaint();
				Klient.dzwiek("karta1");

			}
			}
			else {
				wybranaKarta=-1;
				repaint();
			}
		}

		//////////////////// GRACZ NIE WJECHAL NA ZADNE POLE////////////////////
		else {
			if (wybranePole != 0) {
				wybranePole = 0;
				repaint();
			}
		}
	}

	public boolean sprawdzenie() {
		// TEN SAM ZNAK
		if (Klient.kartaStol.charAt(1) == Klient.kartyGracza[wybranaKarta + licznikDoku - 14].charAt(1))
			return true;

		// TEN SAM KOLOR
		else if (Klient.kolor == Klient.kartyGracza[wybranaKarta + licznikDoku - 14].charAt(0)) {
			// JEŒLI GRACZ MA DOBRAÆ
			if (Klient.dobranie > 0) {
				// JEŒLI GRACZ MA TEN SAM ZNAK(+2 lub +4)
				if (Klient.kartaStol.charAt(1) == Klient.kartyGracza[wybranaKarta + licznikDoku - 14].charAt(1))
					return true;
				else
					return false;
			}
			// JEŒLI NIE MUSI DOBRAÆ
			else {
				return true;
			}
		}
		// ZMIANA KOLORU
		else if (Klient.kartyGracza[wybranaKarta + licznikDoku - 14].charAt(0) == 'b' && Klient.dobranie == 0)
			return true;

		// ZMIANA KOLORU +4
		else if (Klient.kartyGracza[wybranaKarta + licznikDoku - 14] == "bf" && Klient.kartaStol.charAt(1) != 't')
			return true;

		// INNE
		else
			return false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		//////////////////// GRACZ KLIKNAL WLASNA STRZALKE////////////////////
		if (Klient.kartyGracza[14] != "n" && wybranePole == 2) {
			if (Klient.kartyGracza[licznikDoku] == "n")
				licznikDoku = 14;
			else
				licznikDoku = licznikDoku + 14;
			repaint();
			Klient.dzwiek("strzalka2");
		}
		if (Klient.tura == Klient.numerGracza) {
			//////////////////// GRACZ KLIKNAL WYBOR KOLORU///////////////
			if (wybranePole == 4) {
				Klient.dzwiek("karta2");
				Klient.czyDobralPierwsza = 0;
				if (myszX > 475) {
					if (zmianaKoloru == 1)
						Klient.out.println("bcy");

					else
						Klient.out.println("bfy");
				} else if (myszX > 400) {
					if (zmianaKoloru == 1)
						Klient.out.println("bcn");
					else
						Klient.out.println("bfn");
				} else if (myszX > 325) {
					if (zmianaKoloru == 1)
						Klient.out.println("bcc");
					else
						Klient.out.println("bfc");
				} else {
					if (zmianaKoloru == 1)
						Klient.out.println("bcz");
					else
						Klient.out.println("bfz");
				}
				zmianaKoloru = 0;
				repaint();
				Klient.tura = 5;
			} else {
				zmianaKoloru = 0;
				repaint();
				//////////////////// GRACZ KLIKNAL WLASNA KARTE////////////////////
				if ((wybranaKarta >= 0) && (wybranaKarta <= ileWyswietla) && (wybranePole == 1)) {
					if (sprawdzenie()) {
						if (Klient.kartyGracza[wybranaKarta + licznikDoku - 14].charAt(0) == 'b') {
							if (Klient.kartyGracza[wybranaKarta + licznikDoku - 14].charAt(1) == 'c')
								zmianaKoloru = 1;
							if (Klient.kartyGracza[wybranaKarta + licznikDoku - 14].charAt(1) == 'f')
								zmianaKoloru = 2;
							repaint();
						} else {
							Klient.czyDobralPierwsza = 0;
							Klient.out.println(Klient.kartyGracza[wybranaKarta + licznikDoku - 14]);
							Klient.tura = 5;
							Klient.dzwiek("karta2");
						}
					}
				}

				//////////////////// GRACZ KLIKNAL STOS KART////////////////////
				if (wybranePole == 3 && Klient.czyDobralPierwsza == 0) {
					if (Klient.dobranie == 0) {
						Klient.czyDobralPierwsza = 1;
						Klient.out.println("dx");
					} else {
						Klient.czyDobralPierwsza = 1;
						Klient.out.println("dx");
					}
					Klient.tura = 5;
					Klient.dzwiek("stos2");
				}
			}
			//////////////////// GRACZ KLIKNAL STRZALKE POD STOSEM////////////////////
			if (wybranePole == 5) {
				Klient.czyDobralPierwsza = 0;
				Klient.out.println("dy");
				repaint();
				Klient.tura = 5;
				
				if(Klient.dobranie==0)
					Klient.dzwiek("strzalka3");
				else
					Klient.dzwiek("stos2");
				
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
