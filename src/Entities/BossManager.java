package Entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BossManager {
    List<Boss> bossList = new ArrayList<>();

    public BossManager(){}

    public List<Boss> getBossList() {
        return bossList;
    }

    public void setBossList(List<Boss> bossList) {
        this.bossList = bossList;
    }

    public void addBoss(Boss b, List<Boss> bL){
        bL.add(b);
    }

    public static void showList(List<Boss> bL){
        for (Boss boss : bL){
            showBoss(boss);
        }
    }
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

    public List<Boss> createBossList() {

        //1)Create storing list
        List<Boss> newBossList = new ArrayList<>();

        //2)Create Boss objects using the constructor and add them to the list
        Boss boss1 = new Boss(1, "TestBoss1", "TestLocation1", 100, 50.0, 200, "Drop1", "Description1");
        Boss boss2 = new Boss(2, "TestBoss2", "TestLocation2", 150, 60.0, 250, "Drop2", "Description2");
        Boss boss3 = new Boss(3, "TestBoss3", "TestLocation3", 120, 55.0, 220, "Drop3", "Description3");

        addBoss(boss1, newBossList);
        addBoss(boss2, newBossList);
        addBoss(boss3, newBossList);

        return newBossList;
    }

    public List<Boss> modifyBoss(int bossID, String newName, String newLocation){
        Boolean match = false;

        for(Boss boss : bossList){
            if (bossID == boss.getBossID()){
                match=true;

                System.out.println("Match found!");
                System.out.println("Old Boss Name: "+boss.getBossName());
                System.out.println("Old Boss Location "+boss.getLocation());

                boss.setBossName(newName);
                boss.setLocation(newLocation);

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

    public void deleteBoss(int bossID){
        Iterator<Boss> iterator = bossList.iterator();
        Boolean match=false;
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
