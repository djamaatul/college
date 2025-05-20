import java.util.LinkedList;

class collections {

    public static void main(String[] args) {
        // Buatlah sebuah deklarasi variabel dengan tipe data integer yang bernama ‘jumlahBaris’?

        int jumlahBaris = 100;

        System.out.println(jumlahBaris);

        // Buatlah sebuah deklarasi variabel dengan tipe data string yang bernama ‘kalimatBaru’ yang berisi kata ‘Deklarasi tipe data String’?

        String kalimatBaru = "Deklarasi tipe data String";

        System.out.println(kalimatBaru);

        // Buatlah pendeklarasian array satu dimensi dengan nama ‘empatAngka’, tipe data integer, yang berisi angka (07, 10, 20, 23)? Pendeklarasian tersebut dengan menggunakan bahasa pemrograman Java.

        int[] empatAngka = { 07, 10, 20, 23 };
        int empatAngka2[] = { 07, 10, 20, 23 };
        // int[] empatAngka3 = new int[4];
        // empatAnka3[0] = 07;
        // empatAnka3[1] = 10;
        // empatAnka3[2] = 20;
        // empatAnka3[3] = 23;
        // var empatAngka4 = new int[]{};

        for (int i = 0; i < empatAngka.length; ++i) {
            System.out.println("Empat Engka index ke - " + i+ empatAngka[i]);
        }

        // . Buatlah deklarasi linked list dengan nama 'listAngka' yang memiliki list (26, 08, 23, 24, 16)? Pendeklarasian
        //   tersebut dengan menggunakan bahasa pemrograman Java.

        LinkedList<Integer> listAngka = new LinkedList<Integer>();

        listAngka.add(26);
        listAngka.add(8);
        listAngka.add(23);
        listAngka.add(24);
        listAngka.add(16);

        System.out.println(listAngka);
    }
}
