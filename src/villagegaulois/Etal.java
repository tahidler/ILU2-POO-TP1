package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
	    if (!etalOccupe) {
	        throw new IllegalStateException("Impossible de libérer un étal qui n'a jamais été occupé !");
	    }

	    etalOccupe = false;
	    StringBuilder chaine = new StringBuilder("Le vendeur " + vendeur.getNom() + " quitte son étal, ");
	    int produitVendu = quantiteDebutMarche - quantite;

	    if (produitVendu > 0) {
	        chaine.append("il a vendu ").append(produitVendu).append(" parmi ").append(produit).append(".\n");
	    } else {
	        chaine.append("il n'a malheureusement rien vendu.\n");
	    }

	    return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
	    if (acheteur == null) {
	        throw new NullPointerException("L'acheteur ne peut pas être null !");
	    }
	    if (quantiteAcheter < 1) {
	        throw new IllegalArgumentException("La quantité achetée doit être supérieure à 0 !");
	    }
	    if (!etalOccupe) {
	        throw new IllegalStateException("Impossible d'acheter un produit dans un étal non occupé !");
	    }

	    StringBuilder chaine = new StringBuilder();
	    chaine.append(acheteur.getNom()).append(" veut acheter ").append(quantiteAcheter)
	          .append(" ").append(produit).append(" à ").append(vendeur.getNom());

	    if (quantite == 0) {
	        chaine.append(", malheureusement il n'y en a plus !");
	        quantiteAcheter = 0;
	    } else if (quantiteAcheter > quantite) {
	        chaine.append(", comme il n'y en a plus que ").append(quantite)
	              .append(", ").append(acheteur.getNom()).append(" vide l'étal de ")
	              .append(vendeur.getNom()).append(".\n");
	        quantiteAcheter = quantite;
	        quantite = 0;
	    } else {
	        quantite -= quantiteAcheter;
	        chaine.append(". ").append(acheteur.getNom())
	              .append(" est ravi de tout trouver sur l'étal de ")
	              .append(vendeur.getNom()).append("\n");
	    }

	    return chaine.toString();
	}

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}

}
