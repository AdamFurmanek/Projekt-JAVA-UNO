
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
	private static int gracz1,gracz2,gracz3,gracz4;
	private static ArrayList<String> lista = new ArrayList<String>();
	private static String[] tablica1 = new String[108];
	private static String[] tablica2 = new String[108];
	private static String[] tablica3 = new String[108];
	private static String[] tablica4 = new String[108];
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
	
public static void  main(String[] args)throws IOException {
	
	reset();
	polaczenie();
	wyslanie();
	System.out.println(in1.readLine());

		//WHILE(TRUE)
		//BLOK WYSLANIA
		//ODEBRANIE
		//BLOK OBLICZENIA
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
		
		lista.add("bz");
		lista.add("bz");
		lista.add("bz");
		lista.add("bz");
		lista.add("bf");
		lista.add("bf");
		lista.add("bf");
		lista.add("bf");
		
		for(int i=0;i<500;i++)
			lista.add("bf");
		
		Collections.shuffle(lista);
        for(int i =0;i<108;i++){
        	tablica1[i]= new String();
        	tablica1[i]="n";
        	tablica2[i]= new String();
        	tablica2[i]="n";
        	tablica3[i]= new String();
        	tablica3[i]="n";
        	tablica4[i]= new String();
        	tablica4[i]="n";
        }
		for(int i=0;i<7;i++) {
			tablica1[i]=lista.remove(0);
			tablica2[i]=lista.remove(0);
			tablica3[i]=lista.remove(0);
			tablica4[i]=lista.remove(0);
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
		gracz1=7; gracz2=7; gracz3=7; gracz4=7;
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
			kod=
				(gracz1/100)+""+(gracz1/10)+""+(gracz1)+""+
			    (gracz2/100)+""+(gracz2/10)+""+(gracz2)+""+
			    (gracz3/100)+""+(gracz3/10)+""+(gracz3)+""+
			    (gracz4/100)+""+(gracz4/10)+""+(gracz4)+""+
			     tura+kartaStol+kierunek+dobranie+kolor;
			if(j==1) {
				for(int i=0;i<108;i++)
					kod=kod+tablica1[i];
				out1.println(kod);
				out1.flush();
			}
			else if(j==2) {
				for(int i=0;i<108;i++)
					kod=kod+tablica2[i];
				out2.println(kod);
				out2.flush();
			}
			else if(j==3) {
				for(int i=0;i<108;i++)
					kod=kod+tablica3[i];
				out3.println(kod);
				out3.flush();
			}
			else if(j==4) {
				for(int i=0;i<108;i++)
					kod=kod+tablica4[i];
				out4.println(kod);
				out4.flush();
			}
		}	
	}
}
