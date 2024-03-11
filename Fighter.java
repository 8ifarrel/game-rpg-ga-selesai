public abstract class Fighter {
  Double experiencePoint;
  int level;
  double health;
  double attack;
  double mana;
  double defense;
  double ultimateOrb;
  boolean isAlive;
  boolean isVisible; // attribute untuk second skill (Smoke Bomb) dari Assassin

  public Fighter() {
    final int DEFAULT_LEVEL = 1;
    final double DEFAULT_EXPERIENCE_POINT = 0;
    final double DEFAULT_HEALTH = 100.0;
    final double DEFAULT_ATTACK = 9.2;
    final double DEFAULT_MANA = 54.3;
    final double DEFAULT_DEFENSE = 7.3;
    final double DEFAULT_ULTIMATE_ORB = 0.0;

    this.level = DEFAULT_LEVEL;
    this.experiencePoint = DEFAULT_EXPERIENCE_POINT;
    this.health = DEFAULT_HEALTH;
    this.attack = DEFAULT_ATTACK;
    this.mana = DEFAULT_MANA;
    this.defense = DEFAULT_DEFENSE;
    this.ultimateOrb = DEFAULT_ULTIMATE_ORB;

    this.isAlive = true;
    this.isVisible = true;
  }

  // Skill mana cost
  protected final double MANA_COST_FIRST_SKILL = 8.2;
  protected final double MANA_COST_SECOND_SKILL = 9.6;
  protected final double MANA_COST_ULTIMATE_SKILL = 19.6;

  // Basic attack dan skill
  public abstract void basicAttack(Enemy enemy);
  public abstract void firstSkill(Enemy enemy);
  public abstract void secondSkill();
  public abstract void ultimateSkill(Enemy enemy);

}

/**
 * Assassin
 */
class Assassin extends Fighter {
  protected String fighterRole = this.getClass().getSimpleName();

  /**
   * Assassin mempunyai kelebihan dalam attack sehingga dapat memberikan
   * serangan yang mematikan kepada enemy.
   */
  public Assassin() {
    super();
    this.attack *= 2.69;
  }

  /**
   * Punch
   */
  @Override
  public void basicAttack(Enemy enemy) {
    double damage = (1.0 * this.attack) - enemy.defense;
    String enemyRole = enemy.getClass().getSimpleName();

    // Enemy tidak akan menerima damage jika damage terlalu kecil
    if (damage <= 0) {
      System.out.println(enemyRole + " IMMUNE terhadap serangan dari " + fighterRole);
      return;
    }

    enemy.health -= damage;
    System.out.println(fighterRole + " melakukan serangan fisik ke " + enemyRole);

    // Mengecek apakah enemy yang diserang masih hidup
    if (enemy.health <= 0) {
      enemy.isAlive = false;
      System.out.println(enemyRole + " telah mati");
    }
  }

  /**
   * Backstab
   * 
   * Skill ini memungkinkan player menyerang dari belakang kepada satu enemy
   * yang dipilin sekaligus mengabaikan sebagian defense enemy.
   */
  @Override
  public void firstSkill(Enemy enemy) {
    final double DEFENSE_REDUCTION_PERCENTAGE = 0.7;
    double damage = (2.0 * this.attack) - (enemy.defense * DEFENSE_REDUCTION_PERCENTAGE);
    String enemyRole = enemy.getClass().getSimpleName();

    // Player tidak bisa menggunakan skill jika mana kurang
    if (this.mana < MANA_COST_FIRST_SKILL) {
      System.out.println("Mana tidak cukup untuk menggunakan skill Backstab");
      return;
    }

    this.mana -= MANA_COST_FIRST_SKILL;

    // Enemy tidak akan menerima damage jika damage terlalu kecil
    if (damage <= 0) {
      System.out.println(enemyRole + " IMMUNE terhadap serangan dari " + fighterRole);
      return;
    }

    enemy.health -= damage;
    System.out.println(fighterRole + " melakukan serangan fisik ke " + enemyRole);

    // Mengecek apakah enemy yang diserang masih hidup
    if (enemy.health <= 0) {
      enemy.isAlive = false;
      System.out.println(enemyRole + " telah mati");
    }
  }

  /**
   * Smoke Bomb
   * 
   * Skill ini memberikan effect yang membuat player tidak terlihat sehingga
   * enemy tidak menyadari keberadaan player tersebut selama dua putaran.
   * Efek ini tidak berlaku untuk rekan, hanya berlaku untuk diri sendiri.
   */
  @Override
  public void secondSkill() {
    // Player tidak bisa menggunakan skill jika mana kurang
    if (this.mana < MANA_COST_SECOND_SKILL) {
      System.out.println("Mana tidak cukup untuk menggunakan skill Smoke Bomb");
      return;
    }

    this.mana -= MANA_COST_SECOND_SKILL;

    this.isVisible = false;
  }

  /**
   * Assassination Slash
   * 
   * Skill ini memungkinkan player melakukan serangan yang mematikan kepada
   * satu enemy yang dipilih. Memberikan damage yang sangat besar.
   */
  @Override
  public void ultimateSkill(Enemy enemy) {
    double damage = (23.0 * this.attack) - enemy.defense;
    String enemyRole = enemy.getClass().getSimpleName();

    // Player tidak bisa menggunakan skill jika mana kurang
    if (this.mana < MANA_COST_ULTIMATE_SKILL) {
      System.out.println("Mana tidak cukup untuk menggunakan skill Backstab");
      return;
    }

    this.mana -= MANA_COST_ULTIMATE_SKILL;

    // Enemy tidak akan menerima damage jika damage terlalu kecil
    if (damage <= 0) {
      System.out.println(enemyRole + " IMMUNE terhadap serangan dari " + fighterRole);
      return;
    }

    enemy.health -= damage;
    System.out.println(fighterRole + " melakukan serangan fisik ke " + enemyRole);

    // Mengecek apakah enemy yang diserang masih hidup
    if (enemy.health <= 0) {
      enemy.isAlive = false;
      System.out.println(enemyRole + " telah mati");
    }
  }
}

/**
 * Warrior
 */
class Warrior extends Fighter {
  /**
   * Warrior mempunyai kelebihan dalam defense dan health sehingga dapat
   * melindungi rekan.
   */
  public Warrior() {
    super();
    this.health *= 3.69;
    this.defense *= 2.69;
  }

  /**
   * Punch
   */
  @Override
  public void basicAttack(Enemy enemy) {

  }

  /**
   * Demoralizing Strike
   * 
   * Skill ini memungkinkan player melancarkan serangan kepada satu
   * enemy yang dipilih. Enemy yang menerima serangan akan mengalami
   * penurunan sebagian defense selama dua putaran.
   */
  
  @Override
  public void firstSkill(Enemy enemy) {

  }

  /**
   * Vanguard's Shield
   * 
   * Skill ini memungkinkan player untuk memberikan perisai kepada satu
   * rekan yang dipilih atau kepada diri sendiri selama dua putaran.
   * Selain itu, jika enemy menyerang player yang mempunyai perisai ini,
   * enemy akan menerima sebagian damage berdasarkan damage yang enemy
   * tersebut berikan.
   */
  @Override
  public void secondSkill() {

  }

  /**
   * Berserker Rage
   *
   * Player memasuki keadaan amarah selama tiga putaran. Meningkatkan serangan
   * secara drastis dan mengubah area serangan menjadi AoE, namun membuatnya
   * rentan terhadap kerusakan dan tidak dapat menggunakan Vanguard's Shield.
   */
  @Override
  public void ultimateSkill(Enemy enemy) {

  }
}

/**
 * Sorcerer
 */
class Sorcerer extends Fighter {
  /**
   * Sorcerer mempunyai kelebihan dalam attack dan mana sehingga dapat
   * memberikan serangan yang kuat dan dapat menggunakan skill
   * berkali-kali untuk menyembuhkan player atau menyerang musuh.
   */
  public Sorcerer() {
    super();
    this.attack *= 1.69;
    this.mana *= 2.69;
  }

  /**
   * Punch
   */
  @Override
  public void basicAttack(Enemy enemy) {

  }

  /**
   * Nova Forst
   * 
   * Skill ini memungkinkan player untuk melancarkan serangan kepada satu
   * satu enemy yang dipilih. Enemy yang menerima serangan ini kemungkinan
   * akan mengalami efek beku yang meleleh secara perlahan selama dua putaran.
   * Putaran pertama efek beku membuat enemy tidak bisa bergerak sama sekali
   * dan putaran kedua efek beku membuat enemy tidak bisa menggunakan skill
   * apapun namun masih bisa melakukan punch.
   */
  @Override
  public void firstSkill(Enemy enemy) {

  }

  /**
   * Celestial Blessing
   * 
   * Skill ini memungkinkan player untuk memancarkan gelombang energi
   * penyembuhan yang menjangkau seluruh tim, menyembuhkan kesehatan kepada
   * semua rekan dan diri sendiri. Menambah health dan mana berdasarkan
   * sebagian jumlah health dan mana pengguna skill ini.
   */
  @Override
  public void secondSkill() {

  }

  /**
   * Galactic Havoc
   * 
   * Skill ini memungkinkan Player untuk memanggil kekacauan kosmis, menciptakan
   * serangan yang memadukan gempa bumi, hujan meteor, dan kilat petir yang
   * menyapu seluruh enemy. Serangan ini menghasilkan damage yang sangat besar.
   * Selain itu, enemy akan menerima efek burn selama dua putaran.
   */
  @Override
  public void ultimateSkill(Enemy enemy) {

  }
}