import java.util.Scanner;

class Salary {

    public static void main(String[] args) {
        float[] gajiPokok = { 5000000, 6500000, 9500000 };
        int[] percentLembur = { 30, 32, 34, 36, 38 };

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Masukan golongan anda: ");
            System.out.println("1. A");
            System.out.println("2. B");
            System.out.println("3. C");

            if (!scan.hasNextInt()) {
                scan.next();
                System.out.println(
                    "Nilai tidak valid, harap isi dengan angka pilihan"
                );
                continue;
            }

            int golongan = scan.nextInt();

            if (golongan <= 0 || golongan > gajiPokok.length) {
                System.out.println("Pilihan golongan tidak valid");
                continue;
            }

            float gaji = gajiPokok[golongan - 1];

            System.out.println(gaji);

            while (true) {
                System.out.println("Masukan jumlah lembur");

                if (!scan.hasNextInt()) {
                    scan.next();
                    System.out.println(
                        "Nilai tidak valid, harap isi dengan angka pilihan"
                    );
                    continue;
                }

                float gajiLembur = 0;

                int jumlahLembur = scan.nextInt();

                if (jumlahLembur > 0) {
                    if (jumlahLembur >= 5) {
                        jumlahLembur = 5;
                    }
                    gajiLembur = (gaji * percentLembur[jumlahLembur - 1]) / 100;
                }
                
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.printf(
                    "Gaji anda bulan ini adalah: %f \n",
                    gaji + gajiLembur
                );
                
                break;
            }
        }
    }
}
