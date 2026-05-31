public class Main {
  public static void main(String[] args) {
    Menu menu = new Menu();

    menu.tambahMenu(PenyimpananMenu.muat("menu.csv"));
    Resto resto1 = new Resto("Ubar lapar", menu);

    resto1.beranda();
  }
}
