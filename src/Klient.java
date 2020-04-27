
import java.net.*;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.awt.EventQueue;

public class Klient {

	public static Clip clip;
	
	public static Socket socket;
	public static PrintWriter out;
	public static BufferedReader in;

	public static Okno okno;

	public static int odliczanieNowejRundy = 0, numerGracza, tura = 1, kierunek = 0, dobranie = 0, czyDobralPierwsza = 0;
	public static String kartaStol = new String("a"), oczekiwanieNaGraczy = new String("Oczekiwanie na graczy");
	public static char kolor = 'c';

	public static int[] iloscKart = { 7, 7, 7, 7 };
	public static int[] punkty = { 0, 0, 0, 0 };
	public static int[][] konwersja = { { 0, 1, 2, 3 }, { 3, 0, 1, 2 }, { 2, 3, 0, 1 }, { 1, 2, 3, 0 } };
	public static String[] kartyGracza = new String[108];
	public static String[] nazwaGracza = { "?", "?", "?", "?" };
	
	public static Random random = new Random();

	public static void main(String[] args) throws IOException {
		
		nazwaGracza[0] = (String) JOptionPane.showInputDialog(null, "Podaj swój nazwaGracza (1-14 znaków):",
				"Nazwa gracza", JOptionPane.INFORMATION_MESSAGE, null, null, "Mistrz");
		if (nazwaGracza[0] == null)
			System.exit(0);
		if (nazwaGracza[0].isEmpty() || nazwaGracza[0].length() > 14)
			nazwaGracza[0] = "Gracz";

		String ip = (String) JOptionPane.showInputDialog(null, "Podaj IP serwera", "IP serwera",
				JOptionPane.INFORMATION_MESSAGE, null, null, "localhost");
		if (ip == null)
			System.exit(0);

		try {
			socket = new Socket(ip, 4999);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.println(nazwaGracza[0]);
			numerGracza = ((int) odbierz().charAt(0)) - 48;
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "Nie mo¿na po³¹czyæ z serwerem o podanym IP.", "Brak po³¹czenia",
					JOptionPane.DEFAULT_OPTION);
			System.exit(0);
		}

		for (int i = 0; i < 108; i++) {
			kartyGracza[i] = new String();
			kartyGracza[i] = "n";
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Okno();
			}
		});
		
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
		Clip czekanie = dzwiek("czekanie");
		
		while(true) {
			Okno.panelUno.repaint();
			oczekiwanieNaGraczy+=".";
			if(oczekiwanieNaGraczy.length()==25)
				oczekiwanieNaGraczy="Oczekiwanie na graczy";
			if(in.ready()) {
				for (int i = 0; i < 4; i++)
					nazwaGracza[konwersja[numerGracza - 1][i]] = odbierz();
				break;
			}
			try {
				Thread.sleep(600);
			}
			catch (Exception e) {
			}
		}
		
		czekanie.stop();

		Clip tlo = dzwiek("muzyka"+(random.nextInt(4)+1));

		while (true) {
			
			odebranie();
			if (kartyGracza[0] == "n" || iloscKart[1] == 0 || iloscKart[2] == 0 || iloscKart[3] == 0) {
				tlo.stop();
				dzwiek("wygrana"+(random.nextInt(4)+1));
				Okno.panelUno.licznikDoku = 14;
				odebranie();
				tura = 5;
				try {
					for (odliczanieNowejRundy = 10; odliczanieNowejRundy > 0; odliczanieNowejRundy--) {
						Okno.panelUno.repaint();
						Thread.sleep(1000);
					}
				} catch (Exception e) {
				}
				for (int i = 0; i < 4; i++)
					punkty[konwersja[numerGracza - 1][i]] = Integer.parseInt(odbierz());
				odebranie();
				tlo.start();
			}
			try {
				Thread.sleep(10);
			}
			catch (Exception e) {
			}
			if(!tlo.isRunning()) {
				tlo = dzwiek("muzyka"+(random.nextInt(4)+1));
			}
		}
	}

	public static void odebranie() throws IOException {

		for (int i = 0; i < 4; i++) {
			int staraIloscKart=iloscKart[konwersja[numerGracza - 1][i]];
			iloscKart[konwersja[numerGracza - 1][i]] = Integer.parseInt(odbierz());
			if(staraIloscKart!=iloscKart[konwersja[numerGracza - 1][i]]&&iloscKart[konwersja[numerGracza - 1][i]]==1)
				dzwiek("uno"+(random.nextInt(4)+1));
		}

		tura = Integer.parseInt(odbierz());
		String staraKartaStol = new String(kartaStol);
		kartaStol = odbierz();
		if(staraKartaStol.compareTo(kartaStol)!=0&&(kartaStol.charAt(1)=='z'||kartaStol.charAt(1)=='p'||kartaStol.charAt(1)=='f'||kartaStol.charAt(1)=='t'))
			dzwiek("akcja"+(random.nextInt(6)+1));
		
		kierunek = Integer.parseInt(odbierz());
		dobranie = Integer.parseInt(odbierz());
		kolor = odbierz().charAt(0);

		for (int i = 0; i < 108; i++) {
			kartyGracza[i] = "n";
		}

		for (int i = 0; i < iloscKart[0]; i++) {
			kartyGracza[i] = odbierz();
		}

		Okno.panelUno.repaint();

	}

	public static Clip dzwiek(String nazwa) {
		try {
        		Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File("music/"+nazwa+".wav")));
                clip.start();
                return clip;
		}
		catch(Exception e) {}
		return null;
	}
	public static String odbierz() {
		
		try {
		return in.readLine();
		}
		catch(Exception e){
		JOptionPane.showMessageDialog(Okno.panelUno, "Utracono po³¹czenie z serwerem.", "Utracona po³¹czenie", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
		}
		return null;
	}
}
