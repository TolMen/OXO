package morpion;

import java.util.Scanner;

public class Morpion {

    public static final Scanner saisie = new Scanner(System.in);
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void displayPlateau(int[][] plateau) {
	// Affichage des numéros de colonnes en en-tête
	System.out.print("   ");
	for (int iCol = 1; iCol <= plateau[0].length; iCol++) {
	    System.out.print(iCol + "   ");
	}
	System.out.println();

	// Initialisation de l'affichage du plateau masqué (non creusé)
	for (int indexLigne = 0; indexLigne < plateau.length; indexLigne++) {
	    System.out.print((indexLigne + 1) + " ");
	    for (int indexColonne = 0; indexColonne < plateau[indexLigne].length; indexColonne++) {
		switch (plateau[indexLigne][indexColonne]) {
		    case 0 ->
			System.out.print(" -  ");
		    case 1 ->
			System.out.print(" x  ");
		    case 2 ->
			System.out.print(" o  ");
		}
	    }
	    System.out.println();
	}
    }

    public static boolean checkCase(int[][] plateau, int numeroLigne, int numeroColonne) {
	if (numeroLigne < 0 || numeroLigne >= plateau.length || numeroColonne < 0 || numeroColonne >= plateau[0].length) {
	    System.out.println(ANSI_RED + "Coordonnées invalides. Veuillez entrer des valeurs dans les limites du plateau !" + ANSI_RESET);
	    return false;
	} else if (plateau[numeroLigne][numeroColonne] != 0) {
	    System.out.println(ANSI_RED + "Cette case est déjà occupée. Veuillez en choisir une autre !" + ANSI_RESET);
	    return false;
	}
	return true;
    }

    public static void fillCase(int[][] plateau, int numeroLigne, int numeroColonne, int idJoueur) {
	if (plateau[numeroLigne][numeroColonne] == 0) {
	    plateau[numeroLigne][numeroColonne] = idJoueur;
	}
    }

    public static boolean testArray(int[] array, int value) {
	for (int element : array) {
	    if (element != value) {
		return false;
	    }
	}
	return true;
    }

    public static boolean testNumbers(int a, int b, int c, int d) {
	return (a == b && b == c && c == d);
    }

    public static boolean hasWon(int[][] plateau, int idJoueur) {
	// Vérification des lignes et colonnes
	for (int i = 0; i < 3; i++) {
	    if (testNumbers(plateau[i][0], plateau[i][1], plateau[i][2], idJoueur)) {
		return true;
	    }
	    
	    if (testNumbers(plateau[0][i], plateau[1][i], plateau[2][i], idJoueur)) {
		return true;
	    }
	}

	// Vérification des diagonales
	if (testNumbers(plateau[0][0], plateau[1][1], plateau[2][2], idJoueur)) {
	    return true;
	}
	if (testNumbers(plateau[0][2], plateau[1][1], plateau[2][0], idJoueur)) {
	    return true;
	}

	// Si aucune condition de victoire n'est remplie, retourne false
	return false;
    }

    public static boolean isPlateauFull(int[][] plateau) {
	for (int indexLigne = 0; indexLigne < plateau.length; indexLigne++) {
	    for (int indexColonne = 0; indexColonne < plateau[indexLigne].length; indexColonne++) {
		if (plateau[indexLigne][indexColonne] == 0) {
		    return false;
		}
	    }
	}
	return true;
    }

    public static void playGame() {

	System.out.print("Entrez le nom du Joueur 1 (X) : ");
	String nomJoueur1 = saisie.nextLine();

	System.out.print("Entrez le nom du Joueur 2 (O) : ");
	String nomJoueur2 = saisie.nextLine();

	System.out.print("Entrez la taille du plateau (ex : 3 pour un plateau 3x3) : ");
	int taille = saisie.nextInt();

	int[][] plateau = new int[taille][taille];
	int idJoueur = 1;
	boolean partieEnCours = true;

	while (partieEnCours) {
	    String nomJoueur = (idJoueur == 1) ? nomJoueur1 : nomJoueur2;
	    System.out.println("\nTour de " + nomJoueur + "\n");
	    displayPlateau(plateau);

	    System.out.println("\nEntrez le numéro de ligne puis de colonne : ");
	    int numeroLigne = saisie.nextInt() - 1;
	    int numeroColonne = saisie.nextInt() - 1;

	    while (!checkCase(plateau, numeroLigne, numeroColonne)) {
		System.out.println("\nEntrez le numéro de ligne puis de colonne : ");
		numeroLigne = saisie.nextInt() - 1;
		numeroColonne = saisie.nextInt() - 1;
	    }

	    fillCase(plateau, numeroLigne, numeroColonne, idJoueur);

	    if (hasWon(plateau, idJoueur)) {
		displayPlateau(plateau);
		System.out.println(ANSI_YELLOW + "\nFélicitations ! " + nomJoueur + " a gagné !" + ANSI_RESET);
		partieEnCours = false;
	    } else if (isPlateauFull(plateau)) {
		displayPlateau(plateau);
		System.out.println(ANSI_YELLOW + "\nMatch nul ! Le plateau est plein." + ANSI_RESET);
		partieEnCours = false;
	    } else {
		idJoueur = (idJoueur == 1) ? 2 : 1;
	    }
	}

	// Option pour relancer une partie
	System.out.print("\nVoulez-vous rejouer ? (o/n) : ");
	saisie.nextLine();
	String replay = saisie.nextLine();
	if (replay.equals("o")) {
	    playGame();  // Relancer le jeu
	} else {
	    System.out.println("Merci d'avoir joué !");
	}
    }

    public static void main(String[] args) {
	System.out.println("Bienvenue dans Le Morpion Java !");
	System.out.println("");
	playGame();
    }
}
