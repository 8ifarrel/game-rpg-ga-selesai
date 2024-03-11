import java.util.UUID;

public abstract class Enemy {
  UUID uuid;
  int level;
  double health;
  double attack;
  double defense;
  boolean isAlive;

  public Enemy(UUID uuid, int level) {
    final double DEFAULT_HEALTH = 20.3;
    final double DEFAULT_ATTACK = 9.2;
    final double DEFAULT_DEFENSE = 7.3;

    this.uuid = uuid;
    this.level = level;
    this.health = DEFAULT_HEALTH;
    this.attack = DEFAULT_ATTACK;
    this.defense = DEFAULT_DEFENSE;

    isAlive = true;
  }

  public abstract void basicAttack(Fighter fighter);
}

/**
 * Slime
 */
class Slime extends Enemy {
  String enemyRole = this.getClass().getSimpleName();

  public Slime(UUID uuid, Integer level) {
    super(uuid, level);
  }

  @Override
  public void basicAttack(Fighter fighter) {
    double damage = (1.0 * this.attack) - fighter.defense;
    String fighterRole = fighter.getClass().getSimpleName();

    // Enemy tidak akan menerima damage jika damage terlalu kecil
    if (damage <= 0) {
      System.out.println(fighterRole + " IMMUNE terhadap serangan dari " + enemyRole);
      return;
    }

    fighter.health -= damage;
    System.out.println(enemyRole + " melakukan serangan fisik ke " + fighterRole);

    // Mengecek apakah fighter yang diserang masih hidup
    if (fighter.health <= 0) {
      fighter.isAlive = false;
      System.out.println(fighterRole + " telah mati");
    }
  }
}
