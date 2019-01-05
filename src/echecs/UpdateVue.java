/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import Vue.FenetreJeu;
import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.IntStream;
import javax.swing.ImageIcon;

/**
 *
 * @author lyes
 */
public class UpdateVue {

    public FenetreJeu fj;
    Color couleur = new Color(153, 76, 0);
    Color couleur1 = new Color(255, 255, 204);
    Color couleur2 = new Color(255, 153, 0);

    ArrayList<Couleur> couleurs = new ArrayList<Couleur>();

    UpdateVue(FenetreJeu fj) {
        this.fj = fj;
    }

    public void showPossibilities(ArrayList<int[]> poss) {
        int[] possibilitie;
        for (int k = 0; k <= poss.size() - 1; k++) {
            possibilitie = poss.get(k);
            couleurs.add(k, fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getCouleur());
            System.out.println(fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getCouleur());
            fj.labels[possibilitie[0]][possibilitie[1]].setBackground(couleur2);
        }
    }

    public void hidePossibilities(ArrayList<int[]> poss) {
        int[] possibilitie;
        int size = poss.size();
        for (int k = 0; k <= size - 1; k++) {
            possibilitie = poss.get(k);
            if (couleurs.get(k) == Couleur.BLANC) {
                System.out.println(couleurs.get(k));
                fj.labels[possibilitie[0]][possibilitie[1]].setBackground(couleur1);
            } else {
                fj.labels[possibilitie[0]][possibilitie[1]].setBackground(couleur);
            }
        }
    }

    public void executeMove(int newx, int newy, int x, int y, boolean capture) {
        if (capture) {
            fj.echiquier.cases[newx][newy].piece = fj.echiquier.cases[x][y].getPiece();
            fj.labels[newx][newy].setIcon(fj.labels[x][y].getIcon());

        } else {
            fj.echiquier.cases[newx][newy].piece = fj.echiquier.cases[x][y].getPiece();
            fj.labels[newx][newy].setIcon(fj.labels[x][y].getIcon());
            fj.echiquier.cases[x][y].setPiece(null);
            fj.labels[x][y].setIcon(null);
        }

    }

    public void promotion(int x, int y, String icone) {
        ImageIcon ic = new ImageIcon(icone);
        this.fj.labels[x][y].setIcon(ic);
    }

    public void executePetitRoque(Couleur tour) {
        if (tour == Couleur.BLANC) {
            // Petit Roque blanc
            // déplacement du roi
            fj.echiquier.cases[7][6].setPiece(fj.echiquier.cases[7][4].getPiece());
            fj.labels[7][6].setIcon(fj.labels[7][4].getIcon());
            fj.echiquier.cases[7][4].setPiece(null);
            fj.labels[7][4].setIcon(null);
            // déplacement de la tour 
            fj.echiquier.cases[7][5].setPiece(fj.echiquier.cases[7][7].getPiece());
            fj.labels[7][5].setIcon(fj.labels[7][7].getIcon());
            fj.echiquier.cases[7][7].setPiece(null);
            fj.labels[7][7].setIcon(null);
        } else {
            // petit Roque noir
            // déplacement du roi
            System.out.println("************************************");
            fj.echiquier.cases[0][6].setPiece(fj.echiquier.cases[0][4].getPiece());
            fj.labels[0][6].setIcon(fj.labels[0][4].getIcon());
            fj.echiquier.cases[0][4].setPiece(null);
            fj.labels[0][4].setIcon(null);
            // déplacement de la tour 
            fj.echiquier.cases[0][5].setPiece(fj.echiquier.cases[0][7].getPiece());
            fj.labels[0][5].setIcon(fj.labels[0][7].getIcon());
            fj.echiquier.cases[0][7].setPiece(null);
            fj.labels[0][7].setIcon(null);

        }
    }

    public void executeGrandRoque(Couleur tour) {
        if (tour == Couleur.BLANC) {
            // grand Roque blanc
            // déplacement du roi
            fj.echiquier.cases[7][2].setPiece(fj.echiquier.cases[7][4].getPiece());
            fj.labels[7][2].setIcon(fj.labels[7][4].getIcon());
            fj.echiquier.cases[7][4].setPiece(null);
            fj.labels[7][4].setIcon(null);
            // déplacement de la tour 
            fj.echiquier.cases[7][3].setPiece(fj.echiquier.cases[7][0].getPiece());
            fj.labels[7][3].setIcon(fj.labels[7][0].getIcon());
            fj.echiquier.cases[7][0].setPiece(null);
            fj.labels[7][0].setIcon(null);
        } else {
            // grand Roque noir
            // déplacement du roi
            fj.echiquier.cases[0][2].setPiece(fj.echiquier.cases[0][4].getPiece());
            fj.labels[0][2].setIcon(fj.labels[0][4].getIcon());
            fj.echiquier.cases[0][4].setPiece(null);
            fj.labels[0][4].setIcon(null);
            // déplacement de la tour 
            fj.echiquier.cases[0][3].setPiece(fj.echiquier.cases[0][0].getPiece());
            fj.labels[0][3].setIcon(fj.labels[0][0].getIcon());
            fj.echiquier.cases[0][0].setPiece(null);
            fj.labels[0][0].setIcon(null);

        }
    }

}
