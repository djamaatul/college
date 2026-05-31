import java.util.ArrayList;

class PesananItem {
  MenuItem menu;
  int jumlah;

  PesananItem(MenuItem menu, int jumlah) {
    this.menu = menu;
    this.jumlah = jumlah;
  }
}

class Pesanan {
  private String nama = "";
  private ArrayList<PesananItem> items = new ArrayList<>();

  public Pesanan(String nama) {
    this.nama = nama;
  }

  public String getNama() {
    return nama;
  }

  public ArrayList<PesananItem> getItemsPesanan() {
    return items;
  }

  public double totalHargaPesanan() {
    double harga = 0;
    System.out.println("-------------PESANAN ANDA-------------");

    int index = 1;
    for (PesananItem item : this.items) {
      System.out.printf("%d. %s \t - %.0f x %d\n", index, item.menu.getNama(), item.menu.getHarga(),
          item.jumlah);
      if(item.menu.getKategori() == "Diskon") {
        harga -= item.menu.getHarga();
      } else {
        harga += item.menu.getHarga() * item.jumlah;
      }
    }
    System.out.println("--------------------------------------");
    return harga;
  }

  public void addItemsPesanan(PesananItem menu) {
    this.items.add(menu);
  }
}
