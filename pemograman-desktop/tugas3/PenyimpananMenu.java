import java.io.*;
import java.util.*;

public class PenyimpananMenu {

  public static void simpan(
      ArrayList<MenuItem> daftarMenu,
      String namaFile) {

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(namaFile))) {

      for (MenuItem item : daftarMenu) {

        bw.write(
            item.getNama() + ";" +
                item.getHarga() + ";" +
                item.getKategori() + ";" +
                item.getJenis());
        bw.newLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static ArrayList<MenuItem> muat(String namaFile) {

    ArrayList<MenuItem> daftarMenu = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(namaFile))) {

      String line;

      while ((line = br.readLine()) != null) {

        String[] data = line.split(";", -1);

        String nama = data[0];
        double harga = Double.parseDouble(data[1]);
        String tipe = data[2];
        String jenis = data[3];

        switch (tipe) {
          case "Makanan":
            daftarMenu.add(new Makanan(nama, harga, jenis));
            break;
          case "Minuman":
            daftarMenu.add(new Minuman(nama, harga, jenis));
            break;
          case "Diskon":
            daftarMenu.add(new Diskon(nama, harga));
            break;
          default:
            break;
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return daftarMenu;
  }
}
