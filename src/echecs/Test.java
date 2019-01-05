/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import Vue.FenetreJeu;
import echecs.Pieces.Piece;
import java.util.ArrayList;

/**
 *
 * @author lyes
 * cette classe vérifie 
 * si le roi est menacé
 * si le roi est en echec et mat 
 * si le mouvement est sûr
 */
public class Test {

    Deplacements dep = new Deplacements();
    int xKing = 0;
    int yKing = 0;
    ArrayList<int[]> listOfPossibilities;
    ArrayList<int[]> kingListOfPossibilities;
    int[] possibilite;
    int[] possibilite1;
    Piece piece;

  
    // methode récupérant les coordonnées du la piece ROI 
    public void locateKing(Couleur couleur, FenetreJeu fj) {

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (fj.echiquier.cases[i][j].getPiece() != null) {
                    if (fj.echiquier.cases[i][j].getPiece().getType() == Type.ROI && fj.echiquier.cases[i][j].getPiece().getCouleur() == couleur) {
                        this.xKing = i;
                        this.yKing = j;

                    }
                }
            }
        }
    }

    // methode vérifiant si le roi est menacé
    public boolean isKingThreatened(Couleur couleur, FenetreJeu fj) {

        if (this.checkThreat(couleur, fj)) {
            return true;
        }
        return false;
    }

    // methode vérifiant si echec et mat
    public boolean checkMate(Couleur couleur, FenetreJeu fj) {
        // localisation du roi en question 
        this.locateKing(couleur, fj);
        // récupérer les possibilités de déplacements du ROI 
        this.kingListOfPossibilities = dep.moves(Type.ROI, xKing, yKing, couleur, fj, 8);
        // parcourir toutes les cases de l'echiquier 
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                // vérifier si la case n'est pas vide
                if (fj.echiquier.cases[i][j].getPiece() != null) {
                    // vérifier si la pièc appartient a l'adversaire
                    if (fj.echiquier.cases[i][j].getPiece().getCouleur() != couleur) {
                        // si l'adversaire est blanc 
                        if (couleur == Couleur.BLANC) {
                            // si la pièce adversaire est un pion 
                            // je l'ai séparée des autres pièce parce que le pion possède un id
                            if (fj.echiquier.cases[i][j].getPiece().getType() == Type.PION) {
                                // récupération de tous ces mouvements possibles en appelant la méthode moves de la classe deplacements 
                                listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.NOIR, fj, fj.echiquier.cases[i][j].getPiece().getId());
                                // parcourir tous les possible du pion adversaire
                                for (int k = 0; k < listOfPossibilities.size(); k++) {
                                    possibilite = listOfPossibilities.get(k);
                                    for (int p = 0; p < kingListOfPossibilities.size(); p++) {
                                        possibilite1 = kingListOfPossibilities.get(p);
                                        // vérifier si les coordonnées du mouvement possible correspondent a la position du roi
                                        if (possibilite[0] == possibilite1[0] && possibilite[1] == possibilite1[1]) {
                                            // si oui on supprime cette possibilité de mouvement pour le roi 
                                            this.kingListOfPossibilities.remove(p);
                                        }
                                    }

                                }
                            } else {
                                // meme processus pour les autres pieces cette fois  
                                listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.NOIR, fj, 8);
                                for (int k = 0; k < listOfPossibilities.size(); k++) {
                                    possibilite = listOfPossibilities.get(k);
                                    for (int p = 0; p < kingListOfPossibilities.size(); p++) {
                                        possibilite1 = kingListOfPossibilities.get(p);
                                        if (possibilite[0] == possibilite1[0] && possibilite[1] == possibilite1[1]) {
                                            this.kingListOfPossibilities.remove(p);
                                        }
                                    }
                                }
                            }
                        } else // si l'adversaire est noir 
                        if (fj.echiquier.cases[i][j].getPiece().getType() == Type.PION) {
                            listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.BLANC, fj, fj.echiquier.cases[i][j].getPiece().getId());
                            for (int k = 0; k < listOfPossibilities.size(); k++) {
                                possibilite = listOfPossibilities.get(k);
                                for (int p = 0; p < kingListOfPossibilities.size(); p++) {
                                    possibilite1 = kingListOfPossibilities.get(p);
                                    if (possibilite[0] == possibilite1[0] && possibilite[1] == possibilite1[1]) {
                                        this.kingListOfPossibilities.remove(p);
                                    }
                                }

                            }
                        } else {
                            listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.BLANC, fj, 8);
                            for (int k = 0; k < listOfPossibilities.size(); k++) {
                                possibilite = listOfPossibilities.get(k);
                                for (int p = 0; p < kingListOfPossibilities.size(); p++) {
                                    possibilite1 = kingListOfPossibilities.get(p);
                                    if (possibilite[0] == possibilite1[0] && possibilite[1] == possibilite1[1]) {
                                        this.kingListOfPossibilities.remove(p);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        // on vérifie si il reste encore des possibilités de mouvement pour le roi 
        if (this.kingListOfPossibilities.size() == 0) {
            // si il  en reste aucune 
            //on vérifie si une de ses pièce peut le sauver 
            // // avec la récursivité on simule chaque mouvement possible de c'est pièe et on appelle récursivement les fonction check mat
            for (int i = 0; i <= 7; i++) {
                for (int j = 0; j <= 7; j++) {
                    if (fj.echiquier.cases[i][j].getPiece() != null) {
                        // vérifier si la pièc appartient a l'adversaire
                        if (fj.echiquier.cases[i][j].getPiece().getCouleur() == couleur) {
                            if (fj.echiquier.cases[i][j].getPiece().getType() == Type.PION) {
                                // récupération de tous ces mouvements possibles en appelant la méthode moves de la classe deplacements 
                                listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.NOIR, fj, fj.echiquier.cases[i][j].getPiece().getId());
                                // parcourir tous les possible du pion adversaire
                                for (int k = 0; k < listOfPossibilities.size(); k++) {
                                    possibilite = listOfPossibilities.get(k);
                                    piece = fj.echiquier.cases[possibilite[0]][possibilite[1]].getPiece();
                                    fj.echiquier.cases[possibilite[0]][possibilite[1]].setPiece(fj.echiquier.cases[i][j].getPiece());
                                    fj.echiquier.cases[i][j].setPiece(null);
                                    if (this.checkMate(couleur, fj)) {
                                        System.out.println("je suis la ");
                                        return true;
                                    }
                                    fj.echiquier.cases[i][j].setPiece(fj.echiquier.cases[possibilite[0]][possibilite[1]].getPiece());
                                    fj.echiquier.cases[possibilite[0]][possibilite[1]].setPiece(piece);
                                    piece = null;
                                    return false;

                                }
                            } else {
                                // meme processus pour les autres pieces cette fois  
                                listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.NOIR, fj, 8);
                                for (int k = 0; k < listOfPossibilities.size(); k++) {
                                    possibilite = listOfPossibilities.get(k);
                                    for (int p = 0; p < kingListOfPossibilities.size(); p++) {
                                        possibilite1 = kingListOfPossibilities.get(p);
                                        if (possibilite[0] == possibilite1[0] && possibilite[1] == possibilite1[1]) {
                                            this.kingListOfPossibilities.remove(p);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return true;
        } else {
            // si il en reste on vérifie si le mouvement est sûr
            for (int k = 0; k < kingListOfPossibilities.size(); k++) {
                possibilite = kingListOfPossibilities.get(k);
                if (!isMoveSafe(couleur, xKing, yKing, possibilite[0], possibilite[1], fj) == false) {
                    // on supprime les mouvements riqués
                    kingListOfPossibilities.remove(k);
                }

            }
            // si au final la liste des possibilités est vide  
            if (this.kingListOfPossibilities.size() == 0) {
                // si il en reste aucun mouvement possible 
                //on vérifie si une de ses pièce peut le sauver 
                // avec la récursivité on simule chaque mouvement possible de c'est pièce et on appelle récursivement les fonction check mat
                for (int i = 0; i <= 7; i++) {
                    for (int j = 0; j <= 7; j++) {
                        if (fj.echiquier.cases[i][j].getPiece() != null) {
                            // vérifier si la pièc appartient a l'adversaire
                            if (fj.echiquier.cases[i][j].getPiece().getCouleur() == couleur) {
                                if (fj.echiquier.cases[i][j].getPiece().getType() == Type.PION) {
                                    // récupération de tous ces mouvements possibles en appelant la méthode moves de la classe deplacements 
                                    listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.NOIR, fj, fj.echiquier.cases[i][j].getPiece().getId());
                                    // parcourir tous les possible du pion adversaire
                                    for (int k = 0; k < listOfPossibilities.size(); k++) {
                                        possibilite = listOfPossibilities.get(k);
                                        piece = fj.echiquier.cases[possibilite[0]][possibilite[1]].getPiece();
                                        fj.echiquier.cases[possibilite[0]][possibilite[1]].setPiece(fj.echiquier.cases[i][j].getPiece());
                                        fj.echiquier.cases[i][j].setPiece(null);
                                        if (this.checkMate(couleur, fj)) {
                                            System.out.println("je suis la ");
                                            return true;
                                        }
                                        fj.echiquier.cases[i][j].setPiece(fj.echiquier.cases[possibilite[0]][possibilite[1]].getPiece());
                                        fj.echiquier.cases[possibilite[0]][possibilite[1]].setPiece(piece);
                                        piece = null;
                                        return false;

                                    }
                                } else {
                                    // meme processus pour les autres pieces cette fois  
                                    listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.NOIR, fj, 8);
                                    for (int k = 0; k < listOfPossibilities.size(); k++) {
                                        possibilite = listOfPossibilities.get(k);
                                        for (int p = 0; p < kingListOfPossibilities.size(); p++) {
                                            possibilite1 = kingListOfPossibilities.get(p);
                                            if (possibilite[0] == possibilite1[0] && possibilite[1] == possibilite1[1]) {
                                                this.kingListOfPossibilities.remove(p);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                System.out.println("je suis la ");
                return true;
            }
        }

        return false;
    }

    // méthode vérifiant si le déplacement est autorisé
    public boolean isMoveSafe(Couleur couleur, int x, int y, int newx, int newy, FenetreJeu fj) {
        // simuler le déplacement sans déplacer la piece (il n'est pas visible vis a vis du joueur)
        // et vérifier si le roi est menacé ou pas 
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (x == i && y == j) {
                    piece = fj.echiquier.cases[newx][newy].getPiece();
                    fj.echiquier.cases[newx][newy].setPiece(fj.echiquier.cases[x][y].getPiece());
                    fj.echiquier.cases[x][y].setPiece(null);
                }
            }
        }
     // appel a la fonction check threat pour vérfier si le roi est menacé 
        if (this.checkThreat(couleur, fj)) {
            fj.echiquier.cases[x][y].setPiece(fj.echiquier.cases[newx][newy].getPiece());
            fj.echiquier.cases[newx][newy].setPiece(piece);
            piece = null;
            return false;
        }
        fj.echiquier.cases[x][y].setPiece(fj.echiquier.cases[newx][newy].getPiece());
        fj.echiquier.cases[newx][newy].setPiece(piece);
        piece = null;

        return true;
    }

    // methode vérifiant si y a menace sur le roi    
    public boolean checkThreat(Couleur couleur, FenetreJeu fj) {
        this.locateKing(couleur, fj);

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (fj.echiquier.cases[i][j].getPiece() != null) {
                    if (fj.echiquier.cases[i][j].getPiece().getCouleur() != couleur) {
                        if (couleur == Couleur.BLANC) {
                            if (fj.echiquier.cases[i][j].getPiece().getType() == Type.PION) {

                                listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.NOIR, fj, fj.echiquier.cases[i][j].getPiece().getId());
                                for (int k = 0; k < listOfPossibilities.size(); k++) {
                                    possibilite = listOfPossibilities.get(k);
                                    System.out.println(xKing + "," + yKing + " et " + possibilite[0] + " ," + possibilite[1] + " et id:" + fj.echiquier.cases[i][j].getPiece().getId());
                                    if (possibilite[0] == this.xKing && possibilite[1] == this.yKing) {
                                        System.out.println("Roi menacé");

                                        return true;
                                    }
                                }
                            } else {
                                listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.NOIR, fj, 8);
                                for (int k = 0; k < listOfPossibilities.size(); k++) {
                                    possibilite = listOfPossibilities.get(k);
                                    if (possibilite[0] == this.xKing && possibilite[1] == this.yKing) {
                                        System.out.println("Roi menacé");
                                        return true;
                                    }
                                }
                            }
                        } else if (fj.echiquier.cases[i][j].getPiece().getType() == Type.PION) {
                            listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.BLANC, fj, fj.echiquier.cases[i][j].getPiece().getId());
                            for (int k = 0; k < listOfPossibilities.size(); k++) {
                                possibilite = listOfPossibilities.get(k);
                                System.out.println(xKing + "," + yKing + " et " + possibilite[0] + " ," + possibilite[1] + " et id:" + fj.echiquier.cases[i][j].getPiece().getId());
                                if (possibilite[0] == this.xKing && possibilite[1] == this.yKing) {
                                    System.out.println("Roi menacé");
                                    return true;
                                }
                            }
                        } else {
                            listOfPossibilities = dep.moves(fj.echiquier.cases[i][j].getPiece().getType(), i, j, Couleur.BLANC, fj, 8);
                            for (int k = 0; k < listOfPossibilities.size(); k++) {
                                possibilite = listOfPossibilities.get(k);
                                if (possibilite[0] == this.xKing && possibilite[1] == this.yKing) {
                                    System.out.println("Roi menacé");
                                    return true;
                                }
                            }
                        }
                    }

                }
            }
        }
        System.out.println("Roi non menacé");
        return false;
    }

}
