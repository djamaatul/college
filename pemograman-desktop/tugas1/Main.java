public class Main {
  public static void main(String[] args) {
    Resto resto1 = new Resto("Ubar lapar", new Menu[] {
        new Menu("Bakso Sapi", 100000, "Makanan"),
        new Menu("Sate Ayam", 20000, "Makanan"),
        new Menu("Soto Ayam", 18000, "Makanan"),
        new Menu("Mie Ayam", 15000, "Makanan"),

        new Menu("Es Teh", 5000, "Minuman"),
        new Menu("Es Kopi", 8000, "Minuman"),
        new Menu("Es Jeruk", 7000, "Minuman"),
        new Menu("Air Mineral", 5000, "Minuman"),
    });

    resto1.beranda();
  }
}
