import java.util.Scanner;

/*
 * JeudePuissance4.java
 * Diverses fonctions permettent de vérifier les cas de victoires (en utilisant des return).
 */

public class JeudePuissance4 {

	public static void main(String[] args) {

		//----------Déclaration des Scanners----------//
		 Scanner sc = new Scanner(System.in);
		 Scanner sc1 = new Scanner(System.in);
		 Scanner sc2 = new Scanner(System.in);
		 Scanner sc3 = new Scanner(System.in);
		
		//----------Règles du Jeu----------//
		  String rule;
		  System.out.println("Connaissez-vous les règles du jeu ? : (oui/non) ");
		  rule = sc.nextLine();
		
		  while (!rule.equals("oui") && !rule.equals("non")) {
			   System.out.println("Saisie Incorrecte! Veuillez saisir oui si vous ne connaissez pas les règles sinon non");
			   rule = sc.nextLine();
		  }
		
		  if (rule.equals("non")) {
			   System.out.println("Les joueurs laissent tomber chacun leur tour un jeton dans une colonne. "
			    +
			    "Lorsqu'un joueur aligne 4 jetons de sa couleur (en ligne ou en diagonale), il a gagné. "
			    +
			    "Il peut arriver qu'aucun joueur ne parvienne à aligner 4 jetons, donnant lieu à un match nul.");
		  }
		  
		//----------Jouer contre l'IA ou contre un joueur----------//
		  boolean ia = false;
		  String game;
		  System.out.println("Souhaitez-vous jouer contre un autre joueur ? : (oui/non) ");
		  game = sc3.nextLine();
		
		  while (!game.equals("oui") && !game.equals("non")) {
			   System.out.println("Saisie Incorrecte! Veuillez saisir oui si vous voulez jouer contre un joueur humain sinon non");
			   game = sc3.nextLine();
		  }
		
		  if (game.equals("non")) {
			  ia = true;
		  }
		  char J1= ' ', J2= ' ';
		  
		  boolean retry = false;
		
		//----------Boucle do pour recommencer une partie----------//
		  do {
		
			  do {
				//----------Choix du Pion pour J1----------//
				  System.out.println("J1 : Choisissez entre le pion \"O\" ou le pion \"X\"  :");
					String J1String = sc2.nextLine();
				    if(!J1String.isEmpty()) {
				    	J1=J1String.charAt(0);
				    }
				 } while (J1 != 'O' && J1 != 'X');
				
				   if (J1 == 'O') {
					   J2 = 'X';
				   } 
				   else {
					   J2 = 'O';
				   }
				   
				 //----------Initialisation du plateau de jeu----------//
				   char[][] plateau;
				   plateau = initPlateau();
				   System.out.println();
				 //----------Appel a la fonction pour choisir au hasard qui commence (J1 ou J2)----------//
				   char firstPlayer = quiCommence(J1, J2);
				   char secondPlayer;
				   
				   if (firstPlayer == J1) {
					   secondPlayer = J2;
				    } 
				   else {
					   secondPlayer = J1;
				   }
				
				   int switchPlayer = 1;
				   
				 //----------Déclaration && Initialisation des boolean (cas de vicoire)----------//
				   boolean partieNonFiniHorizontal = false;
				   boolean partieNonFiniVertical = false;
				   boolean partiNonFiniDiagonaleGauche = false;
				   boolean partiNonFiniDiagonaleDroite = false;
				   boolean partiNul = false;
				
				   char mainPlayer = 'O';
				
				 //----------Boucle while tant que la partie n'est pas fini----------//
				   while (!partieNonFiniHorizontal && !partieNonFiniVertical && !partiNonFiniDiagonaleGauche && !partiNonFiniDiagonaleDroite && !partiNul) {
					    int choixColonne;
					    char player;
					    boolean rempliColonne = false;
					
					  //----------Attribution des "roles"----------//
					    if (switchPlayer % 2 != 0) {
					    	player = firstPlayer;
					    } 
					    else {
					    	player = secondPlayer;
					    }
					    
					  //----------Boucle do pour vérifier que la colonne est rempli----------//
					    do {
					    	//----------IA choisit une colonne aléatoirement----------//
						      if (ia == true && player == J2) {
						      choixColonne = (int)(Math.random() * 6) + 1;
						     } 
						    //----------Tel joueur chosit telle colonne----------//
						     else {
						      System.out.println("Le joueur ayant le pion " + player + " Entrer une colonne svp ");
						      choixColonne = sc.nextInt();
						     }
						     rempliColonne = true;
						
						     if (choixColonne <= 7 && choixColonne >= 1) {
						      rempliColonne = verifColonneRempli(plateau, choixColonne);
						     } 
						     else {
						      System.out.println("La Colonne " + choixColonne + " est remplie, merci au joueur ayant le pion " + player + " de ressaisir une autre colonne");
						     }
					     
					    } while (rempliColonne);
					
					  //----------Tour du Joueur ayant commencé la partie----------//
					    if (switchPlayer % 2 != 0) {
						     plateau = modifPlateau(plateau, firstPlayer, choixColonne);
						
						     if (player == J2 && !ia) {
						    	 System.out.println("Le joueur ayant le pion " + player + " a choisi la colonne " + choixColonne);
						     } 
						     else if (player == J1) {
						    	 System.out.println("Le joueur ayant le pion " + player + " a choisi la colonne " + choixColonne);
						     } 
						     else {
						    	 System.out.println("L'IA ayant le pion " + player + " a choisi la colonne " + choixColonne);
						     }
						     
						     //----------Appel aux fonctions pour vérifier les cas de victoires ou non----------//
						     afficherPlateauModif(plateau);
						     partieNonFiniVertical = verifVerticaleSiPlayerWin(plateau, choixColonne, player);
						     partieNonFiniHorizontal = verifHorizontalSiPlayerWin(plateau, choixColonne, player);
						     partiNonFiniDiagonaleGauche = verifDiagonaleGauche(plateau, choixColonne, player);
						     partiNonFiniDiagonaleDroite = verifDiagonaleDroite(plateau, choixColonne, player);
						     partiNul = matchNul(plateau);
					    }
					    
					    //----------Tour du Joueur Suivant----------//
					    if (switchPlayer % 2 == 0) {
					     plateau = modifPlateau(plateau, secondPlayer, choixColonne);
					
					     if (player == J2 && !ia) {
					      System.out.println("Le joueur ayant le pion " + player + " a choisi la colonne " + choixColonne);
					     } 
					     else if (player == J1) {
					      System.out.println("Le joueur ayant le pion " + player + " a choisi la colonne " + choixColonne);
					     } 
					     else {
					      System.out.println("L'IA ayant le pion " + player + " a choisi la colonne " + choixColonne);
					     }
					     
					   //----------Appel aux fonctions pour vérifier les cas de victoires ou non----------//
					     afficherPlateauModif(plateau);
					     partieNonFiniVertical = verifVerticaleSiPlayerWin(plateau, choixColonne, player);
					     partieNonFiniHorizontal = verifHorizontalSiPlayerWin(plateau, choixColonne, player);
					     partiNonFiniDiagonaleGauche = verifDiagonaleGauche(plateau, choixColonne, player);
					     partiNonFiniDiagonaleDroite = verifDiagonaleDroite(plateau, choixColonne, player);
					     partiNul = matchNul(plateau);
					    }
					    
					  //----------Changement de Joueur----------//
					    switchPlayer++;
					    mainPlayer = player;
				   }
				   
				 //----------Tel joueur à gagner la partie----------//
				   if (partieNonFiniHorizontal || partieNonFiniVertical || partiNonFiniDiagonaleDroite || partiNonFiniDiagonaleGauche) {
					   System.out.println("Joueur avec le pion " + mainPlayer + " a gagné");
				   } 
				 //----------Aucun des deux joueurs n'ont gagnés----------//
				   else {
					   System.out.println("Match Nul");
				   }
				 //----------Recommencer une autre partie de jeu----------//
				   System.out.println("Voulez-vous rejouer une autre partie ? (oui/non) : ");
				   String partie = sc1.nextLine();
				   
				   while (!partie.equals("oui") && !partie.equals("non")) {
					    System.out.println("Saisie Incorrecte! Veuillez saisir oui si vous voulez refaire une autre partie sinon non");
					    partie = sc1.nextLine();
				   }
				   
				   if (partie.equals("non")) {
					   retry = false;
				   } 
				   else {
					   retry = true;
				   }
				   
			} while (retry);
				
		//----------Fermeture des saisie----------//		
		sc.close();
		sc1.close();
		sc2.close();
		sc3.close();
	}
	
	//----------Fonction pour vérifier qui commence le jeu et retourne un char----------//
	static char quiCommence(char J1, char J2) {
	
		  char beginPlayer;
		  int randomBegin = (int)(Math.random() * 2);
		  
		  if (randomBegin == 1) {
			   System.out.println("La partie commence avec le joueur qui a le pion " + J1);
			   beginPlayer = J1;
			   return beginPlayer;
		  } 
		  else {
		   System.out.println("La partie commence avec le joueur qui a le pion " + J2);
			   beginPlayer = J2;
			   return beginPlayer;
		  }
	}
	
	//----------Méthode pour afficher le plateau du jeu modifié par le joueur correspondant au tour----------//
	static void afficherPlateauModif(char[][] tableauModif) {
	
		  System.out.println("   1    2    3    4    5    6    7");
		  System.out.println("   ▼    ▼    ▼    ▼    ▼    ▼    ▼");
		  System.out.println("-------------------------------------");
		  
		  for (int x = 0; x < tableauModif.length; x++) {
			  System.out.print("|");
			  for (int y = 0; y < tableauModif[x].length; y++) {
				  System.out.print(" " + " " + tableauModif[x][y] + " " + " ");
			  }
				  System.out.print("|");
				  System.out.println();
		  }
			System.out.println("-------------------------------------");
	 }
	
	//----------Fonction pour initialiser le plateau du jeu et retourne le plateau----------//
	static char[][] initPlateau() {
	
		  char[][] plateau = new char[6][7];
		  System.out.println("   1    2    3    4    5    6    7");
		  System.out.println("   ▼    ▼    ▼    ▼    ▼    ▼    ▼");
		  System.out.println("-------------------------------------");
		  
		  for (int x = 0; x < plateau.length; x++) {
			  System.out.print("|");
			  for (int y = 0; y < plateau[x].length; y++) {
				   plateau[x][y] = '*';
				   System.out.print(" " + " " + plateau[x][y] + " " + " ");
			  }
				   System.out.print("|");
				   System.out.println();
		  }
		  System.out.println("-------------------------------------");
		  
		  return plateau;
	
	 }
	 
	//----------Fonction pour modifié le plateau du jeu et retourne le plateau modifié----------//
	static char[][] modifPlateau(char[][] plateau, char player, int choixColonne) {
	
		  for (int x = plateau.length - 1; x >= 0; x--) {
			  for (int y = 0; y < plateau[x].length; y++) {
			   
				 if (plateau[x][choixColonne - 1] == '*') {
					 plateau[x][choixColonne - 1] = player;
					 return plateau;
			     }
			
			  }
			
	      }
		  return plateau;
	 }
	 
	//----------Fonction pour vérifier si la colonne est rempli et retourne true ou false----------//
	static boolean verifColonneRempli(char[][] plateau, int choixColonne) {
	
		  if (plateau[0][choixColonne - 1] == 'O' || plateau[0][choixColonne - 1] == 'X') {
			return true;
		  }
		  return false;
	 }
	//----------Fonction pour récupérer la position du pion et retourne un int----------//
	static int recuperePositionLignePion(char[][] plateau, int choixColonne) {
	  int positionLignePion = 0;
	  for (int x = plateau.length - 1; x >= 0; x--) {
		  for (int y = 0; y < plateau[x].length; y++) {
		
			  if (plateau[x][choixColonne - 1] == '*') {
				  positionLignePion = x + 1;
				  return positionLignePion;
			  }
		  }
	  }
	  return positionLignePion;
	 }
	
	//----------Fonction pour vérifier le cas de victoire en verticale et retourne true ou false----------//
	static boolean verifVerticaleSiPlayerWin(char[][] plateau, int positionColonnePion, char player) {
	
		  int positionLignePion = recuperePositionLignePion(plateau, positionColonnePion);
		  boolean victoire = false;
		
		  if (positionLignePion > 2) {
			  return victoire;
		  }
		  
		  if (plateau[positionLignePion][positionColonnePion - 1] == player && plateau[positionLignePion + 1][positionColonnePion - 1] == player && plateau[positionLignePion + 2][positionColonnePion - 1] == player && plateau[positionLignePion + 3][positionColonnePion - 1] == player) {
			   System.out.println("victoire");
			   victoire = true;
		  }
	  return victoire;
	 }
	 
	//----------Fonction pour vérifier le cas de victoire en horizontale et retourne true ou false----------//
	static boolean verifHorizontalSiPlayerWin(char[][] plateau, int positionColonnePion, char player) {
	
		  int x = recuperePositionLignePion(plateau, positionColonnePion);
		  int y = positionColonnePion - 1;
		  int somme = -1;
		  
		  while (y >= 0 && plateau[x][y] == player) {
			   somme = somme + 1;
			   y--;
		  }
		  
		  y = positionColonnePion - 1;
		  while (y < 7 && plateau[x][y] == player) {
			   somme = somme + 1;
			   y++;
		  }
		  
		  if (somme >= 4) {
			  return true;
		  }
		  return false;
	 }
	
	//----------Fonction pour vérifier le cas de victoire en diagonale partant de la gauche et se termine par la droite (\) et retourne true ou false----------//
	static boolean verifDiagonaleGauche(char[][] plateau, int positionColonnePion, char player) {
	
		  int x = recuperePositionLignePion(plateau, positionColonnePion);
		  int y = positionColonnePion - 1;
		  int somme = -1;
		
		  while (y >= 0 && x >= 0 && plateau[x][y] == player) {
			   somme = somme + 1;
			   x--;
			   y--;
		  }
		  
		  y = positionColonnePion - 1;
		  x = recuperePositionLignePion(plateau, positionColonnePion);
		
		  while (y < 7 && x < 6 && plateau[x][y] == player) {
			   somme = somme + 1;
			   x++;
			   y++;
		  }
		
		  if (somme >= 4) {
			  return true;
		  }
		  return false;
	 }
	
	//----------Fonction pour vérifier le cas de victoire en diagonale partant de la droite et se termine par la gauche (/) et retourne true ou false----------//
	static boolean verifDiagonaleDroite(char[][] plateau, int positionColonnePion, char player) {
	
		  int x = recuperePositionLignePion(plateau, positionColonnePion);
		  int y = positionColonnePion - 1;
		  int somme = -1;
		
		  while (y < 7 && x >= 0 && plateau[x][y] == player) {
			   somme = somme + 1;
			   x--;
			   y++;
		  }
		  
		  y = positionColonnePion - 1;
		  x = recuperePositionLignePion(plateau, positionColonnePion);
		  
		  while (y >= 0 && x < 6 && plateau[x][y] == player) {
			   somme = somme + 1;
			   x++;
			   y--;
		  }
		  
		  if (somme >= 4) {
			  return true;
		  }
		  return false;
	 }
	 
	//----------Fonction pour vérifier le cas du match nul et retourne true ou false----------//
	static boolean matchNul(char[][] plateau) {
	
		  for (int y = 0; y < plateau[0].length; y++)
		
		   if (plateau[0][y] == '*') {
			   return false;
		   }
		  return true;
	 }

}