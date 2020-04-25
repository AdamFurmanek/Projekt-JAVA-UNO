
import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;
import java.awt.EventQueue;

public class Klient {

	public static Socket socket;
	public static PrintWriter out;
	public static BufferedReader in;

	public static Okno okno;

	public static int odliczanieNowejRundy = 0, numerGracza, tura = 1, kierunek = 0, dobranie = 0, czyDobralPierwsza = 0;
	public static String kartaStol = new String("a");
	public static char kolor = 'c';

	public static int[] iloscKart = { 7, 7, 7, 7 };
	public static int[] punkty = { 0, 0, 0, 0 };
	public static int[][] konwersja = { { 0, 1, 2, 3 }, { 3, 0, 1, 2 }, { 2, 3, 0, 1 }, { 1, 2, 3, 0 } };
	public static String[] kartyGracza = new String[108];
	public static String[] nazwaGracza = { "?", "?", "?", "?" };

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
			numerGracza = ((int) in.readLine().charAt(0)) - 48;
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

		for (int i = 0; i < 4; i++)
			nazwaGracza[konwersja[numerGracza - 1][i]] = in.readLine();

		while (true) {
			odebranie();
			if (kartyGracza[0] == "n" || iloscKart[1] == 0 || iloscKart[2] == 0 || iloscKart[3] == 0) {
				Okno.panelUno.licznikDoku = 14;
				odebranie();
				tura = 5;
				try {
					for (odliczanieNowejRundy = 5; odliczanieNowejRundy > 0; odliczanieNowejRundy--) {
						Okno.panelUno.repaint();
						Thread.sleep(1000);
					}
				} catch (Exception e) {
				}
				for (int i = 0; i < 4; i++)
					punkty[konwersja[numerGracza - 1][i]] = Integer.parseInt(in.readLine());
				odebranie();
			}
		}
	}

	public static void odebranie() throws IOException {

		for (int i = 0; i < 4; i++)
			iloscKart[konwersja[numerGracza - 1][i]] = Integer.parseInt(in.readLine());

		tura = Integer.parseInt(in.readLine());
		kartaStol = in.readLine();
		kierunek = Integer.parseInt(in.readLine());
		dobranie = Integer.parseInt(in.readLine());
		kolor = in.readLine().charAt(0);

		for (int i = 0; i < 108; i++) {
			kartyGracza[i] = "n";
		}

		for (int i = 0; i < iloscKart[0]; i++) {
			kartyGracza[i] = in.readLine();
		}

		Okno.panelUno.repaint();

	}

}
