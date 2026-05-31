public abstract class MenuItem {
  private String nama;
  private double harga;
  private String kategori;

  public MenuItem(String nama, double harga, String kategori) {
    this.nama = nama;
    this.harga = harga;
    this.kategori = kategori;
  }

  public String getNama() {
    return this.nama;
  }

  public double getHarga() {
    return this.harga;
  }

  public String getKategori() {
    return this.kategori;
  }

  public void setHarga(double harga) {
    if (this.harga <= 0) {
      throw new Error("Harga harus melebihi 0");
    }

    this.harga = harga;
  }

  public abstract void tampilMenu();
  public abstract String getJenis();
}
