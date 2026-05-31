import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Menu {

  private Scanner baca = new Scanner(System.in);
  public ArrayList<MenuItem> daftarMenu = new ArrayList<>();

  private Set<String> getSemuaKategori() {
    Set<String> listKategori = new HashSet<>();
    for (MenuItem menu : this.daftarMenu) {
      System.out.println(menu.getNama() + menu.getKategori());
      listKategori.add(menu.getKategori());
    }

    return listKategori;
  }

  public void tampilSemuaMenu() {
    int index = 0;
    for (String kategori : getSemuaKategori()) {
      if (kategori == "Diskon")
        continue;
      System.out.printf("\n** MENU %s **\n\n", kategori.toUpperCase());
      for (MenuItem menu : this.daftarMenu) {
        if (menu.getKategori() != kategori) {
          continue;
        }
        ++index;
        System.out.print(index + ". ");
        menu.tampilMenu();
      }
      System.out.println();
    }
  }

  public void tampilSemuaMenu(boolean semua) {
    int index = 0;
    for (MenuItem menu : this.daftarMenu) {
      ++index;
      System.out.print(index + ". ");
      menu.tampilMenu();
    }
    System.out.println();

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

  boolean konfirmasi(String aksi) {
    System.out.printf("Anda yakin untuk %s ? (ya/tidak): ", aksi);
    String pilih = baca.nextLine();

    if (pilih.equalsIgnoreCase("ya")) {
      return true;
    }

    return false;
  }

  int ambilMenu() {
    tampilSemuaMenu();

    System.out.println("\n0. Batal");

    System.out.print("\nPilih: ");
    int pilih = baca.nextInt();
    baca.nextLine();

    if (pilih == 0) {
      clearScreen();
      return 0;
    }

    if (pilih < 0 || pilih > this.daftarMenu.size() || this.daftarMenu.get(pilih - 1).getKategori() == "Diskon") {
      error("Pilihan tidak ditemukan");
      return -1;
    }
    return pilih;
  }

  private void hapusMenu() {
    int pilih = ambilMenu();

    if (pilih == -1) {
      hapusMenu();
      return;
    }

    if (pilih == 0) {
      manajemenMenu();
      return;
    }

    boolean ok = konfirmasi("Hapus");

    if (!ok) {
      manajemenMenu();
      return;
    }

    this.daftarMenu.remove(pilih - 1);

    manajemenMenu();
  }

  private void ubahHargaMenu() {
    int pilih = ambilMenu();

    if (pilih == -1) {
      hapusMenu();
      return;
    }

    if (pilih == 0) {
      manajemenMenu();
      return;
    }

    System.out.printf("\n Masukan Harga baru: ");
    double hargaBaru = baca.nextDouble();
    baca.nextLine();

    boolean ok = konfirmasi("Ubah");

    if (!ok) {
      manajemenMenu();
      return;
    }

    this.daftarMenu.get(pilih - 1).setHarga(hargaBaru);

    manajemenMenu();
  }

  public void tambahMenu(MenuItem item) {
    this.daftarMenu.add(item);
    PenyimpananMenu.simpan(this.daftarMenu, "menu.csv");
  }

  public void tambahMenu(List<MenuItem> items) {
    this.daftarMenu.addAll(items);
    PenyimpananMenu.simpan(this.daftarMenu, "menu.csv");
  }

  private void tambahMenu() {

    System.out.print("Nama Menu: ");
    String nama = baca.nextLine();

    System.out.print("Harga Menu: ");
    double harga = baca.nextDouble();
    baca.nextLine();

    String kategori;
    while (true) {
      System.out.println();
      System.out.println("1. Makanan");
      System.out.println("2. Minuman");
      System.out.println("3. Diskon");
      System.out.print("Pilih Kategori: ");
      int jenisKategori = baca.nextInt();
      baca.nextLine();

      switch (jenisKategori) {
        case 1:
          kategori = "Makanan";
          break;
        case 2:
          kategori = "Minuman";
          break;
        case 3:
          kategori = "Diskon";
          break;
        default:
          continue;
      }
      break;
    }

    System.out.printf("Jenis (opsional)\s: ");
    String jenisItem = baca.nextLine();

    boolean ok = konfirmasi("Tambah");

    if (!ok) {
      manajemenMenu();
      return;
    }

    int index = 0;
    int indexDisimpan = this.daftarMenu.size();
    for (MenuItem menu : this.daftarMenu) {
      if (menu.getKategori() == kategori) {
        indexDisimpan = index;
      }
      ++index;
    }
    switch (kategori) {
      case "Makanan":
        this.daftarMenu.add(indexDisimpan + 1, new Makanan(nama, harga, jenisItem));
        break;
      case "Minuman":
        this.daftarMenu.add(indexDisimpan + 1, new Minuman(nama, harga, jenisItem));
        break;
      case "Diskon":
        this.daftarMenu.add(new Diskon(nama, harga));
        break;
      default:
        tambahMenu();
    }

    PenyimpananMenu.simpan(this.daftarMenu, "menu.csv");
    manajemenMenu();
  }

  void manajemenMenu() {

    System.out.println("-------------MANAJEMEN MENU-------------");

    System.out.println("1. Tambah Menu");
    System.out.println("2. Ubah Harga");
    System.out.println("3. Hapus Menu");
    System.out.println("4. Lihat Menu");
    System.out.println("0. Batal");

    System.out.print("\nPilih: ");
    int pilih = baca.nextInt();
    baca.nextLine();

    if (pilih == 0) {
      clearScreen();
      return;
    }

    switch (pilih) {
      case 1:
        tambahMenu();
        break;
      case 2:
        ubahHargaMenu();
        break;
      case 3:
        hapusMenu();
        break;
      case 4:
        tampilSemuaMenu(true);
        manajemenMenu();
        break;
      default:
        break;
    }

  }
}
