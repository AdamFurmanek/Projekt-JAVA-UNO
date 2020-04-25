

import java.net.*;

import javax.swing.JOptionPane;

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
    public static int czyDobralPierwsza=0;
    public static int wygrana;
    public static String[] nick = {"1","2","3","4"};
    public static Integer[] punkty = {0,0,0,0};
    
	public static void  main(String[] args) throws IOException {
		
		nick[0] = (String)JOptionPane.showInputDialog(null,"Podaj sw�j nick:", "Nazwa gracza", JOptionPane.INFORMATION_MESSAGE, null, null, "Mistrz");
		if(nick[0]==null)
			System.exit(0);
		
		String ip = (String)JOptionPane.showInputDialog(null,"Podaj IP serwera", "IP serwera", JOptionPane.INFORMATION_MESSAGE, null, null, "localhost");
		if(ip==null)
			System.exit(0);
		

		try {
		socket = new Socket(ip, 4999);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out.println(nick[0]);
		gracz=((int)in.readLine().charAt(0))-48;
		}
		catch(IOException e){
			JOptionPane.showConfirmDialog(null,"Nie mo�na po��czy� z serwerem o podanym IP.", "Brak po��czenia", JOptionPane.DEFAULT_OPTION);
			System.exit(0);
		}
		
        for(int i=0;i<108;i++) {
        	tablica[i]= new String();
        	tablica[i]="n";
        }
        
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Okno();
			}
		});
		
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
		if(gracz==1) {
			nick[0]=in.readLine();
			nick[1]=in.readLine();
			nick[2]=in.readLine();
			nick[3]=in.readLine();
		}
		else if(gracz==2) {
			nick[3]=in.readLine();
			nick[0]=in.readLine();
			nick[1]=in.readLine();
			nick[2]=in.readLine();
		}
		else if(gracz==3) {
			nick[2]=in.readLine();
			nick[3]=in.readLine();
			nick[0]=in.readLine();
			nick[1]=in.readLine();
		}
		else if(gracz==4) {
			nick[1]=in.readLine();
			nick[2]=in.readLine();
			nick[3]=in.readLine();
			nick[0]=in.readLine();
		}
		
		while(true) {
			odebranie();
	    	if(tablica[0]=="n"||graczLewo==0||graczGora==0||graczPrawo==0) {
	    		wygrana=1;
	    		odebranie();

	 			if(gracz==1) {
		 			punkty[0]=Integer.parseInt(in.readLine());
		 			punkty[1]=Integer.parseInt(in.readLine());
		 			punkty[2]=Integer.parseInt(in.readLine());
		 			punkty[3]=Integer.parseInt(in.readLine());
	 			}
	 			else if(gracz==2) {
		 			punkty[3]=Integer.parseInt(in.readLine());
		 			punkty[0]=Integer.parseInt(in.readLine());
		 			punkty[1]=Integer.parseInt(in.readLine());
		 			punkty[2]=Integer.parseInt(in.readLine());
	 			}
	 			else if(gracz==3) {
		 			punkty[2]=Integer.parseInt(in.readLine());
		 			punkty[3]=Integer.parseInt(in.readLine());
		 			punkty[0]=Integer.parseInt(in.readLine());
		 			punkty[1]=Integer.parseInt(in.readLine());
	 			}
	 			else if(gracz==4) {
		 			punkty[1]=Integer.parseInt(in.readLine());
		 			punkty[2]=Integer.parseInt(in.readLine());
		 			punkty[3]=Integer.parseInt(in.readLine());
		 			punkty[4]=Integer.parseInt(in.readLine());
	 			}
	 			odebranie();
	    	}
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

