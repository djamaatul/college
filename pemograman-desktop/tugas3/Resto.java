import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class Resto {
  String nama;
  Menu menuResto;
  Scanner baca = new Scanner(System.in);
  Pesanan pesanan = new Pesanan("");

  double pajak = 0.1; // 10%
  int admin = 20000; // 10%
  int freeMinuman = 50000;

  public Resto(String nama, Menu menuResto) {
    this.nama = nama;

    this.menuResto = menuResto;
  }

  void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  void error(String msg) {
    clearScreen();
    System.out.print(msg);
    baca.nextLine();
  }

  public void beranda() {

    if (this.pesanan.getItemsPesanan().isEmpty()) {
      System.out.println("\nSelamat Datang di resto " + this.nama);
    } else {
      this.pesanan.totalHargaPesanan();
    }

    System.out.println("1. Pesan");
    System.out.println("2. Manajemen Menu");

    if (!this.pesanan.getItemsPesanan().isEmpty()) {
      System.out.println("3. Bayar");
      System.out.println("4. Ulang");
    }

    System.out.println("0. Keluar");

    System.out.print("\nPilih: ");
    int pilih = baca.nextInt();
    baca.nextLine();

    clearScreen();
    switch (pilih) {
      case 0:
        baca.close();
        System.exit(1);
        break;
      case 1:
        if (this.pesanan.getItemsPesanan().size() == 0) {
          System.out.print("\n Nama: ");
          this.pesanan = new Pesanan(baca.nextLine());
        }
        pilihMenu();
        break;
      case 2:
        this.menuResto.manajemenMenu();
        beranda();
        break;
      case 3:
        bayar();
        break;
      case 4:
        this.pesanan.getItemsPesanan().clear();
        beranda();
        break;
    }
  }

  void pilihMenu() {
    int pilih = this.menuResto.ambilMenu();

    if (pilih == -1) {
      pilihMenu();
      return;
    }

    if (pilih == 0) {
      beranda();
      return;
    }

    System.out.print("\nJumlah: ");
    int jumlah = baca.nextInt();
    baca.nextLine();

    this.pesanan.addItemsPesanan(new PesananItem(this.menuResto.daftarMenu.get(pilih - 1), jumlah));

    clearScreen();
    beranda();
  }

  void pilihMinumanGratis() {
    for (PesananItem pesanan : this.pesanan.getItemsPesanan()) {
      if (pesanan.menu.getKategori() == "Minuman") {
        System.out.printf("## Selamat anda mendapatkan minuman %s ##\n", pesanan.menu.getNama());
        return;
      }
    }
  }

  void bayar() {

    double totalBayar = this.pesanan.totalHargaPesanan();
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

    double kembalian = 0;
    while (true) {
      System.out.print("Masukan uang      :Rp.");
      double uang = baca.nextDouble();

      kembalian = uang - totalBayar;

      if (kembalian < 0) {
        System.out.println("Uang kurang, harap isi uang dengan benar");
        continue;
      }

      break;
    }

    System.out.printf("Kembalian         :Rp.%.0f\n", kembalian);
    System.out.println("--------------------------------------");

    if (totalBayar >= this.freeMinuman) {
      pilihMinumanGratis();
    }

    PenyimpananStruk.simpan(this.pesanan, pajak, admin, kembalian,
        String.format("./struk/%s-%d.csv", this.pesanan.getNama(), Instant.now().getEpochSecond()));

    this.pesanan.getItemsPesanan().clear();

    beranda();
  }
}
