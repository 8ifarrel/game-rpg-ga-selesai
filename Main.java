import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {
  private static ArrayList<Player> players = new ArrayList<>();

  public static void main(String[] args) {
    int choice;

    do {
      Utils.clearScreen();

      System.out.println("[]===========================[]");
      System.out.println("      PEJUANG YTTA EMPIRE      ");
      System.out.println("[]===========================[]");
      System.out.println("");
      System.out.println("-------------------------------");
      System.out.println("| 1. Register                 |");
      System.out.println("| 2. Log in                   |");
      System.out.println("| 3. Keluar                   |");
      System.out.println("-------------------------------");
      System.out.println("");

      Scanner sc = new Scanner(System.in);

      System.out.print("Pilih menu: ");
      choice = sc.nextInt();

      switch (choice) {
        case 1:
          registerAccount();
          break;
        case 2:
          loginAccount();
          break;
        case 3:
          exitGame();
          break;
        default:
          Utils.invalidChoice();
      }
    } while (choice != 3);
  }

  static void registerAccount() {
    Utils.clearScreen();

    System.out.println("[]===========================[]");
    System.out.println("      PEJUANG YTTA EMPIRE      ");
    System.out.println("[]===========================[]");
    System.out.println("");

    Scanner sc = new Scanner(System.in);

    // Username
    System.out.print("Username\t: ");
    String username = sc.nextLine();
    System.out.println("");

    // Password
    System.out.print("Password\t: ");
    String password = sc.nextLine();
    System.out.println("");

    // In-game name
    System.out.print("In-game name\t: ");
    String name = sc.nextLine();
    System.out.println("");

    // UUID
    UUID playerUUID = UUID.randomUUID();

    Player player = new Player(username, password, name, playerUUID);
    players.add(player);

    System.out.println("-----------------------------");
    System.out.println("| Akun berhasil ditambahkan |");
    System.err.println("-----------------------------");
    System.out.println("");

    Utils.pressEnterToContinue();
  }

  static void loginAccount() {
    Utils.clearScreen();

    System.out.println("[]===========================[]");
    System.out.println("      PEJUANG YTTA EMPIRE      ");
    System.out.println("[]===========================[]");
    System.out.println("");

    Scanner sc = new Scanner(System.in);

    System.out.print("Masukkan username: ");
    String username = sc.nextLine();
    System.out.println("");

    System.out.print("Masukkan password: ");
    String password = sc.nextLine();
    System.out.println("");

    for (Player player : players) {
      if (player.getUsername().equals(username) && player.getPassword().equals(password)) {
        mainMenu(player); 
        return;
      }
    }

    System.out.println("---------------");
    System.out.println("| Login salah |");
    System.out.println("---------------");
    System.out.println("");

    Utils.pressEnterToContinue();
  }

  static void mainMenu(Player player) { // Accepting Player object as parameter
    int choice;

    do {
      Utils.clearScreen();

      System.out.println("[]===========================[]");
      System.out.println("      PEJUANG YTTA EMPIRE      ");
      System.out.println("[]===========================[]");
      System.out.println("");
      System.out.println("-------------------------------");
      System.out.println("| 1. Mulai Game               |");
      System.out.println("| 2. Konfigurasi Akun         |");
      System.out.println("| 3. Log Out                  |");
      System.out.println("-------------------------------");
      System.out.println("");

      Scanner sc = new Scanner(System.in);

      System.out.print("Pilih menu: ");
      choice = sc.nextInt();
      System.out.println("");

      switch (choice) {
        case 1:
          startGame(player); // Start game option
          break;
        case 2:
          System.out.println("Konfigurasi akun");
          break;
        case 3:
          break;
        default:
          Utils.invalidChoice();
      }
    } while (choice != 3);
  }

  static void startGame(Player player) {
    // Create enemies
    ArrayList<Enemy> enemies = new ArrayList<>();
    enemies.add(new Slime(UUID.randomUUID(), 1));
    enemies.add(new Slime(UUID.randomUUID(), 1));
    enemies.add(new Slime(UUID.randomUUID(),1));

    // Create game instance
    Game game = new Game(player, enemies);

    // Start the game
    game.start();
  }

  static void exitGame() {
    Utils.clearScreen();

    System.out.println("[]===========================[]");
    System.out.println("      PEJUANG YTTA EMPIRE      ");
    System.out.println("[]===========================[]");
    System.out.println("");
    System.out.println("-------------------------------");
    System.out.println("|       Selamat tinggal       |");
    System.out.println("-------------------------------");
  }
}
