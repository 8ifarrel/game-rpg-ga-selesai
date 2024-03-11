import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
  private Player player;
  private ArrayList<Enemy> enemies;
  private int currentEnemyIndex;
  private int deadEnemiesCount;
  private boolean playerTurn;
  private Random random;
  private Fighter fighter;

  public Game(Player player, ArrayList<Enemy> enemies) {
    this.player = player;
    this.enemies = enemies;
    this.currentEnemyIndex = 0;
    this.deadEnemiesCount = 0; // berapa jumlah musuh yang sudah mati
    this.playerTurn = true; // player pertama kali jalan
    this.random = new Random();
    this.fighter = null;
  }

  public void start() {
    // Pilih fighter
    byte choice;

    do {
      Utils.clearScreen();

      System.out.println("[]===========================[]");
      System.out.println("      PEJUANG YTTA EMPIRE      ");
      System.out.println("[]===========================[]");
      System.out.println("");
      System.out.println("-------------------------------");
      System.out.println("| 1. Assassin                 |");
      System.out.println("| 2. Warrior                  |");
      System.out.println("| 3. Sorcerer                 |");
      System.out.println("-------------------------------");
      System.out.println("");

      Scanner sc = new Scanner(System.in);

      System.out.print("Pilih fighter Anda: ");
      choice = sc.nextByte();
      System.out.println("");

      switch (choice) {
        case 1:
          fighter = new Assassin();
          break;
        case 2:
          fighter = new Warrior();
          break;
        case 3:
          fighter = new Sorcerer();
          break;
        default:
          Utils.invalidChoice();
          break;
      }
    } while (choice != 3 && choice != 2 && choice != 1);

    player.getFighters().add(fighter);

    // Game dimulai di sini
    System.out.println("------------------------");
    System.out.println("| Pertempuran dimulai! |");
    System.out.println("------------------------");
    System.out.println("");

    Utils.pressEnterToContinue();

    // Game tetap berjalan jika masih ada yang hidup
    while (!isGameOver()) {
      if (playerTurn) {
        playerTurn();
      } else {
        enemyTurn();
      }
    }

    // Menang jika semua musuh sudah mati
    if (deadEnemiesCount == enemies.size()) {
      Utils.clearScreen();

      System.out.println("---------------");
      System.out.println("| Anda menang |");
      System.out.println("---------------");
      System.out.println("");

      Utils.pressEnterToContinue();
      return;
    }

    // Kalah jika semua fighter sudah mati
    if (player.isAllFightersDead()) {
      Utils.clearScreen();

      System.out.println("--------------");
      System.out.println("| Anda kalah |");
      System.out.println("--------------");
      System.out.println("");

      Utils.pressEnterToContinue();
      return;
    }
  }

  /**
   * Mengecek apakah player atau musuh kalah
   */
  private boolean isGameOver() {
    return player.isAllFightersDead() || deadEnemiesCount == enemies.size();
  }

  /**
   * Memilih musuh mana yang ingin diserang
   */
  private Enemy chooseEnemy() {
    int enemyChoice;

    do {
      for (int i = 0; i < enemies.size(); i++) {
        Enemy enemy = enemies.get(i);
        if (enemy.isAlive) {
          System.out.println((i + 1) + ". " + enemy.getClass().getSimpleName() + " (HP: " + enemy.health + ")");
        }
      }
      System.out.println("");

      System.out.print("Pilih musuh yang ingin Anda serang: ");
      enemyChoice = new Scanner(System.in).nextInt() - 1;
      System.out.println("");

      // Jika salah memilih musuh
      if (enemyChoice < 0 || enemyChoice >= enemies.size() || !enemies.get(enemyChoice).isAlive) {
        System.out.println("Pilihan musuh tidak valid.");
      }
    } while (enemyChoice < 0 || enemyChoice >= enemies.size() || !enemies.get(enemyChoice).isAlive);

    return enemies.get(enemyChoice);
  }

  /**
   * Memilih menyerang musuh menggunakan apa
   */
  private void chooseAction(Fighter fighter, Enemy targetEnemy) {
    while (true) {
      System.out.println("1. Basic Attack");
      System.out.println("2. First Skill");
      System.out.println("3. Second Skill");
      System.out.println("4. Ultimate Skill");
      System.out.println("");

      Scanner sc = new Scanner(System.in);

      System.out.print("Pilih aksi: ");
      int choice = sc.nextInt();
      System.out.println("");

      System.out.println("=========================================");
      System.out.println("");

      switch (choice) {
        case 1:
          fighter.basicAttack(targetEnemy);
          Utils.pressEnterToContinue();
          return;
        case 2:
          if (fighter.mana >= fighter.MANA_COST_FIRST_SKILL) {
            fighter.firstSkill(targetEnemy);
            Utils.pressEnterToContinue();
            return;
          } else {
            Utils.insufficientMana();
          }
          break;
        case 3:
          if (fighter.mana >= fighter.MANA_COST_SECOND_SKILL) {
            fighter.secondSkill();
            Utils.pressEnterToContinue();
            return;
          } else {
            Utils.insufficientMana();
          }
          break;
        case 4:
          if (fighter.mana >= fighter.MANA_COST_ULTIMATE_SKILL) {
            fighter.ultimateSkill(targetEnemy);
            Utils.pressEnterToContinue();
            return;
          } else {
            Utils.insufficientMana();
          }
          break;
        default:
          Utils.invalidChoice();
      }
    }
  }

  /**
   * Giliran player
   */
  private void playerTurn() {
    Utils.clearScreen();

    System.out.println("----------------");
    System.out.println("| Giliran Anda |");
    System.out.println("----------------");
    System.out.println("");

    System.out.println("=========================================");
    System.out.println("");

    Fighter fighter = player.chooseFighter();

    System.out.println("=========================================");
    System.out.println("");

    Enemy targetEnemy = chooseEnemy();

    System.out.println("=========================================");
    System.out.println("");

    chooseAction(fighter, targetEnemy);

    // Mengecek berapa musuh yang sudah mati
    if (targetEnemy.health <= 0) {
      deadEnemiesCount++;
    }

    playerTurn = false;
  }

  /**
   * Giliran musuh
   */
  private void enemyTurn() {
    Utils.clearScreen();

    System.out.println("-----------------");
    System.out.println("| Giliran Musuh |");
    System.out.println("-----------------");
    System.out.println("");

    // Secara acak memilih musuh mana yang menyerang
    Enemy enemy = enemies.get(random.nextInt(enemies.size()));

    // Musuh menyerang jika masih ada yang hidup
    if (enemy.isAlive) {
      // Secara acak memilih fighter mana yang ingin diserang
      Fighter targetFighter = player.getFighters().get(random.nextInt(player.getFighters().size()));

      // musuh menyerang dengan basic attack
      enemy.basicAttack(targetFighter);

      Utils.pressEnterToContinue();
    }

    playerTurn = true;
  }
}
