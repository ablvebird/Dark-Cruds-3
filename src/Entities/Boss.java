package Entities;

/**
 * Represents a boss entity in Dark Souls 3
 */
public class Boss {

    //Class attributes
    private int bossId;
    private String bossName;
    private String location;
    private int HP;
    private double Poise;
    private int Souls;
    private String dropName;
    private String description;

    //Empty constructor
    public Boss(){}

    //Complete constructor
    public Boss(int bossId, String bossName, String location, int HP, double Poise,
                int Souls, String dropName, String description){

        this.bossId = bossId;
        this.bossName = bossName;
        this.location = location;
        this.HP = HP;
        this.Poise = Poise;
        this.Souls = Souls;
        this.dropName = dropName;
        this.description = description;
    }

//GETTERS AND SETTERS

    // Getter and Setter for bossID
    public int getBossID() {
        return bossId;
    }
    public void setBossID(int bossID) { this.bossId = bossID; }

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

    //Getter and Setter for Location
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
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