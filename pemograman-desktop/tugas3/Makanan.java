public class Makanan extends MenuItem {
  private String jenisMakanan;

  public Makanan(String nama, double harga, String jenisMakanan) {
    super(nama, harga, "Makanan");

    this.jenisMakanan = jenisMakanan;
  }

  @Override
  public void tampilMenu() {
    System.out.printf(" %s \t@ Rp.%.0f %s\n", super.getNama(), this.getHarga(), this.jenisMakanan);
  }

  @Override
  public String getJenis() {
    return this.jenisMakanan;
  }

}
