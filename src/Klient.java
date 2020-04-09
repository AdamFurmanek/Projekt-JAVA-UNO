

import java.net.*;
import java.io.*;
import java.awt.EventQueue;

public class Klient{

public static void  main(String[] args) throws IOException {
	//Socket s = new Socket("localhost", 4999);
	
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			new Okno();
		}
	});
	
	}

}
