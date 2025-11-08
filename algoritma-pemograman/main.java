import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

class Salary {

    public static void main(String[] args) {
        var gajiGologan = new HashMap<Character, Integer>();

        gajiGologan.put('A', 5000000);
        gajiGologan.put('B', 6500000);
        gajiGologan.put('C', 9500000);

        int gaji = 0;
        int uangLembur = 0;

        Scanner scan = new Scanner(System.in);

        System.out.print("Masukan jabatan anda: ");

        Character jabatan = scan.next().toUpperCase().charAt(0);
        int jumlahLembur = 0;

        try {
            System.out.print("Masukan jumlah lembur: ");
            jumlahLembur = scan.nextInt();
        } catch (InputMismatchException error) {
            System.out.println("JumlahLembur harus angka");
        }

        gaji = gajiGologan.getOrDefault(jabatan, 0);

        if (jumlahLembur > 0) {
            if (jumlahLembur >= 5) {
                uangLembur = (gaji * 38) / 100;
            } else {
                uangLembur = (gaji * (30 + (2 * (jumlahLembur - 1)))) / 100;
            }
        }
        System.out.println("Jumlah penghasilan: " + (gaji + uangLembur));
    }
}
