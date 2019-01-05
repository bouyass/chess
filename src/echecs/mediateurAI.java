/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import Vue.FenetreJeu;
import echecs.Pieces.Piece;
import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.IntStream;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 *
 * @author lyes
 */
public class mediateurAI {

    boolean firstClick = true;
    int x, y, newx, newy, pos;
    FenetreJeu fj;
    final Border border = BorderFactory.createLineBorder(Color.RED, 3);
    Couleur tour = Couleur.BLANC;
    Deplacements dep;
    AI ai;
    ArrayList<Piece> CapturedBlackPiece;
    ArrayList<Piece> CapturedWhitePiece;
    boolean moved = false;
    UpdateVue uv;
    Test test;
    int compteurBlanc = 0, compteurNoir = 0;
    boolean threat = false;
    boolean pc = false;
    boolean player = true;
// constructeur mediateur

    public mediateurAI(FenetreJeu fj) {
        this.fj = fj;

        dep = new Deplacements();
        test = new Test();
        pos = fj.ta.getCaretPosition();
        CapturedBlackPiece = new ArrayList<Piece>();
        CapturedWhitePiece = new ArrayList<Piece>();
        uv = new UpdateVue(fj);
        ai = new AI();
    }

    // methode recevant les clicks des joueurs 
    public void getClick(int p, int o) {
        if (player = true) {
            if (firstClick) {

                System.out.println("premier");
                if (fj.echiquier.cases[p][o].getPiece() != null) {

                    fj.labels[p][o].setBorder(border);
                    if (fj.echiquier.cases[p][o].getPiece().getType() == Type.PION) {
                        System.out.println(fj.echiquier.cases[p][o].getPiece().getId());
                        uv.showPossibilities(dep.moves(fj.echiquier.cases[p][o].getPiece().getType(), p, o, tour, fj, fj.echiquier.cases[p][o].getPiece().getId()));
                    } else {
                        uv.showPossibilities(dep.moves(fj.echiquier.cases[p][o].getPiece().getType(), p, o, tour, fj, 8));
                    }
                    x = p;
                    y = o;
                    firstClick = false;
                    if (test.isKingThreatened(tour, fj) == true) {
                        threat = true;
                    }

                } else {

                    fj.labels[x][y].setBorder(null);
                    x = p;
                    y = o;
                    fj.labels[x][y].setBorder(border);
                }

            } else {

                System.out.println("second");
                newx = p;
                newy = o;

                // si le roi est menacé 
                if (threat == true) {

                    if (test.isMoveSafe(tour, x, y, newx, newy, fj)) {
                        if (contains(dep.listOfPossibilities, newx, newy)) {

                            if (contains(dep.listOfCaptureMoves, newx, newy)) {
                                // enregistrer les pièces capturée par les deux joueurs 
                                if (fj.echiquier.cases[newx][newy].getPiece().getCouleur() == Couleur.BLANC) {
                                    CapturedWhitePiece.add(fj.echiquier.cases[newx][newy].getPiece());
                                } else {
                                    CapturedBlackPiece.add(fj.echiquier.cases[newx][newy].getPiece());
                                }
                            }
                            // limiter le mouvement en avant en une seule case après son premier mouvement
                            if (fj.echiquier.cases[x][y].getPiece() != null) {

                                if (fj.echiquier.cases[x][y].getPiece().getType() == Type.PION) {

                                    if (fj.echiquier.cases[x][y].getPiece().getCouleur() == Couleur.BLANC) {
                                        int id = fj.echiquier.cases[x][y].getPiece().getId();

                                        if (!IntStream.of(dep.whitePawnsIds).anyMatch(k -> k == id)) {

                                            dep.whitePawnsIds[compteurBlanc] = fj.echiquier.cases[x][y].getPiece().getId();
                                            compteurBlanc++;
                                        }
                                    } else {
                                        int id = fj.echiquier.cases[x][y].getPiece().getId();
                                        if (!IntStream.of(dep.blackPawnsIds).anyMatch(k -> k == id)) {
                                            dep.blackPawnsIds[compteurNoir] = fj.echiquier.cases[x][y].getPiece().getId();
                                            compteurNoir++;
                                        }
                                    }
                                }
                            }
                            // effectuer le mouvement 
                            uv.executeMove(newx, newy, x, y, false);
                            // mettre a jour le plateau
                            firstClick = true;
                            fj.labels[x][y].setBorder(null);
                            uv.hidePossibilities(dep.listOfPossibilities);
                            // changement de tour 

                            if (test.checkMate(tour, fj)) {
                                JOptionPane jop = new JOptionPane(), jop1 = new JOptionPane();
                                String[] option = {"Sortir", "Rejouer"};
                                int rang = jop.showOptionDialog(null, "Fin de la partie, echec et mate \n le joueur " + tour + " a gagné, bravo!!", "Fin de partie",
                                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
                                jop1.showMessageDialog(null, "Vous allez jouer " + option[rang], "validation choix", JOptionPane.INFORMATION_MESSAGE);
                                if (option[rang] == "Rejouer") {
                                    // lancer une nouvelle partie
                                    Echecs.main(option);
                                } else {
                                    // quitter le jeu
                                    System.exit(0);
                                }

                            }
                            if (dep.listOfFeasiblePossibilities != null) {
                                dep.listOfCaptureMoves.clear();
                            }
                            if (dep.listOfPossibilities != null) {
                                dep.listOfPossibilities.clear();
                            }
                            if (dep.listOfCaptureMoves != null) {
                                dep.listOfCaptureMoves.clear();
                            }
                            moved = true;
                            threat = false;

                        }
                        if (moved) {
                            moved = false;

                        } else {
                            fj.labels[x][y].setBorder(null);
                            x = newx;
                            y = newy;
                            fj.labels[x][y].setBorder(border);
                            uv.hidePossibilities(dep.listOfPossibilities);
                            if (dep.listOfPossibilities != null) {
                                dep.listOfPossibilities.clear();
                            }
                            if (dep.listOfCaptureMoves != null) {
                                dep.listOfCaptureMoves.clear();
                            }
                            firstClick = true;
                            getClick(x, y);
                        }

                    } else {
                        // alerter le joueur roi menacé
                        JOptionPane jop2 = new JOptionPane();
                        jop2.showMessageDialog(null, "Ton roi est menacé", "Alerte", JOptionPane.INFORMATION_MESSAGE);
                        fj.labels[x][y].setBorder(null);
                        x = newx;
                        y = newy;
                        fj.labels[x][y].setBorder(border);
                        uv.hidePossibilities(dep.listOfPossibilities);
                        if (dep.listOfPossibilities != null) {
                            dep.listOfPossibilities.clear();
                        }
                        if (dep.listOfCaptureMoves != null) {
                            dep.listOfCaptureMoves.clear();
                        }
                        firstClick = true;
                    }

                } else // la roi n'est pas ménacé 
                if (test.isMoveSafe(tour, x, y, newx, newy, fj)) {

                    if (fj.echiquier.cases[x][y].getPiece() != null) {

                        int[] xp = {newx, newy};

                        if (contains(dep.listOfPossibilities, newx, newy)) {

                            if (contains(dep.listOfCaptureMoves, newx, newy)) {
                                // enregistrer les pièces capturée par les deux joueurs 
                                if (fj.echiquier.cases[newx][newy].getPiece().getCouleur() == Couleur.BLANC) {
                                    CapturedWhitePiece.add(fj.echiquier.cases[newx][newy].getPiece());
                                } else {
                                    CapturedBlackPiece.add(fj.echiquier.cases[newx][newy].getPiece());
                                }
                            }
                            // limiter le mouvement en avant en une seule case après son premier mouvement
                            if (fj.echiquier.cases[x][y].getPiece() != null) {

                                if (fj.echiquier.cases[x][y].getPiece().getType() == Type.PION) {

                                    if (fj.echiquier.cases[x][y].getPiece().getCouleur() == Couleur.BLANC) {
                                        int id = fj.echiquier.cases[x][y].getPiece().getId();

                                        if (!IntStream.of(dep.whitePawnsIds).anyMatch(k -> k == id)) {

                                            dep.whitePawnsIds[compteurBlanc] = fj.echiquier.cases[x][y].getPiece().getId();
                                            compteurBlanc++;
                                        }
                                    } else {
                                        int id = fj.echiquier.cases[x][y].getPiece().getId();
                                        if (!IntStream.of(dep.blackPawnsIds).anyMatch(k -> k == id)) {
                                            dep.blackPawnsIds[compteurNoir] = fj.echiquier.cases[x][y].getPiece().getId();
                                            compteurNoir++;
                                        }
                                    }
                                }
                            }
                            // effectuer le mouvement 
                            uv.executeMove(newx, newy, x, y, false);

                            // mettre a jour le plateau
                            firstClick = true;

                            fj.labels[x][y].setBorder(null);
                            uv.hidePossibilities(dep.listOfPossibilities);

                            pc = true;
                            if (dep.listOfFeasiblePossibilities != null) {
                                dep.listOfCaptureMoves.clear();
                            }
                            if (dep.listOfPossibilities != null) {
                                dep.listOfPossibilities.clear();
                            }
                            if (dep.listOfCaptureMoves != null) {
                                dep.listOfCaptureMoves.clear();
                            }
                            moved = true;
                        }
                        // dans le cas ou le joueur clicke sur 2 piece differente ui lui apartiennent donc on rappele la fonction getClick pour afficher les 
                        // les nouvelles positions possible
                        if (moved) {
                            moved = false;

                        } else {
                            fj.labels[x][y].setBorder(null);
                            x = newx;
                            y = newy;
                            fj.labels[x][y].setBorder(border);
                            uv.hidePossibilities(dep.listOfPossibilities);
                            if (dep.listOfPossibilities != null) {
                                dep.listOfPossibilities.clear();
                            }
                            if (dep.listOfCaptureMoves != null) {
                                dep.listOfCaptureMoves.clear();
                            }
                            firstClick = true;
                            getClick(x, y);
                        }
                    } else {
                        fj.labels[x][y].setBorder(null);
                        x = newx;
                        y = newy;
                        fj.labels[x][y].setBorder(border);
                        uv.hidePossibilities(dep.listOfPossibilities);
                        if (dep.listOfPossibilities != null) {
                            dep.listOfPossibilities.clear();
                        }
                        if (dep.listOfCaptureMoves != null) {
                            dep.listOfCaptureMoves.clear();
                        }
                    }
                } else {
                    fj.labels[x][y].setBorder(null);
                    x = newx;
                    y = newy;
                    fj.labels[x][y].setBorder(border);
                    uv.hidePossibilities(dep.listOfPossibilities);
                    if (dep.listOfPossibilities != null) {
                        dep.listOfPossibilities.clear();
                    }
                    if (dep.listOfCaptureMoves != null) {
                        dep.listOfCaptureMoves.clear();
                    }
                }
            }
        }

    }

    // contains vérifie si un mouvement existe dans une liste donnée 
    public boolean contains(ArrayList<int[]> tab, int v, int w) {

        if (tab != null) {

            int size = tab.size();

            int[] possibilitie;
            for (int k = 0; k < size; k++) {
                possibilitie = tab.get(k);

                System.out.println(possibilitie[0] + " , " + possibilitie[1] + " et " + v + " et " + w);
                if (possibilitie[0] == v && possibilitie[1] == w) {
                    return true;
                }
            }
        }
        return false;
    }
}
