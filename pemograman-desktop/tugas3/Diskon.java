public class Diskon extends MenuItem {
  private double diskon = 0;

  public Diskon(String nama, double diskon) {
    super(nama, diskon, "Diskon");
    this.diskon = diskon;
  }

  public double getDiskon() {
    return this.diskon;
  }

  @Override
  public void tampilMenu() {
    System.out.printf("Diskon (%s) @ Rp. %.0f \t\n", this.getNama(), this.diskon);
  }

  @Override
  public String getJenis() {
    return "";
  }

}
