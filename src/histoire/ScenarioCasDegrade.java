package histoire;

import villagegaulois.Etal;
import personnages.Gaulois;

public class ScenarioCasDegrade {
    public static void main(String[] args) {
        try {
            Etal etal = new Etal();
            System.out.println(etal.acheterProduit(10, null)); 
        } catch (NullPointerException e) {
            System.out.println("Exception capturée (acheteur null) : " + e.getMessage());
        }

        try {
            Etal etal = new Etal();
            System.out.println(etal.acheterProduit(-5, new Gaulois("Astérix", 10))); 
        } catch (IllegalArgumentException e) {
            System.out.println("Exception capturée (quantité négative) : " + e.getMessage());
        }

        try {
            Etal etal = new Etal();
            System.out.println(etal.acheterProduit(5, new Gaulois("Obélix", 25))); 
        } catch (IllegalStateException e) {
            System.out.println("Exception capturée (étal vide) : " + e.getMessage());
        }

        System.out.println("Fin du test");
    }
}