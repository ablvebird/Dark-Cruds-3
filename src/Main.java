public class Main {
    public static void main(String[] args) {

        //DOM
        DomManager domManager = new DomManager("src/bosses.xml");
        domManager.parseXML();
        int numberOfBosses = domManager.getNumBosses();
        System.out.println("Number of bosses: " + numberOfBosses);
        domManager.printBossData();
        domManager.readUnknownXML();

        //SAX

        //JDOM
    }
}