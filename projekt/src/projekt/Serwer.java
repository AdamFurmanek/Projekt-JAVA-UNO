package projekt;

import java.net.*;
import java.io.*;

public class Serwer {

public static void  main(String[] args) throws IOException {
	ServerSocket ss = new ServerSocket(4999);
	Socket s1 = ss.accept();
	System.out.println("polaczono z 1");
	Socket s2 = ss.accept();
	System.out.println("polaczono z 2");
}

}
