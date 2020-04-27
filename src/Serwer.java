
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Collections;

import javax.swing.JOptionPane;

public class Serwer {

	private static ServerSocket serverSocket;
	private static Socket[] socket = new Socket[4];
	private static PrintWriter[] out = new PrintWriter[4];
	private static BufferedReader[] in = new BufferedReader[4];

	private static int ileGraczy, tura = 1, kierunek, dobranie, czyDobralPierwsza = 0, pierwszeWyswietlenie;
	private static char kolor;
	private static String kartaStol, odebrane;

	private static int[] iloscKart = new int[4];
	private static int[] obecni = { 0, 0, 0, 0 };
	private static int[] punkty = { 0, 0, 0, 0 };
	private static ArrayList<String> stos = new ArrayList<String>();
	private static String[][] kartyGraczy = new String[4][108];
	private static String[] nazwaGracza = { "Komputer", "Komputer", "Komputer", "Komputer" };

	public static void main(String[] args) throws IOException {

		Integer[] wyborIleGraczy = { 1, 2, 3, 4 };
		InetAddress ip = InetAddress.getLocalHost();
		try {
			ileGraczy = (Integer) JOptionPane.showInputDialog(null,
					"IP serwera: " + ip.getHostAddress()
							+ "\nPodaj iloœæ graczy ludzkich i naciœnij ok, aby uruchomiæ serwer",
					"Serwer", JOptionPane.DEFAULT_OPTION, null, wyborIleGraczy, wyborIleGraczy[0]);
		} catch (Exception e) {
			System.exit(0);
		}

		reset();
		polaczenie();

		while (true) {
			//////////////////// WYSLANIE////////////////////

			sortuj();
			wyslanie();
			try {
				Thread.sleep(600);
			} catch (Exception e) {
			}
			pierwszeWyswietlenie = 0;
			wyslanie();
			try {
				Thread.sleep(600);
			} catch (Exception e) {
			}
			pierwszeWyswietlenie = 1;

			//////////////////// RESET PO ZWYCIÊSTWIE////////////////////
			if (kartyGraczy[0][0] == "n" || kartyGraczy[1][0] == "n" || kartyGraczy[2][0] == "n"
					|| kartyGraczy[3][0] == "n") {
				wynik();
				reset();
				wyslanie();
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
				}
			}

			//////////////////// ODEBRANIE/ SZTUCZNA INTELIGENCJA////////////////////
			odebranie();
			if (obecni[0] + obecni[1] + obecni[2] + obecni[3] == 0)
				System.exit(0);
			//////////////////// DOBRANIE/WYLOZENIE KART////////////////////
			dobranieLubWylozenie();
			//////////////////// OBLICZENIE DOBRANIA////////////////////
			if (odebrane.charAt(0) != 'd') {
				if (odebrane.charAt(1) == 't')
					dobranie = dobranie + 2;
				if (odebrane.charAt(1) == 'f')
					dobranie = dobranie + 4;
			}

			//////////////////// OBLICZENIE KIERUNKU////////////////////
			if (odebrane.charAt(0) != 'd') {
				if (odebrane.charAt(1) == 'z') {
					if (kierunek == 1)
						kierunek = 2;
					else
						kierunek = 1;
				}
			}
			//////////////////// OBLICZENIE KOLORU////////////////////
			if (odebrane.charAt(0) != 'd') {
				if (odebrane.charAt(0) == 'b')
					kolor = odebrane.charAt(2);
				else
					kolor = odebrane.charAt(0);
			}
			//////////////////// OBLICZENIE KARTY NA STOLE////////////////////
			if (odebrane.charAt(0) != 'd') {
				stos.add(kartaStol);
				Collections.shuffle(stos);

				kartaStol = odebrane.charAt(0) + "" + odebrane.charAt(1);
			}
			//////////////////// OBLICZENIE TURY////////////////////
			if (odebrane.charAt(0) != 'd' || odebrane.charAt(1) != 'x') {
				if (kierunek == 1) {
					tura++;
					if (tura == 5)
						tura = 1;
					if (odebrane.charAt(0) != 'd') {
						if (odebrane.charAt(1) == 'p') {
							tura++;
							if (tura == 5)
								tura = 1;
						}
					}
				} else {
					tura--;
					if (tura == 0)
						tura = 4;
					if (odebrane.charAt(0) != 'd') {
						if (odebrane.charAt(1) == 'p') {
							tura--;
							if (tura == 0)
								tura = 4;
						}
					}
				}
			}
		}
	}

	public static void dobranieLubWylozenie() {
		int poszukiwacz = 0;
		if (odebrane.charAt(0) != 'd') {
			(iloscKart[tura - 1])--;
			stos.add(odebrane.charAt(0) + "" + odebrane.charAt(1));
			Collections.shuffle(stos);
			for (int i = 0; i < 108; i++) {

				if (odebrane.charAt(0) == kartyGraczy[tura - 1][i].charAt(0)
						&& odebrane.charAt(1) == kartyGraczy[tura - 1][i].charAt(1)) {
					poszukiwacz = i;
					break;
				} else {

				}
			}
			for (int i = poszukiwacz; i < 107; i++) {
				kartyGraczy[tura - 1][i] = kartyGraczy[tura - 1][i + 1];
			}
			kartyGraczy[tura - 1][107] = "n";
		} else {
			for (int i = 0; i < 108; i++) {
				if (kartyGraczy[tura - 1][i] == "n") {
					poszukiwacz = i;
					break;
				}
			}
			if (odebrane.charAt(1) == 'x') {
				if (!stos.isEmpty()) {
					(iloscKart[tura - 1])++;
					kartyGraczy[tura - 1][poszukiwacz] = stos.remove(0);
				}
				pierwszeWyswietlenie = 0;
			} else {
				if (dobranie > 0)
					dobranie--;
				for (int i = 0; dobranie > 0; dobranie--, i++) {
					if (!stos.isEmpty()) {
						(iloscKart[tura - 1])++;
						kartyGraczy[tura - 1][i + poszukiwacz] = stos.remove(0);
					}
				}
			}
		}
	}

	public static void wynik() {
		int suma = 0;
		for (int i = 0; i < 4; i++) {
			int j = 0;
			while (kartyGraczy[i][j] != "n") {
				if (kartyGraczy[i][j].charAt(0) == 'b')
					suma += 50;
				else if (kartyGraczy[i][j].charAt(1) == 'z' || kartyGraczy[i][j].charAt(1) == 't'
						|| kartyGraczy[i][j].charAt(1) == 'p')
					suma += 20;
				else
					suma += (int) kartyGraczy[i][j].charAt(1);
				j++;
			}
		}

		if (kartyGraczy[0][0] == "n") {
			punkty[0] += suma;
		} else if (kartyGraczy[1][0] == "n") {
			punkty[1] += suma;
		} else if (kartyGraczy[2][0] == "n") {
			punkty[2] += suma;
		} else {
			punkty[3] += suma;
		}
		for (int i = 0; i < ileGraczy; i++) {
			out[i].println(punkty[0]);
			out[i].println(punkty[1]);
			out[i].println(punkty[2]);
			out[i].println(punkty[3]);
			out[i].flush();
		}
	}

	public static boolean sprawdzenie(String karta) {
		if (karta == "n")
			return false;

		// TEN SAM ZNAK
		if (kartaStol.charAt(1) == karta.charAt(1))
			return true;

		// TEN SAM KOLOR
		else if (kolor == karta.charAt(0)) {
			// JEŒLI GRACZ MA DOBRAÆ
			if (dobranie > 0) {
				// JEŒLI GRACZ MA TEN SAM ZNAK(+2 lub +4)
				if (kartaStol.charAt(1) == karta.charAt(1))
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
		else if (karta.charAt(0) == 'b' && dobranie == 0)
			return true;

		// ZMIANA KOLORU +4
		else if (karta.charAt(0) == 'b' && karta.charAt(1) == 'f' && kartaStol.charAt(1) != 't')
			return true;

		// INNE
		else
			return false;
	}

	public static void reset() {
		for (int i = 1; i < 10; i++) {
			stos.add("c" + i);
			stos.add("c" + i);
			stos.add("z" + i);
			stos.add("z" + i);
			stos.add("n" + i);
			stos.add("n" + i);
			stos.add("y" + i);
			stos.add("y" + i);
		}
		stos.add("c0");
		stos.add("z0");
		stos.add("n0");
		stos.add("y0");

		stos.add("cz");
		stos.add("cz");
		stos.add("cp");
		stos.add("cp");
		stos.add("ct");
		stos.add("ct");

		stos.add("zz");
		stos.add("zz");
		stos.add("zp");
		stos.add("zp");
		stos.add("zt");
		stos.add("zt");

		stos.add("nz");
		stos.add("nz");
		stos.add("np");
		stos.add("np");
		stos.add("nt");
		stos.add("nt");

		stos.add("yz");
		stos.add("yz");
		stos.add("yp");
		stos.add("yp");
		stos.add("yt");
		stos.add("yt");

		stos.add("bc");
		stos.add("bc");
		stos.add("bc");
		stos.add("bc");
		stos.add("bf");
		stos.add("bf");
		stos.add("bf");
		stos.add("bf");

		Collections.shuffle(stos);
		for (int i = 0; i < 108; i++) {
			kartyGraczy[0][i] = new String();
			kartyGraczy[0][i] = "n";
			kartyGraczy[1][i] = new String();
			kartyGraczy[1][i] = "n";
			kartyGraczy[2][i] = new String();
			kartyGraczy[2][i] = "n";
			kartyGraczy[3][i] = new String();
			kartyGraczy[3][i] = "n";
		}
		for (int i = 0; i < 7; i++) {
			kartyGraczy[0][i] = stos.remove(0);
			kartyGraczy[1][i] = stos.remove(0);
			kartyGraczy[2][i] = stos.remove(0);
			kartyGraczy[3][i] = stos.remove(0);
		}
		for (int i = 0; i < 9; i++) {
			if (stos.get(i).charAt(0) != 'b') {
				kartaStol = stos.remove(i);
				break;
			}
		}
		if (kartaStol.charAt(1) == 'p') {
			tura++;
			if (tura == 5)
				tura = 4;
		}
		if (kartaStol.charAt(1) == 'z')
			kierunek = 2;
		else
			kierunek = 1;
		if (kartaStol.charAt(1) == 't')
			dobranie = 2;
		else
			dobranie = 0;
		pierwszeWyswietlenie = 0;
		kolor = kartaStol.charAt(0);
		iloscKart[0] = 7;
		iloscKart[1] = 7;
		iloscKart[2] = 7;
		iloscKart[3] = 7;
		czyDobralPierwsza = 0;
		if (tura < 1 || tura > 4)
			tura = 1;
	}

	public static void polaczenie() throws IOException {
		serverSocket = new ServerSocket(4999);
		serverSocket.setReuseAddress(true);

		for (int i = 0; i < 4; i++) {
			if (ileGraczy > i) {
				socket[i] = serverSocket.accept();
				out[i] = new PrintWriter(socket[i].getOutputStream(), true);
				in[i] = new BufferedReader(new InputStreamReader(socket[i].getInputStream()));
				nazwaGracza[i] = in[i].readLine();
				out[i].println(i + 1);
				out[i].flush();
				obecni[i] = 1;
			}
		}

		for (int i = 0; i < ileGraczy; i++) {
			out[i].println(nazwaGracza[0]);
			out[i].println(nazwaGracza[1]);
			out[i].println(nazwaGracza[2]);
			out[i].println(nazwaGracza[3]);
			out[i].flush();
		}

	}

	public static void wyslanie() {
		for (int j = 1; j < ileGraczy + 1; j++) {

			out[j - 1].println(iloscKart[0]);
			out[j - 1].println(iloscKart[1]);
			out[j - 1].println(iloscKart[2]);
			out[j - 1].println(iloscKart[3]);

			if (pierwszeWyswietlenie == 0)
				out[j - 1].println(tura);
			else
				out[j - 1].println(5);
			out[j - 1].println(kartaStol);
			out[j - 1].println(kierunek);
			out[j - 1].println(dobranie);
			out[j - 1].println(kolor);
			if (j == 1) {
				for (int i = 0; i < iloscKart[0]; i++)
					out[j - 1].println(kartyGraczy[0][i]);
				if (out[0].checkError())
					obecni[0] = 0;
			} else if (j == 2) {
				for (int i = 0; i < iloscKart[1]; i++)
					out[j - 1].println(kartyGraczy[1][i]);
				if (out[1].checkError())
					obecni[1] = 0;
			} else if (j == 3) {
				for (int i = 0; i < iloscKart[2]; i++)
					out[j - 1].println(kartyGraczy[2][i]);
				if (out[2].checkError())
					obecni[2] = 0;
			} else {
				for (int i = 0; i < iloscKart[3]; i++)
					out[j - 1].println(kartyGraczy[3][i]);
				if (out[3].checkError())
					obecni[3] = 0;
			}
		}
	}

	public static void odebranie() {
		if (obecni[tura - 1] == 1) {
			try {
				odebrane = in[tura - 1].readLine();
			} catch (IOException e) {
				obecni[tura - 1] = 0;
				komputer(tura);
			}
		} else {
			komputer(tura);
		}
	}

	public static void sortuj() {

		String pom = new String();
		for (int k = 0; k < 4; k++) {
			for (int i = 0; i < 107; i++) {
				for (int j = i + 1; j < 108; j++) {
					if (kartyGraczy[k][j] != "n" && (kartyGraczy[k][i]) != "n") {
						if (kartyGraczy[k][i].compareTo(kartyGraczy[k][j]) > 0) {
							pom = kartyGraczy[k][j];
							kartyGraczy[k][j] = kartyGraczy[k][i];
							kartyGraczy[k][i] = pom;
						}
					}
				}
			}
		}
	}

	public static void komputer(int ktoryGracz) {
		if (czyDobralPierwsza == 0) {
			odebrane = "dx";
			czyDobralPierwsza = 1;

		} else {
			odebrane = "dy";
			czyDobralPierwsza = 0;
		}
		for (int i = 0; i < 108; i++) {
			if (sprawdzenie(kartyGraczy[ktoryGracz - 1][i])) {
				if (kartyGraczy[ktoryGracz - 1][i].charAt(0) == 'b') {
					if (i % 4 == 0)
						odebrane = kartyGraczy[ktoryGracz - 1][i] + "c";
					else if (i % 4 == 1)
						odebrane = kartyGraczy[ktoryGracz - 1][i] + "z";
					else if (i % 4 == 2)
						odebrane = kartyGraczy[ktoryGracz - 1][i] + "y";
					else
						odebrane = kartyGraczy[ktoryGracz - 1][i] + "n";
					break;
				} else {
					odebrane = kartyGraczy[ktoryGracz - 1][i];
					czyDobralPierwsza = 0;
					break;
				}
			}
		}
	}
}
