

public class Stack {
 
     // Zdefiniowanie tablicy i elementów odpowiedzialnych za wierzcho³ek.
     char[] tab;
     int firstFree;
 
     // Konstruktor, który zainicjuje wierzcho³ek i stos o odpowiedniej
     // wielkoœci.
     public Stack(int MaxSize) {
         tab = new char[MaxSize];
         firstFree = 0;
     }
 
     // Metoda zwracaj¹ca maksymalny rozmiar stosu
     int getMaximumStackSize() {
         return tab.length;
     }
 
     // Metoda zwracaj¹ca prawdê, je¿eli stos jest pusty
     boolean isEmpty() {
         if (firstFree == 0) {
             return true;
         } else
             return false;
     }
 
     // Metoda zwracaj¹ca rozmiar stosu
     int getSize() {
         return firstFree;
     }
 
     // Metoda dodaj¹ca na stos
     void Push(char E) throws ArrayIndexOutOfBoundsException {
         if (firstFree < tab.length) {
             tab[firstFree] = E;
             firstFree++;
 
         } else {
             throw new ArrayIndexOutOfBoundsException(
                     "Przepe³nienie stosu, operacja nie powiod³a siê");
         }
     }
 
     // Metoda zdejmuj¹ca ze stosu
     double Pop() throws IndexOutOfBoundsException {
         if (firstFree <= 0) {
             throw new IndexOutOfBoundsException(
                     "Stos jest pusty, operacja nie powiod³a siê");
         }
 
         double temp = tab[firstFree - 1];
         firstFree--;
         return temp;
     }
 
     // Metoda wyœwietlaj¹ca zawartoœæ stosu
     void display() throws IndexOutOfBoundsException {
         if (firstFree == 0) {
             throw new IndexOutOfBoundsException(
                     "Stos jest pusty, operacja nie powiod³a siê");
         }
 
         int temp = firstFree - 1;
         do {
             System.out.println(tab[temp]);
             temp--;
         } while (temp > -1);
 
     }
 
     // Metoda wielokrotnego zdjêcia
     void multiPop(int k) {
         if (k < firstFree) {
             for (int i = k; i > 0; i--) {
                 System.out.println(Pop());
             }
         }
     }
 
     // Metoda czyszcz¹ca stos
     void clear() {
         for (int i = 0; i < firstFree; i++) {
             tab[i] = 'n';
 
         }
 
         firstFree = 0;
     }
 
     // Metoda odwracaj¹ca kolejnoœæ elementów na stosie
    // void reverse() {
    //     for (int i = 0; i < firstFree / 2; i++) {
      //       double temp = tab[i];
     //        tab[i] = tab[firstFree - 1 - i];
      //       tab[firstFree - 1 - i] = temp;
 
      //   }
    // }
 }