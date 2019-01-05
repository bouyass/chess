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
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author lyes
 */
public class Deplacements {

    ArrayList<int[]> listOfPossibilities;
    ArrayList<int[]> listOfFeasiblePossibilities;
    ArrayList<int[]> listOfCaptureMoves;
    int[] whitePawnsIds = {8, 8, 8, 8, 8, 8, 8};
    int[] blackPawnsIds = {8, 8, 8, 8, 8, 8, 8};
    int[] whiteRookIds = {8, 8};
    int[] blackRookIds = {8, 8};
    boolean whiteKingFirstMove = true;
    boolean blackKingFirstMove = true;
    int[] possibilitie;
    int size, cpt;

    Border border = BorderFactory.createLineBorder(Color.GREEN, 3);

    public Deplacements() {

        listOfPossibilities = new ArrayList<int[]>();
        listOfCaptureMoves = new ArrayList<int[]>();
        listOfFeasiblePossibilities = new ArrayList<int[]>();

    }

// methode retournant une liste de tous les mouvements possibles pour une pièce recue en paramètre    
    public ArrayList<int[]> moves(Type type, int x, int y, Couleur tour, FenetreJeu fj, int j) {

        listOfFeasiblePossibilities.clear();
        listOfPossibilities.clear();
        listOfCaptureMoves.clear();
        switch (type) {
            // regles de deplacements d'un cavalier 
            case CAVALIER:
                int CX[] = {2, 1, -1, -2, -2, -1, 1, 2};
                int CY[] = {1, 2, 2, 1, -1, -2, -2, -1};
                // Check if each possible move is valid or not 
                for (int i = 0; i <= 7; i++) {
                    // une des position possible pour la cavalier apres le deplacement 
                    int p = x + CX[i];
                    int q = y + CY[i];

                    //  verification de faisabilité des deplacements on verifie si la position se trouve dans le plateau de jeu et pas en dehors
                    if (p >= 0 && q >= 0 && p <= 7 && q <= 7) {
                        int[] xp = {p, q};
                        listOfPossibilities.add(xp);

                    }
                }
                cpt = 0;
                size = this.listOfPossibilities.size();

                // verification de la faisabilités des deplamcement on empeche le cavalier de se deplacer dans une des case deja occupée par son joueur
                for (int k = 0; k <= this.listOfPossibilities.size() - 1; k++) {
                    possibilitie = this.listOfPossibilities.get(k);

                    if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() != null) {
                        if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece().getCouleur() == tour) {

                            this.listOfPossibilities.remove(possibilitie);
                        } else {
                            this.listOfCaptureMoves.add(possibilitie);
                        }
                    }
                }

                for (int k = 0; k <= this.listOfPossibilities.size() - 1; k++) {
                    possibilitie = this.listOfPossibilities.get(k);
                    if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() != null) {
                        if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece().getCouleur() == tour) {

                            this.listOfPossibilities.remove(possibilitie);
                        } else {
                            this.listOfCaptureMoves.add(possibilitie);
                        }

                    }
                }
                break;

            // regles de depalacements d'un pion
            case PION:

                if (tour == Couleur.BLANC) {

                    if (IntStream.of(this.whitePawnsIds).anyMatch(k -> k == j)) {

                        int PX[] = {1, 1, 1};
                        int PY[] = {0, -1, 1};
                        for (int i = 0; i < 3; i++) {
                            int p = x - PX[i];
                            int q = y - PY[i];
                            if (p >= 0 && q >= 0 && p <= 7 && q <= 7) {
                                int[] xp = {p, q};
                                this.listOfFeasiblePossibilities.add(xp);
                            }
                        }
                    } else {

                        int PX[] = {2, 1, 1, 1};
                        int PY[] = {0, 0, -1, 1};
                        for (int i = 0; i < 4; i++) {
                            int p = x - PX[i];
                            int q = y - PY[i];
                            System.out.println(p + " et " + q);
                            if (p >= 0 && q >= 0 && p <= 7 && q <= 7) {
                                int[] xp = {p, q};
                                this.listOfFeasiblePossibilities.add(xp);
                            }
                        }

                    }

                } else // toutes les positions de déplacment possibles pour un pion (noir)
                if (IntStream.of(this.blackPawnsIds).anyMatch(k -> k == j)) {
                    int PX[] = {1, 1, 1};
                    int PY[] = {0, -1, 1};
                    for (int i = 0; i < 3; i++) {
                        int p = x + PX[i];
                        int q = y + PY[i];
                        if (p >= 0 && q >= 0 && p <= 7 && q <= 7) {
                            int[] xp = {p, q};
                            this.listOfFeasiblePossibilities.add(xp);
                        }
                    }
                } else {

                    int PX[] = {2, 1, 1, 1};
                    int PY[] = {0, 0, -1, 1};
                    for (int i = 0; i < 4; i++) {
                        int p = x + PX[i];
                        int q = y + PY[i];
                        System.out.println(p + " et " + q);
                        if (p >= 0 && q >= 0 && p <= 7 && q <= 7) {
                            int[] xp = {p, q};
                            this.listOfFeasiblePossibilities.add(xp);
                        }
                    }

                }

                // dans le cas ou le pion est de couleur blanche
                if (tour == Couleur.BLANC) {
                    //System.out.println(x+" et "+y);
                    for (int k = 0; k < this.listOfFeasiblePossibilities.size(); k++) {
                        possibilitie = this.listOfFeasiblePossibilities.get(k);

                        // premiere possiblité de capture pour un pion (noir)
                        int[] xp = {x - 1, y - 1};
                        if (xp[0] == possibilitie[0] && xp[1] == possibilitie[1]) {
                            // si il peut capturer un piece ennemie 
                            if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() != null && fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece().getCouleur() != tour) {
                                this.listOfCaptureMoves.add(possibilitie);
                                this.listOfPossibilities.add(possibilitie);

                            }
                        }
                        // deuxième possiblité de capture pour un pion (blanc)
                        int[] xp1 = {x - 1, y + 1};

                        if (xp1[0] == possibilitie[0] && xp1[1] == possibilitie[1]) {

                            if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() != null && fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece().getCouleur() != tour) {
                                this.listOfCaptureMoves.add(possibilitie);
                                this.listOfPossibilities.add(possibilitie);

                            }
                        }

                        int[] xp2 = {(x - 2), (y)};
                        if (xp2[0] == possibilitie[0] && xp2[1] == possibilitie[1] && fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() == null) {
                            this.listOfPossibilities.add(possibilitie);
                        }
                        int[] xp3 = {(x - 1), (y)};
                        if (xp3[0] == possibilitie[0] && xp3[1] == possibilitie[1] && fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() == null) {
                            this.listOfPossibilities.add(possibilitie);
                        }
                    }

                } // dans le cas ou le pion est de couleur noir 
                else {
                    for (int k = 0; k < this.listOfFeasiblePossibilities.size(); k++) {
                        possibilitie = this.listOfFeasiblePossibilities.get(k);

                        // premiere possiblité de capture pour un pion (noir)
                        int[] xp = {x + 1, y + 1};
                        if (xp[0] == possibilitie[0] && xp[1] == possibilitie[1]) {
                            // si il peut capturer un piece ennemie 
                            if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() != null && fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece().getCouleur() != tour) {
                                this.listOfCaptureMoves.add(possibilitie);
                                this.listOfPossibilities.add(possibilitie);

                            }
                        }
                        // deuxième possiblité de capture pour un pion (blanc)
                        int[] xp1 = {x + 1, y - 1};

                        if (xp1[0] == possibilitie[0] && xp1[1] == possibilitie[1]) {

                            if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() != null && fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece().getCouleur() != tour) {
                                this.listOfCaptureMoves.add(possibilitie);
                                this.listOfPossibilities.add(possibilitie);

                            }
                        }

                        int[] xp2 = {(x + 2), (y)};
                        if (xp2[0] == possibilitie[0] && xp2[1] == possibilitie[1] && fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() == null) {
                            this.listOfPossibilities.add(possibilitie);
                        }
                        int[] xp3 = {(x + 1), (y)};
                        if (xp3[0] == possibilitie[0] && xp3[1] == possibilitie[1] && fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() == null) {
                            this.listOfPossibilities.add(possibilitie);
                        }
                    }

                }

                break;
            // regles de déplacement de tour 
            case TOUR:

                int nbr = 0;
                boolean xBlocked = false;
                boolean yBlocked = false;
                boolean xBlocked1 = false;
                boolean yBlocked1 = false;
                while (nbr <= 7) {
                    nbr++;
                    if (x + nbr <= 7) {
                        if (!xBlocked) {
                            if (fj.echiquier.cases[x + nbr][y].getPiece() != null) {
                                if (fj.echiquier.cases[x + nbr][y].getPiece().getCouleur() != tour) {
                                    int[] xp = {x + nbr, y};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                xBlocked = true;
                            } else {

                                int[] xp = {x + nbr, y};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }
                    if (y + nbr <= 7) {
                        if (!yBlocked) {
                            if (fj.echiquier.cases[x][y + nbr].getPiece() != null) {
                                if (fj.echiquier.cases[x][y + nbr].getPiece().getCouleur() != tour) {
                                    int[] xp = {x, y + nbr};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                yBlocked = true;
                            } else {
                                int[] xp = {x, y + nbr};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }

                    if (y - nbr >= 0) {
                        if (!yBlocked1) {
                            if (fj.echiquier.cases[x][y - nbr].getPiece() != null) {
                                if (fj.echiquier.cases[x][y - nbr].getPiece().getCouleur() != tour) {
                                    int[] xp = {x, y - nbr};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                yBlocked1 = true;
                            } else {
                                int[] xp = {x, y - nbr};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }
                    if (x - nbr >= 0) {
                        if (!xBlocked1) {
                            if (fj.echiquier.cases[x - nbr][y].getPiece() != null) {
                                if (fj.echiquier.cases[x - nbr][y].getPiece().getCouleur() != tour) {
                                    int[] xp = {x - nbr, y};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                xBlocked1 = true;
                            } else {

                                int[] xp = {x - nbr, y};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }

                }

                break;

            case FOU:
                int cpt = 0;
                boolean xBlock = false;
                boolean yBlock = false;
                boolean xBlock1 = false;
                boolean yBlock1 = false;
                while (cpt <= 7) {
                    cpt++;
                    if (x + cpt <= 7 && y + cpt <= 7) {
                        if (!xBlock) {
                            if (fj.echiquier.cases[x + cpt][y + cpt].getPiece() != null) {
                                if (fj.echiquier.cases[x + cpt][y + cpt].getPiece().getCouleur() != tour) {
                                    int[] xp = {x + cpt, y + cpt};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                xBlock = true;
                            } else {

                                int[] xp = {x + cpt, y + cpt};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }
                    if (x + cpt <= 7 && y - cpt >= 0) {
                        if (!yBlock) {
                            if (fj.echiquier.cases[x + cpt][y - cpt].getPiece() != null) {
                                if (fj.echiquier.cases[x + cpt][y - cpt].getPiece().getCouleur() != tour) {
                                    int[] xp = {x + cpt, y - cpt};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                yBlock = true;
                            } else {
                                int[] xp = {x + cpt, y - cpt};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }

                    if (x - cpt >= 0 && y + cpt <= 7) {
                        if (!yBlock1) {
                            if (fj.echiquier.cases[x - cpt][y + cpt].getPiece() != null) {
                                if (fj.echiquier.cases[x - cpt][y + cpt].getPiece().getCouleur() != tour) {
                                    int[] xp = {x - cpt, y + cpt};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                yBlock1 = true;
                            } else {
                                int[] xp = {x - cpt, y + cpt};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }
                    if (x - cpt >= 0 && y - cpt >= 0) {
                        if (!xBlock1) {
                            if (fj.echiquier.cases[x - cpt][y - cpt].getPiece() != null) {
                                if (fj.echiquier.cases[x - cpt][y - cpt].getPiece().getCouleur() != tour) {
                                    int[] xp = {x - cpt, y - cpt};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                xBlock1 = true;
                            } else {

                                int[] xp = {x - cpt, y - cpt};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }

                }
                break;
            case ROI:
                int Xmoves[] = {1, 0, 1, -1, -1, 1, -1, 0};
                int Ymoves[] = {0, 1, 1, -1, 1, -1, 0, -1};
                // Check if each possible move is valid or not 
                for (int i = 0; i <= 7; i++) {
                    // une des position possible pour la cavalier apres le deplacement 
                    int p = x + Xmoves[i];
                    int q = y + Ymoves[i];

                    //  verification de faisabilité des deplacements on verifie si la position se trouve dans le plateau de jeu et pas en dehors
                    if (p >= 0 && q >= 0 && p <= 7 && q <= 7) {
                        int[] xp = {p, q};
                        listOfPossibilities.add(xp);

                    }
                }
                cpt = 0;
                size = this.listOfPossibilities.size();

                // verification de la faisabilité des deplamcements on empêche le roi de se deplacer dans une des case deja occupée par son joueur
                // tour correspondant a la couleur du joueur actuel 
                for (int k = 0; k < this.listOfPossibilities.size(); k++) {
                    possibilitie = this.listOfPossibilities.get(k);

                    if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() != null) {
                        if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece().getCouleur() != tour) {
                            this.listOfCaptureMoves.add(possibilitie);

                        } else {
                            this.listOfPossibilities.remove(possibilitie);
                        }
                    }
                }

                for (int k = 0; k < this.listOfPossibilities.size(); k++) {
                    possibilitie = this.listOfPossibilities.get(k);
                    if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() != null) {
                        if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece().getCouleur() != tour) {
                            this.listOfCaptureMoves.add(possibilitie);

                        } else {
                            this.listOfPossibilities.remove(possibilitie);
                        }

                    }
                }
                for (int k = 0; k < this.listOfPossibilities.size(); k++) {
                    possibilitie = this.listOfPossibilities.get(k);
                    if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece() != null) {
                        if (fj.echiquier.cases[possibilitie[0]][possibilitie[1]].getPiece().getCouleur() != tour) {
                            this.listOfCaptureMoves.add(possibilitie);

                        } else {
                            this.listOfPossibilities.remove(possibilitie);
                        }

                    }
                }
                break;
            case DAME:
                boolean lock1 = false;
                boolean lock2 = false;
                boolean lock3 = false;
                boolean lock4 = false;
                boolean lock5 = false;
                boolean lock6 = false;
                boolean lock7 = false;
                boolean lock8 = false;
                int compteur = 0;
                while (compteur <= 7) {
                    compteur++;
                    if (x + compteur <= 7 && y + compteur <= 7) {
                        if (!lock1) {
                            if (fj.echiquier.cases[x + compteur][y + compteur].getPiece() != null) {
                                if (fj.echiquier.cases[x + compteur][y + compteur].getPiece().getCouleur() != tour) {
                                    int[] xp = {x + compteur, y + compteur};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                lock1 = true;
                            } else {

                                int[] xp = {x + compteur, y + compteur};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }
                    if (x + compteur <= 7 && y - compteur >= 0) {
                        if (!lock2) {
                            if (fj.echiquier.cases[x + compteur][y - compteur].getPiece() != null) {
                                if (fj.echiquier.cases[x + compteur][y - compteur].getPiece().getCouleur() != tour) {
                                    int[] xp = {x + compteur, y - compteur};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                lock2 = true;
                            } else {
                                int[] xp = {x + compteur, y - compteur};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }

                    if (x - compteur >= 0 && y + compteur <= 7) {
                        if (!lock3) {
                            if (fj.echiquier.cases[x - compteur][y + compteur].getPiece() != null) {
                                if (fj.echiquier.cases[x - compteur][y + compteur].getPiece().getCouleur() != tour) {
                                    int[] xp = {x - compteur, y + compteur};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                lock3 = true;
                            } else {
                                int[] xp = {x - compteur, y + compteur};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }
                    if (x - compteur >= 0 && y - compteur >= 0) {
                        if (!lock4) {
                            if (fj.echiquier.cases[x - compteur][y - compteur].getPiece() != null) {
                                if (fj.echiquier.cases[x - compteur][y - compteur].getPiece().getCouleur() != tour) {
                                    int[] xp = {x - compteur, y - compteur};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                lock4 = true;
                            } else {

                                int[] xp = {x - compteur, y - compteur};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }

                    if (x + compteur <= 7) {
                        if (!lock5) {
                            if (fj.echiquier.cases[x + compteur][y].getPiece() != null) {
                                if (fj.echiquier.cases[x + compteur][y].getPiece().getCouleur() != tour) {
                                    int[] xp = {x + compteur, y};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                lock5 = true;
                            } else {

                                int[] xp = {x + compteur, y};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }
                    if (y + compteur <= 7) {
                        if (!lock6) {
                            if (fj.echiquier.cases[x][y + compteur].getPiece() != null) {
                                if (fj.echiquier.cases[x][y + compteur].getPiece().getCouleur() != tour) {
                                    int[] xp = {x, y + compteur};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                lock6 = true;
                            } else {
                                int[] xp = {x, y + compteur};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }

                    if (y - compteur >= 0) {
                        if (!lock7) {
                            if (fj.echiquier.cases[x][y - compteur].getPiece() != null) {
                                if (fj.echiquier.cases[x][y - compteur].getPiece().getCouleur() != tour) {
                                    int[] xp = {x, y - compteur};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                lock7 = true;
                            } else {
                                int[] xp = {x, y - compteur};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }
                    if (x - compteur >= 0) {
                        if (!lock8) {
                            if (fj.echiquier.cases[x - compteur][y].getPiece() != null) {
                                if (fj.echiquier.cases[x - compteur][y].getPiece().getCouleur() != tour) {
                                    int[] xp = {x - compteur, y};
                                    this.listOfPossibilities.add(xp);
                                    this.listOfCaptureMoves.add(xp);
                                }
                                lock8 = true;
                            } else {

                                int[] xp = {x - compteur, y};
                                this.listOfPossibilities.add(xp);
                            }
                        }
                    }

                }
        }

        return listOfPossibilities;

    }

    String RoqueType;

    public boolean isRoque(int x, int y, int newx, int newy, Couleur tour, FenetreJeu fj, Test test) {
        if (tour == Couleur.BLANC) {
            // Roque blanc
            if (newy == 6) {
                if (whiteKingFirstMove) {
                    if (!IntStream.of(this.whiteRookIds).anyMatch(k -> k == 7)) {
                        //  petit Roque
                        if (test.isMoveSafe(tour, x, y, 7, 6, fj)) {
                            if (test.isMoveSafe(tour, x, y, 7, 5, fj)) {
                                RoqueType = "Roque1";
                                return true;
                            }
                        }
                    }
                }
            } else if (newy == 2) {
                if (!IntStream.of(this.whiteRookIds).anyMatch(k -> k == 0)) {
                    // grand Roque
                    if (test.isMoveSafe(tour, x, y, 7, 2, fj)) {
                        if (test.isMoveSafe(tour, x, y, 7, 3, fj)) {
                            RoqueType = "Roque2";
                            return true;
                        }
                    }
                }
            }

        } else // Roque noir
        if (newy == 6) {
            if (blackKingFirstMove) {
                if (!IntStream.of(this.blackRookIds).anyMatch(k -> k == 7)) {
                    //  petit Roque
                    if (test.isMoveSafe(tour, x, y, 0, 6, fj)) {
                        if (test.isMoveSafe(tour, x, y, 0, 5, fj)) {
                            RoqueType = "Roque1";
                            return true;
                        }
                    }
                }
            }
        } else if (newy == 2) {
            if (!IntStream.of(this.blackRookIds).anyMatch(k -> k == 0)) {
                // grand Roque
                if (test.isMoveSafe(tour, x, y, 0, 2, fj)) {
                    if (test.isMoveSafe(tour, x, y, 0, 3, fj)) {
                        RoqueType = "Roque2";
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
