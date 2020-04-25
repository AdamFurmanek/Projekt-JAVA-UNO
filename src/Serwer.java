
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Collections;

import javax.swing.JOptionPane;

public class Serwer {
	
	private static ServerSocket server;
	private static Socket[] client = new Socket[4];
	private static PrintWriter[] out = new PrintWriter[4];
	private static BufferedReader[] in = new BufferedReader[4];
	
	private static int ileGraczy=2, tura=1, kierunek, dobranie, poszukiwacz, czyDobralPierwsza=0, pierwszeWyswietlenie=1;
	private static char kolor;
	private static String kartaStol, kod, odebrane;
	
	private static int[] ileKart = new int[4];
	private static int[] obecni = {0,0,0,0};
	private static int[] punkty = {0,0,0,0};
	private static ArrayList<String> stos = new ArrayList<String>();
	private static String[][] tablica = new String[4][108];
	private static String[] nick = {"Komputer", "Komputer", "Komputer", "Komputer"};

public static void  main(String[] args)throws IOException {
	
    Integer[] wyborIleGraczy = {1,2,3,4};
    InetAddress ip= InetAddress.getLocalHost();
    try {
    ileGraczy = (Integer)JOptionPane.showInputDialog(null, "IP serwera: "+ip.getHostAddress()+"\nPodaj iloœæ graczy ludzkich i naciœnij ok, aby uruchomiæ serwer",
            "Serwer", JOptionPane.DEFAULT_OPTION, null, wyborIleGraczy, wyborIleGraczy[0]);
    }
    catch(Exception e) {
    	System.exit(0);
    }
    
	reset();
	polaczenie();

	while(true) {
		////////////////////WYSLANIE////////////////////
		
		sortuj();
		wyslanie();
		try {
			Thread.sleep(600);
		}catch(Exception e) {}
		pierwszeWyswietlenie=2;
		wyslanie();
		try {
			Thread.sleep(600);
		}catch(Exception e) {}
		pierwszeWyswietlenie=1;
		
		////////////////////RESET PO ZWYCIÊSTWIE////////////////////
		if(tablica[0][0]=="n"||tablica[1][0]=="n"||tablica[2][0]=="n"||tablica[3][0]=="n") {
			int suma=0;
			for(int i=0;i<4;i++) {
				int j=0;
				while(tablica[i][j]!="n") {
					if(tablica[i][j].charAt(0)=='b')
						suma+=50;
					else if(tablica[i][j].charAt(1)=='z'||tablica[i][j].charAt(1)=='t'||tablica[i][j].charAt(1)=='p')
						suma+=20;
					else
						suma+=(int)tablica[i][j].charAt(1);
					j++;
				}
			}
			
			if(tablica[0][0]=="n") {
				punkty[0]+=suma;
			}
			else if(tablica[1][0]=="n") {
				punkty[1]+=suma;
			}
			else if(tablica[2][0]=="n") {
				punkty[2]+=suma;
			}
			else {
				punkty[3]+=suma;
			}
			for(int i=0;i<ileGraczy;i++) {
				out[i].println(punkty[0]);
				out[i].println(punkty[1]);
				out[i].println(punkty[2]);
				out[i].println(punkty[3]);
				out[i].flush();
			}
			if(obecni[0]==1)
				in[0].readLine();
			if(obecni[1]==1)
				in[1].readLine();
			if(obecni[2]==1)
				in[2].readLine();
			if(obecni[3]==1)
				in[3].readLine();
			reset();
			wyslanie();
		}
			
		////////////////////ODEBRANIE/ SZTUCZNA INTELIGENCJA////////////////////
		odebranie();
		if(obecni[0]+obecni[1]+obecni[2]+obecni[3]==0)
			System.exit(0);
		////////////////////ZABRANIE/DODANIE KART////////////////////
		if(odebrane.charAt(0)!='d') {
			(ileKart[tura-1])--;
			stos.add(odebrane.charAt(0)+""+odebrane.charAt(1));
			Collections.shuffle(stos);
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
				(ileKart[tura-1])++;
				tablica[tura-1][poszukiwacz]=stos.remove(0);
				pierwszeWyswietlenie=2;
			}
			else {
				if(dobranie>0)
					dobranie--;
				for(int i=0;dobranie>0;dobranie--,i++) {
					(ileKart[tura-1])++;
					tablica[tura-1][i+poszukiwacz]=stos.remove(0);
				}
			}
		}
		
		////////////////////OBLICZENIE DOBRANIA////////////////////
		if(odebrane.charAt(0)!='d') {
			if(odebrane.charAt(1)=='t')
				dobranie=dobranie+2;
			if(odebrane.charAt(1)=='f')
				dobranie=dobranie+4;
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
			stos.add(kartaStol);
			Collections.shuffle(stos);
			
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
			stos.add("c"+i);
			stos.add("c"+i);
			stos.add("z"+i);
			stos.add("z"+i);
			stos.add("n"+i);
			stos.add("n"+i);
			stos.add("y"+i);
			stos.add("y"+i);
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
		for(int i=0;i<18;i++) {
			tablica[0][i]=stos.remove(0);
			tablica[1][i]=stos.remove(0);
			tablica[2][i]=stos.remove(0);
			tablica[3][i]=stos.remove(0);
		}
		for(int i=0; i<9; i++) {
			if(stos.get(i).charAt(0)!='b') {
				kartaStol=stos.remove(i);	
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
		ileKart[0]=1; ileKart[1]=18; ileKart[2]=18; ileKart[3]=18;
		czyDobralPierwsza=0;
		if(tura<1||tura>4)
			tura=1;
	}

	public static void polaczenie() throws IOException {
		server = new ServerSocket(4999);
		server.setReuseAddress(true);

		client[0] = server.accept();
		out[0] = new PrintWriter(client[0].getOutputStream(), true);
		in[0] = new BufferedReader(new InputStreamReader(client[0].getInputStream()));
		nick[0]=in[0].readLine();
		out[0].println("1");
		out[0].flush();
		obecni[0]=1;

		if(ileGraczy>1) {
			client[1] = server.accept();
			out[1] = new PrintWriter(client[1].getOutputStream(), true);
			in[1] = new BufferedReader(new InputStreamReader(client[1].getInputStream()));
			nick[1]=in[1].readLine();
			out[1].println("2");
			out[1].flush();
			obecni[1]=1;
		}
		if(ileGraczy>2) {
			client[2] = server.accept();
			out[2] = new PrintWriter(client[2].getOutputStream(), true);
			in[2] = new BufferedReader(new InputStreamReader(client[2].getInputStream()));
			nick[2]=in[2].readLine();
			out[2].println("3");
			out[2].flush();
			obecni[2]=1;
		}
		if(ileGraczy>3) {
			client[3] = server.accept();
			out[3] = new PrintWriter(client[3].getOutputStream(), true);
			in[3] = new BufferedReader(new InputStreamReader(client[3].getInputStream()));
			nick[3]=in[3].readLine();
			out[3].println("4");
			out[3].flush();
			obecni[3]=1;
		}
		for(int i=0;i<ileGraczy;i++) {
			out[i].println(nick[0]);
			out[i].println(nick[1]);
			out[i].println(nick[2]);
			out[i].println(nick[3]);
			out[i].flush();
		}
		
		
	}
	
	public static void wyslanie() {
		for(int j=1;j<ileGraczy+1;j++) {
			kod="";
			if(ileKart[0]>99)
				kod+=ileKart[0];
			else if(ileKart[0]>9)
				kod+="0"+ileKart[0];
			else
				kod+="00"+ileKart[0];
			
			if(ileKart[1]>99)
				kod+=ileKart[1];
			else if(ileKart[1]>9)
				kod+="0"+ileKart[1];
			else
				kod+="00"+ileKart[1];
			
			if(ileKart[2]>99)
				kod+=ileKart[2];
			else if(ileKart[2]>9)
				kod+="0"+ileKart[2];
			else
				kod+="00"+ileKart[2];
			
			if(ileKart[3]>99)
				kod+=ileKart[3];
			else if(ileKart[3]>9)
				kod+="0"+ileKart[3];
			else
				kod+="00"+ileKart[3];
			
			if(pierwszeWyswietlenie==2)
				kod+= tura+"";
			else
				kod+="5";
			kod+=kartaStol+""+kierunek+"";
			
			if(dobranie>9)
				kod+=dobranie+"";
			else
				kod+="0"+dobranie+"";
			
			kod+=kolor+"";
			
			if(j==1) {
				for(int i=0;i<108;i++)
					kod=kod+tablica[0][i];
				out[0].println(kod);
				if(out[0].checkError())
					obecni[0]=0;
			}
			else if(j==2) {
				for(int i=0;i<108;i++)
					kod=kod+tablica[1][i];
				out[1].println(kod);
				if(out[1].checkError())
					obecni[1]=0;
			}
			else if(j==3) {
				for(int i=0;i<108;i++)
					kod=kod+tablica[2][i];
				out[2].println(kod);
				if(out[2].checkError())
					obecni[2]=0;
			}
			else if(j==4) {
				for(int i=0;i<108;i++)
					kod=kod+tablica[3][i];
				out[3].println(kod);
				if(out[3].checkError())
					obecni[3]=0;
			}
		}	
	}
	
	public static void odebranie(){
		if(obecni[tura-1]==1) {
			try {
			odebrane=in[tura-1].readLine();
			}
			catch(IOException e){
				obecni[tura-1]=0;
				komputer(tura);
			}
		}
		else {
			komputer(tura);
		}
	}
	
	public static void sortuj() {
		
		String pom = new String();
		for(int k=0;k<4;k++) {
			for(int i=0;i<107;i++) {
				for(int j=i+1;j<108;j++) {
					if(tablica[k][j]!="n"&&(tablica[k][i])!="n") {
						if(tablica[k][i].compareTo(tablica[k][j])>0) {
							pom=tablica[k][j];
							tablica[k][j]=tablica[k][i];
							tablica[k][i]=pom;
						}
					}	
				}
			}
		}		
	}
	
	public static void komputer(int ktoryGracz) {
		if(czyDobralPierwsza==0) {
			odebrane="dx";
			czyDobralPierwsza=1;
			
		}
		else {
			odebrane="dy";
			czyDobralPierwsza=0;
		}
		for(int i=0;i<108;i++) {
			if(sprawdzenie(tablica[ktoryGracz-1][i])) {
				if(tablica[ktoryGracz-1][i].charAt(0)=='b') {
					if(i%4==0)
						odebrane=tablica[ktoryGracz-1][i]+"c";
					else if(i%4==1)
						odebrane=tablica[ktoryGracz-1][i]+"z";
					else if(i%4==2)
						odebrane=tablica[ktoryGracz-1][i]+"y";
					else
						odebrane=tablica[ktoryGracz-1][i]+"n";
					break;
				}
				else {
					odebrane=tablica[ktoryGracz-1][i];
					czyDobralPierwsza=0;
					break;
				}
			}
		}
	}
}
