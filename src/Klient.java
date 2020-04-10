

import java.net.*;
import java.io.*;
import java.awt.EventQueue;

public class Klient{

	public static String kartaStol= new String("c4");
	public static int graczLewo=8, graczGora=20, graczPrawo=3;
	public static int kierunek=2;

	
	public static void  main(String[] args) throws IOException {
		//Socket s = new Socket("localhost", 4999);
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Okno();
			}
		});
	}
}
