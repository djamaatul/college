import java.util.ArrayList;
import java.util.Scanner;

public class Resto {
  String nama;
  Menu[] menuResto;
  Scanner baca = new Scanner(System.in);
  ArrayList<Menu> pesanan = new ArrayList<Menu>();

  double pajak = 0.1; // 10%
  int admin = 20000; // 10%
  int freeMinuman = 50000;

  Resto(String nama, Menu[] menuResto) {
    this.nama = nama;
    this.menuResto = menuResto;
  }

  void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  void beranda() {

    if (this.pesanan.isEmpty()) {
      System.out.println("\nSelamat Datang di resto " + this.nama);
    } else {
      totalHargaPesanan();
    }

    System.out.println("1. Pesan Makanan Minuman");
    System.out.println("2. Keluar");

    if (!this.pesanan.isEmpty()) {
      System.out.println("3. Bayar");
      System.out.println("4. Ulang");
    }

    System.out.print("\nPilih: ");
    int pilih = baca.nextInt();

    clearScreen();
    switch (pilih) {
      case 1:
        pilihMenu();
        break;
      case 2:
        baca.close();
        System.exit(1);
        break;
      case 3:
        bayar();
        break;
      case 4:
        this.pesanan.clear();
        beranda();
        break;
    }
  }

  void pilihMenu() {
    if (this.pesanan.size() >= 4) {
      System.out.println("Anda hanya boleh memilih 4 pesanan");
      beranda();
      return;
    }

    lihatMakanan();
    lihatMinuman();

    System.out.println("0. Batal");

    System.out.print("\nPilih: ");
    int pilih = baca.nextInt();

    if (pilih == 0) {
      beranda();
    }

    if (pilih <= 0 || pilih > this.menuResto.length) {
      System.out.print("Pilihan anda salah, pilih nomor yang tertera");
      pilihMenu();
    }

    this.pesanan.add(this.menuResto[pilih - 1]);

    clearScreen();
    beranda();
  }

  void lihatMakanan() {
    System.out.println("-------------MENU MAKANAN-------------");
    System.out.println("1. " + this.menuResto[0].nama + "\t - " + this.menuResto[0].harga);
    System.out.println("2. " + this.menuResto[1].nama + "\t - " + this.menuResto[1].harga);
    System.out.println("3. " + this.menuResto[2].nama + "\t - " + this.menuResto[2].harga);
    System.out.println("4. " + this.menuResto[3].nama + "\t - " + this.menuResto[3].harga);
  }

  void lihatMinuman() {
    System.out.println("-------------MENU MINUMAN-------------");
    System.out.println("5. " + this.menuResto[4].nama + "\t - " + this.menuResto[4].harga);
    System.out.println("6. " + this.menuResto[5].nama + "\t - " + menuResto[5].harga);
    System.out.println("7. " + this.menuResto[6].nama + "\t - " + this.menuResto[6].harga);
    System.out.println("8. " + this.menuResto[7].nama + "\t - " + this.menuResto[7].harga);
  };

  int hargaPesanan(int pesananKe) {
    if (this.pesanan.size() >= pesananKe + 1) {
      Menu item = pesanan.get(pesananKe);
      System.out.println("# " + item.nama + "\t: Rp." + item.harga);
      return item.harga;
    }
    return 0;
  }

  double totalHargaPesanan() {
    double harga = 0;
    System.out.println("-------------PESANAN ANDA-------------");
    harga += hargaPesanan(0);
    harga += hargaPesanan(1);
    harga += hargaPesanan(2);
    harga += hargaPesanan(3);
    System.out.println("--------------------------------------");
    return harga;
  }

  void pilihMinumanGratis() {
    lihatMinuman();

    System.out.print("Total bayar lebih dari " + this.freeMinuman + " Pilih minuman gratis: ");
    int pilih = baca.nextInt();
    Menu item = this.menuResto[pilih - 1];

    if (item.kategori != "Minuman") {
      pilihMinumanGratis();
      return;
    }

    System.out.println("# " + item.nama + "\t: Rp.0");
  }

  void bayar() {

    double totalBayar = totalHargaPesanan();
    double diskon = 0;

    if (totalBayar >= 100000) {
      diskon += (totalBayar) * 10 / 100; // Diskon 10%
    }

    double totalPajak = (totalBayar * pajak);
    totalBayar += totalPajak;

    totalBayar -= diskon;
    totalBayar += admin;

    System.out.println("--------------------------------------");
    System.out.println("Biaya Pelayanan   :" + admin);
    System.out.println("Pajak             :" + totalPajak + " (" + (pajak * 100) + "%" + ")");
    System.out.println("Diskon            :" + diskon);
    System.out.println("Total Pesanan     :" + totalBayar);
    System.out.println("--------------------------------------");

    System.out.print("Masukan uang        :");
    double uang = baca.nextDouble();

    double kembalian = uang - totalBayar;

    if (kembalian < 0) {
      clearScreen();
      System.out.println("Uang kurang, harap isi uang dengan benar");
      bayar();
      return;
    }

    System.out.println("Kembalian         :" + kembalian);

    if (totalBayar >= this.freeMinuman) {
      pilihMinumanGratis();
    }

    this.pesanan.clear();

    beranda();
  }
}
