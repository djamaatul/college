public class Minuman extends MenuItem {
  private String jenisMinuman;

  public Minuman(String nama, double harga, String jenisMinuman) {
    super(nama, harga, "Minuman");
    this.jenisMinuman = jenisMinuman;
  }

  @Override
  public void tampilMenu() {
    System.out.printf(" %s \t@ Rp.%.0f %s\n", super.getNama(), this.getHarga(), this.jenisMinuman);
  }

  @Override
  public String getJenis() {
    return this.jenisMinuman;
  }

}
