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

	public String getProduit() {
		return produit;
	}

	public int getQuantite() {
		return quantite;
	}

	public int getQuantiteDebutMarche() {
		return quantiteDebutMarche;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {

		try {
			etalOccupe = false;
			StringBuilder chaine = new StringBuilder("Le vendeur " + vendeur.getNom() + " quitte son étal, ");
			int produitVendu = quantiteDebutMarche - quantite;
			if (produitVendu > 0) {
				chaine.append("il a vendu " + produitVendu + " parmi les " + quantiteDebutMarche
						+ " qu'il voulait vendre.\n");
			} else {
				chaine.append("il n'a malheureusement rien vendu.\n");
			}
			return chaine.toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite + " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur)
			throws IllegalArgumentException, IllegalStateException {
		if (quantiteAcheter <= 0) {
			throw new IllegalArgumentException("La quantit�e a�acheter doit etre supperieure a�> 0 \n");
		}
		if (!etalOccupe) {
			throw new IllegalStateException("On ne peut pas acheter a�un �tal innocup�e ! \n");
		}
		try {
			StringBuilder chaine = new StringBuilder();
			chaine.append(
					acheteur.getNom() + " veut acheter " + quantiteAcheter + " " + produit + " �" + vendeur.getNom());
			if (quantite == 0) {
				chaine.append(", malheureusement il n'y en a plus !");
				quantiteAcheter = 0;
			}
			if (quantiteAcheter > quantite) {
				chaine.append(", comme il n'y en a plus que " + quantite + ", " + acheteur.getNom()
						+ " vide l'étal de " + vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			if (quantite != 0) {
				quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom() + ", est ravi de tout trouver sur l'étal de " + vendeur.getNom()
						+ "\n");
			}
			return chaine.toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}

}