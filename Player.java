import java.util.ArrayList;
import java.util.UUID;

public class Player {
  String username;
  String password;
  String name;
  UUID uuid;
  ArrayList<Fighter> fighters;

  public Player(String username, String password, String name, UUID uuid) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.uuid = uuid;
    this.fighters = new ArrayList<>();
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public UUID getUuid() {
    return uuid;
  }

  public ArrayList<Fighter> getFighters() {
    return fighters;
  }

  /**
   * Memilih fighter mana yang digunakan untuk menyerang
   */
  public Fighter chooseFighter() {
    for (int i = 0; i < fighters.size(); i++) {
      Fighter fighter = fighters.get(i);
      System.out.println((i + 1) + ". " + fighter.getClass().getSimpleName() + " (HP: " + fighter.health + "|| Mana: "
          + fighter.mana + ")");
    }
    System.out.println("");

    int choice = Utils.getUserInputInt("Pilih fighter: ", 1, fighters.size()) - 1;
    System.out.println("");

    return fighters.get(choice);
  }

  /**
   * Mengecek apakah semua fighter sudah mati
   */
  public boolean isAllFightersDead() {
    for (Fighter fighter : fighters) {
      if (fighter.health > 0) {
        return false;
      }
    }
    return true;
  }
}
