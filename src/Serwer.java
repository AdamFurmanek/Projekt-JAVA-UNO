
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Collections;

public class Serwer {
	
	private static int ileGraczy=1;
	private static int tura=1;
	private static String kartaStol;
	private static int kierunek;
	private static int dobranie;
	private static char kolor;
	private static int[] gracz= new int[4];
	private static ArrayList<String> lista = new ArrayList<String>();
	private static String[][] tablica = new String[4][108];
	private static String kod = new String();
	private static ServerSocket server;
	private static Socket client1;
	private static PrintWriter out1;
	private static BufferedReader in1;
	private static Socket client2;
	private static PrintWriter out2;
	private static BufferedReader in2;
	private static Socket client3;
	private static PrintWriter out3;
	private static BufferedReader in3;
	private static Socket client4;
	private static PrintWriter out4;
	private static BufferedReader in4;
	private static String odebrane = new String();
	private static int poszukiwacz;
	public static int czyDobral1=0;
	
public static void  main(String[] args)throws IOException {
	
	reset();
	polaczenie();

	while(true) {
		////////////////////WYSLANIE////////////////////
		wyslanie();
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
		////////////////////ODEBRANIE////////////////////
		if(tura==1)
			odebrane=in1.readLine();
		else if(tura==2) {
			if(ileGraczy>1)
				odebrane=in2.readLine();
			else {
				if(czyDobral1==0) {
					odebrane="dx";
					czyDobral1=1;
				}
				else {
					odebrane="dy";
					czyDobral1=0;
				}
				for(int i=0;i<108;i++) {
					if(sprawdzenie(tablica[1][i])) {
						if(tablica[1][i].charAt(0)=='b') {
							if(i%4==0)
								odebrane=tablica[1][i]+"c";
							else if(i%4==1)
								odebrane=tablica[1][i]+"z";
							else if(i%4==2)
								odebrane=tablica[1][i]+"y";
							else
								odebrane=tablica[1][i]+"n";
							break;
						}
						else {
							odebrane=tablica[1][i];
							break;
						}
					}
				}
				
			}
		}
		else if(tura==3) {
			if(ileGraczy>2)
				odebrane=in3.readLine();
			else {
				if(czyDobral1==0) {
					odebrane="dx";
					czyDobral1=1;
				}
				else {
					odebrane="dy";
					czyDobral1=0;
				}
				for(int i=0;i<108;i++) {
					if(sprawdzenie(tablica[2][i])) {
						if(tablica[2][i].charAt(0)=='b') {
							if(i%4==0)
								odebrane=tablica[2][i]+"c";
							else if(i%4==1)
								odebrane=tablica[2][i]+"z";
							else if(i%4==2)
								odebrane=tablica[2][i]+"y";
							else
								odebrane=tablica[2][i]+"n";
							break;
						}
						else {
							odebrane=tablica[2][i];
							break;
						}
					}
				}
			}
		}
		else if(tura==4) {
			if(ileGraczy>3)
				odebrane=in4.readLine();
			else {
				if(czyDobral1==0) {
					odebrane="dx";
					czyDobral1=1;
				}
				else {
					odebrane="dy";
					czyDobral1=0;
				}
				for(int i=0;i<108;i++) {
					if(sprawdzenie(tablica[3][i])) {
						if(tablica[3][i].charAt(0)=='b') {
							if(i%4==0)
								odebrane=tablica[3][i]+"c";
							else if(i%4==1)
								odebrane=tablica[3][i]+"z";
							else if(i%4==2)
								odebrane=tablica[3][i]+"y";
							else
								odebrane=tablica[3][i]+"n";
							break;
						}
						else {
							odebrane=tablica[3][i];
							break;
						}
					}
				}
			}
		}

		////////////////////ZABRANIE/DODANIE KART////////////////////
		if(odebrane.charAt(0)!='d') {
			(gracz[tura-1])--;
			lista.add(odebrane.charAt(0)+""+odebrane.charAt(1));
			Collections.shuffle(lista);
			for(int i=0;i<108;i++) {
				
				if(odebrane.charAt(0)==tablica[tura-1][i].charAt(0)&&odebrane.charAt(1)==tablica[tura-1][i].charAt(1)) {
					poszukiwacz=i;
					break;
				}
				else {
					
				}
			}
			for(int i=poszukiwacz;i<107;i++) {
				tablica[tura-1][i]=tablica[tura-1][i+1];
			}
			tablica[tura-1][107]="n";
		}
		else {
			for(int i=0;i<108;i++) {
				if(tablica[tura-1][i]=="n") {
					poszukiwacz=i;
					break;
				}	
			}
			if(odebrane.charAt(1)=='x') {
				(gracz[tura-1])++;
				tablica[tura-1][poszukiwacz]=lista.remove(0);
			}
			else {
				if(dobranie>0)
					dobranie--;
				for(int i=0;dobranie>0;dobranie--,i++) {
					(gracz[tura-1])++;
					tablica[tura-1][i+poszukiwacz]=lista.remove(0);
				}
				
			}
			
			
		}
		////////////////////OBLICZENIE DOBRANIA////////////////////
		System.out.println("tura gracz: "+tura);
		System.out.println("dobranie: "+dobranie);

		if(odebrane.charAt(0)!='d') {
			if(odebrane.charAt(1)=='t')
				dobranie=dobranie+2;
			if(odebrane.charAt(1)=='f')
				dobranie=dobranie+4;
		System.out.println("dobranie po obliczeniu: "+dobranie);
		}
		////////////////////OBLICZENIE KIERUNKU////////////////////
		if(odebrane.charAt(0)!='d') {
			if(odebrane.charAt(1)=='z') {
				if(kierunek==1)
					kierunek=2;
				else
					kierunek=1;
			}
		}
		////////////////////OBLICZENIE KOLORU////////////////////
		if(odebrane.charAt(0)!='d') {
			if(odebrane.charAt(0)=='b')
				kolor=odebrane.charAt(2);
			else
				kolor=odebrane.charAt(0);
		}
		////////////////////OBLICZENIE KARTY NA STOLE////////////////////
		if(odebrane.charAt(0)!='d') {
			lista.add(kartaStol);
			Collections.shuffle(lista);
			
			kartaStol=odebrane.charAt(0)+""+odebrane.charAt(1);
		}
		////////////////////OBLICZENIE TURY////////////////////
		if(odebrane.charAt(0)!='d'||odebrane.charAt(1)!='x') {
		if(kierunek==1) {
			tura++;
			if(tura==5)
				tura=1;
			if(odebrane.charAt(0)!='d') {
				if(odebrane.charAt(1)=='p') {
					tura++;
					if(tura==5)
						tura=1;
				}
			}
		}
		else {
			tura--;
			if(tura==0)
				tura=4;
			if(odebrane.charAt(0)!='d') {
				if(odebrane.charAt(1)=='p') {
					tura--;
					if(tura==0)
						tura=4;
				}
			}
		}
		}
	}
}

	public static boolean sprawdzenie(String karta) {
	 if(karta=="n")
		 return false;
	
	 //TEN SAM ZNAK
	 if(kartaStol.charAt(1)==karta.charAt(1))
		 return true;
	 
	 //TEN SAM KOLOR
	 else if (kolor==karta.charAt(0)) {
		 //JEŒLI GRACZ MA DOBRAÆ
		 if(dobranie>0) {
			 //JEŒLI GRACZ MA TEN SAM ZNAK(+2 lub +4)
			 if(kartaStol.charAt(1)==karta.charAt(1))
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
	 else if(karta.charAt(0)=='b'&&dobranie==0)
		 return true;
		 
	 //ZMIANA KOLORU +4
	 else if(karta.charAt(0)=='b'&&karta.charAt(1)=='f'&&kartaStol.charAt(1)!='t')
		 return true;
		 
	 //INNE
	 else
		 return false;
	}

	public static void reset() {
		for(int i=1;i<10;i++) {
			lista.add("c"+i);
			lista.add("c"+i);
			lista.add("z"+i);
			lista.add("z"+i);
			lista.add("n"+i);
			lista.add("n"+i);
			lista.add("y"+i);
			lista.add("y"+i);
		}
		lista.add("c0");
		lista.add("z0");
		lista.add("n0");
		lista.add("y0");

		
		lista.add("cz");
		lista.add("cz");
		lista.add("cp");
		lista.add("cp");
		lista.add("ct");
		lista.add("ct");
		
		lista.add("zz");
		lista.add("zz");
		lista.add("zp");
		lista.add("zp");
		lista.add("zt");
		lista.add("zt");
		
		lista.add("nz");
		lista.add("nz");
		lista.add("np");
		lista.add("np");
		lista.add("nt");
		lista.add("nt");
		
		lista.add("yz");
		lista.add("yz");
		lista.add("yp");
		lista.add("yp");
		lista.add("yt");
		lista.add("yt");
		
		lista.add("bc");
		lista.add("bc");
		lista.add("bc");
		lista.add("bc");
		lista.add("bf");
		lista.add("bf");
		lista.add("bf");
		lista.add("bf");
		
		Collections.shuffle(lista);
        for(int i =0;i<108;i++){
        	tablica[0][i]= new String();
        	tablica[0][i]="n";
        	tablica[1][i]= new String();
        	tablica[1][i]="n";
        	tablica[2][i]= new String();
        	tablica[2][i]="n";
        	tablica[3][i]= new String();
        	tablica[3][i]="n";
        }
		for(int i=0;i<7;i++) {
			tablica[0][i]=lista.remove(0);
			tablica[1][i]=lista.remove(0);
			tablica[2][i]=lista.remove(0);
			tablica[3][i]=lista.remove(0);
		}
		for(int i=0; i<5; i++) {
			if(lista.get(i).charAt(0)!='b') {
				kartaStol=lista.remove(i);	
				break;
			}
		}
		if(kartaStol.charAt(1)=='p') {
			tura++;
			if(tura==5)
				tura=4;
		}
		if(kartaStol.charAt(1)=='z')
			kierunek=2;
		else
			kierunek=1;
		if(kartaStol.charAt(1)=='t')
			dobranie=2;
		else
			dobranie=0;
		kolor=kartaStol.charAt(0);
		gracz[0]=7; gracz[1]=7; gracz[2]=7; gracz[3]=7;
	}
	

	public static void polaczenie() throws IOException {
		server = new ServerSocket(4999);
		server.setReuseAddress(true);

		client1 = server.accept();
		out1 = new PrintWriter(client1.getOutputStream(), true);
		in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
		out1.println("1");
		out1.flush();

		if(ileGraczy>1) {
			client2 = server.accept();
			out2 = new PrintWriter(client2.getOutputStream(), true);
			in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
			out2.println("2");
			out2.flush();
		}
		if(ileGraczy>2) {
			client3 = server.accept();
			out3 = new PrintWriter(client3.getOutputStream(), true);
			in3 = new BufferedReader(new InputStreamReader(client3.getInputStream()));
			out3.println("3");
			out3.flush();
		}
		if(ileGraczy>3) {
			client4 = server.accept();
			out4 = new PrintWriter(client4.getOutputStream(), true);
			in4 = new BufferedReader(new InputStreamReader(client4.getInputStream()));
			out4.println("4");
			out4.flush();
		}
	}
	
	
	public static void wyslanie() {
		for(int j=1;j<ileGraczy+1;j++) {
			kod="";
			if(gracz[0]>99)
				kod+=gracz[0];
			else if(gracz[0]>9)
				kod+="0"+gracz[0];
			else
				kod+="00"+gracz[0];
			
			if(gracz[1]>99)
				kod+=gracz[1];
			else if(gracz[1]>9)
				kod+="0"+gracz[1];
			else
				kod+="00"+gracz[1];
			
			if(gracz[2]>99)
				kod+=gracz[2];
			else if(gracz[2]>9)
				kod+="0"+gracz[2];
			else
				kod+="00"+gracz[2];
			
			if(gracz[3]>99)
				kod+=gracz[3];
			else if(gracz[3]>9)
				kod+="0"+gracz[3];
			else
				kod+="00"+gracz[3];
			
			kod+= tura+""+kartaStol+""+kierunek+"";
			
			if(dobranie>9)
				kod+=dobranie+"";
			else
				kod+="0"+dobranie+"";
			
			kod+=kolor+"";
			
			if(j==1) {
				for(int i=0;i<108;i++)
					kod=kod+tablica[0][i];
				out1.println(kod);
				out1.flush();
			}
			else if(j==2) {
				for(int i=0;i<108;i++)
					kod=kod+tablica[1][i];
				out2.println(kod);
				out2.flush();
			}
			else if(j==3) {
				for(int i=0;i<108;i++)
					kod=kod+tablica[2][i];
				out3.println(kod);
				out3.flush();
			}
			else if(j==4) {
				for(int i=0;i<108;i++)
					kod=kod+tablica[3][i];
				out4.println(kod);
				out4.flush();
			}
		}	
	}
	
}
