

import java.net.*;
import java.io.*;
import java.awt.EventQueue;

public class Klient {

	public static String kod = new String();
	public static int gracz, tura=1;
	public static String kartaStol = new String("");
	public static int graczLewo=7, graczGora=7, graczPrawo=7;
	public static int kierunek=0, dobranie=0;
	public static char kolor='c';
	public static Socket socket;
	public static PrintWriter out;
	public static BufferedReader in;
    public static String[] tablica = new String[108];
    public static Okno okno;
    public static int czyDobral1=0;
    
	public static void  main(String[] args) throws IOException {
        for(int i=0;i<108;i++) {
        	tablica[i]= new String();
        	tablica[i]="n";
        }
        	
		socket = new Socket("localhost", 4999);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		gracz=((int)in.readLine().charAt(0))-48;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Okno();
			}
		});
		
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
		
		while(true) {
			odebranie();
		}
	}
	public static void odebranie() throws IOException  {
		kod=in.readLine();
		int gracz1,gracz2,gracz3,gracz4;
		gracz1=(((int)kod.charAt(0))-48)*100+(((int)kod.charAt(1))-48)*10+(((int)kod.charAt(2))-48);
		gracz2=(((int)kod.charAt(3))-48)*100+(((int)kod.charAt(4))-48)*10+(((int)kod.charAt(5))-48);
		gracz3=(((int)kod.charAt(6))-48)*100+(((int)kod.charAt(7))-48)*10+(((int)kod.charAt(8))-48);
		gracz4=(((int)kod.charAt(9))-48)*100+(((int)kod.charAt(10))-48)*10+(((int)kod.charAt(11))-48);
		if(gracz==1) {
			graczLewo=gracz2;
			graczGora=gracz3;
			graczPrawo=gracz4;
		}
		else if(gracz==2) {
			graczLewo=gracz3;
			graczGora=gracz4;
			graczPrawo=gracz1;
		}
		else if(gracz==3) {
			graczLewo=gracz4;
			graczGora=gracz1;
			graczPrawo=gracz2;
		}
		else if(gracz==4) {
			graczLewo=gracz1;
			graczGora=gracz2;
			graczPrawo=gracz3;
		}
		
		tura=((int)kod.charAt(12))-48;
		kartaStol="";
		kartaStol=kartaStol+kod.charAt(13);
		kartaStol=kartaStol+kod.charAt(14);
		kierunek=((int)kod.charAt(15))-48;
		
		dobranie=(((int)kod.charAt(16))-48)*10+(((int)kod.charAt(17))-48);
		
		kolor=kod.charAt(18);
        for(int i =0;i<108;i++){
        	tablica[i]="n";
        }
		for(int i=0;i<54;i++) {
			if(kod.charAt(2*i+20)=='n')
				break;
			tablica[i]="";
			tablica[i]=tablica[i]+kod.charAt(2*i+19);
			tablica[i]=tablica[i]+kod.charAt(2*i+20);
		}
		
		Okno.panelUno.repaint();
	}
	public static void wyslanie(String kod) {
		out.println(kod);
	}
}

