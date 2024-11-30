package Entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que gestiona las operaciones sobre los jefes del juego
 * Permite crear, modificar, eliminar y mostrar información de los jefes
 */
public class BossManager {
    // Lista principal donde se guardan todos los jefes
    List<Boss> bossList = new ArrayList<>();

    // Constructor por defecto
    public BossManager(){}

    // Obtener la lista completa de jefes
    public List<Boss> getBossList() {
        return bossList;
    }

    // Establecer una nueva lista de jefes
    public void setBossList(List<Boss> bossList) {
        this.bossList = bossList;
    }

    // Añade un jefe a una lista específica
    public void addBoss(Boss b, List<Boss> bL){
        bL.add(b);
    }

    // Muestra todos los jefes de una lista
    public static void showList(List<Boss> bL){
        for (Boss boss : bL){
            showBoss(boss);
        }
    }

    // Muestra la información detallada de un jefe
    public static void showBoss(Boss boss){
        System.out.println("Boss ID: " + boss.getBossID());
        System.out.println("Boss Name: " + boss.getBossName());
        System.out.println("Location: " + boss.getLocation());
        System.out.println("HP: " + boss.getHP());
        System.out.println("Poise: " + boss.getPoise());
        System.out.println("Souls: " + boss.getSouls());
        System.out.println("Drop Name: " + boss.getDropName());
        System.out.println("Description: " + boss.getDescription());
    }

    // Crea una lista de prueba con tres jefes de ejemplo
    public List<Boss> createBossList() {
        // Crear nueva lista vacía
        List<Boss> newBossList = new ArrayList<>();

        // Crear tres jefes de prueba con datos de ejemplo
        Boss boss1 = new Boss(1, "TestBoss1", "TestLocation1", 100, 50.0, 200, "Drop1", "Description1");
        Boss boss2 = new Boss(2, "TestBoss2", "TestLocation2", 150, 60.0, 250, "Drop2", "Description2");
        Boss boss3 = new Boss(3, "TestBoss3", "TestLocation3", 120, 55.0, 220, "Drop3", "Description3");

        // Añadir los jefes a la lista
        addBoss(boss1, newBossList);
        addBoss(boss2, newBossList);
        addBoss(boss3, newBossList);

        return newBossList;
    }

    // Modifica el nombre y ubicación de un jefe por su ID
    public List<Boss> modifyBoss(int bossID, String newName, String newLocation){
        boolean match = false;

        // Buscar el jefe en la lista
        for(Boss boss : bossList){
            if (bossID == boss.getBossID()){
                match=true;

                // Mostrar datos antiguos
                System.out.println("Match found!");
                System.out.println("Old Boss Name: "+boss.getBossName());
                System.out.println("Old Boss Location "+boss.getLocation());

                // Actualizar datos
                boss.setBossName(newName);
                boss.setLocation(newLocation);

                // Mostrar datos nuevos
                System.out.println("New Boss Data:");
                System.out.println("Boss Name: "+boss.getBossName());
                System.out.println("Boss Location: "+boss.getLocation());
            }
        }
        if (!match){
            System.out.println("Boss ID not found in list");
        }
        return bossList;
    }

    // Elimina un jefe de la lista por su ID
    public void deleteBoss(int bossID){
        Iterator<Boss> iterator = bossList.iterator();
        boolean match=false;
        // Usar iterator para evitar problemas al modificar la lista mientras se recorre
        while (iterator.hasNext()) {
            Boss boss = iterator.next();
            if (bossID == boss.getBossID()) {
                iterator.remove();
                match=true;
                System.out.println("Boss deleted successfully. ID: " + bossID);
            }
        }
        if (!match){
            System.out.println("No matches found for ID " + bossID);
        }
    }
}