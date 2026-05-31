import java.io.*;
import java.util.*;

public class PenyimpananStruk {

  public static void simpan(Pesanan pesanan, double pajak, double admin, double kembalian, String namaFile) {

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {

      writer.write("Nama Pelanggan: " + pesanan.getNama());
      writer.newLine();

      writer.write("--------------------------------");
      writer.newLine();

      double totalBayar = pesanan.totalHargaPesanan();

      System.out.println("-------------PESANAN ANDA-------------");

      int index = 1;
      for (PesananItem item : pesanan.getItemsPesanan()) {
        writer.write(String.format("%d. %s \t - %.0f x %d\n", index, item.menu.getNama(), item.menu.getHarga(), item.jumlah));
        writer.newLine();
        if (item.menu.getKategori() == "Diskon") {
          totalBayar -= item.menu.getHarga();
        } else {
          totalBayar += item.menu.getHarga() * item.jumlah;
        }
      }
      writer.write("--------------------------------------");
      writer.newLine();

      double diskon = 0;

      if (totalBayar >= 100000) {
        diskon += (totalBayar) * 10 / 100; // Diskon 10%
      }

      double totalPajak = (totalBayar * pajak);
      totalBayar += totalPajak;

      totalBayar -= diskon;
      totalBayar += admin;

      writer.write("Biaya Pelayanan   :" + admin);
      writer.newLine();
      writer.write("Pajak             :" + totalPajak + " (" + (pajak * 100) + "%" + ")");
      writer.newLine();
      writer.write("Diskon            :" + diskon);
      writer.newLine();
      writer.write("Total Pesanan     :" + totalBayar);
      writer.newLine();

      writer.write(String.format("Kembalian         :Rp.%.0f\n", kembalian));
      writer.newLine();

      writer.write("--------------------------------");
      writer.newLine();

      System.out.println("Struk berhasil disimpan.");

    } catch (IOException e) {
      System.out.println("Gagal menyimpan struk: " + e.getMessage());
    }
  }
}
