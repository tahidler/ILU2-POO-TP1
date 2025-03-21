package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalsMarche);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public boolean chefPresent() {
		return chef != null;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {

		if (!chefPresent()) {
			throw new VillageSansChefException("Le village ne peut pas exister sans chef ! \n");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int touverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int nbEtalsAvecProd = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit))
					nbEtalsAvecProd++;
			}
			Etal[] etalsAvecProd = new Etal[nbEtalsAvecProd];
			for (int i = 0, j = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsAvecProd[j] = etals[i];
					j++;
				}
			}
			return etalsAvecProd;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
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

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ". \n");
		int etalLibre = marche.touverEtalLibre();
		marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
		int numeroEtal = etalLibre + 1; // Car Numero different d'indice
		chaine.append(
				"Le vendeur " + vendeur.getNom() + " vend des " + produit + " Ã  l'Ã©tal nÂ°" + numeroEtal + ". \n");
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();

		Etal[] tabVendeurs = marche.trouverEtals(produit);
		int nbProduits = tabVendeurs.length;
		if (nbProduits == 0)
			chaine.append("Il n'y a pas de vendeur qui propose des fleurs au marchÃ©. \n");
		else if (nbProduits == 1)
			chaine.append("Seul le vendeur " + tabVendeurs[0].getVendeur().getNom() + " propose des " + produit
					+ " au marchÃ©. \n");
		else {
			chaine.append("Les vendeurs qui proposent des fleurs sont : \n");
			for (int i = 0; i < nbProduits; i++) {
				chaine.append("- " + tabVendeurs[i].getVendeur().getNom() + "\n");
			}
		}

		return chaine.toString();
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
}