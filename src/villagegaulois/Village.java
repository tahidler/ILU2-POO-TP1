package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
    private String nom;
    private Chef chef;
    private Gaulois[] villageois;
    private int nbVillageois = 0;
    private Marche marche; 

    
    public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
        this.nom = nom;
        villageois = new Gaulois[nbVillageoisMaximum];
        this.marche = new Marche(nbEtals);
    }

    public String getNom() {
        return nom;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public void ajouterHabitant(Gaulois gaulois) {
        if (nbVillageois < villageois.length) {
            villageois[nbVillageois] = gaulois;
            nbVillageois++;
        }
    }

    public String afficherVillageois() {
        StringBuilder chaine = new StringBuilder();
        if (nbVillageois < 1) {
            chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
        } else {
            chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
            for (int i = 0; i < nbVillageois; i++) {
                chaine.append("- " + villageois[i].getNom() + "\n");
            }
        }
        return chaine.toString();
    }

   
    public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
        int etalLibre = marche.trouverEtalLibre();
        if (etalLibre == -1) {
            return "Aucun étal disponible pour " + vendeur.getNom() + ".";
        }
        StringBuilder message = new StringBuilder();
        message.append(vendeur.getNom()).append(" cherche un endroit pour vendre ").append(nbProduit).append(" ").append(produit).append(".\n");
        marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
        message.append("Le vendeur ").append(vendeur.getNom()).append(" vend des ").append(produit).append(" à l'étal n°").append(etalLibre + 1).append(".");
        return message.toString();
    }

    public String rechercherVendeursProduit(String produit) {
        Etal[] etalsProduit = marche.trouverEtals(produit);
        if (etalsProduit.length == 0) {
            return "Il n'y a pas de vendeur qui propose " + produit + " au marché.";
        }
        StringBuilder resultat = new StringBuilder("Les vendeurs qui proposent " + produit + " sont :\n");
        for (Etal etal : etalsProduit) {
            resultat.append("- ").append(etal.getVendeur().getNom()).append("\n");
        }
        return resultat.toString();
    }
    
    public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }

    public String partirVendeur(Gaulois vendeur) {
        Etal etal = marche.trouverVendeur(vendeur);
        if (etal != null) {
            return etal.libererEtal();
        }
        return vendeur.getNom() + " n'avait pas d'étal dans le marché.";
    }
    public String afficherMarche() {
        return marche.afficherMarche();
    }

    
    private static class Marche {
        private Etal[] etals;

        private Marche(int nbEtals) {
            this.etals = new Etal[nbEtals];
            for (int i = 0; i < nbEtals; i++) {
                etals[i] = new Etal();
            }
        }

        private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
            if (indiceEtal < 0 || indiceEtal >= etals.length) {
                throw new IndexOutOfBoundsException("Indice d'étal invalide !");
            }
            etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
        }

        private int trouverEtalLibre() {
            for (int i = 0; i < etals.length; i++) {
                if (!etals[i].isEtalOccupe()) {
                    return i;
                }
            }
            return -1;
        }
        
        private Etal trouverVendeur(Gaulois vendeur) {
            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.getVendeur().equals(vendeur)) {
                    return etal;
                }
            }
            return null;
        }
        private Etal[] trouverEtals(String produit) {
            int count = 0;

            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
                    count++;
                }
            }

            Etal[] etalsProduit = new Etal[count];
            int index = 0;

            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
                    etalsProduit[index++] = etal;
                }
            }

            return etalsProduit;
        }

        private String afficherMarche() { 
            StringBuilder affichage = new StringBuilder();
            int nbEtalsVides = 0;

            for (Etal etal : etals) {
                if (etal.isEtalOccupe()) {
                    affichage.append(etal.afficherEtal()).append("\n");
                } else {
                    nbEtalsVides++;
                }
            }

            affichage.append("Il reste ").append(nbEtalsVides).append(" étals non utilisés dans le marché.\n");
            return affichage.toString();
        }
    }
}