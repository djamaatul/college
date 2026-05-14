import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Resto {
  String nama;
  ArrayList<Menu> menuResto;
  Scanner baca = new Scanner(System.in);
  ArrayList<Pesanan> pesanan = new ArrayList<Pesanan>();

  double pajak = 0.1; // 10%
  int admin = 20000; // 10%
  int freeMinuman = 50000;

  Resto(String nama, Menu[] menuResto) {
    this.nama = nama;
    this.menuResto = new ArrayList<>(Arrays.asList(menuResto));
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

    System.out.println("1. Pesan");
    System.out.println("2. Manajemen Menu");

    if (!this.pesanan.isEmpty()) {
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
        pilihMenu();
        break;
      case 2:
        manajemenMenu();
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

  int ambilMenu() {
    lihatSemuaMenu();

    System.out.println("0. Batal");

    System.out.print("\nPilih: ");
    int pilih = baca.nextInt();
    baca.nextLine();

    if (pilih == 0) {
      return 0;
    }

    if (pilih <= 0 || pilih > this.menuResto.size()) {
      clearScreen();
      System.out.println("Pilihan anda salah, pilih nomor yang tertera");
      return -1;
    }

    return pilih;
  }

  void pilihMenu() {

    int pilih = ambilMenu();
    
    if(pilih == -1) {
      pilihMenu();
      return;
    }

    if(pilih == 0) {
      beranda();
      return;
    }

    System.out.print("Jumlah: ");
    int jumlah = baca.nextInt();
    baca.nextLine();

    this.pesanan.add(new Pesanan(this.menuResto.get(pilih - 1), jumlah));

    clearScreen();
    beranda();
  }

  void lihatSemuaMenu() {
    System.out.println("------------------------------------");
    lihatMakanan();
    lihatMinuman();
    System.out.println("------------------------------------");
  }

  void lihatMakanan() {
    System.out.println("\n** LIHAT MAKANAN **\n");
    int index = 0;
    for (Menu menu : this.menuResto) {
      ++index;
      if (menu.kategori != "Makanan")
        continue;
      System.out.printf("%d. %s \t\t @ %.0f\n", index, menu.nama, menu.harga);
    }
  }

  void lihatMinuman() {
    System.out.println("\n** LIHAT MINUMAN **\n");

    int index = 0;
    for (Menu menu : this.menuResto) {
      ++index;
      if (menu.kategori != "Minuman")
        continue;
      System.out.printf("%d. %s \t\t @ %.0f\n", index, menu.nama, menu.harga);
    }
  };

  float hargaPesanan(int pesananKe) {
    if (this.pesanan.size() >= pesananKe + 1) {
      Pesanan item = pesanan.get(pesananKe);
      System.out.println("# " + item.menu.nama + "\t: Rp." + item.menu.harga);
      return item.menu.harga;
    }
    return 0;
  }

  double totalHargaPesanan() {
    double harga = 0;
    int index = 0;
    System.out.println("-------------PESANAN ANDA-------------");
    for (Pesanan pesanan : this.pesanan) {
      harga += pesanan.menu.harga * pesanan.jumlah;

      System.out.printf("%d. %s @ %.0f x %d\n", index, pesanan.menu.nama, pesanan.menu.harga, pesanan.jumlah);
    }
    System.out.println("--------------------------------------");
    return harga;
  }

  void pilihMinumanGratis() {
    for (Pesanan pesanan : this.pesanan) {
      if (pesanan.menu.kategori == "Minuman") {
        System.out.printf("## Selamat anda mendapatkan minuman %s ##\n", pesanan.menu.nama);
        return;
      }
    }
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

    while (true) {
      System.out.print("Masukan uang      :");
      double uang = baca.nextDouble();
      baca.nextLine();

      double kembalian = uang - totalBayar;

      if (kembalian < 0) {
        System.out.println("Uang kurang, harap isi uang dengan benar");
        continue;
      }
      System.out.printf("Kembalian         :Rp%.0f\n", kembalian);
      break;
    }

    System.out.println("--------------------------------------");

    if (totalBayar >= this.freeMinuman) {
      pilihMinumanGratis();
    }

    this.pesanan.clear();

    beranda();
  }

  void manajemenMenu() {
    clearScreen();
    System.out.println("1. Lihat Menu");
    System.out.println("2. Tambah Menu");
    System.out.println("3. Ubah Harga Menu");
    System.out.println("4. Hapus Menu");

    System.out.println("\n0. Keluar");

    System.out.print("\nPilih: ");
    int pilih = baca.nextInt();
    baca.nextLine();

    switch (pilih) {
      case 0:
        clearScreen();
        beranda();
        break;
      case 1:
        clearScreen();
        lihatSemuaMenu();
        System.out.print("\nKetuk apa saja untuk kembali");
        baca.nextLine();
        manajemenMenu();
        break;
      case 2:
        tambahMenu();
        break;
      case 3:
        ubahHargaMenu();
        break;
      case 4:
        hapusMenu();
        break;
      default:
        manajemenMenu();
        break;
    }
  }

  boolean konfirmasi(String message) {
    System.out.printf("Anda yakin untuk %s? (ya/tidak):", message);
    String pilih = baca.nextLine();
    if (pilih.equalsIgnoreCase("ya"))
      return true;
    return false;
  };

  void tambahMenu() {
    System.out.println("-------------TAMBAH MENU-------------");

    System.out.print("Nama Menu: ");
    String nama = baca.nextLine();
    System.out.print("Harga Menu: ");
    float harga = baca.nextFloat();
    baca.nextLine();

    int jenisKategori;
    while (true) {
      System.out.print("Pilih Kategori (1 = Makanan, 2 = Minuman): ");
      jenisKategori = baca.nextInt();
      baca.nextLine();

      if (jenisKategori != 1 && jenisKategori != 2)
        continue;

      break;
    }

    String kategori = jenisKategori == 1 ? "Makanan" : "Minuman";

    boolean ok = konfirmasi("Tambah");

    if (!ok) {
      manajemenMenu();
      return;
    }

    int index = 0;
    int menuDisimpan = this.menuResto.size();

    for (Menu menu : this.menuResto) {
      if (menu.kategori == kategori)
        menuDisimpan = index;
      ++index;
    }

    this.menuResto.add(menuDisimpan + 1, new Menu(nama, harga, kategori));

    manajemenMenu();
  }

  private void hapusMenu() {
    System.out.println("-------------HAPUS HARGA-------------");
    int pilih = ambilMenu();

    if (pilih == 0) {
      manajemenMenu();
      return;
    }

    if (pilih == -1) {
      hapusMenu();
      return;
    }

    boolean ok = konfirmasi(String.format("Menghapus %s", this.menuResto.get(pilih - 1).nama));

    if (!ok) {
      manajemenMenu();
      return;
    }

    this.menuResto.remove(pilih - 1);

    manajemenMenu();
  }

  private void ubahHargaMenu() {
    clearScreen();
    System.out.println("-------------UBAH HARGA-------------");

    int pilih = ambilMenu();

    if (pilih == 0) {
      manajemenMenu();
      return;
    }

    if (pilih == -1) {
      ubahHargaMenu();
      return;
    }

    System.out.print("Harga Baru : ");
    float hargaBaru = baca.nextFloat();
    baca.nextLine();

    boolean ok = konfirmasi(String.format("Ubah Harga ke %.0f", hargaBaru));

    if (!ok) {
      manajemenMenu();
      return;
    }

    this.menuResto.get(pilih - 1).harga = hargaBaru;

    manajemenMenu();
  }

}
