package Entities;

import java.io.Serializable;

/**
 * Clase que representa un jefe (boss) del juego Dark Souls 3
 * Implementa Serializable para poder guardar los objetos en archivos
 */
public class Boss implements Serializable {

    // Atributos que definen a un jefe
    private int bossId;          // Identificador único del jefe
    private String bossName;     // Nombre del jefe
    private String location;     // Ubicación donde se encuentra
    private int HP;             // Puntos de vida
    private double Poise;       // Equilibrio (determina si puede ser interrumpido)
    private int Souls;          // Almas que otorga al ser derrotado
    private String dropName;    // Nombre del objeto que deja al morir
    private String description; // Descripción del objeto que deja

    // Constructor vacío - necesario para algunas operaciones
    public Boss(){}

    // Constructor con todos los atributos - usado para crear jefes completos
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

    // Constructor sin ID - usado para base de datos donde el ID se genera automáticamente
    public Boss(String bossName, String location, int HP, double Poise,
                int Souls, String dropName, String description){
        this.bossName = bossName;
        this.location = location;
        this.HP = HP;
        this.Poise = Poise;
        this.Souls = Souls;
        this.dropName = dropName;
        this.description = description;
    }

    // Métodos para obtener y modificar los atributos (getters y setters)

    // ID del jefe
    public int getBossID() {
        return bossId;
    }
    public void setBossID(int bossID) {
        this.bossId = bossID;
    }

    // Nombre del jefe
    public String getBossName() {
        return bossName;
    }
    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    // Puntos de vida
    public int getHP() {
        return HP;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }

    // Ubicación del jefe
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    // Equilibrio
    public double getPoise() {
        return Poise;
    }
    public void setPoise(double Poise) {
        this.Poise = Poise;
    }

    // Almas que otorga
    public int getSouls() {
        return Souls;
    }
    public void setSouls(int Souls) {
        this.Souls = Souls;
    }

    // Nombre del objeto que deja
    public String getDropName() {
        return dropName;
    }
    public void setDropName(String dropName) {
        this.dropName = dropName;
    }

    // Descripción del objeto
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}