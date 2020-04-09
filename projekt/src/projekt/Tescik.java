package projekt;


public class Tescik{
	public static char[][] tabliczka = new char[108][2];

	public static void  main(String[] args) {
		
		
Stack stos = new Stack(30);
for (int i=1;i<6;i++)
{

tabliczka[i][0]='x';
stos.Push(tabliczka[i][0]);
}
stos.Pop();
stos.Pop();
stos.display();

		};

	
}
