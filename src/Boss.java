public class Boss {

    //Class attributes
    private int bossID;
    private String bossName;
    private int HP;
    private double Poise;
    private int Souls;
    private String dropName;
    private String description;

    // Getter and Setter for bossID
    public int getBossID() {
        return bossID;
    }
    public void setBossID(int bossID) { this.bossID = bossID; }

    // Getter and Setter for bossName
    public String getBossName() {
        return bossName;
    }
    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    // Getter and Setter for HP
    public int getHP() {
        return HP;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }

    // Getter and Setter for Poise
    public double getPoise() {
        return Poise;
    }
    public void setPoise(double Poise) {
        this.Poise = Poise;
    }

    // Getter and Setter for Souls
    public int getSouls() {
        return Souls;
    }
    public void setSouls(int Souls) {
        this.Souls = Souls;
    }

    // Getter and Setter for dropName
    public String getDropName() {
        return dropName;
    }
    public void setDropName(String dropName) {
        this.dropName = dropName;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}