import java.util.Scanner;

public class Utils {
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void pressEnterToContinue() {
    System.out.println("Tekan ENTER untuk melanjutkan");
    Scanner sc = new Scanner(System.in);
    sc.nextLine();
  }

  public static void invalidChoice() {
    System.out.println("-----------------------");
    System.out.println("| Pilihan tidak valid |");
    System.out.println("-----------------------");
    System.out.println("");

    pressEnterToContinue();
  }

  public static void insufficientMana() {
    System.out.println("------------------------------------------------");
    System.out.println("| Mana tidak cukup untuk menggunakan skill ini |");
    System.out.println("------------------------------------------------");

    Utils.clearScreen();
  }

  private static Scanner scanner = new Scanner(System.in);

  public static int getUserInputInt(String message, int min, int max) {
    int input;
    do {
      System.out.print(message);
      input = scanner.nextInt();
      if (input < min || input > max) {
        System.out.println("Pilihan tidak valid, silakan masukkan nomor antara " + min + " dan " + max + ".");
      }
    } while (input < min || input > max);
    return input;
  }
}